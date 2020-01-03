package com.cn.jc.jmxm.controll;

import com.cn.jc.jmxm.comm.config.FendaResponse;
import com.cn.jc.jmxm.service.JcService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/jc")
public class JcControll {
    @Autowired
    private JcService jcService;

    /**
     * 获取项目
     * @return
     */
    @PostMapping("/get_data")
    public FendaResponse logs() {
        jcService.getData();
        return new FendaResponse().message("SUCCESS");
    }

    /**
     * 获取咨询列表
     * @return
     */
    @PostMapping("/get_zx_data")
    public FendaResponse getZxData(){
        jcService.getZxData();
        return new FendaResponse().message("SUCCESS");
    }

    @PostMapping("/get_zx_data_xq")
    public FendaResponse getZxDataXq(){
        jcService.getZxDataXq();
        return new FendaResponse().message("SUCCESS");
    }

}
