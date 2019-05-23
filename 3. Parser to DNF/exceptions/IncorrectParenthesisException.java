package exceptions;

public class IncorrectParenthesisException extends ParsingException {
    public IncorrectParenthesisException(int mode) {
        super("Expected more " + (mode == 1 ? "opening" : "closing") + " parenthesises");
    }
}