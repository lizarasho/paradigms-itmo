package expression.primitives;

import expression.parser.TripleExpression;

public class Variable<T> implements TripleExpression<T> {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public T evaluate(T x, T y, T z) {
        switch (name) {
            case "x": {
                return x;
            }
            case "y": {
                return y;
            }
            case "z": {
                return z;
            }
        }
        return null;
    }
}