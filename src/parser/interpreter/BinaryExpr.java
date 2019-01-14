package parser.interpreter;

import exception.InterpreterException;
import tokenizer.Type;

public class BinaryExpr extends Expr{
	private Expr first;
	private Type operation;
	private Expr second;

	BinaryExpr(Expr first, Type operation, Expr second){
		this.first = first;
		this.operation = operation;
		this.second = second;
	}

	@Override
	public Val eval(Env env) throws InterpreterException {
		Val lVal = first.eval(env);
		Val rVal = second.eval(env);
		
		switch(operation) {
			case EQEQ:
				return lVal.equal(rVal);
			case NOTEQ:
				return lVal.notEqual(rVal);
			case LEFTTAG:
				return lVal.lower(rVal);
			case LEFTEQUALSTAG:
				return lVal.lowerEqual(rVal);
			case RIGHTTAG:
				return lVal.greater(rVal);
			case RIGHTEQUALSTAG:
				return lVal.greaterEqual(rVal);
			case PLUS:
				return lVal.plus(rVal);
			case MINUS:
				return lVal.minus(rVal);
			case STAR:
				return lVal.times(rVal);
			case SLASH:
				return lVal.division(rVal);
			case MOD:
				return lVal.module(rVal);
		}
		
		return NilVal.nil;
	}
}
