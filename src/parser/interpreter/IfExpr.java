package parser.interpreter;

import exception.InterpreterException;
import tokenizer.Type;

public class IfExpr extends Expr{
	private Expr condition;
	private Expr first;
	private Expr second;
	private Type operator;
	
	
	IfExpr(Expr condition, Type operator, Expr first){
		this.condition = condition;
		this.operator = operator;
		this.first = first;
		this.second = null;
	}
	
	//if second == null -> non presente else
	IfExpr(Expr condition, Type operator, Expr first, Expr second){
		this.condition = condition;
		this.operator = operator;
		this.first = first;
		this.second = second;
	}

	@Override
	Val eval(Env env) throws InterpreterException {
		BoolVal valued = condition.eval(env).checkBool();
		
		if((valued.getValue() && operator == Type.IF) || (!valued.getValue() && operator == Type.IFNOT))
			return first.eval(env);
		else if(second != null)
			return second.eval(env);
		else
			return new NilVal();
	}

}
