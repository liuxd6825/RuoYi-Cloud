package com.ruoyi.common.rsql;



public class GreaterThanComparison extends Comparison {
    public GreaterThanComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return ">";
    }
}
