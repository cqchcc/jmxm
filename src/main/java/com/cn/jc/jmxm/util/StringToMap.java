package com.cn.jc.jmxm.util;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringToMap {


    public static Map<String, String> getStringToMap(String str) {
        String ss = replaceAllBlank(str);
        //根据逗号截取字符串数组
        String[] str1 = ss.split(",");
        //创建Map对象
        Map<String, String> map = new HashMap<>();
        //循环加入map集合
        for (int i = 0; i < str1.length; i++) {
            //根据":"截取字符串数组
            String[] str2 = str1[i].split(":");
            //str2[0]为KEY,str2[1]为值
            map.put(str2[0], str2[1]);
            System.out.println(map.get("province"));
        }
        return map;
    }

    //去除所有空格
    public static String replaceAllBlank(String str) {
        String s = "";
        if (str != null) {
            Pattern p = Pattern.compile("\\s*|\t|\r|\n");
                     /*\n 回车(\u000a)
        \t 水平制表符(\u0009)
           \s 空格(\u0008)
           \r 换行(\u000d)*/
            Matcher m = p.matcher(str);
            s = m.replaceAll("");
        }
        return s;
    }

}
