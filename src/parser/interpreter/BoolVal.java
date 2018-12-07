package parser.interpreter;

import exception.InterpreterException;
import tokenizer.Type;

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
}
