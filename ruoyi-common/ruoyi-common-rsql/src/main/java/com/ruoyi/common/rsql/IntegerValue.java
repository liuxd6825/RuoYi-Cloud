package com.ruoyi.common.rsql;

public class IntegerValue implements Value {
    private long value;

    public IntegerValue(long value) {
        this.value = value;
    }

    @Override
    public String valueName() {
        return "int";
    }

    @Override
    public String toString() {
        return Long.toString(value);
    }

    public long getValue() {
        return value;
    }

    public void setValue(long value) {
        this.value = value;
    }
}
