package com.cn.jc.jmxm.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.util.Map;

@Slf4j
public class OpenHttps {


    /**
     * 整个网页下载
     *
     * @return
     */

    public static String https(String url) {
        try {
            CloseableHttpClient httpclient = HttpClients.createDefault(); //创建httpClient实例

            HttpGet httpget = new HttpGet(url);//创建httpGet实例
            CloseableHttpResponse response = httpclient.execute(httpget);//执行请求
            HttpEntity entity = response.getEntity();//获取响应实体
            String content = EntityUtils.toString(entity, "utf-8");//响应数据转成字符串，字符集根据网站编码决定
            return content;
        } catch (Exception e) {
            log.error("获取网页失败", e);
            return null;
        }

    }


    /**
     * 图片下载
     *
     * @param url  图片地址
     * @param name 图片名字
     * @return
     */
    public static String imgs(String url, String name, String flag)  {

        String lj = null;
        if (flag.equals("zx")) {
            lj = "frontend/zxImgs";
        } else if (flag.equals("xm")) {
            lj = "frontend/imgs";
        }

        try {
            URL url1;
            url1 = new URL(url);
            //String fileName ="C:/Users/Administrator/Desktop/sss/"+name;
            File directory = new File("");// 参数为空
            String courseFile = directory.getCanonicalPath();

            String fileName = courseFile + "/" + lj + "/" + name;

            log.info("图片存放地址:" + fileName);
            DataInputStream fileInputStream = new DataInputStream(url1.openStream());
            @SuppressWarnings("resource")// 创建输出流输入存放地址
                    FileOutputStream outputStream = new FileOutputStream(fileName);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = fileInputStream.read(buffer)) != -1) {
                outputStream.write(buffer, 0, length);
            }
            return fileName;
        } catch (Exception e) {
            log.error("保存图片异常",e);
            return null;
        }


    }

    /**
     * 手机号归属地查询
     *
     * @param tel 手机号码
     * @return 手机号归属地信息，需要自己解析
     */
    public static Map<String, String> getTelAddress(String tel) {
        String url = "https://tcc.taobao.com/cc/json/mobile_tel_segment.htm?tel=" + tel;
        String telAddress = https(url);
        log.info("归属地返回的字符串:" + telAddress);
        return StringToMap.getStringToMap(telAddress);
    }
}
