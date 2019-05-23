package md2html;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class SourceReader {
    public static char END = '\0';

    private char c;
    private Reader reader;

    protected int pos;
    protected int line = 1;
    protected int posInLine;

    public SourceReader(String fileName) throws Md2HtmlException {
        try {
            reader = new BufferedReader(new FileReader(fileName, StandardCharsets.UTF_8));
        } catch (final IOException e) {
            throw error("Error opening input file '%s': %s", fileName, e.getMessage());
        }
    }

    private char readChar() throws IOException {
        int read = reader.read();
        return read == -1 ? END : (char) read;
    }

    public char getChar() {
        return c;
    }

    public char nextChar() throws Md2HtmlException {
        try {
            if (c == '\n') {
                line++;
                posInLine = 0;
            }
            c = readChar();
            pos++;
            posInLine++;
            return c;
        } catch (final IOException e) {
            throw error("Source read error", e.getMessage());
        }
    }

    public Md2HtmlException error(String format, Object... args) {
        return new Md2HtmlException(line, posInLine, String.format("%d:%d: %s", line, posInLine, String.format(format, args)));
    }
}
