package operations;
import parser.Expression;

public class And extends AbstractBinaryOperation {
    public And(Expression first, Expression second) {
        super(first, second);
    }

    public int compute(int first, int second) {
        return first & second;
    }

}