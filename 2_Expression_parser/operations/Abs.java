package expression.operations;

import expression.exceptions.EvaluatingException;
import expression.parser.TripleExpression;
import expression.modes.Operation;

public class Abs<T> extends AbstractUnaryOperation<T> {
    public Abs(TripleExpression first, Operation operation) {
        super(first, operation);
    }

    public T compute(T x) throws EvaluatingException {
        return operation.abs(x);
    }
}
