package com.cn.jc.jmxm.service.impl;

import com.cn.jc.jmxm.entity.jc.*;
import com.cn.jc.jmxm.entity.jc.dto.XmDto;
import com.cn.jc.jmxm.entity.jc.vo.JcTypeVo;
import com.cn.jc.jmxm.entity.jc.vo.XmVo;
import com.cn.jc.jmxm.mapper.*;
import com.cn.jc.jmxm.service.XmService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class XmImpl implements XmService {

    @Autowired
    private JcXqMapper jcXqMapper;
    @Autowired
    private JcTypeListMapper jcTypeListMapper;
    @Autowired
    private JcSeoMapper jcSeoMapper;
    @Autowired
    private JmYqMapper jmYqMapper;
    @Autowired
    private JmYsMapper jmYsMapper;

    @Override
    public List<JcXq> selJcXq(String typeId, String name) {
        //按条件查询
        return jcXqMapper.selJcxqTypeName(typeId, name);
    }

    @Override
    public void delXm(String xmId) {
        //不做物理删除，只做状态更新
        jcXqMapper.upJcxq("3", xmId);
    }

    @Override
    public XmVo getXqMx(String xmId) {
        XmVo xmVo = new XmVo();
        //详情
        JcXq jcXq = jcXqMapper.selectById(xmId);
        //seo
        JcSeo jcSeo = jcSeoMapper.selectById(xmId);
        //加盟要求
        JmYq jmYq = jmYqMapper.selectById(xmId);
        //加盟政策优势
        JmYs jmYs = jmYsMapper.selectById(xmId);

        BeanUtils.copyProperties(jcXq, xmVo);
        BeanUtils.copyProperties(jcSeo, xmVo);
        BeanUtils.copyProperties(jmYq, xmVo);
        BeanUtils.copyProperties(jmYs, xmVo);

        return xmVo;
    }

    @Override
    @Transactional
    public void updXqXm(XmDto xmDto) {
        JcXq jcXq = new JcXq();
        BeanUtils.copyProperties(xmDto,jcXq);
        jcXqMapper.updateById(jcXq);

        JcSeo jcSeo = new JcSeo();
        BeanUtils.copyProperties(xmDto,jcSeo);
        jcSeoMapper.updateById(jcSeo);

        JmYq jmYq = new JmYq();
        BeanUtils.copyProperties(xmDto,jmYq);
        jmYqMapper.updateById(jmYq);

        JmYs jmYs = new JmYs();
        BeanUtils.copyProperties(xmDto, jmYs);
        jmYsMapper.updateById(jmYs);
    }

    @Override
    public List<JcTypeVo> getXmType() {
        return jcTypeListMapper.selJcXqXqType();
    }

    @Override
    public List<JcTypeList> selJcTypeDl(){
        return jcTypeListMapper.selAll();

    }
}
