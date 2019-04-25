package expression.exceptions;

public class IllegalParenthesisException extends ParsingException {
    public IllegalParenthesisException(int mode) {
        super("Expected more " + (mode == 1 ? "opening" : "closing") + " parenthesises");
    }
}