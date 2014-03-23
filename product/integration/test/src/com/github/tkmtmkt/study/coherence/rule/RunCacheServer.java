package com.github.tkmtmkt.study.coherence.rule;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.junit.rules.ExternalResource;

import com.tangosol.net.CacheFactory;

public class RunCacheServer extends ExternalResource {

    private static final String LOG_PATTERN = "%m%n";

    private static final String LOG_FILE_FORMAT = "TEST-%s-test.txt";

    private static final String MAX_FILE_SIZE = "2MB";

    private static final int MAX_BACKUP_INDEX = 5;

    private final Class<?> clazz;

    public RunCacheServer(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void before() throws Throwable {
        super.before();

        //ログ設定
        /*
         * プロダクションコード内のログを全てコンソールに出力するようにする。
         * antのjunitタスクでformatterにplaneを設定すると、標準出力を
         * TEST-{クラス名}.txtに保存するようになる。
         * この設定で出力先が切り替わっているか調べる。
         */
        BasicConfigurator.resetConfiguration();
        BasicConfigurator.configure(new ConsoleAppender(
                new PatternLayout("%8r [%t] %-5p %1c - %m%n")));

        /*
         * テストコード内のログは別ファイルに出力するようにして、プロダクション
         * コードの実行時出力に混ざることの無いようにする。
         */
        PatternLayout layout = new PatternLayout(LOG_PATTERN);
        String logFile = String.format(LOG_FILE_FORMAT, this.clazz.getName());

        RollingFileAppender appender = new RollingFileAppender(layout, logFile, false);
        appender.setMaxFileSize(MAX_FILE_SIZE);
        appender.setMaxBackupIndex(MAX_BACKUP_INDEX);

        Logger logger = Logger.getLogger(this.clazz);
        logger.removeAllAppenders();
        logger.addAppender(appender);

        //Cohrence単体起動設定
        System.setProperty("tangosol.coherence.cluster", System.getProperty("user.name"));
        System.setProperty("tangosol.coherence.clusterport", "12345");
        System.setProperty("tangosol.coherence.localhost", "127.0.0.1");
        System.setProperty("tangosol.coherence.ttl", "0");
        System.setProperty("tangosol.coherence.distributed.localstorage", "true");

        //JMX管理設定（）
        System.setProperty("tangosol.coherence.management", "none");
        System.setProperty("tangosol.coherence.management.remote", "false");

        //ログ設定
        System.setProperty("tangosol.coherence.log", "log4j");
        System.setProperty("tangosol.coherence.log.level", "9");

        //Coherenceクラスタ起動
        CacheFactory.ensureCluster();
        logger.debug("STARTUP: cluster");
    }

    @Override
    protected void after() {
        Logger logger = Logger.getLogger(this.clazz);

        //Coherenceクラスタ停止
        CacheFactory.shutdown();
        logger.debug("SHUTDOWN: cluster");

        super.after();
    }

}
