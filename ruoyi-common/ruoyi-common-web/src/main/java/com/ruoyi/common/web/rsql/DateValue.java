package com.ruoyi.common.web.rsql;


public class DateValue implements Value {
    private String value;

    public DateValue(String value) {
        this.value = value;
    }

    @Override
    public String valueName() {
        return "date";
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