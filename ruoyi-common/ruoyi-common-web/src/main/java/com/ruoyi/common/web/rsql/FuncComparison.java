package com.ruoyi.common.web.rsql;

public class FuncComparison extends Comparison {
    public FuncComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "=func=";
    }
}
