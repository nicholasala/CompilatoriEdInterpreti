package parser.interpreter;

import exception.InterpreterException;

public class NilVal extends Val{
	
	@Override
	protected NilVal checkNil() throws InterpreterException{
		return this;
	}
}
