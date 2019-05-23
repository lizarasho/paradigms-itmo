package parser;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import exceptions.*;


public class MyTokenizer {
    private String expression;
    private Token curToken;
    private int curPointer;
    private int bracketsCounter;
    private int value;
    private String name;
    private static Map<String, Token> binaryOperations = new HashMap<>();
    private static Map<String, Token> unaryTokens = new HashMap<>();
    static {
        binaryOperations.put("|", Token.OR);
        binaryOperations.put("&", Token.AND);
    }
    static {
        unaryTokens.put("~", Token.NOT);
        for (char c = 'a'; c <= 'z'; c++) {
            unaryTokens.put(String.valueOf(c), Token.VARIABLE);
        }
    }
    private static Set<Token> operations = EnumSet.of(
            Token.OR,
            Token.AND,
            Token.NOT);

    MyTokenizer(String expression) {
        this.expression = expression;
        curPointer = bracketsCounter = 0;
        curToken = Token.BEGIN;
    }

    Token nextToken() throws ParsingException{
        readToken();
        return curToken;
    }

    Token currentToken() {
        return curToken;
    }

    int getValue() {
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

    private void readToken() throws ParsingException {
        skipSpaces();
        if (isEnd()) {
            checkArgument(curPointer);
            if (bracketsCounter != 0) {
                throw new IncorrectParenthesisException(0);
            }
            return;
        }
        char symbol = expression.charAt(curPointer);
        switch (symbol) {
            case '~': {
                curToken = Token.NOT;
                break;
            }
            case '&':
            case '|':
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
                    throw new IncorrectParenthesisException(1);
                }
                curToken = Token.CLOSE_BRACKET;
                break;
            }
            default: {
                if (Character.isDigit(symbol)) {
                    checkOperation(curPointer);
                    String number = readNumber();
                    if (!number.equals("0") && !number.equals("1"))
                        throw new IllegalNumberException(expression, curPointer - number.length() + 1, number);
                    value = Integer.parseInt(number);
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
