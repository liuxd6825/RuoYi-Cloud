package com.ruoyi.common.rsql;


// ==================== Value Implementations ====================
public class StringValue implements Value {
    private String value;

    public StringValue(String value) {
        this.value = value;
    }

    @Override
    public String valueName() {
        return "string";
    }

    @Override
    public String toString() {
        return value;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}