package com.example.Titan.interceptor;



import com.example.Titan.expections.CustomException;
import jakarta.servlet.ReadListener;
import jakarta.servlet.ServletInputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletRequestWrapper;
import org.springframework.http.HttpStatus;

import java.io.*;

public class InterceptorRequestWrapper extends HttpServletRequestWrapper {
	private final String body;

	public InterceptorRequestWrapper(HttpServletRequest request) throws IOException, CustomException {
		super(request);
		body = readBody(request);
	}

	public InterceptorRequestWrapper(HttpServletRequest request, String requestBody) throws IOException {
		super(request);
		body = requestBody;
	}

	@Override
	public ServletInputStream getInputStream() throws IOException {
		final ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(
				body.getBytes());
		ServletInputStream servletInputStream = new ServletInputStream() {
			@Override
			public boolean isFinished() {
				return byteArrayInputStream.available() == 0;
			}

			@Override
			public boolean isReady() {
				return true;
			}

			@Override
			public void setReadListener(ReadListener readListener) {
			}

			@Override
			public int read() throws IOException {
				return byteArrayInputStream.read();
			}

		};
		return servletInputStream;
	}

	@Override
	public BufferedReader getReader() throws IOException {
		return new BufferedReader(new InputStreamReader(this.getInputStream()));
	}

	public String getBody() {
		return this.body;
	}

	private String readBody(HttpServletRequest request) throws IOException, CustomException {
		String body = null;
		StringBuilder stringBuilder = new StringBuilder();
		BufferedReader bufferedReader = null;

		try {
			InputStream inputStream = request.getInputStream();
			if (inputStream != null) {
				bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
				char[] charBuffer = new char[128];
				int bytesRead = -1;
				while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
					stringBuilder.append(charBuffer, 0, bytesRead);
				}
			} else {
				stringBuilder.append("");
			}
		} catch (IOException ex) {
			throw new CustomException(HttpStatus.BAD_REQUEST,"Something went wrong");
		} finally {
			if (bufferedReader != null) {
				bufferedReader.close();
			}
		}

		body = stringBuilder.toString();
		return body;
	}
}