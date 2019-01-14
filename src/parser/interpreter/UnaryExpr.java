package parser.interpreter;

import tokenizer.Type;

public class UnaryExpr extends Expr{
	private final Type operation;
	private final Expr expr;

	UnaryExpr(Type operation, Expr expr){
		this.operation = operation;
		this.expr = expr;
	}

	@Override
	public Val eval(Env env) {
		//Type.PLUS
		//Type.MINUS
		//Type.EXCLAMATION
		return null;
	}
}
