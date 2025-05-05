package com.ruoyi.common.rsql;

public class EndComparison extends Comparison {
    public EndComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "=end=";
    }
}