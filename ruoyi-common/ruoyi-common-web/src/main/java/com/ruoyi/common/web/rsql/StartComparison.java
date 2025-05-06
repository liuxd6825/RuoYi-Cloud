package com.ruoyi.common.web.rsql;

public class StartComparison extends Comparison {
    public StartComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "=start=";
    }
}