package expression.operations;

import expression.exceptions.EvaluatingException;
import expression.parser.TripleExpression;
import expression.modes.Operation;

public class Square<T> extends AbstractUnaryOperation<T> {
    public Square(TripleExpression<T> first, Operation<T> operation) {
        super(first, operation);
    }

    T compute(T x) throws EvaluatingException {
        return operation.square(x);
    }
}
