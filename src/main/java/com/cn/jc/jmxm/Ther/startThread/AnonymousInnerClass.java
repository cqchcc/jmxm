package com.cn.jc.jmxm.Ther.startThread;

/**
 * 匿名内部类实现线程
 */
public class AnonymousInnerClass {
    public static void main(String[] args) {
        new Thread(){
            @Override
            public void run(){
                System.out.println(Thread.currentThread().getName());
            }
        }.start();
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println(Thread.currentThread().getName());
            }
        }).start();
    }
}
