package parser.interpreter;

import exception.InterpreterException;

public class BoolVal extends Val{
	private final boolean value;
	
	BoolVal(boolean value){
		this.value = value;
	}
	
	@Override
	protected BoolVal checkBool() throws InterpreterException{
		return this;
	}
}
