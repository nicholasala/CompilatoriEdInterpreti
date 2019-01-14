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
	
	public ClosureVal checkClosure() throws InterpreterException{
		throw new InterpreterException(this + " Is not a Closure");
	}
	
	public Val plus(Val rVal) throws InterpreterException{
		throw new InterpreterException("Plus not allowed");
	}
	
	public Val minus(Val rVal) throws InterpreterException{
		throw new InterpreterException("Minus not allowed on "+this);
	}
	
	public Val times(Val rVal) throws InterpreterException{
		throw new InterpreterException("Times not allowed on "+this);
	}
	
	public Val division(Val rVal) throws InterpreterException{
		throw new InterpreterException("Division not allowed on "+this);
	}
	
	public Val module(Val rVal) throws InterpreterException{
		throw new InterpreterException("Module not allowed on "+this);
	}
	
	public Val equal(Val rVal) throws InterpreterException{
		throw new InterpreterException("Equal not allowed on "+this);
	}
	
	public Val notEqual(Val rVal) throws InterpreterException{
		throw new InterpreterException("NotEqual not allowed on "+this);
	}
	
	public Val lower(Val rVal) throws InterpreterException{
		throw new InterpreterException("lower not allowed on "+this);
	}
	
	public Val lowerEqual(Val rVal) throws InterpreterException{
		throw new InterpreterException("lowerEqual not allowed on "+this);
	}
	
	public Val greater(Val rVal) throws InterpreterException{
		throw new InterpreterException("greater not allowed on "+this);
	}
	
	public Val greaterEqual(Val rVal) throws InterpreterException{
		throw new InterpreterException("greaterEqual not allowed on "+this);
	}
}
