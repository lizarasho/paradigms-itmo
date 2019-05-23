package exceptions;

public class NoArgumentException extends ParsingException {
    public NoArgumentException(String expression, int index) {
        super("Expected argument before position " + (index + 1) + '\n' + expression
                + '\n' + getPosition(index, 1));
    }
}
