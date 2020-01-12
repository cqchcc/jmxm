package com.cn.jc.jmxm.service;

import com.cn.jc.jmxm.entity.jc.JcTypeList;
import com.cn.jc.jmxm.entity.jc.JcXq;
import com.cn.jc.jmxm.entity.jc.JcXq;
import com.cn.jc.jmxm.entity.jc.dto.XmDto;
import com.cn.jc.jmxm.entity.jc.vo.JcTypeVo;
import com.cn.jc.jmxm.entity.jc.vo.XmVo;

import java.util.List;

public interface XmService {

    List<JcXq> selJcXq(String typeId, String name);
    void delXm(String xmId);
    XmVo getXqMx(String xmId);
    void updXqXm(XmDto xmDto);
    List<JcTypeVo> getXmType();

    List<JcTypeList> selJcTypeDl();
}
