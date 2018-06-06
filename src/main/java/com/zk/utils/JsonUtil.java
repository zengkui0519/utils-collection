package com.zk.utils;

import com.google.gson.Gson;

public class JsonUtil {

    private JsonUtil() {}

    public static String getJsonFromObject(Object object){
        Gson gson = new Gson();
        String jsonStr = gson.toJson(object);
        return jsonStr;
    }

    public static <T> T getObjectFromJson(String jsonStr, Class<T> cls){
        System.out.println(jsonStr.trim());
        Gson gson = new Gson();
        T t = gson.fromJson(jsonStr.trim(), cls);
        return t;
    }

}

