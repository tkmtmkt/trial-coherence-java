package com.github.tkmtmkt.study.coherence.util;

import static org.junit.Assert.*;

import java.util.HashMap;
import java.util.Map;

import org.junit.Test;

import com.github.tkmtmkt.study.coherence.pof.Action;
import com.github.tkmtmkt.study.coherence.pof.Target;

public class JsonWriterTest {

    @Test
        public void testWrite() {
            Target person = new Target();
            person.setName("高松");
            person.setSpeed(43);
            Map<String, Action> other = new HashMap<String, Action>();
            other.put("hoge", new Action());
            other.put("fuga", new Action());
            person.setAction(other);

            String json = JsonWriter.write(person);
            System.out.println(json);

            Map<?, ?> map = JsonReader.read(json);
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                System.out.println(entry.getKey());
                System.out.println(entry.getValue());
            }

            fail("まだ実装されていません");
        }

}
