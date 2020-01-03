package com.cn.jc.jmxm.service.impl;

import com.cn.jc.jmxm.entity.jc.JcLiuYan;
import com.cn.jc.jmxm.mapper.JcLiuYanMapper;
import com.cn.jc.jmxm.service.LyService;
import com.cn.jc.jmxm.util.CusAccessObjectUtil;
import com.cn.jc.jmxm.util.OpenHttps;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Slf4j
@Service
public class LyImpl implements LyService {


    @Autowired
    private JcLiuYanMapper jcLiuYanMapper;

    @Override
    public void insMessageWait(JcLiuYan liuYan, HttpServletRequest httpServletRequest) {
        //拿到用户的真是IP地址
        String ip;
        try {
            ip = CusAccessObjectUtil.getIpAddress(httpServletRequest);
        } catch (Exception e) {
            log.error("获取真实IP地址异常，保存http中的ip地址");
            ip = httpServletRequest.getRemoteAddr();
        }
        liuYan.setIpAddress(ip);
        //获取手机归属地
        String telAddress;
        try {
            Map<String, String> map = OpenHttps.getTelAddress(liuYan.getUserTel());
            telAddress = map.get("province");
        } catch (Exception e) {
            log.error("电话号码归属地查询失败");
            telAddress = "默认";
        }
        liuYan.setTelAddress(telAddress);
        liuYan.setPcData("2");//爬下来的数据标识；1为爬下来的数据，2为真实留言
        liuYan.setFlag("1");//留言状态1为正常。2为删除
        jcLiuYanMapper.insert(liuYan);
    }


}
