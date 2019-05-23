package expression.modes;

import expression.exceptions.IllegalOperationException;
import expression.exceptions.OverflowException;

public class IntegerOperation extends Operation<Integer> {
    private boolean needCheck;
    public IntegerOperation(boolean needCheck) {
        this.needCheck = needCheck;
    }

    private void checkAdd(Integer x, Integer y) throws OverflowException {
        if (x > 0 && Integer.MAX_VALUE - x < y || x < 0 && Integer.MIN_VALUE - x > y) {
            throw new OverflowException(x + " + " + y);
        }
    }

    public Integer add(Integer x, Integer y) throws OverflowException {
        if (needCheck) {
            checkAdd(x, y);
        }
        return x + y;
    }

    private void checkDivide(Integer x, Integer y) throws OverflowException {
        if (x == Integer.MIN_VALUE && y == -1) {
            throw new OverflowException(x + " / " + y);
        }
    }

    private void checkZero(Integer z) throws IllegalOperationException {
        if (z == 0) {
            throw new IllegalOperationException("division by zero");
        }
    }

    public Integer divide(Integer x, Integer y) throws OverflowException, IllegalOperationException {
        checkZero(y);
        if (needCheck) {
            checkDivide(x, y);
        }
        return x / y;
    }

    private void checkSubtract(Integer x, Integer y) throws OverflowException {
        if (x >= 0 && y < 0 && x - Integer.MAX_VALUE > y
                || x <= 0 && y > 0 && Integer.MIN_VALUE - x > -y) {
            throw new OverflowException(x + " - " + y);
        }
    }

    public Integer subtract(Integer x, Integer y) throws OverflowException {
        if (needCheck) {
            checkSubtract(x, y);
        }
        return x - y;
    }

    private void checkNegate(Integer x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("- " + x);
        }
    }

    public Integer negate(Integer x) throws OverflowException {
        if (needCheck) {
            checkNegate(x);
        }
        return -x;
    }

    private void checkMultiply(Integer x, Integer y) throws OverflowException {
        if (x > 0 && y > 0 && Integer.MAX_VALUE / x < y
                || x > 0 && y < 0 && Integer.MIN_VALUE / x > y
                || x < 0 && y > 0 && Integer.MIN_VALUE / y > x
                || x < 0 && y < 0 && Integer.MAX_VALUE / x > y) {
            throw new OverflowException(x + " * " + y);
        }
    }

    public Integer multiply(Integer x, Integer y) throws OverflowException{
        if (needCheck) {
            checkMultiply(x, y);
        }
        return x * y;
    }

    public Integer parseNumber(String s) {
        return Integer.parseInt(s);
    }

    private void checkAbs(Integer x) throws OverflowException {
        if (x == Integer.MIN_VALUE) {
            throw new OverflowException("abs " + x);
        }
    }

    public Integer abs(Integer x) throws OverflowException {
        if (needCheck) {
            checkAbs(x);
        }
        return Math.abs(x);
    }

    private void checkSquare(Integer x) throws OverflowException {
        if (x == Integer.MIN_VALUE || abs(x) >= Math.sqrt(Integer.MAX_VALUE)) {
            throw new OverflowException("square " + x);
        }
    }

    public Integer square(Integer x) throws OverflowException {
        if (needCheck) {
            checkSquare(x);
        }
        return x * x;
    }

    public Integer mod(Integer first, Integer second) throws IllegalOperationException {
        checkZero(second);
        return first % second;
    }
}
