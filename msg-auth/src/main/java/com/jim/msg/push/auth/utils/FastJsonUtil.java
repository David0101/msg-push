package com.jim.msg.push.auth.utils;


import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;

import java.util.ArrayList;

/**
 * @author lianxinxiong
 */
public class FastJsonUtil {

    /**
     * 根据JSON对象转自定义对象
     * @param jsonData
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T jsonDataToObject(Object jsonData, Class<T> obj) {
        T jsonObject = JSONObject.parseObject(JSONObject.toJSONString(jsonData), obj);
        return jsonObject;
    }

    /**
     * 根据JSON对象转自定义对象列表
     * @param jsonData
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> jsonDataToListObject(Object jsonData, Class<T> obj) {
        ArrayList<T> jsonObject = JSONObject.parseObject(JSONObject.toJSONString(jsonData), new TypeReference<ArrayList<T>>(obj) {
        });
        return jsonObject;
    }

    /**
     * 根据JSON字符串转自定义对象
     * @param jsonStr
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> T jsonStrToObject(String jsonStr, Class<T> obj) {
        T jsonObject = JSONObject.parseObject(jsonStr, obj);
        return jsonObject;
    }

    /**
     * 根据JSON字符串转自定义对象列表
     * @param jsonStr
     * @param obj
     * @param <T>
     * @return
     */
    public static <T> ArrayList<T> jsonStrToListObject(String jsonStr, Class<T> obj) {
        ArrayList<T> jsonObject = JSONObject.parseObject(jsonStr, new TypeReference<ArrayList<T>>(obj) {
        });
        return jsonObject;
    }

    /**
     * 将自定义对象转成JSON字符串
     * @param obj
     * @return
     */
    public static String getJsonStr(Object obj) {
        return JSONObject.toJSONString(obj);
    }
}

