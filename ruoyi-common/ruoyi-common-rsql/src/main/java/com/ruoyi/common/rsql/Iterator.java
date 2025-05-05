package com.ruoyi.common.rsql;

import java.util.List;

public class Iterator {
    private int length;
    private int idx;
    private List<Token> items;

    // Constructor for Iterator
    public Iterator(List<Token> items) {
        this.length = items.size();
        this.idx = 0;
        this.items = items;
    }

    public List<Token> getItems() {
        return items;
    }

    public int getIdx(){
        return this.idx;
    }

    public int getLength(){
        return this.length;
    }

    // Get token by index
    public Token get(int idx) {
        if (idx >= length) {
            return new Token(TokenType.EOFToken, "", length, "");
        }
        return items.get(idx);
    }

    // Get the current token
    public Token current() {
        return get(idx);
    }

    // Get current token and move the index forward by a specified count
    public Token currentAndMove(int... potentialCount) {
        int count = 1;
        if (potentialCount.length > 0) {
            count = potentialCount[0];
        }
        Token result = current();
        if (result.type != TokenType.EOFToken) {
            idx += count;
        }
        return result;
    }
}