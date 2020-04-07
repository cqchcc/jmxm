package com.cn.jc.jmxm.controll;

import com.cn.jc.jmxm.comm.config.FendaResponse;
import com.cn.jc.jmxm.service.JcService;

import com.cn.jc.jmxm.service.ThreadPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jc")
public class JcControll {
    @Autowired
    private JcService jcService;
    @Autowired
    private ThreadPoolService threadPoolService;

    /**
     * 抓取项目
     * @return
     */
    @PostMapping("/get_data")
    public FendaResponse logs() {
        threadPoolService.threadPoll();
        return new FendaResponse().message("SUCCESS");
    }

    /**
     * 获取咨询列表
     * @return
     */
    @PostMapping("/get_zx_data")
    public FendaResponse getZxData(){
        threadPoolService.threadPoolGetJcZx();
        return new FendaResponse().message("SUCCESS");
    }

    /**
     * 获取咨询详情
     * @return
     */
    @PostMapping("/get_zx_data_xq")
    public FendaResponse getZxDataXq(){
        jcService.getZxDataXq();
        return new FendaResponse().message("SUCCESS");
    }

}
