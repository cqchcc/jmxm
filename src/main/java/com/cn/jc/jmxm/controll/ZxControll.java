package com.cn.jc.jmxm.controll;


import com.cn.jc.jmxm.comm.config.FendaResponse;
import com.cn.jc.jmxm.entity.jc.JcXq;
import com.cn.jc.jmxm.entity.jc.JcZx;
import com.cn.jc.jmxm.service.ZxService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/xm")
public class ZxControll {

    @Autowired
    private ZxService zxService;

    /**
     * 查询资讯列表
     *
     * @param typeId 大类ID
     * @param name   资讯名称
     * @return
     */
    @GetMapping("/get_xm_xq_all")
    public FendaResponse getZxAll(@RequestParam(value = "typeId", required = false) String typeId, @RequestParam(value = "name", required = false) String name) {
        try {
            List<JcZx> list = zxService.selJcZx(typeId,name);
            return new FendaResponse().message("SUCCESS").code(200).date(list);
        } catch (Exception e) {
            log.error("查询资讯列表异常", e);
            return new FendaResponse().message("ERROR").code(500);
        }

    }
}
