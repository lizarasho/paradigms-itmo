package expression.modes;

public class FloatOperation extends Operation<Float> {

    public Float add(Float first, Float second) {
        return first + second;
    }

    public Float divide(Float first, Float second) {
        return first / second;
    }

    public Float subtract(Float first, Float second) {
        return first - second;
    }

    public Float negate(Float first) {
        return -first;
    }

    public Float multiply(Float first, Float second) {
        return first * second;
    }

    public Float parseNumber(String s) {
        return Float.parseFloat(s);
    }

    public Float abs(Float first) {
        return Math.abs(first);
    }

    public Float square(Float first) {
        return first * first;
    }

    public Float mod(Float first, Float second) {
        return first % second;
    }
}
