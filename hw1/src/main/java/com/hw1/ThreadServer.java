package com.hw1;

import java.sql.Timestamp;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * Created by liangzhang on 10/7/18.
 */
public class ThreadServer implements Runnable {
    private int threadNum;
    private int iterationNum;
    private int requestNum;
    private int responseNum;
    private WebClient client;
    private List<Long> latencyList;

    public ThreadServer(int threadNum, int iterationNum, String url){
        this.threadNum = threadNum;
        this.iterationNum = iterationNum;
        client = new WebClient(url);
        this.latencyList = new LinkedList<Long>();
    }

    public synchronized void addLatency(long latency) {
        latencyList.add(latency);
    }

    public void run() {
        Timestamp startTime = new Timestamp(System.currentTimeMillis());

        phase("Warmup");
        phase("Loading");
        phase("Peak");
        phase("Cooldown");

        Timestamp endTime = new Timestamp(System.currentTimeMillis());
        double wallTime = (endTime.getTime() - startTime.getTime()) / 1000.0;
        int throughput = (int)(requestNum / wallTime);
        Latency latency = new Latency(this.latencyList);
        latency.compute();

        System.out.println("===================================================");
        System.out.println("Total number of requests sent: " + requestNum);
        System.out.println("Total number of Successful responses: " + responseNum);
        System.out.println("Test Wall Time: " + wallTime+ " seconds");
        System.out.println("Overall throughput across all phases: " + throughput);
        System.out.println("Mean latency is: " + latency.getMeanLatency() / 1000.0 + " seconds. "
                + "Median latency is: " + latency.getMedianLatency() / 1000.0 + " seconds.");
        System.out.println("99th percentile latency is: " + latency.getNintyNinePercentile() / 1000.0 + " seconds. "
                + "95th percentile latency is: " + latency.getNintyFivePercentile() / 1000.0 + " seconds.");

    }

    private void phase(String phase){
        int threads = this.threadNum;
        if (phase.equals("Warmup")) {
            threads /= 10;
        }else if (phase.equals("Loading")) {
            threads /= 2;
        }else if (phase.equals("Cooldown")) {
            threads /= 4;
        }

        Timestamp startTime = new Timestamp(System.currentTimeMillis());
        System.out.println("Client starting …..Time: " + startTime);
        System.out.println(phase + " phase: All threads running ….");

        final CountDownLatch latch = new CountDownLatch(threads);

        try {
            for (int i = 0; i < threads; i++) {
                new Thread(new Runnable() {
                    public void run() {
                        for (int j = 0; j < iterationNum; j++) {
                            Timestamp startTime1 = new Timestamp(System.currentTimeMillis());
                            String status = client.getStatus();
                            Timestamp endTime1 = new Timestamp(System.currentTimeMillis());

                            Timestamp startTime2 = new Timestamp(System.currentTimeMillis());
                            String postLen = client.postText("abc", String.class);
                            Timestamp endTime2 = new Timestamp(System.currentTimeMillis());

                            requestNum++;
                            requestNum++;

                            if (status.equals("successfully deployed")) {
                                responseNum++;
                                addLatency(endTime1.getTime() - startTime1.getTime());
                            }
                            if (postLen.equals("3")) {
                                responseNum++;
                                addLatency(endTime2.getTime() - startTime2.getTime());
                            }
                        }
                        latch.countDown();
                    }
                }).start();
            }
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Timestamp endTimestamp = new Timestamp(System.currentTimeMillis());
        System.out.println(phase + " complete: Time " +
                (endTimestamp.getTime() - startTime.getTime()) / 1000.0 + " seconds");
    }
}
