"use strict";

const commonOperation = operation => (...operands) => (x, y, z) => {
    let result = [];
    operands.map(operand =>
        result.push(operand(x, y, z))
    );
    return operation(...result);
};
const add = commonOperation((a, b) => a + b);
const subtract = commonOperation((a, b) => a - b);
const multiply = commonOperation((a, b) => a * b);
const divide = commonOperation((a, b) => a / b);
const negate = commonOperation((a) => -a);
const avg5 = commonOperation((a, b, c, d, e) => (a + b + c + d + e) / 5);
const abs = commonOperation(a => Math.abs(a));
const iff = commonOperation((a, b, c) => {
    return a >= 0 ? b : c;
});
const variable = (name) => (...args) => {
    return args[VARIABLES[name]];
};
const cnst = value => (x, y, z) => value;

const CONSTANTS = {
    "one" : 1,
    "two" : 2,
};
const one = cnst(1);
const two = cnst(2);

const OPERATIONS = {
    "+" : add,
    "-" : subtract,
    "*" : multiply,
    "/" : divide,
    "negate" : negate,
    "avg5" : avg5,
    "abs" : abs,
    "iff" : iff,
};

const VARIABLES = {
    "x" : 0,
    "y" : 1,
    "z" : 2,
};

const ARGS_SIZE = {
    "abs" : 1,
    "negate" : 1,
    "+" : 2,
    "-" : 2,
    "*" : 2,
    "/" : 2,
    "iff" : 3,
    "avg5" : 5,
};

parse = function (expression) {
    return (x, y, z) => {
        let tokens = expression.split(" ").filter(e => e.length > 0);
        let stack = [];
        tokens.map(token => {
            if (token in OPERATIONS) {
                let size = ARGS_SIZE[token];
                let args = stack.slice(stack.length - size, stack.length);
                stack = stack.slice(0, stack.length - size);
                stack.push(OPERATIONS[token](...args));
            } else {
                if (token in VARIABLES) {
                    stack.push(variable(token));
                } else {
                    let number = (token in CONSTANTS) ? CONSTANTS[token] : parseInt(token);
                    stack.push(cnst(number));
                }
            }
        });
        return stack.pop()(x, y, z);
    }
};