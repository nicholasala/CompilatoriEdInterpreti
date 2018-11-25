package parser.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ExprList{
	private final List<Expr> list;
	
	ExprList(){
		list = new ArrayList<Expr>();
	}
	
	public void add(Expr expr) {
		list.add(expr);
	}

	public List<Val> eval(Env env){
		return list.stream().map(expr -> expr.eval(env)).collect(Collectors.toList());
	}

}
