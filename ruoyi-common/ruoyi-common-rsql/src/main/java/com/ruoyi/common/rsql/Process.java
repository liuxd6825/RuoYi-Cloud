package com.ruoyi.common.rsql;


import java.util.List;

import static com.ruoyi.common.rsql.Parse.or;

public class Process {

    public static void parseProcess(Expression expr, IProcess process) throws Exception {
        if (expr instanceof IValueComparison) {
            IValueComparison v = (IValueComparison) expr;
            Value val = v.getValue();
            if (val instanceof FuncValue) {
                FuncValue fn = (FuncValue) val;
                val = process.onFnProcess(expr, fn);
                if (val == null) {
                    return;
                }
                v.setValue(val);
            }
        }

        if (expr instanceof FuncComparison) {
            FuncComparison ex = (FuncComparison) expr;
            System.out.println(ex); // Assuming FuncComparison has a toString method for representation
        } else if (expr instanceof AndExpression) {
            AndExpression ex = (AndExpression) expr;
            process.onAndStart();
            for (int i = 0; i < ex.getItems().size(); i++) {
                parseProcess(ex.getItems().get(i), process);
                if (i < ex.getItems().size() - 1) {
                    process.onAndItem();
                }
            }
            process.onAndEnd();
        } else if (expr instanceof OrExpression) {
            OrExpression ex = (OrExpression) expr;
            process.onOrStart();
            for (int i = 0; i < ex.getItems().size(); i++) {
                parseProcess(ex.getItems().get(i), process);
                if (i < ex.getItems().size() - 1) {
                    process.onOrItem();
                }
            }
            process.onOrEnd();
        } else if (expr instanceof NotEqualsComparison) {
            NotEqualsComparison ex = (NotEqualsComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onNotEquals(name, value, ex.getVal());
        } else if (expr instanceof EqualsComparison) {
            EqualsComparison ex = (EqualsComparison) expr;
            String name = ex.expressionName();
            Object value = Utils.getValue(ex.getVal());
            process.onEquals(name, value, ex.getVal());
        } else if (expr instanceof LikeComparison) {
            LikeComparison ex = (LikeComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onLike(name, value, ex.getVal());
        } else if (expr instanceof NotLikeComparison) {
            NotLikeComparison ex = (NotLikeComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value =Utils.getValue(ex.getVal());
            process.onNotLike(name, value, ex.getVal());
        } else if (expr instanceof GreaterThanComparison) {
            GreaterThanComparison ex = (GreaterThanComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onGreaterThan(name, value, ex.getVal());
        } else if (expr instanceof GreaterThanOrEqualsComparison) {
            GreaterThanOrEqualsComparison ex = (GreaterThanOrEqualsComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onGreaterThanOrEquals(name, value, ex.getVal());
        } else if (expr instanceof LessThanComparison) {
            LessThanComparison ex = (LessThanComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onLessThan(name, value, ex.getVal());
        } else if (expr instanceof LessThanOrEqualsComparison) {
            LessThanOrEqualsComparison ex = (LessThanOrEqualsComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onLessThanOrEquals(name, value, ex.getVal());
        } else if (expr instanceof InComparison) {
            InComparison ex = (InComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onIn(name, value, ex.getVal());
        } else if (expr instanceof NotInComparison) {
            NotInComparison ex = (NotInComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onNotIn(name, value, ex.getVal());
        } else if (expr instanceof ContainsComparison) {
            ContainsComparison ex = (ContainsComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onContains(name, value, ex.getVal());
        } else if (expr instanceof NotContainsComparison) {
            NotContainsComparison ex = (NotContainsComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onNotContains(name, value, ex.getVal());
        } else if (expr instanceof NotIsNullComparison) {
            NotIsNullComparison ex = (NotIsNullComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onNotIsNull(name, value, ex.getVal());
        } else if (expr instanceof IsNullComparison) {
            IsNullComparison ex = (IsNullComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onIsNull(name, value, ex.getVal());
        } else if (expr instanceof StartComparison) {
            StartComparison ex = (StartComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onStart(name, value, ex.getVal());
        } else if (expr instanceof EndComparison) {
            EndComparison ex = (EndComparison) expr;
            String name = ex.getIdentifier().getVal();
            Object value = Utils.getValue(ex.getVal());
            process.onEnd(name, value, ex.getVal());
        }
    }

    public static void parse(String input,IProcess process) throws Exception {
        if (input.isEmpty()) {
            return;
        }
        Expression expr = parse(input);
        if (expr == null) {
            throw new Exception(input + " expression error");
        }
        parseProcess(expr, process);
    }

    public static Expression parse(String input) throws Exception {
        // Create a new lexer and parse the input string
        Lexer lexer = new Lexer(input);
        List<Token> items = lexer.parse();  // Parse the input string into tokens

        // If there was an error during parsing, throw an exception
        if (items == null) {
            throw new Exception("Lexer parsing error");
        }

        // Create an iterator from the parsed tokens and process them using the 'or' method
        Iterator iterator = new Iterator(items);
        return or(iterator);  // Call the 'or' function to process the tokens
    }


}
