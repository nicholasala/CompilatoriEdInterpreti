package parser.interpreter;

import exception.InterpreterException;
//TODO singleton
public class NilVal extends Val{
	
	static final NilVal nil = new NilVal();
	
	private NilVal() {}
	
	@Override
	protected NilVal checkNil() throws InterpreterException{
		return this;
	}
	
	@Override
	public String toString() {
		return "NIL";
	}
}
