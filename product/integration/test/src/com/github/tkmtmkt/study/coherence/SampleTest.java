package com.github.tkmtmkt.study.coherence;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

import java.util.Map;

import org.junit.ClassRule;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.RuleChain;
import org.junit.rules.TestName;
import org.junit.rules.TestRule;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.github.tkmtmkt.study.coherence.pof.Action;
import com.github.tkmtmkt.study.coherence.pof.Target;
import com.github.tkmtmkt.study.coherence.rule.LoadTestData;
import com.github.tkmtmkt.study.coherence.rule.RunCacheServer;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class SampleTest {
    @ClassRule
    public static TestRule chain = RuleChain
            .outerRule(new RunCacheServer())
            .around(new LoadTestData(SampleTest.class));

    private static final Logger logger = LoggerFactory.getLogger(SampleTest.class);

    @Rule
    public final TestName testName = new TestName();

    @Test
    public void 動作確認() {
        NamedCache targetCache = CacheFactory.getCache("dist-targets");
        Target target = (Target) targetCache.get("0001");
        logger.debug(target.toString());

        assertThat("", target.getName(), is("大和"));
        assertThat("", target.getSpeed(), is(40));
        Map<String, Action> actionMap = target.getAction();
        assertThat("", actionMap.get("001").getOrder(), is("START"));
        assertThat("", actionMap.get("002").getOrder(), is("STOP"));
    }
}
