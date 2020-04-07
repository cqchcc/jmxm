package com.cn.jc.jmxm.Ther.startThread;

/**
 * 用Thread方式实现线程
 */
public class ThreadStyle  extends Thread{
    public static void main(String[] args) {
        new ThreadStyle().run();
    }
    @Override
    public void run() {
        System.out.println("使用Thread方式实现线程");
    }
}
