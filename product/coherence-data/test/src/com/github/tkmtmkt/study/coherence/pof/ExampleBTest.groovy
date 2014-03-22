package com.github.tkmtmkt.study.coherence.pof;

import org.junit.Test;

import com.github.tkmtmkt.study.coherence.pof.Target;

public class ExampleBTest {
    private Target example = new Target();

    @Test
    public void countString() {
        assert (5 == example.countString("Hello"));
        assert (6 == example.countString("漢字の文字数"));
    }
}
// vim: set ts=4 sw=4 et:
