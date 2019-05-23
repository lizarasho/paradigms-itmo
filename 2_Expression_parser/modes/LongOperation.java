package expression.modes;


import expression.exceptions.IllegalOperationException;

public class LongOperation extends Operation<Long> {
    public Long add(Long first, Long second) {
        return first + second;
    }

    private void checkZero(Long x) throws IllegalOperationException {
        if (x == 0) {
            throw new IllegalOperationException("division by zero");
        }
    }

    public Long divide(Long first, Long second) throws IllegalOperationException {
        checkZero(second);
        return first / second;
    }

    public Long subtract(Long first, Long second) {
        return first - second;
    }

    public Long negate(Long first)  {
        return -first;
    }

    public Long multiply(Long first, Long second) {
        return first * second;
    }

    public Long parseNumber(String s) {
        return Long.parseLong(s);
    }

    public Long abs(Long first) {
        return Math.abs(first);
    }

    public Long square(Long first) {
        return first * first;
    }

    public Long mod(Long first, Long second) {
        return first % second;
    }
}
