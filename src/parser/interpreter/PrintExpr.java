package parser.interpreter;

import exception.InterpreterException;
import tokenizer.Type;

public class PrintExpr extends Expr{
	private Type operator;
	private ExprList args;
	
	PrintExpr(Type operator, ExprList args){
		this.operator = operator;
		this.args = args;
	}

	@Override
	Val eval(Env env) throws InterpreterException {
		StringBuilder sb = new StringBuilder();
		if(operator == Type.PRINTLN)
			sb.append("\n");
		
		for(Val v : args.eval(env))
			sb.append(v.)
			
		return new NilVal();
	}

}
