package com.ruoyi.common.rsql;

public class FuncComparison extends Comparison {
    public FuncComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "=func=";
    }
}
