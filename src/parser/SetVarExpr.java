package parser;

import tokenizer.Type;

public class SetVarExpr extends Expr{
	private final String id;
	private final Expr expr;
	
	public SetVarExpr(String id, Type operator, Expr expr ) {
		this.id = id;
		this.expr = expr;
	}
}
