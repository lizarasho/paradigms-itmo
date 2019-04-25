package parser;

import exceptions.*;

public interface Parser {
    Expression parse(String expression) throws ParsingException;
    String toDNF(String expression) throws ParsingException;
}