package com.ruoyi.common.rsql;

@FunctionalInterface
public interface ParserFunction {
    Expression apply(Iterator tokens) throws Exception;
}