package com.github.tkmtmkt.study.coherence.rule;

import java.util.HashMap;
import java.util.Map;

import org.junit.rules.ExternalResource;

import com.github.tkmtmkt.study.coherence.pof.Action;
import com.github.tkmtmkt.study.coherence.pof.Target;
import com.tangosol.net.CacheFactory;
import com.tangosol.net.NamedCache;

public class LoadTestData extends ExternalResource {

    private final Class<?> clazz;

    public LoadTestData(Class<?> clazz) {
        this.clazz = clazz;
    }

    @Override
    protected void before() throws Throwable {
        super.before();

        //データロード
        /*
         * 固定のディレクトリ名またはファイル名からデータをロードする
         * 処理を定義する。
         * ※とりあえず動作確認のために決め打ちでデータを突っ込んでみた。
         */
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
