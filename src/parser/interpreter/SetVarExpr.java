package parser.interpreter;

import exception.InterpreterException;

public class SetVarExpr extends Expr{
	private final String id;
	private final Expr expr;
	
	public SetVarExpr(String id, Expr expr ) {
		this.id = id;
		this.expr = expr;
	}
	//shift + alt (con cursore nella zone dalla quale estrarre la variabile)
	//shift + alt + l
	@Override
	public Val eval(Env env) throws InterpreterException {
		Val val = expr.eval(env);
		env.setValue(id, val);
		return val;
	}
}