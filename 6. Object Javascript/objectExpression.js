"use strict";

const VARIABLES = {
    "x" : 0,
    "y" : 1,
    "z" : 2,
};

const ARGS_SIZE = {
    "negate" : 1,
    "atan" : 1,
    "+" : 2,
    "-" : 2,
    "*" : 2,
    "/" : 2,
    "atan2" : 2,
};

function getPosition(index, length) {
    let result = '\n';
    for (let i = 0; i < index; i++) {
        result += " ";
    }
    for (let i = 0; i < length; i++) {
        result += "^";
    }
    return result;
}

function MyException(message) {
    this.message = message;
}
MyException.prototype = Error.prototype;
MyException.prototype.name = "MyException";

function DefineException(name) {
    this.name = name;
}
DefineException.prototype = MyException.prototype;

function createException(name, howToMessage) {
    let result = function (...args) {
        MyException.call(this, howToMessage(...args));
    };
    result.prototype = new DefineException(name);
    return result;
}

const MissOperationException = createException(
    "MissOperationException",
    function (pointer, expression) {
        return ("Expected operation at position " + (pointer + 1) + "\n" + expression + getPosition(pointer, 1));
    }
);

const MissOperandException = createException(
    'MissOperandException',
    function(pointer, expression, operation) {
        return 'Not enough operands for operation ' + operation + ' at position ' + (pointer + 1)
            + '\n' + expression + getPosition(pointer, operation.length);
    }
);

const OddOperandException = createException(
    "OddOperandException",
    function(pointer, expression, operation) {
        return 'Too many operands for operation ' + operation + ' at position ' + (pointer + 1)
            + '\n' + expression + getPosition(pointer, operation.length);
    }
);

const MissOperationParenthesisException = createException(
    "MissOperationParenthesisException",
    function (index, expression, mode) {
        if (!mode) {
            return ("Opening bracket expected before position " + (index + 1) + "\n" + expression +  getPosition(index, 1));
        } else {
            return ("Closing bracket expected after position " + (index + 1) + "\n" + expression +  getPosition(index, 1));
        }
    }
);

const OddClosingParenthesisException = createException(
    "OddClosingParenthesisException",
    function (index, expression) {
        return ("Odd closing parenthesis at position " + (index + 1) + '\n'
            + expression + getPosition(index, 1));
    }
);

const MissClosingParenthesisException = createException(
    "MissClosingParenthesisException",
    function (index, expression) {
        return ("Closing parenthesis expected before position " + (index + 1) + '\n'
            + expression + getPosition(index, 1));
    }
);

const OddPartException = createException(
    "OddPartException",
    function (index, expression) {
        return ("Odd suffix after correct expression at position " + (index + 1)  + '\n'
            + expression + getPosition(index, expression.length - index));
    }
);
const UnknownIdentifierException = createException(
    "UnknownIdentifierException",
    function (index, lexem, expression) {
        return ("Unknown identifier '" + lexem + "' at position " + (index + 1) + "\n"
            + expression + getPosition(index, lexem.length));
    }
);

const UnknownSymbolException = createException(
    "UnknownSymbolException",
    function (index, expression) {
        return ("Unknown symbol '" + expression[index] + "' at position "
            + (index + 1) + '\n' + expression + getPosition(index, 1));
    }
);

const primitive  = {
    simplify: function () { return this }
};

function Const(value) {
    this.getValue = () => value;
}
Const.prototype = Object.create(primitive);
Const.prototype.toString = function () {
    return this.getValue().toString();
};
Const.prototype.diff = () => ZERO;
Const.prototype.prefix = Const.prototype.toString;
Const.prototype.postfix = Const.prototype.toString;
const ZERO = new Const(0);
const ONE = new Const(1);
function isConst(a) {
    return a instanceof Const;
}
function isZero(a) {
    return isConst(a) && a.getValue() === 0;
}
function isOne(a) {
    return isConst(a) && a.getValue() === 1;
}

