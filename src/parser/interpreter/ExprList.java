package parser.interpreter;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import exception.InterpreterException;

public class ExprList{
	private final List<Expr> list;
	
	public ExprList(){
		list = new ArrayList<Expr>();
	}
	
	public void add(Expr expr) {
		list.add(expr);
	}

	//TODO rivedere con uno stream
	public List<Val> eval(Env env) throws InterpreterException{
		/*return list.stream().map(expr -> {
			try {
				return expr.eval(env);
			} catch (InterpreterException e) {
				return NilVal.nil; 
			}
		}).collect(Collectors.toList());*/
		ArrayList<Val> ret = new ArrayList<Val>();
		
		for(Expr e : list)
			ret.add(e.eval(env));
		
		return ret;
	}

}
