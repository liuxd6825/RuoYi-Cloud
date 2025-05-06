package com.ruoyi.common.rsql;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.lang3.StringUtils;

import static com.ruoyi.common.rsql.TokenType.*;

public class Lexer {
    private int pos;
    private String buf;
    private int buflen;
    // 使用数组来表示
    private static final String[] FUNC_TYPES = {"sub"};

    public Lexer(String input) {
        this.buf = input;
        this.pos = 0;
        this.buflen = input.length();
    }

    public List<Token> parse() throws Exception {
        List<Token> res = new ArrayList<>();
        Token token;
        while (true) {
            token = nextToken();
            if (token.type == TokenType.UnknownToken) {
                throw new Exception("Not a valid token at " + pos + " (" + token.info + ")");
            }
            res.add(token);
            if (token.type == TokenType.EOFToken) {
                break;
            }
        }
        return res;
    }

    private Token nextToken() {
        try {
            skipBlank();
            if (pos >= buflen) {
                return new Token(TokenType.EOFToken, "", buflen, "");
            }

            Token token = processFunc();
            if (token.type != TokenType.UnknownToken) return token;

            token = processBool();
            if (token.type != TokenType.UnknownToken) return token;

            token = processOperator();
            if (token.type != TokenType.UnknownToken) return token;

            token = processDate();
            if (token.type != TokenType.UnknownToken) return token;

            token = processNumber();
            if (token.type != TokenType.UnknownToken) return token;

            token = processString();
            if (token.type != TokenType.UnknownToken) return token;

            token = processIdentifier();
            if (token.type != TokenType.UnknownToken) return token;

            token = processReserved();
            if (token.type != TokenType.UnknownToken) return token;

            return unknownToken();
        } catch (Exception e) {
            return unknownToken(e.getMessage());
        }
    }

    private Token unknownToken(String... messages) {

        String info = messages.length > 0 ? StringUtils.join(" ", messages) : "";
        return new Token(TokenType.UnknownToken, "", 0, info);
    }

    private void skipBlank() {
        while (pos < buflen && Character.isWhitespace(buf.charAt(pos))) {
            pos++;
        }
    }

    private Token processFunc() {
        // Iterate over all defined function types
        for (String fn : FUNC_TYPES) {
            // Check if the current position (pos) is the start of a function of type fn
            if (isFunc(pos, fn)) {
                // Calculate the length of the function token starting at pos
                int tokenLen = getFuncLength(pos, fn);
                // Generate and return a token of type FuncToken starting from pos with the calculated length
                return generateToken(FuncToken, pos + tokenLen);
            }
        }

        // If no function was found, return an unknown token
        return unknownToken();

    }

    private boolean isFunc(int pos, String funName) {
        String s = buf.substring(pos);
        if (s.startsWith(funName + "(")) {
            return true;
        }
        if (s.endsWith(funName + " ")) {
            return true;
        }
        return false;
    }

    private int getFuncLength(int pos, String funName) {
        int tokenLen = 0;
        String s = buf.substring(pos);
        int index = s.indexOf('(');
        if (index == -1) {
            return 0;
        }
        s = s.substring(index);
        int count = 1;
        for (int i = 0; i < s.length(); i++) {
            char c = s.charAt(i);
            if (c == '(') {
                count++;
            }
            if (c == ')') {
                count--;
            }
            if (count == 1) {
                tokenLen = i + 1;
                break;
            }
        }
        return index + tokenLen;
    }

    private Token processBool() {
        if (isString(pos, "true")) {
            return generateToken(TokenType.BooleanToken, pos + 4);
        } else if (isString(pos, "false")) {
            return generateToken(TokenType.BooleanToken, pos + 5);
        }
        return unknownToken();
    }

    private Token processOperator() {
        if (isBlankBefore(pos) && (isString(pos, "and") || isString(pos, "AND")) && isBlankAfter(pos + 2)) {
            return generateToken(TokenType.AndToken, pos + 3);
        } else if (isBlankBefore(pos) && (isString(pos, "or") || isString(pos, "OR")) && isBlankAfter(pos + 1)) {
            return generateToken(TokenType.OrToken, pos + 2);
        }
        return unknownToken();
    }

