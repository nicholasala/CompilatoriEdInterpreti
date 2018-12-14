package parser.interpreter;

import exception.InterpreterException;

public class InvokeExpr extends Expr{
	private final Expr expr;
	private final ExprList args;
	
	InvokeExpr(Expr expr, ExprList args ){
		this.expr = expr;
		this.args = args;
	}

	@Override
	public Val eval(Env env) throws InterpreterException {
		return expr.eval(env).checkClosure().apply(args.eval(env));
	}
	
}
