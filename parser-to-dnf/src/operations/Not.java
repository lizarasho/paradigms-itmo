package operations;
import parser.Expression;

public class Not extends AbstractUnaryOperation {
    public Not(Expression first) {
        super(first);
    }

    public int compute(int first) {
        return 1 - first;
    }
}