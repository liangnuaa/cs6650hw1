package com.hw1;

/**
 * Created by liangzhang on 10/2/18.
 */
public class Main {
    private static final int DEFAULT_MAX_THREAD_NUM = 50;
    private static final int DEFAULT_MAX_ITERATION_NUM = 100;

    public static void main(String[] args) {
        if (args == null || args.length != 4) {
            throw new IllegalArgumentException("The input command is invalid");
        }

        int maxNum = args[0].length() == 0 || Integer.parseInt(args[0]) == 0? DEFAULT_MAX_THREAD_NUM : Integer.parseInt(args[0]);
        int maxIteration = args[1].length() == 0 || Integer.parseInt(args[1]) == 0? DEFAULT_MAX_ITERATION_NUM : Integer.parseInt(args[1]);
        String ip = args[2];
        String port = args[3];

//        String url = "http://" + ip + ":" + port + "/WebApp_Web/api/hello";
        String url = ip;


        Thread thread1 = new Thread(new ThreadServer(maxNum, maxIteration, url));
        thread1.start();
    }
}
