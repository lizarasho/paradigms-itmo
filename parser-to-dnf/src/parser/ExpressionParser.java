package parser;

import exceptions.*;
import operations.*;
import primitives.*;
import java.util.Set;
import java.util.TreeSet;
import java.util.List;
import java.util.ArrayList;

public class ExpressionParser implements Parser {
    private MyTokenizer tokenizer;

    public Expression parse(String s) throws ParsingException {
        tokenizer = new MyTokenizer(s);
        return parseThirdPriority();
    }

    public String toDNF(String s) throws ParsingException {
        Set<Character> setVariables = new TreeSet<>();
        for (char c : s.toCharArray()) {
            if (Character.isLetter(c))
                setVariables.add(c);
        }

        List<Character> activeVariables = new ArrayList<>(setVariables);
        List<Character> allVariables = new ArrayList<>();
        for (char c = 'a'; c <= 'z'; c++) {
            allVariables.add(c);
        }

        int size = setVariables.size();
        int[] bytes = new int[activeVariables.size()];
        int[] allBytes = new int[allVariables.size()];

        int number = 1 << size;
        StringBuilder dnf = new StringBuilder();
        // size - количество действующих переменных
        // activeVariables - массив действующих переменных
        // allVariable - массив всех возможных переменных

        boolean firstClause = true;

        for (int mask = 0; mask < number; mask++) {
            for (int i = size -  1; i >= 0; i--) {
                // (size - i - 1) — номер рассматриваемой переменной среди activeVariables
                // activeVariables.get(size - i - 1) — рассматриваемая переменная
                // allVariable.indexOf(activeVariables.get(size - i - 1)) — номер этой переменной среди всех
                allBytes[allVariables.indexOf(activeVariables.get(size - i - 1))] = (mask >> i) % 2;
                bytes[size - i - 1] = (mask >> i) % 2;
            }
            if (parse(s).evaluate(allBytes) == 1) {
                if (!firstClause) {
                    dnf.append("|");
                }
                for (int i = 0; i < size; i++) {
                    if (i > 0) {
                        dnf.append("&");
                    }
                    if (bytes[i] == 0) {
                        dnf.append("~");
                    }
                    dnf.append(activeVariables.get(i));
                }
                firstClause = false;
            }
        }
        return dnf.toString();
    }


    private Expression parseFirstPriority() throws ParsingException {
        Expression res = new Const(0);
        switch (tokenizer.nextToken()) {
            case NUMBER: {
                res = new Const(tokenizer.getValue());
                tokenizer.nextToken();
                break;
            }
            case VARIABLE: {
                res = new Variable(String.valueOf(tokenizer.getName()));
                tokenizer.nextToken();
                break;
            }
            case NOT: {
                res = new Not(parseFirstPriority());
                break;
            }
            case OPEN_BRACKET: {
                res = parseThirdPriority() ;
                tokenizer.nextToken();
                break;
            }
        }
        return res;
    }

    private Expression parseSecondPriority() throws ParsingException {
        Expression left = parseFirstPriority();
        while (true) {
            if (tokenizer.currentToken() == Token.AND) {
                left = new And(left, parseFirstPriority());
            } else {
                return left;
            }
        }
    }

    private Expression parseThirdPriority() throws ParsingException {
        Expression left = parseSecondPriority();
        while (true) {
            if (tokenizer.currentToken() == Token.OR) {
                left = new Or(left, parseSecondPriority());
            } else {
                return left;
            }
        }
    }

}