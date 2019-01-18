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
	public Val eval(Env env) throws InterpreterException {
		BoolVal valued = condition.eval(env).checkBool();
		
		switch(operator) {
		case AND:
			if(valued.getValue()) {
				return first.eval(env).checkBool();
			}else {
				return new BoolVal(Type.FALSE);
			}
		case OR:
			if(valued.getValue()) {
				return new BoolVal(Type.TRUE);
			}else {
				return first.eval(env).checkBool();
			}
		case IF: case IFNOT:
				if((valued.getValue() && operator == Type.IF) || (!valued.getValue() && operator == Type.IFNOT))
					return first.eval(env);
				else if(second != null)
					return second.eval(env);
		}

		return NilVal.nil;
	}

}


/*
if(type == Type.AND || type == Type.OR)
{
    BoolVal eval1 = condition.eval(env).checkBool();
    //BoolVal eval2 = then.eval(env).checkBool();

    //TODO implementa
    switch(type)
    {
        case AND: //se entrambe le condizioni sono vere, torno vero
           // return (then.eval(env).checkBool().getValue() == Type.TRUE && eval1.checkBool().getValue() == Type.TRUE) ? new BoolVal(Type.TRUE) : new BoolVal(Type.FALSE);

            if(eval1.getValue() == Type.FALSE)
                return new BoolVal(Type.FALSE);
            return then.eval(env).checkBool();
        case OR: //se almeno una condizione è vera, torno vero

            if(eval1.getValue() == Type.TRUE)
                return new BoolVal(Type.TRUE);
            return then.eval(env).checkBool();
           // return (then.eval(env).checkBool().getValue() == Type.TRUE || eval1.checkBool().getValue() == Type.TRUE) ? new BoolVal(Type.TRUE) : new BoolVal(Type.FALSE);
    }

}
else
{
    if(type == Type.IF || type == Type.IFNOT)
    {
        BoolVal eval_condition = condition.eval(env).checkBool();

        if(eval_condition.getValue() == Type.TRUE && type == Type.IF)
            return then.eval(env);

        if(eval_condition.getValue() == Type.FALSE && type == Type.IF)
            if(_else != null)
                return _else.eval(env);
            else
                return NilVal.instance();

        if(eval_condition.getValue() == Type.TRUE && type == Type.IFNOT)
            if(_else != null)
                return _else.eval(env);
            else
                return NilVal.instance();

        if(eval_condition.getValue() == Type.FALSE && type == Type.IFNOT)
            return then.eval(env);
    }
}

//throw new EvalException("If expression");
return NilVal.instance();*/





