package com.github.tkmtmkt.study.coherence.rule;

import org.junit.rules.ExternalResource;

import com.tangosol.net.CacheFactory;

public class RunCacheServer extends ExternalResource {

    @Override
    protected void before() throws Throwable {
        super.before();

        //Cohrence単体起動設定
        System.setProperty("tangosol.coherence.cluster", System.getProperty("user.name"));
        System.setProperty("tangosol.coherence.clusterport", "12345");
        System.setProperty("tangosol.coherence.localhost", "127.0.0.1");
        System.setProperty("tangosol.coherence.ttl", "0");
        System.setProperty("tangosol.coherence.distributed.localstorage", "true");

        //JMX管理設定
        System.setProperty("tangosol.coherence.management", "none");
        System.setProperty("tangosol.coherence.management.remote", "true");

        //ログ設定
        System.setProperty("tangosol.coherence.log", "log4j");
        System.setProperty("tangosol.coherence.log.level", "9");

        CacheFactory.ensureCluster();
        System.out.println("STARTUP: cluster");
    }

    @Override
    protected void after() {
        CacheFactory.shutdown();
        System.out.println("SHUTDOWN: cluster");

        super.after();
    }

}
