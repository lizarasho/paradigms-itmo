package exceptions;

public class NoOperationException extends ParsingException {
    public NoOperationException(final String expression, final int index) {
        super("Expected operation before position " + (index + 1) + "\n" +
                expression + "\n" + getPosition(index, 1));
    }
}