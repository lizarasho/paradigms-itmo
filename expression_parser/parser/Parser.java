package expression.parser;

import expression.exceptions.EvaluatingException;
import expression.exceptions.ParsingException;

public interface Parser<T> {
    TripleExpression<T> parse(String expression) throws ParsingException, EvaluatingException;
}