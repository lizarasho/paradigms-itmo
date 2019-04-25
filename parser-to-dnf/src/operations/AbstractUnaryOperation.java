package operations;
import parser.Expression;

public abstract class AbstractUnaryOperation implements Expression {
    private Expression first;

    AbstractUnaryOperation(Expression first) {
        this.first = first;
    }
    public int evaluate(int ... var) {
        return compute(first.evaluate(var));
    }

    public abstract int compute(int first);
}