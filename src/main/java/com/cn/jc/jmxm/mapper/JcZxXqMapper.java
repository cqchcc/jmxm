package com.cn.jc.jmxm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.jc.jmxm.entity.jc.JcZxXq;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;


public interface JcZxXqMapper extends BaseMapper<JcZxXq> {
    @Select("SELECT * FRPM jc_zx_xq where zx_id#{zxId}")
    List<JcZxXq> selJcZxXq(@Param("zxId")String zxId);
}
