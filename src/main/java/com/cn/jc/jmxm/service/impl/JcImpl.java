package com.cn.jc.jmxm.service.impl;


import com.cn.jc.jmxm.entity.jc.*;
import com.cn.jc.jmxm.mapper.*;
import com.cn.jc.jmxm.service.JcService;
import com.cn.jc.jmxm.util.OpenHttps;
import com.cn.jc.jmxm.util.RandomUtil;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Slf4j
@Service
public class JcImpl implements JcService {

    @Autowired
    private JcTypeListMapper jcTypeListMapper;
    @Autowired
    private JcSeoMapper jcSeoMapper;
    @Autowired
    private JcXqMapper jcXqMapper;
    @Autowired
    private JcGsxqjsMapper jcGsxqjsMapper;
    @Autowired
    private JcJmxxMapper jcJmxxMapper;
    @Autowired
    private JcJmMapper jcJmMapper;
    @Autowired
    private JmYsMapper jmYsMapper;
    @Autowired
    private JmYqMapper jmYqMapper;
    @Autowired
    private JcLiuYanMapper jcLiuYanMapper;
    @Autowired
    private JcLogMapper jcLogMapper;
    @Autowired
    private JcZxMapper jcZxMapper;
    @Autowired
    private JcZxXqMapper jcZxXqMapper;
    @Autowired
    private JcZxXqImgsMapper jcZxXqImgsMapper;


