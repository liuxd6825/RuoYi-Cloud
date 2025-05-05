package com.ruoyi.common.rsql.sql;

import com.ruoyi.common.rsql.*;
import org.apache.commons.lang3.StringUtils;

import java.util.List;
import java.util.ArrayList;

import static com.ruoyi.common.rsql.Process.parse;
import static com.ruoyi.common.rsql.Utils.asFieldName;

public class SQLProcess implements IProcess {
    private StringBuilder sb;
    private String tenantId;

    public SQLProcess(String tenantId) {
        this.tenantId = tenantId;
        this.sb = new StringBuilder();
    }

    @Override
    public String getTenantId() {
        return this.tenantId;
    }

    @Override
    public String getSQL() {
        return sb.toString();
    }

    @Override
    public Object getFilter() {
        return getSQL();
    }

    private void add(String format, Object... args) {
        sb.append(String.format(format, args));
    }

    @Override
    public Value onFnProcess(Expression expr, FuncValue fn) {
        switch (fn.getName()) {
            case "sub":
                IProcess subProcess = new SQLProcess(this.tenantId);
                try {
                    parse(fn.getRsql(), subProcess);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                String sql = subProcess.getSQL();
                String field = asFieldName(fn.getArgs().get("field"));
                String table = fn.getArgs().get("table").toString();
                sql = String.format("(select %s from %s where %s)", field, table, sql);
                return new StringValue(sql);
            default:
                return fn;
        }
    }

    @Override
    public void onEquals(String name, Object value, Value rValue) {
        Object val = getValue(rValue);
        add("%s=%v", name, val);
    }

    @Override
    public void onNotEquals(String name, Object value, Value rValue) {
        Object val = getValue(rValue);
        add("%s!=(%v)", name, val);
    }

    @Override
    public void onLike(String name, Object value, Value rValue) {
        String val = getLikeValue(rValue);
        add("%s like %v", name, val);
    }

    @Override
    public void onNotLike(String name, Object value, Value rValue) {
        String val = getLikeValue(rValue);
        add("%s not like %v", name, val);
    }

    @Override
    public void onContains(String name, Object value, Value rValue) {
        if (rValue instanceof StringValue) {
            StringValue s = (StringValue) rValue;
            add("%s like '%%%v%%'", name, s.getValue());
        } else {
            throw new RuntimeException("invalid rsql type in contains");
        }
    }

    @Override
    public void onNotContains(String name, Object value, Value rValue) {
        if (rValue instanceof StringValue) {
            StringValue s = (StringValue) rValue;
            add("%s not like '%%%v%%'", name, s.getValue());
        } else {
            throw new RuntimeException("invalid rsql type in OnNotContains");
        }
    }

    @Override
    public void onGreaterThan(String name, Object value, Value rValue) {
        Object val = getValue(rValue);
        add("%s>%v", name, val);
    }

    @Override
    public void onGreaterThanOrEquals(String name, Object value, Value rValue) {
        Object val = getValue(rValue);
        add("%s>=%v", name, val);
    }

    @Override
    public void onLessThan(String name, Object value, Value rValue) {
        Object val = getValue(rValue);
        add("%s<%v", name, val);
    }

    @Override
    public void onLessThanOrEquals(String name, Object value, Value rValue) {
        Object val = getValue(rValue);
        add("%s<=%v", name, val);
    }

    @Override
    public void onIn(String name, Object value, Value rValue) {
        if (rValue instanceof ListValue) {
            String val = getInValue((ListValue) rValue);
            add("%s in (%s)", name, val);
        } else {
            String val = getSubSql(rValue);
            add("%s in %s", name, val);
        }
    }

    @Override
    public void onNotIn(String name, Object value, Value rValue) {
        if (rValue instanceof ListValue) {
            String val = getInValue((ListValue) rValue);
            add("%s not in (%s)", name, val);
        } else {
            String val = getSubSql(rValue);
            add("%s not in %s", name, val);
        }
    }

    @Override
    public void onAndItem() {
        add(" and ");
    }

    @Override
    public void onAndStart() {
        add("(");
    }

    @Override
    public void onAndEnd() {
        add(")");
    }

    @Override
    public void onOrItem() {
        add(" or ");
    }

    @Override
    public void onOrStart() {
        add("(");
    }

    @Override
    public void onOrEnd() {
        add(")");
    }

    @Override
    public void onIsNull(String name, Object value, Value rValue) {
        add("%s is null", name);
    }

    @Override
    public void onNotIsNull(String name, Object value, Value rValue) {
        add("%s is not null", name);
    }

    @Override
    public void onStart(String name, Object value, Value rValue) {
        Object val = getValue(rValue);
        add("%s like '%s%%'", name, val);
    }

    @Override
    public void onEnd(String name, Object value, Value rValue) {
        Object val = getValue(rValue);
        add("%s like '%%%s'", name, val);
    }

    private String getSubSql(Value rValue) {
        Object val = getValue(rValue);
        if (val instanceof String) {
            return ((String) val).replace("'", "");
        }
        throw new RuntimeException("invalid rsql type in sub sql");
    }

    private Object getValue(Value value) {
        if (value instanceof StringValue) {
            return "'" + ((StringValue) value).getValue() + "'";
        } else if (value instanceof IntegerValue) {
            return ((IntegerValue) value).getValue();
        } else if (value instanceof DateValue) {
            return "'" + ((DateValue) value).getValue() + "'";
        } else if (value instanceof DoubleValue) {
            return ((DoubleValue) value).getValue();
        } else if (value instanceof DateTimeValue) {
            return "'" + ((DateTimeValue) value).getValue() + "'";
        } else if (value instanceof BooleanValue) {
            return ((BooleanValue) value).getValue();
        } else if (value instanceof ListValue) {
            return getValueList((ListValue) value);
        }
        return value;
    }

    private String getLikeValue(Value value) {
        String s = value instanceof StringValue ? ((StringValue) value).getValue() : value.toString();
        s = s.replace("'", "''").replace("*", "%");
        return "'" + s + "'";
    }

    private String getValueList(ListValue listValue) {
        List<String> list = new ArrayList<>();
        for (Value v : listValue.getValue()) {
            list.add(getValue(v).toString());
        }
        return StringUtils.join(",", list);
    }

    private String getInValue(ListValue listValue) {
        StringBuilder sb = new StringBuilder();
        int count = listValue.getValue().size();
        for (int i = 0; i < count; i++) {
            Value v = listValue.getValue().get(i);
            if (v instanceof StringValue) {
                sb.append("'").append(((StringValue) v).getValue()).append("'");
            } else {
                sb.append(v);
            }
            if (i < count - 1) sb.append(",");
        }
        return sb.toString();
    }
}
