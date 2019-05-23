package operations;

import parser.Expression;

public class And extends AbstractBinaryOperation {
    public And(Expression x, Expression y) {
        super(x, y);
    }

    public int compute(int x, int y) {
        return x & y;
    }
}