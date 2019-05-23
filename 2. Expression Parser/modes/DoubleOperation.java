package expression.modes;

public class DoubleOperation extends Operation<Double> {

    public Double add(Double first, Double second) {
        return first + second;
    }

    public Double divide(Double first, Double second) {
        return first / second;
    }

    public Double subtract(Double first, Double second) {
        return first - second;
    }

    public Double negate(Double first) {
        return -first;
    }

    public Double multiply(Double first, Double second) {
        return first * second;
    }

    public Double parseNumber(String s) {
        return Double.parseDouble(s);
    }

    public Double abs(Double first) {
        return Math.abs(first);
    }

    public Double square(Double first) {
        return first * first;
    }

    public Double mod(Double first, Double second) {
        return first % second;
    }
}
