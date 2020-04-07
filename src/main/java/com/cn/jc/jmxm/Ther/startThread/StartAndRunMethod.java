package com.cn.jc.jmxm.Ther.startThread;

import org.omg.PortableServer.THREAD_POLICY_ID;

/**
 * start()和run()两种启动线程的方式
 */
public class StartAndRunMethod {
    public static void main(String[] args) {
       Runnable runnable =()->{
           System.out.println(Thread.currentThread().getName());
       };
       runnable.run();
       new Thread(runnable).start();
    }
}
