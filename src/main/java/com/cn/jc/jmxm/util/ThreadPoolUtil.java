package com.cn.jc.jmxm.util;

import java.util.concurrent.*;

public class ThreadPoolUtil {

    private volatile static  ThreadPoolExecutor threadPoolExecutor = null;
    //先私有化构造方法
    private ThreadPoolUtil() {
    }

    public static ThreadPoolExecutor getThreadPool(){

        if (null == threadPoolExecutor){
            synchronized (ThreadPoolUtil.class){
                if (null == threadPoolExecutor){
                    threadPoolExecutor = new ThreadPoolExecutor(20, 30,
                            10 * 60, TimeUnit.SECONDS, new LinkedBlockingDeque<>());
                }
            }
        }

        return threadPoolExecutor;
    }
}
