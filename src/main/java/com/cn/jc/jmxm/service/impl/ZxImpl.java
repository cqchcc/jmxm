package com.cn.jc.jmxm.service.impl;

import com.cn.jc.jmxm.entity.jc.JcZx;
import com.cn.jc.jmxm.entity.jc.JcZxXq;
import com.cn.jc.jmxm.mapper.JcZxMapper;
import com.cn.jc.jmxm.mapper.JcZxXqMapper;
import com.cn.jc.jmxm.service.ZxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ZxImpl implements ZxService {

    @Autowired
    private JcZxMapper jcZxMapper;
    @Autowired
    private JcZxXqMapper jcZxXqMapper;

    @Override
    public List<JcZx> selJcZx(String id,String flag) {
        List<JcZx> list = jcZxMapper.selJcZx(id,"2");
        return list;
    }

    @Override
    public List<JcZxXq> selZxXq(String zxId) {
        List<JcZxXq> list = jcZxXqMapper.selJcZxXq(zxId);
        return list;
    }

    @Override
    public void delZx(String zxId) {
        jcZxMapper.updJcZx("3",zxId);
    }

    @Override
    public void upZxBt(JcZx jcZx) {
        jcZxMapper.updateById(jcZx);
    }

    @Override
    public void upZxXq(JcZxXq jcZxXq) {
        jcZxXqMapper.updateById(jcZxXq);
    }
}
