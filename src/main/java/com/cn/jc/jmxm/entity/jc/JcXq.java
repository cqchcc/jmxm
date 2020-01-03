package com.cn.jc.jmxm.entity.jc;

import lombok.Data;

@Data
public class JcXq {

  private String xqId;//项目ID
  private String xqXmmc;//项目名称
  private String xqJbtz;//基本投资
  private String xqMdsl;//门店数量
  private String xqPpmc;//品牌名称
  private String xqClrq;//创立日期
  private String xqJmqy;//加盟区域
  private String xqShrq;//适合人群
  private String xqType;//所属行业
  private String xqImgs;//项目图片
  private String xqFlag;//详情状态 发布状态。1为草稿，2为发布，3为删除
  private String xqDate;//记录上次修改时间
  private String xqUrl;//详细url用于后期爬新闻


}
