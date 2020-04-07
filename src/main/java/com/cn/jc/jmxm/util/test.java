package com.cn.jc.jmxm.util;

import com.cn.jc.jmxm.service.XmService;
import com.cn.jc.jmxm.service.impl.XmImpl;

import java.util.concurrent.ThreadPoolExecutor;

public class test {
    public static void main(String[] args) {
        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getThreadPool();
        XmImpl xm = new XmImpl();
        threadPoolExecutor.execute( () -> xm.delXm(""));

    }
}
