package expression.modes;

import expression.exceptions.EvaluatingException;

public abstract class Operation<T> {
    public abstract T add(T first, T second) throws EvaluatingException;
    public abstract T divide (T first, T second) throws EvaluatingException;
    public abstract T subtract(T first, T second) throws EvaluatingException;
    public abstract T negate(T first) throws EvaluatingException;
    public abstract T multiply(T first, T second) throws EvaluatingException;
    public abstract T abs(T first) throws EvaluatingException;
    public abstract T square(T first) throws EvaluatingException;
    public abstract T mod(T first, T second) throws EvaluatingException;
    public abstract T parseNumber(String s) throws EvaluatingException;
}
