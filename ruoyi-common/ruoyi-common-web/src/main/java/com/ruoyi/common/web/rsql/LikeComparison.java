package com.ruoyi.common.web.rsql;

public class LikeComparison extends Comparison {
    public LikeComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "~=";
    }
}