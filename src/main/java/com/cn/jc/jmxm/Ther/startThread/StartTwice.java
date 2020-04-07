package com.cn.jc.jmxm.Ther.startThread;

public class StartTwice {
    public static void main(String[] args) {
        Thread thread = new Thread();
        thread.start();
        thread.start();
        //Exception in thread "main" java.lang.IllegalThreadStateException

    }
}
