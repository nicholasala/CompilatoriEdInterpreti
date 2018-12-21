package parser.interpreter;

import exception.InterpreterException;

public class SetVarExpr extends Expr{
	private final String id;
	private final Expr expr;
	
	public SetVarExpr(String id, Expr expr ) {
		this.id = id;
		this.expr = expr;
	}

	@Override
	public Val eval(Env env) throws InterpreterException {
		env.setValue(id, expr.eval(env));
		return NilVal.nil;
	}
}
