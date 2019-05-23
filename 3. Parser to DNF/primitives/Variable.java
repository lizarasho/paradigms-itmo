package primitives;

import parser.Expression;

public class Variable implements Expression {
    private String name;

    public Variable(String name) {
        this.name = name;
    }

    public int evaluate(int ... var) {
        return var[name.charAt(0) - 'a'];
    }

}