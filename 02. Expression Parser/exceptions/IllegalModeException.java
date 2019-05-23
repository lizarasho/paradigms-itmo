package expression.exceptions;

public class IllegalModeException extends EvaluatingException {
    public IllegalModeException(String message) {
        super("unknown mode \'" + message + "\'");
    }
}
