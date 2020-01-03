package com.cn.jc.jmxm.comm.config;

import java.util.HashMap;

/**
 * 全局返回类
 */
public class FendaResponse extends HashMap<String, Object> {

    public FendaResponse code(Integer code) {
        this.put("code", code);
        return this;
    }

    public FendaResponse message(String message) {
        this.put("message", message);
        return this;
    }

    public FendaResponse date(Object date) {
        this.put("date", date);
        return this;
    }

    @Override
    public FendaResponse put(String key, Object value) {
        super.put(key, value);
        return this;
    }
}
