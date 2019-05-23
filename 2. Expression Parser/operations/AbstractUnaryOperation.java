package expression.operations;

import expression.exceptions.EvaluatingException;
import expression.parser.TripleExpression;
import expression.modes.Operation;

public abstract class AbstractUnaryOperation<T> implements TripleExpression<T> {
    private TripleExpression<T> first;
    protected Operation<T> operation;

    public AbstractUnaryOperation(TripleExpression<T> first, Operation<T> operation) {
        this.first = first;
        this.operation = operation;
    }
    public T evaluate(T x, T y, T z) throws EvaluatingException{
        return compute(first.evaluate(x, y, z));
    }
    abstract T compute(T x) throws EvaluatingException;
}