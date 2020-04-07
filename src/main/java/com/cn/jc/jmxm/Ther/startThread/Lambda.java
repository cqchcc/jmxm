package com.cn.jc.jmxm.Ther.startThread;

/**
 * Lambda表达式创建线程方法
 */
public class Lambda {
    public static void main(String[] args) {
        new Thread(()-> System.out.println(Thread.currentThread().getName())).start();
    }
}
