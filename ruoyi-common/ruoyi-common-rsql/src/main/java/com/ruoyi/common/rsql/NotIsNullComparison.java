package com.ruoyi.common.rsql;

public class NotIsNullComparison extends Comparison {
    public NotIsNullComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "=!null=";
    }
}