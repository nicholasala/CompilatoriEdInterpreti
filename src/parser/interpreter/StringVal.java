package parser.interpreter;

import exception.InterpreterException;

public class StringVal extends Val{
	private final String value;
	
	StringVal(String value){
		this.value = value;
	}
	
	public String getValue() {
		return value;
	}
	
	@Override
	protected StringVal checkString() throws InterpreterException{
		return this;
	}
	
	@Override
	public String toString() {
		return value;
	}
}
