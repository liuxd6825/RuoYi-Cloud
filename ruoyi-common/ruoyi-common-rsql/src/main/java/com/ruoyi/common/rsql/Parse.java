package com.ruoyi.common.rsql;

import java.util.ArrayList;
import java.util.List;

public class Parse {
    public static Expression parse(String input) throws Exception {
        // Create a lexer and parse the input string
        Lexer lexer = new Lexer(input);
        List<Token> items = lexer.parse();

        // If there was an error during parsing, throw an exception
        if (items == null) {
            throw new Exception("Lexer parsing error");
        }

        // Create an iterator from the token list and process it using the `or` function
        Iterator iterator = new Iterator(items);
        return or(iterator);
    }


    public static Expression or(Iterator tokens) throws Exception {
        // Process the tokens with the "OrToken" separator, and apply the "and" function for each section
        List<Expression> items = section(tokens, TokenType.OrToken, new ParserFunction() {
            @Override
            public Expression apply(Iterator tokens) throws Exception {
                return and(tokens);  // Call the 'constraint' method here
            }
        });

        if (items.isEmpty()) {
            // If no valid expressions were found, throw an error
            throw new Exception(String.format("Invalid 'or' section at position: %s", tokens.current()));
        } else if (items.size() == 1) {
            // If only one expression is found, return that expression
            return items.get(0);
        }

        // Otherwise, return an OrExpression containing all the parsed items
        return new OrExpression(items);
    }

    public static Expression and(Iterator tokens) throws Exception {
        // Parse the tokens based on the AndToken separator, applying the constraint function
        List<Expression> items = section(tokens, TokenType.AndToken,   new ParserFunction() {
            @Override
            public Expression apply(Iterator tokens) throws Exception {
                return constraint(tokens);  // Call the 'constraint' method here
            }
        });

        if (items.isEmpty()) {
            // If no valid items are found, throw an error
            throw new Exception(String.format("Invalid 'and' section at position: %s", tokens.current()));
        } else if (items.size() == 1) {
            // If only one item is found, return that expression directly
            return items.get(0);
        }

        // If multiple items are found, return an AndExpression containing all parsed items
        return new AndExpression(items);
    }

    public static Expression constraint(Iterator tokens) throws Exception {
        // If the current token is a LeftParenToken, parse it as a group
        if (tokens.current().type == TokenType.LeftParenToken) {
            return group(tokens);
        }
        // Otherwise, parse it as a comparison
        return comparison(tokens);
    }


    public static Expression group(Iterator tokens) throws Exception {
        tokens.currentAndMove(1); // Move past the '(' token
        int opened = 1;
        int idx = tokens.getIdx();

        // Iterate through the tokens to find matching closing parenthesis
        while (idx < tokens.getLength()) {
            Token token = tokens.get(idx);
            if (token.type == TokenType.LeftParenToken) {
                opened++;
            } else if (token.type == TokenType.RightParenToken) {
                opened--;
                if (opened == 0) {
                    break;
                }
            }
            idx++;
        }

        // If there are unmatched opening parentheses, throw an error
        if (opened > 0) {
            throw new Exception("Closed parentheses don't match");
        }

        // Create a new iterator for the tokens within the parentheses
        Iterator newIterator = new Iterator(tokens.getItems().subList(tokens.getIdx(), idx));
        tokens.currentAndMove(idx - tokens.getIdx()); // Move the token index forward after processing the group

        // Process the group using the 'or' method (or other methods depending on context)
        return or(newIterator);
    }

    public static List<Expression> section(Iterator tokens, TokenType separator, ParserFunction apply) throws Exception {
        List<Expression> result = new ArrayList<>();
        int idx = tokens.getIdx();
        int cursor = tokens.getIdx();
        int opened = 0;

        // Iterate over the tokens
        while (idx < tokens.getLength()) {
            Token c = tokens.get(idx);

            // Track parentheses
            if (c.type == TokenType.LeftParenToken) {
                opened++;
            } else if (c.type == TokenType.RightParenToken) {
                opened--;
                if (opened < 0) {
                    throw new Exception("Invalid parentheses");
                }
            } else if (c.type == separator && opened == 0) {
                // When we reach a separator (e.g., 'OR', 'AND') and parentheses are balanced
                List<Token> items = tokens.getItems().subList(cursor, idx);
                Expression next = apply.apply(new Iterator(items)); // Apply the function to this section
                result.add(next);
                cursor = idx + 1;  // Move cursor to the next token after separator
            }
            idx++;  // Move to the next token
        }

        // Handle the last section of tokens
        if (idx > cursor) {
            List<Token> items = tokens.getItems().subList(cursor, idx);
            Expression next = apply.apply(new Iterator(items));
            result.add(next);
        }

        // Adjust the token index
        tokens.currentAndMove(cursor - tokens.getIdx());

        return result;
    }

