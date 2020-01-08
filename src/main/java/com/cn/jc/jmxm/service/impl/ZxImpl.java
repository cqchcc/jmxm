package com.cn.jc.jmxm.service.impl;

import com.cn.jc.jmxm.entity.jc.JcZx;
import com.cn.jc.jmxm.mapper.JcZxMapper;
import com.cn.jc.jmxm.service.ZxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZxImpl implements ZxService {

    @Autowired
    private JcZxMapper jcZxMapper;

    @Override
    public List<JcZx> selJcZx(String typeId, String name) {


        return null;
    }
}
