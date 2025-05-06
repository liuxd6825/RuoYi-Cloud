package com.ruoyi.common.web.rsql;

@FunctionalInterface
public interface ParserFunction {
    Expression apply(Iterator tokens) throws Exception;
}