    @Override
    public void getData() {
        List<JcTypeList> jcTypeLists = jcTypeListMapper.selJcTypeListFlag();
        jcTypeLists.forEach((JcTypeList jcType) -> {

            // String url = "httpps://www.chinafloor.cn/brand/list-htm-action-all.html";
            // String url = "https://www.chinachugui.com/brand/list-htm-action-all.html";
            String html = OpenHttps.https(jcType.getUrl());

            Document document = Jsoup.parse(html);
            Elements cite = document.select("cite");
            String ym = cite.toString().substring(cite.toString().indexOf("条") + 2, cite.toString().length() - 8);
            List<Element> list = new ArrayList<>();
            for (int a = 1; a <= Integer.valueOf(ym); a++) {
                //拼接翻页的url
                String fyUrl = jcType.getUrl().substring(0, jcType.getUrl().length() - 5) + "-page-" + (a) + jcType.getUrl().substring(jcType.getUrl().length() - 5);
                String fyHtml = OpenHttps.https(fyUrl);
                Document fyDocument = Jsoup.parse(fyHtml);
                Elements fyElements = fyDocument.select("div[class=link]");

                list.addAll(fyElements);
            }
            List<String> jmList = new ArrayList<>();
            for (int i = 0; i < list.size(); i++) {
                String div = list.get(i).toString();
                String jmUrl = div.substring(div.indexOf("a href=") + 8, div.indexOf("加盟") - 18);
                jmList.add(jmUrl);
            }
            for (int i = 0; i < jmList.size(); i++) {
                String mxUrl = jmList.get(i);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                String date = simpleDateFormat.format(new Date());
                //全局项目ID
                long xuId = 100001 + i;
                String xmId = jcType.getId() + "X" + xuId;
                try {
                    String xqthtml = OpenHttps.https(mxUrl);
                    Document htmlDocument = Jsoup.parse(xqthtml);
                    //todo SEO入表
                    JcSeo jcSeo = new JcSeo();
                    String jcSeoTitle = htmlDocument.select("title").toString();
                    String jcSeoMetaKey = htmlDocument.select("meta[name=keywords]").toString();
                    String jcSeoMetaDes = htmlDocument.select("meta[name=description]").toString();
                    jcSeo.setXqId(xmId);
                    jcSeo.setXqTitle(jcSeoTitle.substring(7, jcSeoTitle.length() - 8));
                    jcSeo.setXqMetaKeywords(jcSeoMetaKey.substring(jcSeoMetaKey.indexOf("content") + 9, jcSeoMetaKey.length() - 2));
                    jcSeo.setXqMetaDescription(jcSeoMetaDes.substring(jcSeoMetaDes.indexOf("content") + 9, jcSeoMetaDes.length() - 2));
                    jcSeoMapper.insert(jcSeo);

                    //获取全局的网站变量
                    String ss = htmlDocument.select("div").toString();
                    String bl = ss.substring(12, ss.indexOf("-top-box"));

                    //todo 头部明细
                    JcXq jcXq = new JcXq();
                    jcXq.setXqUrl(mxUrl);
                    Elements imgdc1 = htmlDocument.select("div[class=" + bl + "-wrap cf]");

                    log.info(bl + "系列页面");
                    Elements imgdc2 = imgdc1.select("div[class=" + bl + "-mn2]");
                    Elements imgdc3 = imgdc2.select("div[class=" + bl + "-invest cf]");
                    Elements imgdc4 = imgdc3.select("div[class=" + bl + "-invest-focus js-invest-focus]");
                    Elements imgdc5 = imgdc4.select("div[class=slide]");
                    Elements imgdc6 = imgdc5.select("div[class=bd]");

                    Elements imgsUlElements = imgdc6.select("ul[class=cf]");
                    List<Element> imageLiElements = imgsUlElements.select("li");
                    String imageLi = imageLiElements.get(0).select("img").toString();

                    String imagUrl = imageLi.substring(imageLi.indexOf("src=") + 5, imageLi.indexOf("alt=") - 2);
                    String imageName = OpenHttps.imgs(imagUrl, xmId + imagUrl.substring(imagUrl.lastIndexOf(".")), "xm");
                    jcXq.setXqImgs(imageName);//图片全路径

                    jcXq.setXqId(xmId);
                    jcXq.setXqType(jcType.getId() + "");
                    Elements xqHtml = htmlDocument.select("div[class=" + bl + "-invest-info]");
                    String xqName = xqHtml.select("h2").toString();
                    jcXq.setXqXmmc(xqName.substring(4, xqName.length() - 5));

                    List<Element> pHtml = xqHtml.select("p");
                    Element[] strings = new Element[pHtml.size()];
                    pHtml.toArray(strings);

                    String jbtz = strings[0].toString();
                    jcXq.setXqJbtz(jbtz.substring(jbtz.indexOf("<i>") + 3, jbtz.indexOf("</i>")));
                    String mdsl = strings[1].toString();
                    jcXq.setXqMdsl(mdsl.substring(mdsl.indexOf("<i>") + 3, mdsl.indexOf("</i>")));
                    String ppmc = strings[2].toString();
                    jcXq.setXqPpmc(ppmc.substring(ppmc.indexOf("：") + 1, ppmc.indexOf("</p>")));
                    String clrq = strings[3].toString();
                    jcXq.setXqClrq(clrq.substring(clrq.indexOf("：") + 1, clrq.indexOf("</p>")));
                    String jmqy = strings[4].toString();
                    jcXq.setXqJmqy(jmqy.substring(jmqy.indexOf("：") + 1, jmqy.indexOf("</p>")));
                    String shrq = strings[5].toString();
                    jcXq.setXqShrq(shrq.substring(shrq.indexOf("：") + 1, shrq.indexOf("</p>")));
                    jcXq.setXqDate(date);
                    jcXq.setXqFlag("1");//爬下来的数据默认为0
                    jcXqMapper.insert(jcXq);


                    //todo 公司简介
                    Elements intorCf = htmlDocument.select("div[class=intro cf]");
                    Elements xqjs = intorCf.select("p");
                    JcGsxqjs jcGsxqjs = new JcGsxqjs();
                    jcGsxqjs.setXqId(xmId);
                    jcGsxqjs.setXqGsjs(xqjs.toString());
                    jcGsxqjsMapper.insert(jcGsxqjs);

                    //todo 加盟信息
                    JcJmxx jcJmxx = new JcJmxx();
                    Elements jmxxHtml = htmlDocument.select("div[class=" + bl + "-wrap cf]");
                    Elements cgmn2 = jmxxHtml.select("div[class=" + bl + "-mn2]");
                    Elements bdCf = cgmn2.select("div[class=" + bl + "-agent-info]");
                    List<Element> ul = bdCf.select("li");
                    Element[] uls = new Element[ul.size()];
                    ul.toArray(uls);

                    jcJmxx.setXqId(xmId);
                    String fyd = uls[0].toString();
                    jcJmxx.setJmPpfyd(fyd.substring(fyd.indexOf("：") + 1, fyd.indexOf("</")));
                    String sb = uls[2].toString();
                    jcJmxx.setJmSbzch(sb.substring(sb.indexOf("：") + 1, sb.indexOf("</")));
                    String jyms = uls[4].toString();
                    jcJmxx.setJmJyfs(jyms.substring(jyms.indexOf("：") + 1, jyms.indexOf("</")));
                    String fzms = uls[5].toString();
                    jcJmxx.setJmFzms(fzms.substring(fzms.indexOf("：") + 1, fzms.indexOf("</")));
                    String jycp = bdCf.select("a").text();
                    jcJmxx.setJmJycp(jycp);
                    jcJmxxMapper.insert(jcJmxx);


                    //加盟费用
                    JcJm jcjm = new JcJm();
                    Elements cg_agent_cost = htmlDocument.select("div[class=" + bl + "-agent-cost]");
                    Elements ulCf = cg_agent_cost.select("li");
                    Element[] ulCfs = new Element[ulCf.size()];
                    ulCf.toArray(ulCfs);

                    jcjm.setXqId(xmId);
                    jcjm.setJmJmf(ulCfs[0].select("span").text());
                    jcjm.setJmBzj(ulCfs[1].select("span").text());
                    jcjm.setJmJhj(ulCfs[2].select("span").text());
                    jcjm.setJmJmfSm(ulCfs[3].select("li").text());
                    jcjm.setJmBzjSm(ulCfs[4].select("li").text());
                    jcjm.setJmJhjSm(ulCfs[5].select("li").text());
                    jcJmMapper.insert(jcjm);


                    //加盟优势
                    Elements cg_com_intro = htmlDocument.select("div[class=" + bl + "-com-intro]");
                    Elements bd_cf = cg_com_intro.select("div[class=intro]");
                    String bd_cf_p = bd_cf.select("p").toString();
                    JmYs jmys = new JmYs();
                    jmys.setXqId(xmId);
                    jmys.setJmZcys(bd_cf_p);
                    jmYsMapper.insert(jmys);


                    //加盟要求
                    JmYq jmyq = new JmYq();
                    Elements com_intro = htmlDocument.select("div[class=" + bl + "-com-intro]");
                    Elements bd_intro_cf = com_intro.select("div[class=bd intro cf]");
                    String bd_intro_cf_p = bd_intro_cf.select("p").toString();
                    jmyq.setXqId(xmId);
                    jmyq.setJmJmyq(bd_intro_cf_p);
                    jmYqMapper.insert(jmyq);

                    //爬取留言

                    Elements cg_agent_message_ml20 = htmlDocument.select("div[class=" + bl + "-agent-message ml20]");
                    Elements lyLi = cg_agent_message_ml20.select("p");
                    for (int a = 0; a < lyLi.size(); a++) {
                        JcLiuYan jcLiuYan = new JcLiuYan();
                        jcLiuYan.setZxId(xmId);
                        jcLiuYan.setUserMessge(lyLi.get(a).select("p").text());
                        String lyDate = simpleDateFormat.format(new Date());
                        jcLiuYan.setUpDate(lyDate);
                        jcLiuYan.setPcData("1");
                        jcLiuYanMapper.insert(jcLiuYan);
                    }
                    jcTypeListMapper.updJcTypeListFlag(jcType.getId());


                } catch (Exception e) {
                    JcLog jcLog = new JcLog();
                    jcLog.setXmId(xmId);
                    jcLog.setJcUrl(mxUrl);
                    jcLogMapper.insert(jcLog);
                    log.error("此明细报错：" + xmId + "======" + mxUrl, e);
                }
            }


        });


    }