function Variable(name) {
    this.getName = () => name;
}
Variable.prototype = Object.create(primitive);
Variable.prototype.evaluate = function (...args) {
    return args[VARIABLES[this.getName()]];
};
Variable.prototype.toString = function () {
    return this.getName();
};
Variable.prototype.prefix = Variable.prototype.toString;
Variable.prototype.postfix = Variable.prototype.toString;
Const.prototype.evaluate = function () {
    return this.getValue();
};
Variable.prototype.diff = function (name) {
    return name === this.getName() ? ONE : ZERO;
};

function Operation(...operands) {
    this.getOperands = function () {
        return operands;
    }
}
Operation.prototype.toString = function () {
    return this.getOperands().join(" ").concat(" ", this.getSymbol());
};
Operation.prototype.evaluate = function (...args) {
    return this._action(...this.getOperands().map(operand => operand.evaluate.apply(operand, args)));
};
Operation.prototype.diff = function(d) {
    let result = this.getOperands().map( operand => operand.diff(d));
    return this._diff(d, ...this.getOperands().concat(result));
};
Operation.prototype.prefix = function () {
    return "(".concat(
        this.getSymbol(),
        " ",
        this.getOperands().map(value => value.prefix()).join(" "),
        ")");
};
Operation.prototype.postfix = function () {
    return "(".concat(
        this.getOperands().map(value => value.postfix()).join(" "),
        " ",
        this.getSymbol(),
        ")");
};

Operation.prototype.simplify = function() {
    return this._simpl(...this.getOperands().map( operand => operand.simplify()));
};

function DefineOperation(action, symbol, diff, simpl) {
    this._action = action;
    this.getSymbol = function () {
        return symbol;
    };
    this._diff = diff;
    this._simpl = simpl;
}
DefineOperation.prototype = Operation.prototype;

function createOperation(action, symbol, diff, simpl) {
    let result = function (...args) {
        Operation.apply(this, args);
    };
    result.prototype = new DefineOperation(action, symbol, diff, simpl);
    return result;
}

const Add = createOperation(
    (a, b) => a + b,
    "+",
    (d, a, b, da, db) => new Add(da, db),
    (a, b) => {
        if (isConst(a) && isConst(b)) {
            return new Const(a.getValue() + b.getValue());
        }
        if (isZero(a)) {
            return b;
        }
        if (isZero(b)) {
            return a;
        }
        return new Add(a, b);
    }
);

const Subtract = createOperation(
    (a, b) => a - b,
    "-",
    (d, a, b, da, db) => new Subtract(da, db),
    (a, b) => {
        if (isConst(a) && isConst(b)) {
            return new Const(a.getValue() - b.getValue());
        }
        if (isZero(a)) {
            return new Negate(b);
        }
        if (isZero(b)) {
            return a;
        }
        return new Subtract(a, b);
    }
);

const Multiply = createOperation(
    (a, b) => a * b,
    "*",
    (d, a, b, da, db) => new Add(
        new Multiply(da, b),
        new Multiply(a, db)
    ),
     (a, b) => {
        if (isConst(a) && isConst(b))
            return new Const(a.getValue() * b.getValue());
        if (isZero(a) || isZero(b)) {
            return ZERO;
        }
        if (isOne(a)) {
            return b;
        }
        if (isOne(b)) {
            return a;
        }
        return new Multiply(a, b);
    }
);

const Divide = createOperation(
    (a, b) => a / b,
    "/",
    (d, a, b, da, db) => new Divide(
       new Subtract(
           new Multiply(da, b),
           new Multiply(a, db)
       ),
       new Multiply(b, b)
    ),
    (a, b) => {
        if (isConst(a) && isConst(b)) {
            return new Const(a.getValue() / b.getValue());
        }
        if (isZero(a)) {
            return ZERO;
        }
        if (isOne(b)) {
            return a;
        }
        return new Divide(a, b);
    }
);

const Sumexp = createOperation(
    function (...args) {
        let sum = 0;
        for (let i = 0; i < args.length; i++) {
            sum += Math.exp(args[i]);
        }
        return sum;
    },
    "sumexp",
    function (d, ...operands) {
        let args = operands.slice(0, operands.length / 2);
        let diffArgs = operands.slice(operands.length / 2, operands.length);
        let res = ZERO;
        for (let i = 0; i < args.length; i++) {
            res = new Add(res, new Multiply(new Sumexp(args[i]), diffArgs[i]));
        }
        return res;
    },
);

