package com.cn.jc.jmxm.service.impl;

import com.cn.jc.jmxm.entity.jc.JcTypeList;

import com.cn.jc.jmxm.entity.jc.JcXq;
import com.cn.jc.jmxm.mapper.JcTypeListMapper;
import com.cn.jc.jmxm.mapper.JcXqMapper;
import com.cn.jc.jmxm.mapper.JcZxXqMapper;
import com.cn.jc.jmxm.service.JcService;
import com.cn.jc.jmxm.service.ThreadPoolService;
import com.cn.jc.jmxm.util.ThreadPoolUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;

@Service
public class ThreadPoolImlp implements ThreadPoolService {
    @Autowired
    private JcTypeListMapper jcTypeListMapper;
    @Autowired
    private JcService jcService;
    @Autowired
    private JcXqMapper jcXqMapper;


    /**
     * 爬取项目
     */
    @Override
    public void threadPoll() {
        //获取全部待执行的项目

        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getThreadPool();
        List<JcTypeList> jcTypeList = jcTypeListMapper.selJcTypeListFlag();
        jcTypeList.forEach((JcTypeList type) -> {
            threadPoolExecutor.execute(() -> jcService.getData(type));
        });
    }

    @Override
    public void threadPoolGetJcZx(){

        ThreadPoolExecutor threadPoolExecutor = ThreadPoolUtil.getThreadPool();
        List<JcXq> jcXqList = jcXqMapper.selJcXq();
        jcXqList.forEach((JcXq jcXq)->{
            threadPoolExecutor.execute(()->jcService.getZxData(jcXq));

        });

    }

}






