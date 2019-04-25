package exceptions;

public class IllegalNumberException extends ParsingException {
    public IllegalNumberException(String expression, int index, String number) {
        super("Expected binary number at position " + (index + 1) + "found " + number
                  + '\n' + expression + '\n' + getPosition(index, number.length()));
    }
}
