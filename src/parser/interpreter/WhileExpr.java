package parser.interpreter;

import exception.InterpreterException;
import tokenizer.Type;

public class WhileExpr extends Expr{
	private Type operator;
	private Expr condition;
	private Expr operation;
	
	//if operation == null -> while vuoto
	WhileExpr(Type operator, Expr condition, Expr operation){
		this.operator = operator;
		this.condition = condition;
		this.operation = operation;
	}

	@Override
	public Val eval(Env env) throws InterpreterException {
		
		while((condition.eval(env).checkBool().getValue() && operator == Type.WHILE)
		  || (!condition.eval(env).checkBool().getValue() && operator == Type.WHILENOT)) {
			operation.eval(env);
		}
		
		return new NilVal();
	}
}
