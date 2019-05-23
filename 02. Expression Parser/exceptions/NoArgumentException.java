package expression.exceptions;

public class NoArgumentException extends ParsingException {
    public NoArgumentException(final String s, final int ind) {
        super("expected argument before position " + (ind + 1) + "\n" + s + "\n" + getPosition(ind, 1));
    }
}
