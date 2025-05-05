package com.ruoyi.common.rsql;

public class NotInComparison extends Comparison {
    public NotInComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "=out=";
    }
}