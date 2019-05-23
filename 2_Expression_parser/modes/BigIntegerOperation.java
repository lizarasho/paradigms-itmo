package expression.modes;
import expression.exceptions.IllegalOperationException;

import java.math.BigInteger;

public class BigIntegerOperation extends Operation<BigInteger> {

    public BigInteger add(BigInteger first, BigInteger second) {
        return first.add(second);
    }

    private void checkZero(BigInteger x) throws IllegalOperationException {
        if (x.equals(BigInteger.ZERO)) {
            throw new IllegalOperationException("division by zero");
        }
    }

    private void checkNegative(BigInteger x) throws IllegalOperationException {
        if (x.signum() == -1) {
            throw new IllegalOperationException("division by negative");
        }
    }

    public BigInteger divide(BigInteger first, BigInteger second) throws IllegalOperationException {
        checkZero(second);
        return first.divide(second);
    }

    public BigInteger subtract(BigInteger first, BigInteger second) {
        return first.subtract(second);
    }

    public BigInteger negate(BigInteger first) {
        return first.negate();
    }

    public BigInteger multiply(BigInteger first, BigInteger second) {
        return first.multiply(second);
    }

    public BigInteger parseNumber(String s) {
        return new BigInteger(s);
    }

    public BigInteger abs(BigInteger first) {
        return first.abs();
    }

    public BigInteger square(BigInteger first) {
        return first.multiply(first);
    }

    public BigInteger mod(BigInteger first, BigInteger second) throws IllegalOperationException {
        checkZero(second);
        checkNegative(second);
        return first.mod(second);
    }
}
