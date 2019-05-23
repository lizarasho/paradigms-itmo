package expression.exceptions;

public class IllegalNumberException extends ParsingException {
    public IllegalNumberException(String expression, int index, String number) {
        super("illegal format of number " + number + " at position " + (index + 1) +
                + '\n' + expression + '\n' + getPosition(index, number.length()));
    }
}
