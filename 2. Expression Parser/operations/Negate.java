package expression.operations;

import expression.exceptions.EvaluatingException;
import expression.parser.TripleExpression;
import expression.modes.Operation;

public class Negate<T> extends AbstractUnaryOperation<T> {
    public Negate(TripleExpression<T> first, Operation<T> operation) {
        super(first, operation);
    }

    public T compute(T x) throws EvaluatingException {
        return operation.negate(x);
    }
}
