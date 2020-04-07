package com.cn.jc.jmxm.service;

import com.cn.jc.jmxm.entity.jc.JcTypeList;
import com.cn.jc.jmxm.entity.jc.JcXq;

public interface JcService {

    void getData(JcTypeList jcType);
    void getZxData(JcXq jcxq);
    void getZxDataXq();
}
