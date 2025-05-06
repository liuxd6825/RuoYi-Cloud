package com.ruoyi.common.web.rsql;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;




public class FuncValue implements Value {
    public static final String[] FUNC_TYPES = {"sub"};

    private String value;
    private String name;
    private String content;
    private Map<String, String> args;
    private String rsql;

    public FuncValue(String value, String name, String content,
                     Map<String, String> args, String rsql) {
        this.value = value;
        this.name = name;
        this.content = content;
        this.args = args;
        this.rsql = rsql;
    }


    public static FuncValue New(String value) throws IllegalArgumentException {
        int i = value.indexOf('(');
        if (i == -1) {
            throw new IllegalArgumentException("Invalid function format");
        }

        String name = value.substring(0, i).trim();
        String c = value.substring(i + 1);
        String content = c.substring(0, c.length() - 1);

        Map<String, String> args;
        try {
            args = parseQuery(content);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Failed to parse query: " + e.getMessage());
        }

        String rSql = args.get("rsql");
        return new FuncValue(value, name, content, args, rSql);
    }

    public String valueName() {
        return "func";
    }

    public String rSQL() {
        return rsql;
    }

    // Getters
    public String getValue() { return value; }
    public String getName() { return name; }
    public String getContent() { return content; }
    public Map<String, String> getArgs() { return args; }
    public String getRsql() { return rsql; }




    private static Map<String, Object> parseNonQuotedJSON(String query) throws JsonProcessingException {
        // 使用正则表达式将所有不带双引号的键转换为带双引号的键
        Pattern pattern = Pattern.compile("([a-zA-Z0-9_]+)\\s*:");
        String formattedQuery = pattern.matcher(query).replaceAll("\"$1\":");

        // 为了避免错误，确保 JSON 结构能正确解析
        formattedQuery = "{" + formattedQuery + "}";

        // 使用 Jackson 解析 JSON
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(formattedQuery, Map.class);
    }

    /**
     * 将类似 "table:orderItems,field:customerId,rsql:product=like=*book*" 转换为 Map<String, String>
     */
    public static Map<String, String> parseQuery(String query) throws IllegalArgumentException {
        Map<String, String> result = new HashMap<>();

        // 按 , 分割字符串
        String[] parts = query.split(",");

        // 遍历每一部分并处理
        for (String part : parts) {
            // 按 : 分割每个键值对
            String[] keyValue = part.split(":");
            if (keyValue.length != 2) {
                throw new IllegalArgumentException("Invalid key-value pair: " + part);
            }

            // 将键值对添加到 map 中
            String key = keyValue[0].trim();
            String val = keyValue[1].trim();
            result.put(key, val);
        }

        return result;
    }
}
