package com.ruoyi.common.web.rsql;

public class InComparison extends Comparison {
    public InComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "=in=";
    }
}