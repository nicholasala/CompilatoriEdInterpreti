package parser.interpreter;

import java.util.List;

public class FunExpr extends Expr{
	//private final List<String> params;
	//private final List<String> locals;
	//private final Expr code;

	@Override
	Val eval(Env env) {
        return new ClosureVal(env, this);
    }
}
