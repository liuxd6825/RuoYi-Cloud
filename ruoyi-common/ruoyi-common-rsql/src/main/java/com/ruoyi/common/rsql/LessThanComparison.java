package com.ruoyi.common.rsql;


public class LessThanComparison extends Comparison {
    public LessThanComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "<";
    }
}