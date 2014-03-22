package com.github.tkmtmkt.study.coherence.util;

import java.util.HashMap;
import java.util.Map;

import net.arnx.jsonic.JSON;

public class JsonWriter {
    public static <T> String write(T data) {
        Map<Class<?>, T> source = new HashMap<Class<?>, T>();
        source.put(data.getClass(), data);

        String json = JSON.encode(source);

        return json;
    }
}