    public static Expression comparison(Iterator tokens) throws Exception {
        // Get the identifier
        Identifier id = identifier(tokens);

        // Get the comparator token (like EqualsToken, NotEqualsToken, etc.)
        Token comparator = tokens.currentAndMove(1);

        // Get the arguments for the comparison (e.g., values for equals, greater than)
        Value args = arguments(tokens);

        // Switch on the comparator type to handle each comparison type
        switch (comparator.type) {
            case NotContainsToken:
                return new NotContainsComparison(id, args);
            case ContainsToken:
                return new ContainsComparison(id, args);
            case EqualsToken:
                return new EqualsComparison(id, args);
            case NotEqualsToken:
                return new NotEqualsComparison(id, args);
            case LikeToken:
                return new LikeComparison(id, args);
            case NotLikeToken:
                return new NotLikeComparison(id, args);
            case GreaterToken:
                return new GreaterThanComparison(id, args);
            case GreaterOrEqualsToken:
                return new GreaterThanOrEqualsComparison(id, args);
            case LessToken:
                return new LessThanComparison(id, args);
            case LessOrEqualsToken:
                return new LessThanOrEqualsComparison(id, args);
            case InToken:
                return new InComparison(id, args);
            case NotInToken:
                // Handle the 'NotIn' comparison
                ListValue lv = (args instanceof ListValue) ? (ListValue) args : new ListValue(List.of(args));
                return new NotInComparison(id, lv);
            case NotIsNullToken:
                return new NotIsNullComparison(id, args);
            case IsNullToken:
                return new IsNullComparison(id, args);
            case StartToken:
                return new StartComparison(id, args);
            case EndToken:
                return new EndComparison(id, args);
            case FuncToken:
                return new FuncComparison(id, args);
            default:
                throw new Exception("Comparator not managed for expression: " + comparator.type);
        }
    }

    public static Identifier identifier(Iterator tokens) throws Exception {
        // Get the current token
        Token token = tokens.currentAndMove(1);  // Move past the identifier token

        // Check if the token is of type IdentifierToken
        if (token.type != TokenType.IdentifierToken) {
            throw new Exception("Must be an Identifier");
        }

        // Return the identifier as an Identifier object
        return new Identifier(token.value);
    }

    public static Value arguments(Iterator tokens) throws Exception {
        // If the current token is a LeftParenToken, process it as a list of values
        if (tokens.current().type == TokenType.LeftParenToken) {
            return valueList(tokens);  // Call valueList to process the list of values
        } else {
            // Otherwise, process it as a single value
            return value(tokens);  // Call value to process a single value
        }
    }

    public static Value valueList(Iterator tokens) throws Exception {
        tokens.currentAndMove(1);  // Move past '('
        List<Value> items = new ArrayList<>();
        Token current = tokens.current();

        // Iterate through tokens until the closing parenthesis
        while (current.type != TokenType.RightParenToken) {
            Value v = value(tokens);  // Process each value
            items.add(v);

            // Move to the next token
            current = tokens.currentAndMove(1);

            // Check if the next token is a right parenthesis or a comma
            if (current.type != TokenType.RightParenToken && current.type != TokenType.CommaToken) {
                throw new Exception("Invalid list format, next must be a comma or Right Parent");
            }
        }

        // Return a new ListValue with the collected items
        return new ListValue(items);
    }

    public static Value value(Iterator tokens) throws Exception {
        Token v = tokens.currentAndMove();  // Move to the next token
        switch (v.type) {
            case FuncToken:
                return FuncValue.New(v.value);  // Handle function token
            case StringToken:
                return new StringValue(v.value);  // Handle string token
            case BooleanToken:
                return new BooleanValue(v.value.equals("true"));  // Handle boolean token
            case DoubleToken:
                try {
                    double c = Double.parseDouble(v.value);  // Parse double
                    return new DoubleValue(c);
                } catch (NumberFormatException e) {
                    throw new Exception("Invalid double value: " + v.value);
                }
            case IntegerToken:
                try {
                    long c = Long.parseLong(v.value);  // Parse integer
                    return new IntegerValue(c);
                } catch (NumberFormatException e) {
                    throw new Exception("Invalid integer value: " + v.value);
                }
            case DateToken:
                return new DateValue(v.value);  // Handle date token
            case DateTimeToken:
                return new DateTimeValue(v.value);  // Handle datetime token
            default:
                throw new Exception("Invalid type: " + v.type);  // Handle unrecognized token type
        }
    }

}