    private Token processDate() {
        int idx = pos;

        // 检查日期格式 YYYY-MM-DD
        if (isDigit(idx) && isDigit(idx+1) && isDigit(idx+2) && isDigit(idx+3) &&
                isString(idx+4, "-") &&
                isDigit(idx+5) && isDigit(idx+6) &&
                isString(idx+7, "-") &&
                isDigit(idx+8) && isDigit(idx+9)) {

            TokenType type = TokenType.DateToken;
            idx += 10;

            // 检查时间部分 T00:00:00
            if (idx < buflen && buf.charAt(idx) == 'T' &&
                    isDigit(idx+1) && isDigit(idx+2) && isString(idx+3, ":") &&
                    isDigit(idx+4) && isDigit(idx+5) && isString(idx+6, ":") &&
                    isDigit(idx+7) && isDigit(idx+8)) {

                idx += 9;
                type = TokenType.DateTimeToken;

                // 检查时区部分
                if (idx < buflen) {
                    if (buf.charAt(idx) == 'Z') {
                        idx++;
                    } else if (isString(idx, "+") || isString(idx, "-")) {
                        if (isDigit(idx+1) && isDigit(idx+2)) {
                            if (isString(idx+3, ":") && isDigit(idx+4) && isDigit(idx+5)) {
                                idx += 6;
                            } else if (isDigit(idx+3) && isDigit(idx+4)) {
                                idx += 5;
                            }
                        }
                    }
                }
            }
            return generateToken(type, idx);
        }
        return unknownToken();
    }

    private Token processNumber() {
        int idx = pos;

        // 检查数字开头（可能是负数）
        if (isDigit(idx) || (charAt(idx) == '-' && isDigit(idx + 1))) {
            TokenType type = TokenType.IntegerToken;
            idx++;

            // 处理连续数字
            while (idx < buflen && isDigit(idx)) {
                idx++;
            }

            // 检查是否有小数部分
            if (idx < buflen && charAt(idx) == '.') {
                idx++;
                type = TokenType.DoubleToken;

                // 处理小数部分数字
                while (idx < buflen && isDigit(idx)) {
                    idx++;
                }
            }

            return generateToken(type, idx);
        }

        return unknownToken();
    }


    private Token processString() {
        char currentChar = charAt(pos);
        // 检查是否是单引号或双引号
        if (currentChar == '\'' || currentChar == '"') {
            char quote = currentChar;
            int idx = buf.indexOf(quote, pos + 1) ; // 查找引号的结束位置

            // 处理转义字符
            while (idx != -1 && charAt(idx - 1) == '\\') {
                idx = buf.indexOf(quote, idx + 1) + idx + 1;
            }

            // 如果没有找到结束的引号，抛出异常
            if (idx == -1) {
                throw new RuntimeException(String.format("Unterminated quote at position %d, %d", pos, idx));
            }

            // 创建并返回 token
            String value = buf.substring(pos + 1, idx).replaceAll("\\\\" + quote, String.valueOf(quote));
            Token token = new Token(StringToken, value, pos,"");

            // 更新当前位置
            pos = idx + 1;

            return token;
        }

        // 如果没有匹配的引号，返回未知 token
        return unknownToken();
    }



    private Token processIdentifier() {
        AtomicInteger idx = new AtomicInteger(pos);

        // 检查当前字符是否为字母
        if (isAlpha(idx.get())) {
            idx.getAndIncrement();  // 移动到下一个字符
            // 定义一个过程，处理后续的字母和数字
            Runnable process = () -> {
                while (isDigit(idx.get()) || isAlpha(idx.get())) {
                    idx.getAndIncrement();
                }
            };

            process.run();  // 执行过程

            // 如果遇到点（"."）并且后面是字母，继续处理
            while (charAt(idx.get()) == '.' && isAlpha(idx.get() + 1)) {
                idx.addAndGet(2);
                process.run();
            }

            // 生成并返回标识符 Token
            return generateToken(IdentifierToken, idx.get());
        }

        // 如果不是字母，则返回未知 Token
        return unknownToken();

    }

