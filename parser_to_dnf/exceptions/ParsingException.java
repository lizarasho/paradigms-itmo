package exceptions;

public class ParsingException extends Exception {
    public ParsingException(String message) {
        super(message);
    }

    static protected String getPosition(int index, int length) {
        StringBuilder res = new StringBuilder();
        for (int i = 0; i < index; i++) {
            res.append(' ');
        }
        for (int i = 0; i < length; i++) {
            res.append('^');
        }
        return res.toString();
    }
}
