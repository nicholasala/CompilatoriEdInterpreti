package parser.interpreter;

import exception.InterpreterException;

public class Val extends Expr{
	
	public Val eval(Env env) throws InterpreterException{
        return this;
    }
	
	protected BoolVal checkBool() throws InterpreterException{
		throw new InterpreterException(this + " Is not a bool");
	}
	
	protected NumVal checkNum() throws InterpreterException{
			throw new InterpreterException(this + " Is not a num");
	}
	
	protected StringVal checkString() throws InterpreterException{
		throw new InterpreterException(this + " Is not a string");
	}
	
	protected NilVal checkNil() throws InterpreterException{
		throw new InterpreterException(this + " Is not a nil");
	}
	
	protected ClosureVal checkClosure() throws InterpreterException{
		throw new InterpreterException(this + " Is not a Closure");
	}
}