    private Token processReserved() {
        int idx = pos;

        if (isString(idx, "(")) {
            return generateToken(LeftParenToken, idx + 1);
        } else if (isString(idx, ")")) {
            return generateToken(RightParenToken, idx + 1);
        } else if (isString(idx, ",")) {
            return generateToken(CommaToken, idx + 1);
        } else if (isString(idx, "!=")) {
            return generateToken(NotEqualsToken, idx + 2);
        } else if (isString(idx, "==")) {
            return generateToken(EqualsToken, idx + 2);
        } else if (isString(idx, ">=")) {
            return generateToken(GreaterOrEqualsToken, idx + 2);
        } else if (isString(idx, ">")) {
            return generateToken(GreaterToken, idx + 1);
        } else if (isString(idx, "<=")) {
            return generateToken(LessOrEqualsToken, idx + 2);
        } else if (isString(idx, "<")) {
            return generateToken(LessToken, idx + 1);
        } else if (isString(idx, "=in=")) {
            return generateToken(InToken, idx + 4);
        } else if (isString(idx, "=out=")) {
            return generateToken(NotInToken, idx + 5);
        } else if (isString(idx, "=contains=")) {
            return generateToken(ContainsToken, idx + 10);
        } else if (isString(idx, "=!contains=")) {
            return generateToken(NotContainsToken, idx + 11);
        } else if (isString(idx, "==~") || isString(idx, "~==")) {
            return generateToken(LikeToken, idx + 3);
        } else if (isString(idx, "~=") || isString(idx, "=~")) {
            return generateToken(LikeToken, idx + 2);
        } else if (isString(idx, "!=~") || isString(idx, "!~=")) {
            return generateToken(NotLikeToken, idx + 3);
        } else if (isString(idx, "=null=")) {
            return generateToken(IsNullToken, idx + 6);
        } else if (isString(idx, "=!null=")) {
            return generateToken(NotIsNullToken, idx + 7);
        } else if (isString(idx, "=start=")) {
            return generateToken(IsNullToken, idx + 7);
        } else if (isString(idx, "=end=")) {
            return generateToken(NotIsNullToken, idx + 5);
        }

        return unknownToken();
    }

    private boolean isString(int pos, String value) {
        if (pos + value.length() > buflen) {
            return false;
        }
        return buf.substring(pos, pos + value.length()).equals(value);
    }

    private boolean isBlankBefore(int pos) {
        return pos == 0 || Character.isWhitespace(buf.charAt(pos - 1));
    }

    private boolean isBlankAfter(int pos) {
        return pos >= buflen - 1 || Character.isWhitespace(buf.charAt(pos + 1));
    }

    private Token generateToken(TokenType type, int newPos) {
        String value = buf.substring(pos, newPos);
        Token token = new Token(type, value, pos, "");
        pos = newPos;
        return token;
    }

    private boolean isBlank(int pos) {
        if (pos >= buflen) return false;
        char c = buf.charAt(pos);
        return c == ' ' || c == '\n' || c == '\t' || c == '\r';
    }


    private boolean isAlpha(int idx) {
        if (idx >= buflen) {
            return false;
        }
        char c = buf.charAt(idx);
        return (c >= 'a' && c <= 'z') || (c >= 'A' && c <= 'Z') || c == '_' || c == '$';
    }

    private boolean isDigit(int idx) {
        if (idx >= buflen) {
            return false;
        }
        char c = buf.charAt(idx);
        return c >= '0' && c <= '9';
    }

    private char charAt(int pos) {
        if (pos < 0 || pos >= buflen) {
            return '\0'; // 返回空字符表示越界
        }
        return buf.charAt(pos);
    }
}
