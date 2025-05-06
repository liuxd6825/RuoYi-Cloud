package com.ruoyi.common.web.rsql;

public class NotContainsComparison extends Comparison {
    public NotContainsComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "=!contains=";
    }
}