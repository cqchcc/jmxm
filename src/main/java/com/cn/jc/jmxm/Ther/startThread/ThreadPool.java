package com.cn.jc.jmxm.Ther.startThread;


import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 线程池创建线程
 */
public class ThreadPool {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int a =0;a<1000;a++){
            executorService.submit(new Task() {
            });
        }
    }
}
class Task implements Runnable{
    @Override
    public void run(){
        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //打印线程的名字
        System.out.println(Thread.currentThread().getName());
    }
}