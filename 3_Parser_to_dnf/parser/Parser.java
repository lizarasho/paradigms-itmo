package parser;

import exceptions.*;
import parser.Expression;

public interface Parser {
    Expression parse(String expression) throws ParsingException;
    String toDNF(String expression) throws ParsingException;
}