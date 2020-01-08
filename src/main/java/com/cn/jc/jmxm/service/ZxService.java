package com.cn.jc.jmxm.service;

import com.cn.jc.jmxm.entity.jc.JcXq;
import com.cn.jc.jmxm.entity.jc.JcZx;
import com.cn.jc.jmxm.entity.jc.dto.XmDto;
import com.cn.jc.jmxm.entity.jc.vo.JcTypeVo;
import com.cn.jc.jmxm.entity.jc.vo.XmVo;

import java.util.List;

public interface ZxService {

    List<JcZx> selJcZx(String typeId, String name);
}
