package parser.interpreter;

import exception.InterpreterException;

public class NumVal extends Val{
	private final int value;
	
	NumVal(int value){
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
	
	@Override
	protected NumVal checkNum() throws InterpreterException{
		return this;
	}
}
