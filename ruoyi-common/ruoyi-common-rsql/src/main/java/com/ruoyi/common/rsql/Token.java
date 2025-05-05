package com.ruoyi.common.rsql;

public class Token {
    public TokenType type;
    public String value;
    public int pos;
    public String info;

    public Token(TokenType type, String value, int pos, String info) {
        this.type = type;
        this.value = value;
        this.pos = pos;
        this.info = info;
    }
}