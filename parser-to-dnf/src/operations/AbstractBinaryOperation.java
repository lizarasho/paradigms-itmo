package operations;
import parser.Expression;

public abstract class AbstractBinaryOperation implements Expression {
    private Expression first, second;

    AbstractBinaryOperation(Expression first, Expression second) {
        this.first = first;
        this.second = second;
    }

    public int evaluate(int ... var) {
        return compute(first.evaluate(var), second.evaluate(var));
    }

    public abstract int compute(int first, int second);
}