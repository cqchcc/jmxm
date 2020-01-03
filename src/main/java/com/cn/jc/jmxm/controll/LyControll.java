package com.cn.jc.jmxm.controll;

import com.cn.jc.jmxm.comm.config.FendaResponse;
import com.cn.jc.jmxm.entity.jc.JcLiuYan;
import com.cn.jc.jmxm.service.LyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/ly")
public class LyControll {
    @Autowired
    private LyService lyService;

    @PostMapping("/ins_ly")
    public FendaResponse InsLy(@RequestBody JcLiuYan jcLiuYan, HttpServletRequest httpServletRequest) {
        try {
            if (jcLiuYan.getUserTel() == null) {
                return new FendaResponse().message("ERROR").code(500).date("电话号码不能为空");
            }
            if (jcLiuYan.getUserName() == null){
                return new FendaResponse().message("ERROR").code(500).date("联系人不能为空");
            }
            lyService.insMessageWait(jcLiuYan,httpServletRequest);
            return new FendaResponse().message("SUCCESS").code(200);
        } catch (Exception e) {
            log.error("新增留言报错", e);
            return new FendaResponse().message("ERROR").code(500);
        }
    }
}
