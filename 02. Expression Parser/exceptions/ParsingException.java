package expression.exceptions;

public class ParsingException extends Exception {
    public ParsingException(String message) {
        super(message);
    }

    static protected String getPosition(final int ind, final int size) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < ind; i++) {
            res.append(' ');
        }
        for (int i = 0; i < size; i++) {
            res.append('^');
        }
        return res.toString();
    }
}