    @Override
    public void getZxData() {

        List<JcXq> jcXqList = jcXqMapper.selJcXq();
        for (int q = 0; q < jcXqList.size(); q++) {
            try {


                JcXq jcxq = jcXqList.get(q);
                //单个项目的咨询信息。此处替换为表里面查出来的信息
                String url = jcxq.getXqUrl();
                String zxUrl = null;
                String zyUrl = null;
                String dtUrl = null;


                if (url.indexOf("zhaoshang") > 0) {
                    zxUrl = url.replace("zhaoshang", "news");
                } else if (url.indexOf("agent") > 0) {
                    zxUrl = url.replace("agent", "news");
                } else {
                    log.error("组装新闻列表网页报错");
                    JcLog jcLog = new JcLog();
                    jcLog.setXmId(jcxq.getXqId());
                    jcLog.setJcUrl(url);
                    jcLogMapper.insert(jcLog);
                    jcXqMapper.upJcZxFlag(jcxq.getXqId());
                }
                log.info(jcxq.getXqId() + ":当前处理的新闻网页：" + zxUrl);
                String xx = null;

                String html = OpenHttps.https(zxUrl);
                if (html == null) {
                    continue;
                }
                Document document = Jsoup.parse(html);
                String ss = document.select("div").toString();
                String bl = ss.substring(12, ss.indexOf("-top-box"));
                //拼接翻页url
                Elements yema = document.select("div[class=" + bl + "-page]");
                List<Element> page = yema.select("a");
                int pageSize = 0;
                try {
                    pageSize = Integer.valueOf(page.get(page.size() - 2).text());
                } catch (Exception e) {
                    log.error("没有新闻信息");
                    JcLog jcLog = new JcLog();
                    jcLog.setXmId(jcxq.getXqId());
                    jcLog.setJcUrl(zxUrl);
                    jcLogMapper.insert(jcLog);
                    jcXqMapper.upJcZxFlag(jcxq.getXqId());
                    continue;
                }

                //此处拿到该咨询项目的分类ID
                String xmId = jcxq.getXqId();
                String flid = xmId.substring(0, xmId.indexOf("X"));
                for (int i = 1; i <= pageSize; i++) {
                    //拼接每一页的url
                    String zxItemUrl = zxUrl + "page-" + i + ".shtml";
                    zyUrl = zxItemUrl;
                    log.info("分页URL：" + zxItemUrl);
                    String itemUrl = OpenHttps.https(zxItemUrl);
                    if (itemUrl == null) {
                        continue;
                    }
                    Document itemDocument = Jsoup.parse(itemUrl);
                    List<Element> itemElements = itemDocument.select("div[id=ajax_zt]").select("dl");
                    for (int ia = 0; ia < itemElements.size(); ia++) {
                        try {

                            Element element = itemElements.get(ia);
                            JcZx jcZx = new JcZx();
                            //标题
                            String zxBt = element.select("h2").select("a").text();
                            jcZx.setZxBt(zxBt);
                            String zxxqUrl = element.select("h2").select("a").attr("href");
                            log.info("正在处理：" + zxxqUrl);
                            int xqNum = jcZxMapper.selXqUrl(zxBt, zxxqUrl);
                            //已经爬过的不做处理直接跳出
                            if (xqNum > 0) {
                                log.info("已经处理过，跳出：" + zxxqUrl);
                                continue;
                            }

                            String zxId = flid + "Z" + RandomUtil.getRandom(10);
                            xx = zxId;
                            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                            String date = simpleDateFormat.format(new Date());
                            jcZx.setZxId(zxId);
                            //简介
                            jcZx.setZxJj(element.select("p").get(0).text());
                            jcZx.setFlag("1");//状态，未发布1
                            //更新日期
                            jcZx.setUpDate(date);
                            jcZx.setXmId(jcxq.getXqId());
                            String image = element.select("dt").select("a").select("img").attr("src");
                            if (image != null && !image.equals("")) {
                                String imageName = OpenHttps.imgs(image, xmId + image.substring(image.lastIndexOf(".")), "zx");
                                jcZx.setImgs(imageName);
                            }

                            dtUrl = zxxqUrl;
                            jcZx.setXqUrl(zxxqUrl);

                            jcZxMapper.insert(jcZx);
                        } catch (Exception e) {
                            log.error("此处资讯报错", e);
                            log.error("项目新闻大网页：" + zxUrl);
                            log.error("整页：" + zyUrl);
                            log.error("具体单条：" + dtUrl);
                            JcLog jcLog = new JcLog();
                            jcLog.setXmId(xx);
                            jcLog.setJcUrl(dtUrl);
                            jcLogMapper.insert(jcLog);
                        }


                    }
                }
                jcXqMapper.upJcZxFlag(jcxq.getXqId());
            } catch (Exception e) {
                log.error("期望出现所有异常都能不停的跑下去");
            }
        }

    }

