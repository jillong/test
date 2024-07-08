package com.example.springai.utils;

import org.springframework.stereotype.Component;

@Component
public class IncrementalIdGenerator {

    private static final long twepoch = 1613478270144L; // 设置一个固定的起始时间戳
    private static final long workerIdBits = 5L; // 机器id位数
    private static final long dataCenterIdBits = 5L; // 数据中心id位数
    private static final long maxWorkerId = ~(-1L << workerIdBits); // 最大机器id
    private static final long maxDataCenterId = ~(-1L << dataCenterIdBits); // 最大数据中心id
    private static final long sequenceBits = 12L; // 序列号位数
    private static final long workerIdShift = sequenceBits; // 机器id左移位数
    private static final long dataCenterIdShift = sequenceBits + workerIdBits; // 数据中心id左移位数
    private static final long timestampLeftShift = sequenceBits + workerIdBits + dataCenterIdBits; // 时间戳左移位数
    private static final long sequenceMask = ~(-1L << sequenceBits); // 序列号掩码

    private final long workerId;
    private final long dataCenterId;
    private long sequence = 0L;
    private long lastTimestamp = -1L;

    public IncrementalIdGenerator() {
        this.workerId = 0;
        this.dataCenterId = 0;
    }

    public IncrementalIdGenerator(long workerId, long dataCenterId) {
        if (workerId > maxWorkerId || workerId < 0) {
            throw new IllegalArgumentException(
                    String.format("WorkerId can't be greater than %d or less than 0", maxWorkerId));
        }
        if (dataCenterId > maxDataCenterId || dataCenterId < 0) {
            throw new IllegalArgumentException(
                    String.format("DataCenterId can't be greater than %d or less than 0", maxDataCenterId));
        }
        this.workerId = workerId;
        this.dataCenterId = dataCenterId;
    }

    public synchronized String nextId() {
        long timestamp = System.currentTimeMillis();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id for "
                    + (lastTimestamp - timestamp) + " milliseconds");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & sequenceMask;
            if (sequence == 0) {
                timestamp = tilNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        long id = ((timestamp - twepoch) << timestampLeftShift)
                | (dataCenterId << dataCenterIdShift)
                | (workerId << workerIdShift)
                | sequence;

        return String.format("%019d", id); // 将long类型的id格式化为19位长度的数字字符串
    }

    private long tilNextMillis(long lastTimestamp) {
        long timestamp = System.currentTimeMillis();
        while (timestamp <= lastTimestamp) {
            timestamp = System.currentTimeMillis();
        }
        return timestamp;
    }
}

