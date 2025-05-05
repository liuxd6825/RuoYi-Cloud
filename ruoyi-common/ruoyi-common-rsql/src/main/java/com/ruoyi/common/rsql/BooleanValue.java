package com.ruoyi.common.rsql;


public class BooleanValue implements Value {
    private boolean value;

    public BooleanValue(boolean value) {
        this.value = value;
    }

    @Override
    public String valueName() {
        return "bool";
    }

    @Override
    public String toString() {
        return value ? "true" : "false";
    }

    public boolean getValue() {
        return value;
    }

    public void setValue(boolean value) {
        this.value = value;
    }
}
