package operations;

import parser.Expression;

public class Not extends AbstractUnaryOperation {
    public Not(Expression x) {
        super(x);
    }

    public int compute(int x) {
        return 1 - x;
    }
}