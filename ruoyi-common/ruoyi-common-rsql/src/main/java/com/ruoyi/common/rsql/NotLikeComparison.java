package com.ruoyi.common.rsql;

public class NotLikeComparison extends Comparison {
    public NotLikeComparison(Identifier identifier, Value val) {
        super(identifier, val);
    }

    @Override
    public String expressionName() {
        return "!~=";
    }
}