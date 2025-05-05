package com.ruoyi.common.rsql;

public class LikeComparison extends Comparison {
    public LikeComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "~=";
    }
}