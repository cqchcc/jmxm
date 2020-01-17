package com.cn.jc.jmxm.mapper;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.cn.jc.jmxm.entity.jc.JcXq;
import com.cn.jc.jmxm.entity.jc.JcZx;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
@Mapper
public interface JcZxMapper  extends BaseMapper<JcZx>{

    @Select("SELECT count(*) from jc_zx where zx_bt=#{zxBt} and xq_url=#{xqUrl} ")
    int selXqUrl(@Param("zxBt") String zxBt, @Param("xqUrl") String xqUrl);

    @Select("SELECT * FROM jc_zx WHERE xq_flag=0 limit 100")
    List<JcZx> selJcZxs();

    @Update("update jc_zx set xq_flag=#{flag} where zx_id=#{zxId}")
    void updJcZx(@Param("flag") String flag,@Param("zxId") String zxId);

    @Select("SELECT count(*) FROM jc_zx WHERE xq_flag=0")
    int countJcZx();

    @Select("SELECT * FROM jc_zx WHERE xm_id=#{xmId} and flag=#{flag} ")
    List<JcZx> selJcZx(@Param("xmId")String xmId ,@Param("flag")String flag);

    @Select("<script>" +
            "SELECT * FROM jc_xq  where xq_flag !=3 " +
            "<if test='typeId!=null'> and xq_type=#{typeId} </if>" +
            "<if test='name!=null'> and xq_xmmc like CONCAT('%',#{name},'%') </if>" +
            "</script> ")
    List<JcZx> selJcxqTypeName(@Param("typeId") String typeId, @Param("name") String name);

}
