package com.cn.jc.jmxm.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.jc.jmxm.entity.jc.JcTypeList;

import com.cn.jc.jmxm.entity.jc.vo.JcTypeVo;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface JcTypeListMapper extends BaseMapper<JcTypeList> {

    @Select("SELECT * FROM jc_type_list WHERE flag=0")
    List<JcTypeList> selJcTypeListFlag();

    @Update("UPDATE jc_type_list set flag=1 where id=#{id}")
    void updJcTypeListFlag(@Param("id") Long id);

    @Select("SELECT id,name jc_type_list where flag=1")
    List<JcTypeVo> selJcXqXqType();
}
