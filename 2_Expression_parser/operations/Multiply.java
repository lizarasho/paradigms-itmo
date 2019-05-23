package expression.operations;

import expression.exceptions.EvaluatingException;
import expression.parser.TripleExpression;
import expression.modes.Operation;

public class Multiply<T> extends AbstractBinaryOperation<T> {
    public Multiply(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        super(first, second, operation);
    }

    public T compute(T x, T y) throws EvaluatingException {
        return operation.multiply(x, y);

    }
}
