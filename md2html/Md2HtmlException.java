package md2html;

public class Md2HtmlException extends Throwable {
    private int pos;
    private int line;

    public Md2HtmlException(int line, int pos, String message) {
        super(message);
        this.line = line;
        this.pos = pos;
    }

    public Md2HtmlException(final String message) {
        super(message);
    }

    public int getPosition() {
        return pos;
    }

    public int getLine() {
        return line;
    }
}
