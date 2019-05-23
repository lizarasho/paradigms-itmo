package expression.generic;

import expression.exceptions.*;
import expression.modes.*;
import expression.parser.*;
import java.util.HashMap;
import java.util.Map;

public class GenericTabulator implements Tabulator {
    private static Map<String, Operation<?>> modes = new HashMap<>();
    static {
        modes.put("i", new IntegerOperation(true));
        modes.put("u", new IntegerOperation(false));
        modes.put("d", new DoubleOperation());
        modes.put("f", new FloatOperation());
        modes.put("s", new ShortOperation());
        modes.put("l", new LongOperation());
        modes.put("b", new ByteOperation());
        modes.put("bi", new BigIntegerOperation());
    }

    public Object[][][] tabulate(String mode, String expression, int x1, int x2, int y1, int y2, int z1, int z2)
            throws EvaluatingException, ParsingException {
        return fillTable(getOperation(mode), expression, x1, x2, y1, y2, z1, z2);
    }

    private Operation<?> getOperation(String mode) throws IllegalModeException {
        if (modes.containsKey(mode)) {
            return modes.get(mode);
        } else {
            throw new IllegalModeException(mode);
        }
    }

    private <T> Object [][][] fillTable(Operation<T> operation, String expression, int x1, int x2, int y1, int y2, int z1, int z2)
            throws ParsingException, EvaluatingException {
        Parser<T> parser = new ExpressionParser<>(operation);
        TripleExpression<T> parsedExpression = parser.parse(expression);
        Object[][][] table = new Object[x2 - x1 + 1][y2 - y1 + 1][z2 - z1 + 1];
        for (int i = x1; i <= x2; i++) {
            for (int j = y1; j <= y2; j++) {
                for (int k = z1; k <= z2; k++)
                    try {
                        table[i - x1][j - y1][k - z1] = parsedExpression.evaluate(
                                operation.parseNumber(Integer.toString(i)),
                                operation.parseNumber(Integer.toString(j)),
                                operation.parseNumber(Integer.toString(k)));
                    } catch (EvaluatingException e) {
                        table[i - x1][j - y1][k - z1] = null;
                    }
            }
        }
        return table;
    }
}
