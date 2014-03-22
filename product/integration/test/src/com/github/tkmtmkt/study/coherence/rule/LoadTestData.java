package com.github.tkmtmkt.study.coherence.rule;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;
import org.apache.log4j.RollingFileAppender;
import org.junit.rules.ExternalResource;

import com.github.tkmtmkt.study.coherence.pof.Action;
import com.github.tkmtmkt.study.coherence.pof.Target;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class LoadTestData extends ExternalResource {

    private static final String LOG_FILE_FORMAT = "log/%s.log";

    private static final String MAX_FILE_SIZE = "2MB";

    private static final int MAX_BACKUP_INDEX = 5;

    private final Class<?> clazz;

    public LoadTestData(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void before() throws Throwable {
        super.before();

        //ログ設定
        PatternLayout layout = new PatternLayout("%m%n");

        String logFile = String.format(LOG_FILE_FORMAT, this.clazz.getName());
        RollingFileAppender appender = new RollingFileAppender(layout, logFile, false);
        appender.setMaxFileSize(MAX_FILE_SIZE);
        appender.setMaxBackupIndex(MAX_BACKUP_INDEX);

        Logger logger = Logger.getLogger(this.clazz);
        logger.removeAllAppenders();
        logger.addAppender(appender);

        //データロード
        NamedCache targetCache = CacheFactory.getCache("dist-targets");

        Target target = new Target();
        target.setName("大和");
        target.setSpeed(40);

        Map<String, Action> actionMap = new HashMap<String, Action>();
        Action action1 = new Action();
        action1.setOrder("START");
        actionMap.put("001", action1);
        Action action2 = new Action();
        action2.setOrder("STOP");
        actionMap.put("002", action2);
        target.setAction(actionMap);

        targetCache.put("0001", target);
    }

    @Override
    protected void after() {
        super.after();
    }
}
