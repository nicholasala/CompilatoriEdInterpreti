package parser.interpreter;

public class GetVarExpr extends Expr{
	private final String id;
	
	GetVarExpr(String id){
		this.id = id;
	}

	@Override
	public Val eval(Env env) {
		// TODO Auto-generated method stub
		return null;
	}

}