const Softmax = createOperation(
    function (...args) {
		let sum = 0;
		for (let i = 0; i < args.length; i++) {
			sum += Math.exp(args[i]);
		}
		return Math.exp(args[0]) / sum;
    },
	"softmax",
    function (d, ...operands) {
        let args = operands.slice(0, operands.length / 2);
        return new Divide(new Sumexp(args[0]), new Sumexp(...args)).diff(d);
    },
);

const Negate = createOperation(
    a => -a,
    "negate",
    (d, a, da) => new Negate(da),
    a => isConst(a) ? new Const(-a) : new Negate(a),
);

const ArcTan = createOperation(
    a => Math.atan(a),
    "atan",
    (d, a, da) =>
        new Divide(
            da,
            new Add(
                new Const(1),
                new Multiply(a, a)
            )
        ),
    a => {
        if (isConst(a)) {
            return new Const(Math.atan(a.getValue()));
        }
        return new ArcTan(a);
    },
);

const ArcTan2 = createOperation(
    (a, b) => Math.atan2(a, b),
    "atan2",
    (d, a, b, da, db) =>
        new Divide(
            new Subtract(
                new Multiply(da, b),
                new Multiply(db, a)
            ),
            new Add(
                new Multiply(a, a),
                new Multiply(b, b)
            )
        ),
    (a, b) => {
        if (isConst(a) && isConst(b)) {
            return new Const(Math.atan2(a.getValue(), b.getValue()));
        }
        if (isZero(a)) {
            return ZERO;
        }
        if (isOne(b)) {
            return new ArcTan(a);
        }
        return new ArcTan2(a, b);
    },
);

function isToken(a) {
    return a instanceof Const || a instanceof Variable || a instanceof Operation;
}

const OPERATIONS = {
    "+" : Add,
    "-" : Subtract,
    "*" : Multiply,
    "/" : Divide,
    "negate" : Negate,
    "atan" : ArcTan,
    "atan2" : ArcTan2,
    "sumexp" : Sumexp,
	"softmax" : Softmax,
};

const ARBITRARY = {
    "sumexp" : Sumexp,
	"softmax" : Softmax,
};

let parse = function (expression) {
    let tokens = expression.split(" ").filter(e => e.length > 0);
    let stack = [], args;
    tokens.map(token => {
        if (token in OPERATIONS) {
            args = stack.slice(stack.length - ARGS_SIZE[token], stack.length);
            stack = stack.slice(0, stack.length - ARGS_SIZE[token]);
            stack.push(new OPERATIONS[token](...args));
        } else {
            stack.push(token in VARIABLES ? new Variable(token) : new Const(parseInt(token)));
        }
    });
    return stack.pop();
};


let expression = "";
let pointer = 0;
let stack = [];
let pointers = [];

function isEnd() {
    return pointer >= expression.length;
}

function getLexem() {
    if (!(/[A-Za-z]/.test(expression[pointer]))) {
        throw new UnknownSymbolException(pointer, expression);
    }
    let currentLexem = '';
    while (!isEnd() &&  /\w/.test(expression[pointer])) {
        currentLexem += expression[pointer++];
    }
    return currentLexem;
}

function getNumber() {
    let result = '';
    if (expression[pointer] === '-') {
        result = '-';
        pointer++;
    }
    while (!isEnd() && /\d/.test(expression[pointer]))  {
        result += expression[pointer++];
    }
    return result;
}

function tryParseNumber() {
    let currentNumber = getNumber();
    if (currentNumber === '-') {
        pointer--;
    }
    return (currentNumber !== '' && currentNumber !== '-') ? parseInt(currentNumber) : undefined;
}

function stackTop() {
    return stack[stack.length - 1];
}

function skipWhiteSpace() {
    while (expression[pointer] === " " && !isEnd()) {
        pointer++;
    }
}

