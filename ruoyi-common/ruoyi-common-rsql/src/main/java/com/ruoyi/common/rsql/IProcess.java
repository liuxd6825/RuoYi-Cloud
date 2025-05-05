package com.ruoyi.common.rsql;


public interface IProcess {
    void onAndItem();
    void onAndStart();
    void onAndEnd();
    void onOrItem();
    void onOrStart();
    void onOrEnd();
    void onEquals(String name, Object value, Value rValue);
    void onNotEquals(String name, Object value, Value rValue);
    void onLike(String name, Object value, Value rValue);
    void onNotLike(String name, Object value, Value rValue);
    void onGreaterThan(String name, Object value, Value rValue);
    void onGreaterThanOrEquals(String name, Object value, Value rValue);
    void onLessThan(String name, Object value, Value rValue);
    void onLessThanOrEquals(String name, Object value, Value rValue);
    void onIn(String name, Object value, Value rValue);
    void onNotIn(String name, Object value, Value rValue);
    void onContains(String name, Object value, Value rValue);
    void onNotContains(String name, Object value, Value rValue);
    void onIsNull(String name, Object value, Value rValue);
    void onNotIsNull(String name, Object value, Value rValue);
    void onStart(String name, Object value, Value rValue);
    void onEnd(String name, Object value, Value rValue);

    String getTenantId();
    Value onFnProcess(Expression expr, FuncValue fn);
    String getSQL();
    Object getFilter();
}
