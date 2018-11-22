package parser.interpreter;

import exception.InterpreterException;

public class Val extends Expr{
	
	Val eval(Env env) {
        return this;
    }
	
	private void checkBool() throws InterpreterException{
		if(!(this instanceof BoolVal))
			throw new InterpreterException(this + " Is not a bool");
	}
	
	private void checkNum() throws InterpreterException{
		if(!(this instanceof NumVal))
			throw new InterpreterException(this + " Is not a bool");
	}
	
	private void checkString() throws InterpreterException{
		if(!(this instanceof StringVal))
			throw new InterpreterException(this + " Is not a bool");
	}
	
	private void checkNil() throws InterpreterException{
		if(!(this instanceof NilVal))
			throw new InterpreterException(this + " Is not a bool");
	}
}
