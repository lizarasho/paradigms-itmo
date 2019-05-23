package expression.operations;

import expression.exceptions.EvaluatingException;
import expression.parser.TripleExpression;
import expression.modes.*;

public class Add<T> extends AbstractBinaryOperation<T> {
    public Add(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        super(first, second, operation);
    }

    public T compute(T x, T y) throws EvaluatingException {
        return operation.add(x, y);
    }
}