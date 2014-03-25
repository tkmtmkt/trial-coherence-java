package com.github.tkmtmkt.study.coherence.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Set;

import net.arnx.jsonic.JSON;

import org.slf4j.Logger;

import com.tangosol.coherence.component.util.safeService.SafeCacheService;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.CacheService;
import com.tangosol.net.Cluster;
import com.tangosol.net.NamedCache;
import com.tangosol.util.Filter;
import com.tangosol.util.QueryHelper;
import com.tangosol.util.Service;

public final class Debug {
    private static final String EOL = System.getProperty("line.separator");

    /**
     * クラスタ内のキャッシュデータを一覧表示します。
     *
     * @param logger
     */
    public static void printCache(final Logger logger) {
        final Cluster cluster = CacheFactory.getCluster();
        if (cluster != null) {
            for(final String cacheName : getCacheNames(cluster)) {
                printCache(logger, cacheName);
            }
        }
    }

    /**
     * クラスタ内のキャッシュ名の一覧を表示します。
     *
     * @return
     */
    @SuppressWarnings("unchecked")
    private static List<String> getCacheNames(final Cluster cluster) {
        final List<String> cacheNames = new ArrayList<>();
        final Enumeration<?> serviceNames = cluster.getServiceNames();
        for (final String serviceName : Collections.list((Enumeration<String>)serviceNames)) {
            cacheNames.addAll(getCacheNames(cluster, serviceName));
        }
        return cacheNames;
    }

    /**
     * 指定したサービス名に関するキャッシュ名の一覧を表示します。
     *
     * @param cluster
     * @param serviceName
     * @return
     */
    @SuppressWarnings("unchecked")
    private static List<String> getCacheNames(final Cluster cluster, final String serviceName) {
        final Service service = cluster.getService(serviceName);
        if (service != null) {
            if (service.isRunning()) {
                if (service instanceof SafeCacheService) {
                    final CacheService cacheService = (CacheService) service;
                    final Enumeration<?> cacheNames = cacheService.getCacheNames();
                    return Collections.list((Enumeration<String>)cacheNames);
                }
            }
        }
        return new ArrayList<>();
    }

    public static void printCache(final Logger logger, final String cacheName) {
        printCache(logger, cacheName, null);
    }

    @SuppressWarnings("unchecked")
    public static void printCache(final Logger logger, final String cacheName, final String where) {
        final NamedCache cache = CacheFactory.getCache(cacheName);

        final Set<Map.Entry<?, ?>> entrySet;
        if (where != null) {
            final Filter filter = QueryHelper.createFilter(where);
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
