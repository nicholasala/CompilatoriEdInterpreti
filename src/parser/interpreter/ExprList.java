package parser.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import exception.InterpreterException;

public class ExprList{
	private final List<Expr> list;
	
	ExprList(){
		list = new ArrayList<Expr>();
	}
	
	public void add(Expr expr) {
		list.add(expr);
	}

	public List<Val> eval(Env env) throws InterpreterException{
		return list.stream().map(expr -> {
			try {
				return expr.eval(env);
			} catch (InterpreterException e) { e.printStackTrace(); }
			return null;
		}).collect(Collectors.toList());
	}

}
