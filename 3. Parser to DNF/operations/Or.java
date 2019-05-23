package operations;

import parser.Expression;

public class Or extends AbstractBinaryOperation {

    public Or(Expression x, Expression y) {
        super(x, y);
    }

    public int compute(int x, int y) {
        return x | y;
    }
}
