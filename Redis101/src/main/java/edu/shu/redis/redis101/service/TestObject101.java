package edu.shu.redis.redis101.service;

import java.io.Serializable;

public class TestObject101 implements Serializable {
    private String value1;
    private String value2;
    public TestObject101(final String value1, final String value2) {
        this.value1 = value1;
        this.value2 = value2;
    }
}
