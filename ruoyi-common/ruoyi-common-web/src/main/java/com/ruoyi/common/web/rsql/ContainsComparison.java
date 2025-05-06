package com.ruoyi.common.web.rsql;

public class ContainsComparison extends Comparison {
    public ContainsComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "=contains=";
    }
}