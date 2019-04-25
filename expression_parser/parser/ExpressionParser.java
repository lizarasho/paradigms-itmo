package expression.parser;

import expression.exceptions.EvaluatingException;
import expression.exceptions.ParsingException;
import expression.modes.Operation;
import expression.operations.*;
import expression.primitives.Const;
import expression.primitives.Variable;

public class ExpressionParser<T> implements Parser<T> {
    private MyTokenizer<T> tokenizer;
    private Operation<T> operation;

    public ExpressionParser(Operation<T> operation) {
        this.operation = operation;
    }

    public TripleExpression<T> parse(String s) throws ParsingException, EvaluatingException {
        tokenizer = new MyTokenizer<>(s, operation);
        return parseThirdPriority();
    }

    private TripleExpression<T> parseFirstPriority() throws ParsingException, EvaluatingException {
        TripleExpression<T> res = null;
        switch (tokenizer.nextToken()) {
            case NUMBER: {
                res = new Const<>(tokenizer.getValue());
                tokenizer.nextToken();
                break;
            }
            case VARIABLE: {
                res = new Variable<>(String.valueOf(tokenizer.getName()));
                tokenizer.nextToken();
                break;
            }
            case NEGATE: {
                res = new Negate<>(parseFirstPriority(), operation);
                break;
            }
            case ABS: {
                res = new Abs<>(parseFirstPriority(), operation);
                break;
            }
            case SQUARE: {
                res = new Square<>(parseFirstPriority(), operation);
                break;
            }
            case OPEN_BRACKET: {
                res = parseThirdPriority();
                tokenizer.nextToken();
                break;
            }
        }
        return res;
    }

    private TripleExpression<T> parseSecondPriority() throws ParsingException, EvaluatingException {
        TripleExpression<T> left = parseFirstPriority();
        while (true) {
            switch (tokenizer.currentToken()) {
                case MULTIPLY: {
                    left = new Multiply<>(left, parseFirstPriority(), operation);
                    break;
                }
                case DIVIDE: {
                    left = new Divide<>(left, parseFirstPriority(), operation);
                    break;
                }
                case MOD: {
                    left = new Mod<>(left, parseFirstPriority(), operation);
                    break;
                }
                default:
                    return left;
            }
        }
    }

    private TripleExpression<T> parseThirdPriority() throws ParsingException, EvaluatingException {
        TripleExpression<T> left = parseSecondPriority();
        while (true) {
            switch (tokenizer.currentToken()) {
                case SUBTRACT: {
                    left = new Subtract<>(left, parseSecondPriority(), operation);
                    break;
                }
                case ADD: {
                    left = new Add<>(left, parseSecondPriority(), operation);
                    break;
                }
                default:
                    return left;
            }
        }
    }
}