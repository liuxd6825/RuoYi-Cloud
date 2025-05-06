package com.ruoyi.common.web.rsql;

// ==================== Comparison Operator Implementations ====================
public class EqualsComparison extends Comparison {
    public EqualsComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "==";
    }
}