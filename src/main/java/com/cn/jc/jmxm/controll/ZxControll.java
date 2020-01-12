package com.cn.jc.jmxm.controll;


import com.cn.jc.jmxm.comm.config.FendaResponse;
import com.cn.jc.jmxm.entity.jc.JcZx;
import com.cn.jc.jmxm.entity.jc.JcZxXq;
import com.cn.jc.jmxm.service.ZxService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/zx")
public class ZxControll {

    @Autowired
    private ZxService zxService;

    /**
     *  某个项目的所有资讯
     * @param pageNum 分页
     * @param pageSize 分页
     * @param id 项目ID
     * @return
     */
    @GetMapping("/get_zx_bt")
    public FendaResponse getZxAll(@RequestParam(value = "pageNum", defaultValue = "1") int pageNum, @RequestParam("pageSize") int pageSize,
                                  @RequestParam(value = "id") String id,@RequestParam("flag")String flag) {
        try {
            PageHelper.startPage(pageNum, pageSize);
            List<JcZx> list = zxService.selJcZx(id,flag);
            PageInfo<JcZx> page = new PageInfo<>(list);
            return new FendaResponse().message("SUCCESS").code(200).date(page);
        } catch (Exception e) {
            log.error("查询资讯列表异常", e);
            return new FendaResponse().message("ERROR").code(500);
        }
    }

    @GetMapping("/get_zx_xq")
    public FendaResponse getZxXq(@RequestParam("zxId")String zxId){
        try {
            List<JcZxXq> jcZxXqlist = zxService.selZxXq(zxId);
            return new FendaResponse().message("SUCCESS").code(200).date(jcZxXqlist);
        }catch (Exception e){
            log.error("查询资讯详情报错",e);
            return new FendaResponse().message("ERROR").code(500);
        }
    }

    @PostMapping("/del_zx")
    public FendaResponse delZx(@RequestParam("zxId")String zxId){
        try {
            zxService.delZx(zxId);
            return new FendaResponse().message("SUCCESS").code(200);
        }catch (Exception e){
            log.error("删除资讯异常",e);
            return new FendaResponse().message("ERROR").code(500);
        }
    }
    @PostMapping("/up_zx")
    public FendaResponse upZx(@RequestBody JcZx jcZx){
        try {
            zxService.upZxBt(jcZx);
            return new FendaResponse().message("SUCCESS").code(200);
        }catch (Exception e){
            log.error("删除资讯异常",e);
            return new FendaResponse().message("ERROR").code(500);
        }
    }
    @PostMapping("/up_xq")
    public FendaResponse upZx(@RequestBody JcZxXq jcZxXq){
        try {
            zxService.upZxXq(jcZxXq);
            return new FendaResponse().message("SUCCESS").code(200);
        }catch (Exception e){
            log.error("删除资讯异常",e);
            return new FendaResponse().message("ERROR").code(500);
        }
    }

}
