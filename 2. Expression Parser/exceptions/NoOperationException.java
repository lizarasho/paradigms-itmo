package expression.exceptions;

public class NoOperationException extends ParsingException {
    public NoOperationException(String expression, int index) {
        super("expected operation before position " + (index + 1) + "\n" + expression
                + "\n" + getPosition(index, 1));
    }
}