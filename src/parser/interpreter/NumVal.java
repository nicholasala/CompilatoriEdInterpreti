package parser.interpreter;

import exception.InterpreterException;

public class NumVal extends Val{
	private final int value;
	
	NumVal(int value){
		this.value = value;
	}
	
	@Override
	protected NumVal checkNum() throws InterpreterException{
		return this;
	}
}
