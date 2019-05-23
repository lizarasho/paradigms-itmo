package expression.operations;

import expression.exceptions.EvaluatingException;
import expression.parser.TripleExpression;
import expression.modes.Operation;

public class Mod<T> extends AbstractBinaryOperation<T> {
    public Mod(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        super(first, second, operation);
    }

    T compute(T x, T y) throws EvaluatingException {
        return operation.mod(x, y);
    }
}
