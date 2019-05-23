import exceptions.ParsingException;
import parser.ExpressionParser;
import parser.Parser;

// экзамен за первый семестр

public class Main {
    public static void main(String[] args) throws ParsingException {
        //тесты
        System.out.println(dnf("a & ~b & (c | ~c)"));
        System.out.println(dnf("0 | ~a & a"));
        System.out.println(dnf("a & ~d  & k & l & m & n & o & p & q & (f | ~f)"));
        // в случае тождественного нуля, не представимого в виде ДНФ, результат — пустая строка
    }

    private static String dnf(String s) throws ParsingException {
        Parser parser = new ExpressionParser();
        return parser.toDNF(s);
    }
}
