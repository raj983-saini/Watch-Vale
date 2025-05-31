package com.example.Titan.utils;

import org.springframework.stereotype.Service;

@Service
public class UniqueIdGenerationUtil {
    private static final long EPOCH = 1672531200L; // Custom epoch (2023-01-01)
    private static final long WORKER_ID_BITS = 3L;
    private static final long DATA_CENTER_ID_BITS = 2L;
    private static final long SEQUENCE_BITS = 6L;

    private static final long MAX_WORKER_ID = ~(-1L << WORKER_ID_BITS);
    private static final long MAX_DATA_CENTER_ID = ~(-1L << DATA_CENTER_ID_BITS);
    private static final long SEQUENCE_MASK = ~(-1L << SEQUENCE_BITS);

    private final long workerId;
    private final long dataCenterId;

    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public UniqueIdGenerationUtil() {
        this.workerId = 0L;
        this.dataCenterId = 0L;
    }

    public UniqueIdGenerationUtil(long workerId, long dataCenterId) {
        if (workerId > MAX_WORKER_ID || workerId < 0) {
            throw new IllegalArgumentException("Worker ID can't be greater than " + MAX_WORKER_ID + " or less than 0");
        }
        if (dataCenterId > MAX_DATA_CENTER_ID || dataCenterId < 0) {
            throw new IllegalArgumentException("Data Center ID can't be greater than " + MAX_DATA_CENTER_ID + " or less than 0");
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized String generateId() {
        long timestamp = currentTimestamp();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate ID.");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & SEQUENCE_MASK;
            if (sequence == 0) {
                timestamp = waitForNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        var identifier =  ((timestamp - EPOCH) << (WORKER_ID_BITS + DATA_CENTER_ID_BITS + SEQUENCE_BITS))
                | (dataCenterId << (WORKER_ID_BITS + SEQUENCE_BITS))
                | (workerId << SEQUENCE_BITS)
                | sequence;
        return "OD" + identifier;
    }

    private long waitForNextMillis(long lastTimestamp) {
        long timestamp = currentTimestamp();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTimestamp();
        }
        return timestamp;
    }

    private long currentTimestamp() {
        return System.currentTimeMillis();
    }
}

