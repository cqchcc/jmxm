package com.cn.jc.jmxm.controll;


import com.cn.jc.jmxm.comm.config.FendaResponse;
import com.cn.jc.jmxm.entity.jc.JcTypeList;
import com.cn.jc.jmxm.entity.jc.JcXq;
import com.cn.jc.jmxm.entity.jc.dto.XmDto;
import com.cn.jc.jmxm.entity.jc.vo.JcTypeVo;
import com.cn.jc.jmxm.entity.jc.vo.XmVo;
import com.cn.jc.jmxm.service.XmService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/xm")
public class XmControll {

    @Autowired
    private XmService xmService;

    /**
     * 查询项目列表
     *
     * @param typeId 大类ID
     * @param name   项目名称 是模糊匹配的
     * @return
     */
    @GetMapping("/get_xm_xq_all")
    public FendaResponse getXmXqAll(@RequestParam(value = "typeId", required = false) String typeId, @RequestParam(value = "name", required = false) String name) {
        try {
            List<JcXq> list = xmService.selJcXq(typeId, name);
            return new FendaResponse().message("SUCCESS").code(200).date(list);
        } catch (Exception e) {
            log.error("查询项目列表异常", e);
            return new FendaResponse().message("ERROR").code(500);
        }

    }


    /**
     * 删除项目
     *
     * @param xmId
     * @return
     */
    @PostMapping("/del_xm")
    public FendaResponse delXm(@RequestParam("xmId") String xmId) {
        try {
            xmService.delXm(xmId);
            return new FendaResponse().message("SUCCESS").code(200);
        } catch (Exception e) {
            log.error("删除项目异常", e);
            return new FendaResponse().message("ERROR").code(500);
        }

    }

    /**
     * 项目ID单条精确查询所有信息
     *
     * @param xmId
     * @return
     */
    @PostMapping("/get_xm_xq")
    public FendaResponse getXmxq(@RequestParam("xmId") String xmId) {
        try {
            XmVo xmVo = xmService.getXqMx(xmId);
            return new FendaResponse().message("SUCCESS").code(200).date(xmVo);
        } catch (Exception e) {
            log.error("项目详情单条精确查询异常", e);
            return new FendaResponse().message("ERROR").code(500);
        }

    }

    @PostMapping("/upd_xm_xq")
    public FendaResponse updXmXq(@RequestBody XmDto xmDto) {
        try {
            xmService.updXqXm(xmDto);
            return new FendaResponse().message("SUCCESS").code(200);
        } catch (Exception e) {
            log.error("更新项目详情异常", e);
            return new FendaResponse().message("ERROR").code(500);
        }
    }


    /**
     * 大类ID查询所有该类的项目
     *
     * @param pageNum
     * @param pageSize
     * @return
     */
    @GetMapping("/get_xm_type")
    public FendaResponse getXmType(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam("pageSize") int pageSize) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<JcTypeVo> jcTypeVo = xmService.getXmType();
            PageInfo<JcTypeVo> page = new PageInfo<>(jcTypeVo);
            return new FendaResponse().message("SUCCESS").code(200).date(page);
        } catch (Exception e) {
            log.error("根据项目类型查询所有项目异常", e);
            return new FendaResponse().message("ERROR").code(500);
        }
    }

    /**
     * 查询所有项目大类
     *
     * @return
     */
    @GetMapping("/get_type")
    public FendaResponse getDlAll(){
        try {
            List<JcTypeList> jcTypeVo = xmService.selJcTypeDl();
            return new FendaResponse().message("SUCCESS").code(200).date(jcTypeVo);
        } catch (Exception e) {
            log.error("项目大类查询异常", e);
            return new FendaResponse().message("ERROR").code(500);
        }
    }
}
