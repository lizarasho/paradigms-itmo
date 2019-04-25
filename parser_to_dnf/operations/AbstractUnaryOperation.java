package operations;

import parser.Expression;

public abstract class AbstractUnaryOperation implements Expression {
    private Expression first;

    AbstractUnaryOperation(Expression first) {
        this.first = first;
    }

    public abstract int compute(int x);

    public int evaluate(int ... var) {
        return compute(first.evaluate(var));
    }
}