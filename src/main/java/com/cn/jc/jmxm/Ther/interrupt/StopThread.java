package com.cn.jc.jmxm.Ther.interrupt;

/**
 * 使用interrupt停止线程
 */
public class StopThread implements  Runnable{


    @Override
    public void run() {
       int num = 0;
       while (num <=Integer.MAX_VALUE/2){
           if (!Thread.currentThread().isInterrupted() && num%10000 == 0){
               System.out.println(Thread.currentThread().isInterrupted());
               System.out.println("这个数是一万的倍数"+num);
           }
           num++;
       }
    }

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new StopThread());
        thread.start();
        thread.sleep(1000);
        //thread.interrupt();
    }
}
