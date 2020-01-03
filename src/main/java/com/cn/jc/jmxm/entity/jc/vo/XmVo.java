package com.cn.jc.jmxm.entity.jc.vo;

import com.cn.jc.jmxm.entity.jc.JcXq;
import lombok.Data;

@Data
public class XmVo extends JcXq {
    private String xqTitle;
    private String xqMetaKeywords;
    private String xqMetaDescription;
    private String jmJmyq;
    private String jmZcys;
}
