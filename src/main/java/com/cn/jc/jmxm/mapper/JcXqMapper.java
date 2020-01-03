package com.cn.jc.jmxm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.jc.jmxm.entity.jc.JcXq;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

public interface JcXqMapper extends BaseMapper<JcXq> {
    @Select("SELECT * FROM jc_xq where zx_flag=0")
    List<JcXq> selJcXq();

    @Update("UPDATE jc_xq SET xq_flag=#{flag} WHERE xq_id=#{xqId}")
    void upJcxq(@Param("flag") String flag, @Param("xqId") String xqId);

    @Select("<script>" +
            "SELECT * FROM jc_xq  where xq_flag !=3 " +
            "<if test='typeId!=null'> and xq_type=#{typeId} </if>" +
            "<if test='name!=null'> and xq_xmmc like CONCAT('%',#{name},'%') </if>" +
            "</script> ")
    List<JcXq> selJcxqTypeName(@Param("typeId") String typeId, @Param("name") String name);

    @Update("UPDATE jc_xq SET zx_flag=1 WHERE xq_id=#{xqId}")
    void upJcZxFlag(@Param("xqId") String xqId);
}
