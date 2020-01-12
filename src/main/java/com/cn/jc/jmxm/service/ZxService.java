package com.cn.jc.jmxm.service;

import com.cn.jc.jmxm.entity.jc.JcXq;
import com.cn.jc.jmxm.entity.jc.JcZx;
import com.cn.jc.jmxm.entity.jc.JcZxXq;
import com.cn.jc.jmxm.entity.jc.dto.XmDto;
import com.cn.jc.jmxm.entity.jc.vo.JcTypeVo;
import com.cn.jc.jmxm.entity.jc.vo.XmVo;

import java.util.List;

public interface ZxService {

    List<JcZx> selJcZx(String id,String flag);
    List<JcZxXq> selZxXq(String zxId);
    void delZx(String zxId);
    void upZxBt(JcZx jcZx);
    void upZxXq(JcZxXq jcZxXq);
}
