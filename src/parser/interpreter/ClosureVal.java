package parser.interpreter;

import java.util.List;

import exception.InterpreterException;

public class ClosureVal extends Val{
	private final Env env;
	private final FunExpr funExpr;
	
	ClosureVal(Env env, FunExpr funExpr){
		this.env = env;
		this.funExpr = funExpr;
	}
	
	public Val apply(List<Val> argVals) throws InterpreterException {
        return funExpr.code().eval(
            new Env(new Frame(funExpr.params(), funExpr.locals(), argVals), env));
    }
	
	@Override
	public ClosureVal checkClosure() throws InterpreterException{
		return this;
	}
}
