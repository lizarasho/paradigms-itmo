package exceptions;

public class IllegalNumberException extends ParsingException {
    public IllegalNumberException(String number, String expression, int index) {
        super("expected binary number at position " + (index + 1) + ", found "+ number + '\n' +
                expression + '\n' + getPosition(index, number.length()));
    }
}
