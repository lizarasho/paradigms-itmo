package primitives;

import parser.Expression;

public class Const implements Expression {
    private int value;

    public Const(int value) {
        this.value = value;
    }

    public int evaluate(int ... var) {
        return value;
    }
}
