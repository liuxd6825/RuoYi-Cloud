package com.ruoyi.common.web.rsql;


public class NotEqualsComparison extends Comparison {
    public NotEqualsComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "!=";
    }
}