function doOperation(mode) {
    let tempInd = undefined;
    let curOperation = undefined;
    let operands = [];
    if (mode) {
        if (!(stackTop() in OPERATIONS)) {
            throw new MissOperationException(pointer, expression);
        }
        curOperation = stack.pop();
        tempInd = pointers.pop();
        while ((stackTop() !== '(') && !(stackTop() in OPERATIONS)) {
            let currentOperand = stack.pop();
            pointers.pop();
            if (!isToken(currentOperand)) {
                throw new MissOperandException(tempInd, expression, curOperation);
            }
            operands.push(currentOperand);
        }
        stack.pop();
        if (!(curOperation in ARBITRARY) && operands.length > ARGS_SIZE[curOperation]) {
            throw new OddOperandException(tempInd, expression, curOperation);
        }
        if (!(curOperation in ARBITRARY) && operands.length < ARGS_SIZE[curOperation]) {
            throw new MissOperandException(tempInd, expression, curOperation);
        }
        operands = operands.reverse();
        pointers.pop();
        stack.push(new OPERATIONS[curOperation](...operands));
    } else {
        while ((stackTop() !== "(") && !(stackTop() in OPERATIONS)) {
            operands.push(stack.pop());
            pointers.pop();
        }
        if (stackTop() === "(") {
            throw new MissOperationException(pointers.pop(), expression);
        }
        curOperation = stack.pop();
        tempInd = pointers.pop();
        if (stack.pop() !== "(") {
            throw new MissOperationParenthesisException(pointers.pop(), expression, mode);
        }
        pointers.pop();
        if (!(curOperation in ARBITRARY) && operands.length > ARGS_SIZE[curOperation]) {
            throw new OddOperandException(tempInd, expression, curOperation);
        }
        if (!(curOperation in ARBITRARY) && operands.length < ARGS_SIZE[curOperation]) {
            throw new MissOperandException(tempInd, expression, curOperation);
        }
        stack.push(new OPERATIONS[curOperation](...operands.reverse()));
    }
}

function parseMode(s, mode) {
    let balance = 0;
    pointer = 0;
    expression = s;
    stack = [];
    skipWhiteSpace();
    if (isEnd()) {
        throw new MyException("empty input");
    }
    while (!isEnd()) {
        skipWhiteSpace();
        if (isEnd()) {
            break;
        }
        if (expression[pointer] === ')') {
            balance--;
            if (balance < 0) {
                throw new OddClosingParenthesisException(index, expression);
            }
            doOperation(mode);
            pointer++;
            if (balance === 0) {
                break;
            }
            continue;
        }
        pointers.push(pointer);
        if (expression[pointer] === '(') {
            stack.push('(');
            pointer++;
            balance++;
            continue;
        }
        let curNumber = tryParseNumber();
        if (curNumber !== undefined) {
            stack.push(new Const(curNumber));
            continue;
        }
        let curOperation = undefined;
        let currentLexem;
        if (expression[pointer] in OPERATIONS) {
            curOperation = expression[pointer];
            pointer++;
        } else {
            currentLexem = getLexem();
            if (currentLexem in OPERATIONS) {
                curOperation = currentLexem;
            }
        }
        if (curOperation !== undefined) {
            stack.push(curOperation);
        } else if (currentLexem in VARIABLES) {
            stack.push(new Variable(currentLexem));
            if (balance === 0) {
                break;
            }
        } else {
            throw new UnknownIdentifierException(pointers.pop(), currentLexem, expression);
        }
    }
    skipWhiteSpace();
    if (stack.length > 1) {
        throw new MissOperationParenthesisException(index[0], expression, mode);
    }
    if (balance > 0) {
        throw new MissClosingParenthesisException(index, expression);
    }
    if (pointer !== expression.length) {
        throw new OddPartException(index, expression);
    }
    let res = stack.pop();
    if (!isToken(res)) {
        throw new MissOperationParenthesisException(pointers.pop(), expression, mode);
    }
    return res;
}

function parsePrefix(s) {
    return parseMode(s, 0);
}

function parsePostfix(s) {
    return parseMode(s, 1);
}