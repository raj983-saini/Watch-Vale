package com.example.Titan.utils.thirdPartyApi;

import ai.onnxruntime.*;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.nio.FloatBuffer;
import java.util.*;
import com.google.gson.*;

public class OnnxImageClassifier {

    private static final int IMG_HEIGHT = 224;
    private static final int IMG_WIDTH = 224;

    private static final float[] MEAN = {0.485f, 0.456f, 0.406f};
    private static final float[] STD = {0.229f, 0.224f, 0.225f};

    public List<String> classify(String imagePath, String modelPath, String labelPath) throws Exception {
        OrtEnvironment env = OrtEnvironment.getEnvironment();
        OrtSession session = env.createSession(modelPath, new OrtSession.SessionOptions());

        BufferedImage img = ImageIO.read(new File(imagePath));
        BufferedImage resized = new BufferedImage(IMG_WIDTH, IMG_HEIGHT, BufferedImage.TYPE_INT_RGB);
        resized.getGraphics().drawImage(img, 0, 0, IMG_WIDTH, IMG_HEIGHT, null);

        float[] inputData = new float[IMG_HEIGHT * IMG_WIDTH * 3];
        int idx = 0;
        for (int y = 0; y < IMG_HEIGHT; y++) {
            for (int x = 0; x < IMG_WIDTH; x++) {
                int rgb = resized.getRGB(x, y);
                float r = ((rgb >> 16) & 0xFF) / 255.0f;
                float g = ((rgb >> 8) & 0xFF) / 255.0f;
                float b = (rgb & 0xFF) / 255.0f;

                inputData[idx] = (r - MEAN[0]) / STD[0];
                inputData[idx + IMG_WIDTH * IMG_HEIGHT] = (g - MEAN[1]) / STD[1];
                inputData[idx + 2 * IMG_WIDTH * IMG_HEIGHT] = (b - MEAN[2]) / STD[2];

                idx++;
            }
        }

        long[] shape = {1, 3, IMG_HEIGHT, IMG_WIDTH}; // NCHW
        OnnxTensor inputTensor = OnnxTensor.createTensor(env, FloatBuffer.wrap(inputData), shape);

        Map<String, OnnxTensor> inputs = new HashMap<>();
        inputs.put(session.getInputNames().iterator().next(), inputTensor);

        OrtSession.Result results = session.run(inputs);

        // ✅ FIX: cast to float[][] then get the first row
        float[][] logits2D = (float[][]) results.get(0).getValue();
        float[] logits = logits2D[0];

        // Load labels
        JsonArray labelsJson = JsonParser.parseReader(new FileReader(labelPath)).getAsJsonArray();
        List<String> labels = new ArrayList<>();
        for (JsonElement el : labelsJson) {
            labels.add(el.getAsString());
        }

        // Get top 3 predictions
        int[] topIndices = getTopIndices(logits, 3);
        List<String> topTags = new ArrayList<>();
        for (int i : topIndices) {
            topTags.add(labels.get(i));
        }

        session.close();
        env.close();
        return topTags;
    }


    private int[] getTopIndices(float[] arr, int topN) {
        PriorityQueue<Integer> pq = new PriorityQueue<>(Comparator.comparingDouble(i -> -arr[i]));
        for (int i = 0; i < arr.length; i++) {
            pq.add(i);
        }
        int[] top = new int[topN];
        for (int i = 0; i < topN; i++) {
            top[i] = pq.poll();
        }
        return top;
    }
}
