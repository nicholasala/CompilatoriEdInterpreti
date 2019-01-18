package parser.interpreter;

import exception.InterpreterException;
import tokenizer.Type;
//TODO singleton
public class BoolVal extends Val{
	private final boolean value;
	
	BoolVal(Type value){
		if(value == Type.TRUE)
    		this.value = true;
    	else
    		this.value = false;
	}
	
	public boolean getValue() {
		return value;
	}
	
	@Override
	protected BoolVal checkBool() throws InterpreterException{
		return this;
	}
	
	@Override
	public String toString() {
		return String.valueOf(value);
	}
	
	@Override
	public Val not() throws InterpreterException{
		return value ? new BoolVal(Type.FALSE) : new BoolVal(Type.TRUE);
	}
}
