package com.ruoyi.common.rsql;

import java.util.List;

// ==================== Logical Expressions ====================
public class OrExpression implements Expression {
    private List<Expression> items;

    public OrExpression(List<Expression> items) {
        this.items = items;
    }

    @Override
    public String expressionName() {
        return "Or";
    }

    public List<Expression> getItems() {
        return items;
    }

    public void setItems(List<Expression> items) {
        this.items = items;
    }
}