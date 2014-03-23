package com.github.tkmtmkt.study.coherence.util;

import java.util.Map;
import java.util.Set;

import net.arnx.jsonic.JSON;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.QueryHelper;

public final class Debug {
    public static final Logger logger = LoggerFactory.getLogger("Test");

    private static final String EOL = System.getProperty("line.separator");

    public static void printCache(final String cacheName) {
        printCache(cacheName, null);
    }

    @SuppressWarnings("unchecked")
    public static void printCache(final String cacheName, final String where) {
        final NamedCache cache = CacheFactory.getCache(cacheName);

        final Set<Map.Entry<?, ?>> entrySet;
        if (where != null) {
            Filter filter = QueryHelper.createFilter(where);
            entrySet = cache.entrySet(filter);
        } else {
            entrySet = cache.entrySet();
        }

        final StringBuilder sb = new StringBuilder();
        for (Map.Entry<?, ?> entry : entrySet) {
            sb.append(EOL);
            sb.append(entry.getKey()).append(" = ");
            sb.append(JSON.encode(entry.getValue()));
        }
        logger.debug("{} (size = {}){}", cacheName, entrySet.size(), sb);
    }
}
