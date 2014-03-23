package com.github.tkmtmkt.study.coherence.sample;

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

    /*
     * logger取得後にRunCacheServer#beforeでLoggerのアペンダを設定しなおしているが
     * 問題なく動作している。運よく動いているのか、保証された動作なのか？
     * 気が向いたら調べる。
     */
    private static final Logger logger = LoggerFactory.getLogger(SampleTest.class);

    /*
     * ClassRuleはBeforeClass/AfterClassを定義した抽象クラスを継承するようなものと
     * 考えてよい。
     * RunCacheServer、LoadTestDataの順に定義して、LoadTestData#beforeが先に実行
     * されたため、RuleChainを使用してRule適用順序を明示するようにした。
     * （メンバのインスタンス化の順序はJavaの仕様で決まっていないのか？）
     */
    @ClassRule
    public static final TestRule chain = RuleChain
            .outerRule(new RunCacheServer(SampleTest.class))
            .around(new LoadTestData(SampleTest.class));

    /*
     * ClassRuleはstatic、Ruleはnon-staticで定義する。（当たり前）
     * ClassRule/RuleはPublicで定義する。（TestRunnerから見えるようにだと思う）
     */
    @Rule
    public final TestName testName = new TestName();

    @Test
    public void 動作確認() {
        NamedCache targetCache = CacheFactory.getCache("dist-targets");
        Target target = (Target) targetCache.get("0001");
        logger.debug(target.toString());

        assertThat("Nameが「大和」であること。", target.getName(), is("大和"));
        assertThat("speedが「40」であること。", target.getSpeed(), is(40));
        Map<String, Action> actionMap = target.getAction();
        assertThat("action[001]が「START」であること。", actionMap.get("001").getOrder(), is("START"));
        assertThat("action[002]が「STOP」であること。", actionMap.get("002").getOrder(), is("STOP"));
    }
}
