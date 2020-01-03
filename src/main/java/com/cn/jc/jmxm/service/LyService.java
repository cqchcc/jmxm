package com.cn.jc.jmxm.service;

import com.cn.jc.jmxm.entity.jc.JcLiuYan;


import javax.servlet.http.HttpServletRequest;

public interface LyService {
    void insMessageWait(JcLiuYan liuYan, HttpServletRequest httpServletRequest);
}
