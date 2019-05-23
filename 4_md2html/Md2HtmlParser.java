package md2html;

import java.io.IOException;
import java.io.FileWriter;
import java.util.ArrayDeque;
import java.util.Deque;

public class Md2HtmlParser {
    private SourceReader reader;
    private Deque<Tag> tagDeque = new ArrayDeque<>();
    private char last;
    private int headingLevel;
    private boolean wasInserted;
    private Tag currentBlock = null;
    private enum Tag {
        HEADING,
        PARAGRAPH,
        BOLD,
        STRONG_BOLD,
        STRIKETHROUGH,
        UNDERLINE,
        CODE,
        MARK,
    }

    public Md2HtmlParser(String input) throws Md2HtmlException {
        this.reader = new SourceReader(input);
    }

    public void parseToFile(String output) throws Md2HtmlException {
        StringBuilder parsedText = parse(SourceReader.END);
        try (FileWriter writer = new FileWriter(output)) {
            writer.write(parsedText.toString());
        } catch (IOException e) {
            throw new Md2HtmlException(String.format("Error opening output file '%s'", output));
        }
    }

    private StringBuilder parse(char end) throws Md2HtmlException {
        reader.nextChar();
        StringBuilder text = new StringBuilder();
        skipLines();
        while (!test(end)) {
            text.append(parseValue());
        }
        if (end == SourceReader.END) {
            text.append(clearStack());
        }
        return text;
    }

    private StringBuilder parseValue() throws Md2HtmlException {
        if (currentBlock == null) {
            return openBlock();
        } if (test('\n')) {
            return tryCloseBlock();
        } if (test('*') || test('_')) {
            return tryBold();
        } if (test('-') || test('+')) {
            return tryStrikeOrUnderline();
        } if (test('`') || test('~')) {
            return tryCodeOrMark();
        } if (test('<') || test('>') || test('&')) {
            return parseSpecial();
        } if (test('[')) {
            return parseLink();
        } if (test('!')) {
            return tryImage();
        } if (test('\\')) {
            return new StringBuilder().append(tryBackslash());
        }
        last = reader.getChar();
        reader.nextChar();
        return new StringBuilder().append(last);
    }

    private StringBuilder parseLink() throws Md2HtmlException {
        StringBuilder name = parse(']');
        reader.nextChar();
        reader.nextChar();
        StringBuilder link = copy(')');
        return new StringBuilder().append("<a href=\'").append(link).append("\'>").append(name).append("</a>");
    }

    private StringBuilder tryImage() throws Md2HtmlException {
        reader.nextChar();
        reader.nextChar();
        StringBuilder name = copy(']');
        reader.nextChar();
        StringBuilder link = copy(')');
        return new StringBuilder().append("<img alt='").append(name).append("' src='").append(link).append("'>");
    }

    private StringBuilder copy(char end) throws Md2HtmlException {
        StringBuilder copiedText = new StringBuilder();
        while (!test(end)) {
            copiedText.append(reader.getChar());
            reader.nextChar();
        }
        reader.nextChar();
        return copiedText;
    }

    private Character tryBackslash() throws Md2HtmlException {
        char currentSymbol;
        reader.nextChar();
        if (test('*') || test('-') || test('_') || test('[') || test(']') || test('(') || test(')') || test('!')) {
            currentSymbol = reader.getChar();
        } else {
            currentSymbol = '\\';
        }
        reader.nextChar();
        return currentSymbol;
    }

    private StringBuilder parseSpecial() throws Md2HtmlException {
        StringBuilder currentText = new StringBuilder();
        if (test('<')) {
            currentText.append("&lt;");
        } else if (test('>')) {
            currentText.append("&gt;");
        } else {
            currentText.append("&amp;");
        }
        reader.nextChar();
        return currentText;
    }

    private StringBuilder tryCodeOrMark() throws Md2HtmlException {
        StringBuilder currentText;
        if (test('`')) {
            currentText = tryInsert(Tag.CODE, "code", false);
        } else currentText = tryInsert(Tag.MARK, "mark", false);
        reader.nextChar();
        return currentText;
    }


