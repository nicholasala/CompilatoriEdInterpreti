package parser.interpreter;

import java.util.List;

public class FunExpr extends Expr{
	private final List<String> params;
	private final List<String> locals;
	private final Expr code;
	
	FunExpr(List<String> params, List<String> locals, Expr code){
		this.params = params;
		this.locals = locals;
		this.code = code;
	}

	@Override
	Val eval(Env env) {
        return new ClosureVal(env, this);
    }
	
	public Expr code() { return this.code; }
	
	public List<String> params() { return this.params; }
	
	public List<String> locals() { return this.locals; }
}
