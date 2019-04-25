package expression.parser;

import expression.modes.Operation;
import expression.exceptions.*;
import java.util.Map;
import java.util.HashMap;
import java.util.Set;
import java.util.EnumSet;


class MyTokenizer <T> {
    private String expression;
    private Token curToken;
    private int curPointer;
    private int bracketsCounter;
    private T value;
    private Operation<T> operation;
    private String name;
    private static Map<String, Token> binaryOperations = new HashMap<>();
    private static Map<String, Token> unaryTokens = new HashMap<>();
    static {
        binaryOperations.put("+", Token.ADD);
        binaryOperations.put("-", Token.SUBTRACT);
        binaryOperations.put("*", Token.MULTIPLY);
        binaryOperations.put("/", Token.DIVIDE);
        binaryOperations.put("mod", Token.MOD);
    }
    static {
        unaryTokens.put("-", Token.NEGATE);
        unaryTokens.put("high", Token.HIGH);
        unaryTokens.put("low", Token.LOW);
        unaryTokens.put("x", Token.VARIABLE);
        unaryTokens.put("y", Token.VARIABLE);
        unaryTokens.put("z", Token.VARIABLE);
        unaryTokens.put("abs", Token.ABS);
        unaryTokens.put("square", Token.SQUARE);
    }
    private static Set<Token> operations = EnumSet.of(
            Token.NEGATE,
            Token.ADD,
            Token.SUBTRACT,
            Token.MOD,
            Token.MULTIPLY,
            Token.DIVIDE,
            Token.LOW,
            Token.HIGH);

    MyTokenizer(String expression, Operation<T> operation) {
        this.operation = operation;
        this.expression = expression;
        curPointer = bracketsCounter = 0;
        curToken = Token.BEGIN;

    }

    Token currentToken() {
        return curToken;
    }

    Token nextToken() throws ParsingException, EvaluatingException{
        readToken();
        return curToken;
    }

    T getValue() {
        return value;
    }

    String getName() {
        return name;
    }

    private String readNumber() {
        int beginIndex = curPointer;
        while (curPointer < expression.length() && (Character.isDigit(expression.charAt(curPointer)) ||
                expression.charAt(curPointer) == '.')) {
            curPointer++;
        }
        if (curPointer < expression.length()) {
            return expression.substring(beginIndex, curPointer--);
        }
        return expression.substring(beginIndex, curPointer);
    }

    private void checkOperation(int position) throws NoOperationException {
        if (hasNoOperation()) {
            throw new NoOperationException(expression, position);
        }
    }

    private void checkArgument(int position) throws NoArgumentException {
        if (curToken == Token.BEGIN || curToken == Token.OPEN_BRACKET || operations.contains(curToken)) {
            throw new NoArgumentException(expression, position);
        }
    }

    private boolean hasNoOperation() {
        return (curToken == Token.NUMBER || curToken == Token.VARIABLE || curToken == Token.CLOSE_BRACKET);
    }

    private String readName() throws IllegalLexemeException {
        if (!Character.isLetter(expression.charAt(curPointer))) {
            throw new IllegalLexemeException(Character.toString(expression.charAt(curPointer)), expression, curPointer);
        }
        int beginIndex = curPointer;
        while (curPointer < expression.length()
                && (Character.isLetterOrDigit(expression.charAt(curPointer))
                || expression.charAt(curPointer) == '_')) {
            curPointer++;
        }
        return expression.substring(beginIndex, curPointer--);
    }

    private void skipSpaces() {
        while (curPointer < expression.length() &&
                Character.isWhitespace(expression.charAt(curPointer))) {
            curPointer++;
        }
    }

    private boolean isEnd() {
        return curPointer > expression.length() - 1;
    }

    private void readToken() throws ParsingException, EvaluatingException {
        skipSpaces();
        if (isEnd()) {
            checkArgument(curPointer);
            if (bracketsCounter != 0) {
                throw new IllegalParenthesisException(0);
            }
            return;
        }
        char symbol = expression.charAt(curPointer);
        switch (symbol) {
            case '-': {
                if (hasNoOperation()) {
                    curToken = Token.SUBTRACT;
                } else {
                    curPointer++;
                    if (isEnd())
                        throw new NoArgumentException(expression, curPointer);
                    if (Character.isDigit(expression.charAt(curPointer))) {
                        String number = readNumber();
                        try {
                            value = operation.parseNumber("-" + number);
                        } catch (NumberFormatException e) {
                            throw new IllegalNumberException(expression, curPointer - number.length() + 1, number);
                        }
                        curToken = Token.NUMBER;
                    } else {
                        curToken = Token.NEGATE;
                        curPointer--;
                    }
                }
                break;
            }
            case '+':
            case '*':
            case '/': {
                checkArgument(curPointer);
                curToken = binaryOperations.get(String.valueOf(symbol));
                break;
            }
            case '(': {
                checkOperation(curPointer);
                bracketsCounter++;
                curToken = Token.OPEN_BRACKET;
                break;
            }
            case ')': {
                checkArgument(curPointer);
                bracketsCounter--;
                if (bracketsCounter < 0) {
                    throw new IllegalParenthesisException(1);
                }
                curToken = Token.CLOSE_BRACKET;
                break;
            }
            default: {
                if (Character.isDigit(symbol)) {
                    checkOperation(curPointer);
                    String number = readNumber();
                    try {
                        value = operation.parseNumber(number);
                    } catch (NumberFormatException e) {
                        throw new IllegalNumberException(expression, curPointer - number.length() + 1, number);
                    }
                    curToken = Token.NUMBER;
                    break;
                } else {
                    String curLetters = readName();
                    if (unaryTokens.containsKey(curLetters)) {
                        checkOperation(curPointer);
                        curToken = unaryTokens.get(curLetters);
                        if (curToken == Token.VARIABLE)
                            name = curLetters;
                    } else {
                        if (binaryOperations.containsKey(curLetters)) {
                            checkArgument(curPointer);
                            curToken = binaryOperations.get(curLetters);
                        } else {
                                throw new IllegalLexemeException(curLetters, expression, curPointer - curLetters.length() + 1);
                        }
                    }
                    break;
                }
            }
        }
        curPointer++;
    }
}