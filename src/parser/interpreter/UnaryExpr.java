package parser.interpreter;

import exception.InterpreterException;
import tokenizer.Type;

public class UnaryExpr extends Expr{
	private final Type operation;
	private final Expr expr;

	UnaryExpr(Type operation, Expr expr){
		this.operation = operation;
		this.expr = expr;
	}

	@Override
	public Val eval(Env env) throws InterpreterException {
		Val value = expr.eval(env);
		
		switch(operation) {
			case PLUS:
				return value;
			case MINUS:
				return value.minus();
			case EXCLAMATION:
				return value.not();
		}
		return null;
	}
}
