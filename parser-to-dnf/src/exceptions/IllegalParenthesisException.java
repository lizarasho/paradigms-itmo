package exceptions;

public class IllegalParenthesisException extends ParsingException {
    public IllegalParenthesisException(String message) {
        super(message);
    }
}