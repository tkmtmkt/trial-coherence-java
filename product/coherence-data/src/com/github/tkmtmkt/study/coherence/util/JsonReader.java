package com.github.tkmtmkt.study.coherence.util;

import java.util.Map;

import net.arnx.jsonic.JSON;

public class JsonReader {
    public static Map<?, ?> read(String json) {
        Map<?, ?>map = JSON.decode(json);

        return map;
    }
}
