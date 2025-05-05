package com.ruoyi.common.rsql;

public class IsNullComparison extends Comparison {
    public IsNullComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "=null=";
    }
}
