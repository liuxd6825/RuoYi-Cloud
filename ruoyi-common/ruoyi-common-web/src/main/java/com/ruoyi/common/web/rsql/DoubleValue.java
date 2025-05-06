package com.ruoyi.common.web.rsql;

public class DoubleValue implements Value {
    private double value;

    public DoubleValue(double value) {
        this.value = value;
    }

    @Override
    public String valueName() {
        return "double";
    }

    @Override
    public String toString() {
        return Double.toString(value);
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }
}