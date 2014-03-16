package com.github.tkmtmkt.study.coherence.pof;

import org.junit.Test;

import com.github.tkmtmkt.study.coherence.pof.Example;

public class ExampleBTest {
    private Example example = new Example();

    @Test
    public void countString() {
        assert (5 == example.countString("Hello"));
        assert (6 == example.countString("漢字の文字数"));
    }
}
// vim: set ts=4 sw=4 et:
