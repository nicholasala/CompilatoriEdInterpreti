package parser.interpreter;

import java.util.ArrayList;
import java.util.List;

public class ExprList {
	private final List<Expr> exprs;
	
	ExprList(){
		exprs = new ArrayList<Expr>();
	}
	
	public void add(Expr expr) {
		exprs.add(expr);
	}
}
