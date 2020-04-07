package com.cn.jc.jmxm.Ther.startThread;

/**
 * 用Runnbale创建线程
 */
public class RunnableStyle implements Runnable {
    public static void main(String[] args) {
    Thread thread = new Thread(new RunnableStyle());
    thread.start();
    }

    @Override
    public void run() {
        System.out.println("用Runnable方法实现线程");
    }
}