    private StringBuilder tryStrikeOrUnderline() throws Md2HtmlException {
        char currentSymbol = reader.getChar();
        reader.nextChar();
        if (test(currentSymbol)) {
            reader.nextChar();
            if (currentSymbol == '-') {
                return tryInsert(Tag.STRIKETHROUGH, "s", true);
            } else if (currentSymbol == '+') {
                return tryInsert(Tag.UNDERLINE, "u", true);
            }
        }
        last = currentSymbol;
        return new StringBuilder().append(currentSymbol);
    }

    private StringBuilder tryBold() throws Md2HtmlException {
        StringBuilder currentText;
        char c = reader.getChar();
        reader.nextChar();
        if (test(c)) {
            reader.nextChar();
            currentText = tryInsert(Tag.STRONG_BOLD, "strong", true);
            if (!wasInserted) {
                last = c;
                currentText.append(c).append(c);
            }
        } else {
            currentText = tryInsert(Tag.BOLD, "em", true);
            if (!wasInserted) {
                last = c;
                currentText.append(c);
            }
        }
        return currentText;
    }


    private StringBuilder tryInsert(Tag name, String tag, boolean checkSpace) {
        boolean condition1 = !(tagDeque.peek() == name);
        boolean condition2 = tagDeque.peek() == name;
        if (checkSpace) {
            condition1 &= !test(' ') && !test('\n');
            condition2 &= last != ' ' && last != '\n';
        }
        StringBuilder currentText = new StringBuilder();
        if (condition1) {
            currentText.append(addTag(tag));
            tagDeque.push(name);
        } else if (condition2) {
            currentText.append(addTag("/" + tag));
            tagDeque.pop();
        }
        wasInserted =  condition1 || condition2;
        return currentText;
    }

    private StringBuilder tryCloseBlock() throws Md2HtmlException {
        StringBuilder currentText = new StringBuilder();
        reader.nextChar();
        if (test('\n')) {
            reader.nextChar();
            if (currentBlock.equals(Tag.HEADING)) {
                currentText.append(addTag("/h" + headingLevel));
            } else {
                currentText.append(addTag("/p"));
            }
            tagDeque.pop();
            currentBlock = null;
        }
        if (!test(SourceReader.END)) {
            currentText.append("\n");
        }
        skipLines();
        last = '\n';
        return currentText;
    }

    private void skipLines() throws Md2HtmlException {
        while (test('\n')) {
            reader.nextChar();
        }
    }

    private StringBuilder openBlock() throws Md2HtmlException {
        StringBuilder currentText = new StringBuilder();
        if (isHeading()) {
            currentBlock = Tag.HEADING;
            currentText.append(addTag("h" + headingLevel));
            reader.nextChar();
        } else {
            currentBlock = Tag.PARAGRAPH;
            currentText.append(addTag("p"));
            for (int i = 0; i < headingLevel; i++) {
                currentText.append("#");
                last = '#';
            }
        }
        tagDeque.push(currentBlock);
        return currentText;
    }

    private boolean isHeading() throws Md2HtmlException {
        headingLevel = readNumberSigns();
        return reader.getChar() == ' ' && headingLevel > 0 && headingLevel < 7;
    }

    private StringBuilder clearStack() {
        StringBuilder currentText = new StringBuilder();
        if (!tagDeque.isEmpty()) {
            switch (tagDeque.peek()) {
                case HEADING:
                    currentText.append(addTag("/h" + headingLevel));
                    tagDeque.pop();
                    break;
                case PARAGRAPH:
                    currentText.append(addTag("/p"));
                    tagDeque.pop();
                    break;
            }
        }
        return currentText;
    }

    private StringBuilder addTag(String tag) {
        return new StringBuilder().append("<").append(tag).append(">");
    }

    private int readNumberSigns() throws Md2HtmlException {
        int number = 0;
        while (reader.getChar() == '#') {
            number++;
            reader.nextChar();
        }
        return number;
    }

    private boolean test(final char c) {
        return reader.getChar() == c;
    }
}