    public void getZxDataXq() {

        int countJcZx = jcZxMapper.countJcZx();

        for (int a = 0; a < countJcZx; a++) {
            JcZx jcZx = jcZxMapper.selJcZx();
            try {
                String html = OpenHttps.https(jcZx.getXqUrl());
                if (html == null) {
                    return;
                }
                Document document = Jsoup.parse(html);

                String ss = document.select("div").toString();
                String bl = ss.substring(12, ss.indexOf("-top-box"));
                //先判断是否有多页
                Elements fyxqUrl = document.select("div[class=" + bl + "-page]").select("a");

                if (fyxqUrl.size() <= 0) {
                    fyxqUrl = document.select("ul[class=" + bl + "-read-page cf]").select("a");
                }
                log.info("开始处理：" + jcZx.getXqUrl());

                if (fyxqUrl.size() > 0) {
                    //分页处理
                    Elements wy = fyxqUrl.select("a");
                    int zongyeshu = 0;
                    for (int ias = 0; ias < wy.size(); ias++) {
                        try {
                            String xq = wy.get(ias).text();
                            zongyeshu = Integer.valueOf(xq);
                        } catch (Exception e) {
                            log.info("忽略");
                        }
                    }
                    for (int ias = 1; ias <= zongyeshu; ias++) {
                        JcZxXq jcZxXq = new JcZxXq();
                        jcZxXq.setZxId(jcZx.getZxId());

                        String url = jcZx.getXqUrl();
                        //分页有两种模式，第一种不行就上第二种，不排除还有第三种
                        StringBuffer sb = new StringBuffer();
                        sb.append(url).insert(url.lastIndexOf("."), ("-page-" + ias));
                        String htm = OpenHttps.https(sb.toString());

                        Document document404 = Jsoup.parse(htm);
                        Elements el404 = document404.select("div[class=error404]");

                        StringBuffer sbs = new StringBuffer();

                        if (el404.size() > 0) {
                            sbs.append(url).insert(url.lastIndexOf("."), ("_" + ias));
                            htm = OpenHttps.https(sbs.toString());
                            log.info("sbs" + sbs);
                        }


                        Document docs = Jsoup.parse(htm);

                        Elements elements = docs.select("div[class=" + bl + "-detail]").select("p");

                        Elements elements1 = elements.select("div[class=js_content]").select("p");
                        if (elements1.size() > 0)
                            elements = elements1;


                        List<Element> zxxqImgs = elements.select("img");

                        for (int iasb = 0; iasb < zxxqImgs.size(); iasb++) {
                            JcZxXqImgs jcZxXqImgs = new JcZxXqImgs();
                            String iasUrl = zxxqImgs.get(iasb).attr("original");
                            if (iasUrl.equals("") || iasUrl == null) {
                                iasUrl = zxxqImgs.get(iasb).attr("src");
                            }
                            String hz = iasUrl.substring(iasUrl.lastIndexOf("."));
                            //出现这个说明是腾讯的图片没被授权展示不了
                            if (iasUrl.indexOf("mmbiz.qpic.cn") > 0) {
                                continue;
                            }
                            if (iasUrl.lastIndexOf(".")<0){
                                continue;
                            }
                            if (hz.equals(".gif")) {
                                String s = zxxqImgs.get(iasb).attr("original");
                                if (s != null && !s.equals("")) {
                                    iasUrl = s;
                                }
                            }
                            if (hz.length() > 4) {
                                hz = ".jpg";
                            }
                            String imName = OpenHttps.imgs(iasUrl, jcZx.getZxId() + "-" + ias + "-" + iasb + hz, "zx");

                            //找到要替换的内容
                            Element node = zxxqImgs.get(iasb);
                            //替换原网页中的内容
                            node.attr("src", "");
                            node.attr("original", imName);
                            jcZxXqImgs.setZxId(jcZx.getZxId());
                            jcZxXqImgs.setImgsRowno(iasb + 1);
                            jcZxXqImgs.setZxXqImgs(imName);
                            jcZxXqImgsMapper.insert(jcZxXqImgs);
                        }

                        elements.select("a").remove();
                        String xq = elements.toString();

                        try {
                            String qq = xq.replace(xq.substring(xq.indexOf("【"), xq.indexOf("】") + 1), "");
                            jcZxXq.setZxXq(qq);
                        } catch (Exception e) {
                            jcZxXq.setZxXq(xq);
                        }

                        jcZxXq.setRowno(ias);
                        jcZxXqMapper.insert(jcZxXq);
                    }
                } else {

                    JcZxXq jcZxXq = new JcZxXq();
                    jcZxXq.setZxId(jcZx.getZxId());
                    jcZxXq.setRowno(1);
                    //单页
                    Elements jeHtm = document.select("div[class=" + bl + "-detail]").select("p");
                    Elements elements1 = jeHtm.select("div[class=js_content]").select("p");
                    if (elements1.size() > 0)
                        jeHtm = elements1;

                    List<Element> zxxqImgs = jeHtm.select("img");

                    for (int ias = 0; ias < zxxqImgs.size(); ias++) {
                        Element element = zxxqImgs.get(ias);
                        if (element.toString().indexOf("mmbiz.qpic.cn") > 0) {
                            continue;
                        }

                        JcZxXqImgs jcZxXqImgs = new JcZxXqImgs();
                        String iasUrl = element.attr("src");
                        if (iasUrl.lastIndexOf(".")<0){
                            continue;
                        }
                        String hz = iasUrl.substring(iasUrl.lastIndexOf("."));
                        if (hz.equals(".gif")) {
                            String s = element.attr("original");
                            if (s != null && !s.equals("")) {
                                iasUrl = s;
                            }

                        }
                        if (hz.length() > 4) {
                            hz = ".jpg";
                        }
                        String imName = OpenHttps.imgs(iasUrl, jcZx.getZxId() + "-" + ias + hz, "zx");
                        //找到要替换的内容
                        Element node = element;
                        //替换原网页中的内容
                        node.attr("src", "");
                        node.attr("original", imName);

                        jcZxXqImgs.setZxId(jcZx.getZxId());
                        jcZxXqImgs.setImgsRowno(ias + 1);
                        jcZxXqImgs.setZxXqImgs(imName);
                        jcZxXqImgsMapper.insert(jcZxXqImgs);
                    }
                    //删掉原数据中的A标签
                    jeHtm.select("a").remove();
                    String xq = jeHtm.toString();
                    try {
                        String qq = xq.replace(xq.substring(xq.indexOf("【"), xq.indexOf("】") + 1), "");
                        jcZxXq.setZxXq(qq);
                    } catch (Exception e) {
                        jcZxXq.setZxXq(xq);
                    }
                    jcZxXqMapper.insert(jcZxXq);

                }
                jcZxMapper.updJcZx("1",jcZx.getZxId());
            } catch (Exception e) {
                jcZxMapper.updJcZx("2",jcZx.getZxId());
                log.error("明细错：" + jcZx.getXqUrl(), e);
            }

        }
    }


//    public static void main(String[] args) {
//       // List<JcZx> list = jcZxMapper.selJcZx();
//       // for (int a = 0; a < list.size(); a++) {
//            JcZx jcZx = new JcZx();
//            jcZx.setXqUrl("https://www.chinajsq.cn/news/201809/20/67555.html");
//            //jcZx.setXqUrl("https://e-water.co.chinajsq.cn/news/itemid-12754.shtml");
//            jcZx.setZxId("32432432");
//            try {
//                String html = OpenHttps.https(jcZx.getXqUrl());
//                if (html == null) {
//                    return;
//                }
//                Document document = Jsoup.parse(html);
//
//                String ss = document.select("div").toString();
//                String bl = ss.substring(12, ss.indexOf("-top-box"));
//
//                //先判断是否有多页
//                Elements fyxqUrl = document.select("div[class=" + bl + "-page]").select("a");
//
//                if (fyxqUrl.size()<=0){
//                    fyxqUrl = document.select("ul[class=" + bl + "-read-page cf]").select("a");
//
//                }
//                log.info("开始处理："+jcZx.getXqUrl());
//
//                if (fyxqUrl.size()>0) {
//                    //分页处理
//                    Elements wy = fyxqUrl.select("a");
//                    int zongyeshu = 0;
//                    for (int ias = 0; ias < wy.size(); ias++) {
//                        try {
//                            String xq = wy.get(ias).text();
//                            zongyeshu = Integer.valueOf(xq);
//                        } catch (Exception e) {
//                            log.info("忽略");
//                        }
//                    }
//                    for (int ias = 1; ias <= zongyeshu; ias++) {
//                        JcZxXq jcZxXq = new JcZxXq();
//                        jcZxXq.setZxId(jcZx.getZxId());
//
//                        String url = jcZx.getXqUrl();
//                        //分页有两种模式，第一种不行就上第二种，不排除还有第三种
//                        StringBuffer sb = new StringBuffer();
//                        sb.append(url).insert(url.lastIndexOf("."), ("-page-" + ias));
//                        String htm = OpenHttps.https(sb.toString());
//
//                        Document document404 = Jsoup.parse(htm);
//                        Elements el404 = document404.select("div[class=error404]");
//
//                        StringBuffer sbs = new StringBuffer();
//
//                        if (el404.size()>0) {
//                            sbs.append(url).insert(url.lastIndexOf("."), ("_" + ias));
//                            htm = OpenHttps.https(sbs.toString());
//                            log.info("sbs"+sbs);
//                        }
//
//
//                        Document docs = Jsoup.parse(htm);
//
//                        Elements elements = docs.select("div[class="+bl+"-detail]").select("p");
//
//                        Elements elements1 = elements.select("div[class=js_content]").select("p");
//                        if (elements1.size()>0)
//                            elements=elements1;
//
//
//                        List<Element> zxxqImgs = elements.select("img");
//                        System.out.println("zxxqImgs"+zxxqImgs);
//                        for (int iasb = 0; iasb < zxxqImgs.size(); iasb++) {
//                            JcZxXqImgs jcZxXqImgs = new JcZxXqImgs();
//                            String iasUrl = zxxqImgs.get(iasb).attr("original");
//                            //出现这个说明是腾讯的图片没被授权展示不了
//                            if (iasUrl.indexOf("mmbiz.qpic.cn") > 0) {
//                                continue;
//                            }
//
//                            String imName = OpenHttps.imgs(iasUrl, jcZx.getZxId() + "-" + ias + "-" + iasb + iasUrl.substring(iasUrl.lastIndexOf(".")), "zx");
//
//                            //找到要替换的内容
//                            Element node = zxxqImgs.get(iasb);
//                            //替换原网页中的内容
//                            node.attr("src","");
//                            node.attr("original", imName);
//                            jcZxXqImgs.setZxId(jcZx.getZxId());
//                            jcZxXqImgs.setImgsRowno(iasb+1);
//                            jcZxXqImgs.setZxXqImgs(imName);
//                          //  jcZxXqImgsMapper.insert(jcZxXqImgs);
//                        }
//
//                        elements.select("a").remove();
//                        String xq = elements.toString();
//
//                        try {
//                            String qq = xq.replace(xq.substring(xq.indexOf("【"), xq.indexOf("】") + 1), "");
//                            jcZxXq.setZxXq(qq);
//                        } catch (Exception e) {
//                            jcZxXq.setZxXq(xq);
//                        }
//                        System.out.println(jcZxXq.getZxXq());
//                        jcZxXq.setRowno(ias);
//                     //   jcZxXqMapper.insert(jcZxXq);
//                    }
//                } else {
//
//                    JcZxXq jcZxXq = new JcZxXq();
//                    jcZxXq.setZxId(jcZx.getZxId());
//                    jcZxXq.setRowno(1);
//                    //单页
//                    Elements jeHtm = document.select("div[class=" + bl + "-detail]").select("p");
//                    Elements elements1 = jeHtm.select("div[class=js_content]").select("p");
//                    if (elements1.size()>0)
//                        jeHtm=elements1;
//
//                    List<Element> zxxqImgs = jeHtm.select("img");
//
//                    for (int ias = 0; ias < zxxqImgs.size(); ias++) {
//                        if (zxxqImgs.indexOf("mmbiz.qpic.cn") > 0) {
//                            continue;
//                        }
//                        JcZxXqImgs jcZxXqImgs = new JcZxXqImgs();
//                        String iasUrl = zxxqImgs.get(ias).attr("src");
//                        System.out.println("iasUrl"+iasUrl);
//                        String imName = OpenHttps.imgs(iasUrl, jcZx.getZxId() + "-" + ias + iasUrl.substring(iasUrl.lastIndexOf(".")), "zx");
//                        //找到要替换的内容
//                        Element node = zxxqImgs.get(ias);
//                        //替换原网页中的内容
//                        node.attr("src","");
//                        node.attr("original", imName);
//
//                        jcZxXqImgs.setZxId(jcZx.getZxId());
//                        jcZxXqImgs.setImgsRowno(ias+1);
//                        jcZxXqImgs.setZxXqImgs(imName);
//                     //   jcZxXqImgsMapper.insert(jcZxXqImgs);
//                    }
//                    //删掉原数据中的A标签
//                    jeHtm.select("a").remove();
//                    String xq = jeHtm.toString();
//                    try {
//                        String qq = xq.replace(xq.substring(xq.indexOf("【"), xq.indexOf("】") + 1), "");
//                        jcZxXq.setZxXq(qq);
//                    } catch (Exception e) {
//                        jcZxXq.setZxXq(xq);
//                    }
//                    System.out.println(jcZxXq.getZxXq());
//                  //  jcZxXqMapper.insert(jcZxXq);
//
//                }
//
//            } catch (Exception e) {
//                log.error("明细错："+jcZx.getXqUrl(),e);
//            }
//          //  jcZxMapper.updJcZx(jcZx.getZxId());
//    }


    public static void main(String[] args) {
        String ss = "sdfdsfsdfdsfdsf";
        int s =ss.lastIndexOf(".");
        System.out.println(s);
    }

}


