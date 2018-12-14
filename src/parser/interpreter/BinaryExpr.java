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
		switch(operation) {
			case EQEQ:
				break;
			case NOTEQ:
				break;
			case LEFTTAG:
				break;
			case LEFTEQUALSTAG:
				break;
			case RIGHTTAG:
				break;
			case RIGHTEQUALSTAG:
				break;
			case PLUS:
				break;
			case MINUS:
				break;
			case STAR:
				break;
			case SLASH:
				break;
			case MOD:
				break;
		}
		
		return null;
	}
}
