package com.cn.jc.jmxm.Ther.threadobjectclasscommonmethods;

/**
 * 描述：     演示join，注意语句输出顺序，会变化。
 */
public class Join {
    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "执行完毕");
            }
        });
        Thread thread2 = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(Thread.currentThread().getName() + "执行完毕");
            }
        });

        thread.start();
        thread2.start();
        System.out.println("开始等待子线程运行完毕");
        thread.join();
        thread2.join();
        System.out.println("所有子线程执行完毕");
    }

    /**
     * 描述：     3个线程，线程1和线程2首先被阻塞，线程3唤醒它们。notify, notifyAll。 start先执行不代表线程先启动。
     */
    public static class WaitNotifyAll implements Runnable {

        private static final Object resourceA = new Object();


        public static void main(String[] args) throws InterruptedException {
            Runnable r = new WaitNotifyAll();
            Thread threadA = new Thread(r);
            Thread threadB = new Thread(r);
            Thread threadC = new Thread(new Runnable() {
                @Override
                public void run() {
                    synchronized (resourceA) {
                        resourceA.notifyAll();
    //                    resourceA.notify();
                        System.out.println("ThreadC notified.");
                    }
                }
            });
            threadA.start();
            threadB.start();
    //        Thread.sleep(200);
            threadC.start();
        }
        @Override
        public void run() {
            synchronized (resourceA) {
                System.out.println(Thread.currentThread().getName()+" got resourceA lock.");
                try {
                    System.out.println(Thread.currentThread().getName()+" waits to start.");
                    resourceA.wait();
                    System.out.println(Thread.currentThread().getName()+"'s waiting to end.");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
