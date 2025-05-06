package com.ruoyi.common.web.rsql;

// ==================== Comparison Base Class ====================
public class Comparison implements Expression {
    private Identifier identifier;
    private Value val;

    public Comparison(Identifier identifier, Value val) {
        this.identifier = identifier;
        this.val = val;
    }

    @Override
    public String expressionName() {
        return "Comparison";
    }

    public Value getVal() {
        return val;
    }

    public void setVal(Value val) {
        this.val = val;
    }

    public Identifier getIdentifier() {
        return identifier;
    }

    public void setIdentifier(Identifier identifier) {
        this.identifier = identifier;
    }
}
