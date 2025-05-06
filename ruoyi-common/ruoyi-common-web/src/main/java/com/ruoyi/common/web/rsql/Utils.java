package com.ruoyi.common.web.rsql;

import cn.hutool.core.date.DateUtil;
import org.apache.commons.lang3.StringUtils;
import java.lang.reflect.Field;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class Utils {

    // 其他 Value 类型类似定义...
    public static Object getValue(Value value) {
        if (value instanceof StringValue) {
            return ((StringValue) value).getValue();
        } else if (value instanceof IntegerValue) {
            return ((IntegerValue) value).getValue();
        } else if (value instanceof DateValue) {
            DateValue dv = (DateValue) value;
            return DateUtil.parse(dv.getValue(), "yyyy-MM-dd");
        } else if (value instanceof DateTimeValue) {
            DateTimeValue dtv = (DateTimeValue) value;
            return DateUtil.parse(dtv.getValue(), "yyyy-MM-dd HH:mm:ss");
        } else if (value instanceof BooleanValue) {
            return ((BooleanValue) value).getValue();
        } else if (value instanceof ListValue) {
            return getValueList((ListValue) value);
        } else if (value instanceof FuncValue) {
            return ((FuncValue) value).getValue();
        }
        return value;
    }

    private static List<Object> getValueList(ListValue listValue) {
        if (listValue == null) return Collections.emptyList();
        List<Object> list = new ArrayList<>();
        for (Value item : listValue.getValue()) {
            list.add(getValue(item));
        }
        return list;
    }

    // Snake case 转换
    public static String asFieldName(String name) {
        return StringUtils.lowerCase(name.replaceAll("([a-z])([A-Z])", "$1_$2"));
    }

    /**
     * 从集合中提取字段值
     * @param data 集合或数组
     * @param field 字段名
     * @return 处理后的字符串列表
     */
    public static List<String> getFieldValues(Object data, String field) {
        if (!(data instanceof Collection)) {
            throw new IllegalArgumentException("Input is not a collection");
        }

        List<String> result = new ArrayList<>();
        for (Object item : (Collection<?>) data) {
            if (item == null) continue;

            try {
                Object value;
                if (item instanceof Map) {
                    value = ((Map<?, ?>) item).get(field);
                } else {
                    Field f = item.getClass().getDeclaredField(field);
                    f.setAccessible(true);
                    value = f.get(item);
                }

                result.add(formatValue(value));
            } catch (NoSuchFieldException | IllegalAccessException e) {
                throw new IllegalArgumentException("Error accessing field: " + field, e);
            }
        }
        return result;
    }

    private static String formatValue(Object value) {
        if (value instanceof String) {
            return "'" + value + "'";
        }
        return String.valueOf(value);
    }

}