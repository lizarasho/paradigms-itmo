import parser.ExpressionParser;
import parser.Parser;

public class Main {
    public static void main(String[] args) throws Exception {
        //тесты
        System.out.println(dnf("a & ~b & (c | ~c)"));
        System.out.println(dnf("50 | ~a & a"));
        System.out.println(dnf("a & ~d  & k & l & m & n & o & p & q & (f | ~f)"));
        // P.S. в случае тождественного нуля, не представимого в виде ДНФ, результат — пустая строка
    }

    static String dnf(String s) throws Exception {
        Parser parser = new ExpressionParser();
        return parser.toDNF(s);
    }
}
