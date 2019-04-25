package operations;
import parser.Expression;

public class Or extends AbstractBinaryOperation {
    public Or(Expression first, Expression second) {
        super(first, second);
    }

    public int compute(int first, int second) {
        return first | second;
    }
}
