package com.github.tkmtmkt.study.coherence.pof;

import java.util.Map;

import com.tangosol.io.pof.annotation.Portable;
import com.tangosol.io.pof.annotation.PortableProperty;

/**
 * Exampleクラス.
 * @author TAKAMATSU Makoto
 * @version 1.0
 * @since 1.0
 */
@Portable
public class Target {

    @PortableProperty(0)
    private String name;

    @PortableProperty(1)
    private int speed;

    @PortableProperty(2)
    private Map<String, Action> action;

    public String getName() {
        return name;
    }

    public int getSpeed() {
        return speed;
    }

    public Map<String, Action> getAction() {
        return action;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSpeed(int age) {
        this.speed = age;
    }

    public void setAction(Map<String, Action> other) {
        this.action = other;
    }
}
// vim: set ts=4 sw=4 et:
