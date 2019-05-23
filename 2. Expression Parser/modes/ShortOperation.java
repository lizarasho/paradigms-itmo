package expression.modes;
import expression.exceptions.IllegalOperationException;

public class ShortOperation extends Operation<Short> {
    public Short add(Short first, Short second) {
        return (short)(first + second);
    }

    private void checkZero(Short x) throws IllegalOperationException {
        if (x == 0) {
            throw new IllegalOperationException("division by zero");
        }
    }

    public Short divide(Short first, Short second) throws IllegalOperationException {
        checkZero(second);
        return (short)(first / second);
    }

    public Short subtract(Short first, Short second) {
        return (short)(first - second);
    }

    public Short negate(Short first) {
        return (short)(-first);
    }

    public Short multiply(Short first, Short second) {
        return (short)(first * second);
    }

    public Short parseNumber(String s) {
        return (short)Integer.parseInt(s);
    }

    public Short abs(Short first) {
        return (short)Math.abs(first);
    }

    public Short square(Short first) {
        return (short)(first * first);
    }

    public Short mod(Short first, Short second) {
        return (short)(first % second);
    }
}
