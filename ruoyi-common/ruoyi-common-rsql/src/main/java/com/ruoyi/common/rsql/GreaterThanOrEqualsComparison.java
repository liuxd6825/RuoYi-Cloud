package com.ruoyi.common.rsql;

public class GreaterThanOrEqualsComparison extends Comparison {
    public GreaterThanOrEqualsComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return ">=";
    }
}