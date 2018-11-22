package parser.interpreter;

public class InvokeExpr extends Expr{
	//private final Expr expr;
	//private final ExprList args;
	
	InvokeExpr(Expr Expr, ExprList list ){
		
	}

	@Override
	Val eval(Env env) {
		// TODO Auto-generated method stub
		return null;
	}
	
	//Val eval(Env env){
		//return expr.eval(env).checkClousure().apply(args.eval(env));
	//}
}
