package com.ruoyi.common.web.rsql;

public class LessThanOrEqualsComparison extends Comparison {
    public LessThanOrEqualsComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "<=";
    }
}