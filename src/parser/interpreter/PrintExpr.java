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
	public Val eval(Env env) throws InterpreterException {
		for(Val v : args.eval(env))
			System.out.print(v.toString());
			
		if(operator == Type.PRINTLN)
			System.out.print("\n");
			
		return new NilVal();
	}

}
