package expression.operations;

import expression.exceptions.EvaluatingException;
import expression.parser.TripleExpression;
import expression.modes.Operation;

public abstract class AbstractBinaryOperation <T> implements TripleExpression<T> {
    private TripleExpression<T> first, second;
    protected Operation<T> operation;

    public AbstractBinaryOperation(TripleExpression<T> first, TripleExpression<T> second, Operation<T> operation) {
        this.first = first;
        this.second = second;
        this.operation = operation;
    }

    public T evaluate(final T x, final T y, final T z) throws EvaluatingException {
        return compute(first.evaluate(x, y, z), second.evaluate(x, y, z));
    }

    abstract T compute(T x, T y) throws EvaluatingException;
}