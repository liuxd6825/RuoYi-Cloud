package com.ruoyi.common.rsql;

import java.util.List;

public class AndExpression implements Expression {
    private List<Expression> items;

    public AndExpression(List<Expression> items) {
        this.items = items;
    }

    @Override
    public String expressionName() {
        return "And";
    }

    public List<Expression> getItems() {
        return items;
    }

    public void setItems(List<Expression> items) {
        this.items = items;
    }
}