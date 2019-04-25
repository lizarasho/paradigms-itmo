package expression.modes;


import expression.exceptions.IllegalOperationException;

public class ByteOperation extends Operation<Byte> {
    public Byte add(Byte first, Byte second) {
        return (byte)(first + second);
    }

    private void checkZero(Byte x) throws IllegalOperationException {
        if (x == 0) {
            throw new IllegalOperationException("division by zero");
        }
    }

    public Byte divide(Byte first, Byte second) throws IllegalOperationException {
        checkZero(second);

        return (byte)(first / second);
    }

    public Byte subtract(Byte first, Byte second) {
        return (byte)(first - second);
    }

    public Byte negate(Byte first) {
        return (byte)(-first);
    }

    public Byte multiply(Byte first, Byte second) {
        return (byte)(first * second);
    }

    public Byte parseNumber(String s) {
        return (byte)Integer.parseInt(s);
    }

    public Byte abs(Byte first) {
        return (byte)Math.abs(first);
    }

    public Byte square(Byte first) {
        return (byte)(first * first);
    }

    public Byte mod(Byte first, Byte second) throws IllegalOperationException {
        checkZero(second);
        return (byte)(first % second);
    }
}
