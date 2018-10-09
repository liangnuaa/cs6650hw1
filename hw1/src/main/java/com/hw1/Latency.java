package com.hw1;

import java.util.Comparator;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

/**
 * Created by liangzhang on 10/8/18.
 */
public class Latency {
    private long sumLatencies;
    private long medianLatency;
    private long meanLatency;
    private long nintyNinePercentile;
    private long nintyFivePercentile;
    private PriorityQueue<Long> queue = new PriorityQueue<Long>(new Comparator<Long>() {
        public int compare(Long o1, Long o2) {
            return (int)(o2 - o1);
        }
    });

    public Latency(List<Long> latencyList) {
        for (Long latency : latencyList) {
            queue.offer(latency);
        }
//        for (int i = 0; i < latencyList.size(); i++) {
//            queue.add(latencyList.get(i));
//        }
    }

    public void compute() {
        int size = queue.size();
        int medianTotal = size / 2;

        for (int i = 1; i <= size; i++) {
            long cur = queue.poll();
            if (i == (int) (size * 0.01)) {
                nintyNinePercentile = cur;
            } else if (i == (int) (size * 0.05)) {
                nintyFivePercentile = cur;
            } else if (i == medianTotal) {
                medianLatency = cur;
            }
            sumLatencies += cur;
        }
        meanLatency = sumLatencies / size;
    }

    public long getSumLatencies() {
        return sumLatencies;
    }

    public long getMedianLatency() {
        return medianLatency;
    }

    public long getMeanLatency() {
        return meanLatency;
    }

    public long getNintyNinePercentile() {
        return nintyNinePercentile;
    }

    public long getNintyFivePercentile() {
        return nintyFivePercentile;
    }
}
