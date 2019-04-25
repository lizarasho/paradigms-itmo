package expression.exceptions;

public class IllegalLexemeException extends ParsingException {
    public IllegalLexemeException(String lexeme, String expression, int index) {
        super("unknown lexeme '" + lexeme + "' at position " + (index + 1) + "\n" + expression
                + "\n" + getPosition(index, lexeme.length()));
    }
}
