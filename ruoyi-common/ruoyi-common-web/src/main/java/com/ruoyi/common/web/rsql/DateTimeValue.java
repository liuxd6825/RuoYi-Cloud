package com.ruoyi.common.web.rsql;


public class DateTimeValue implements Value {
    private String value;

    public DateTimeValue(String value) {
        this.value = value;
    }

    @Override
    public String valueName() {
        return "datetime";
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
