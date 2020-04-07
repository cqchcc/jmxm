package com.cn.jc.jmxm.Ther.startThread;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 定时器的方式创建线程
 */
public class TimmerTask {

    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        },1000,1000);
    }

}
