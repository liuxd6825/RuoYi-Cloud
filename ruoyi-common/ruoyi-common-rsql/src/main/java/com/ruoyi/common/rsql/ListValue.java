package com.ruoyi.common.rsql;


import java.util.List;

public class ListValue implements Value {
    private List<Value> value;

    public ListValue(List<Value> value) {
        this.value = value;
    }

    @Override
    public String valueName() {
        return "list";
    }

    @Override
    public String toString() {
        return value.toString();
    }

    public List<Value> getValue() {
        return value;
    }

    public void setValue(List<Value> value) {
        this.value = value;
    }
}