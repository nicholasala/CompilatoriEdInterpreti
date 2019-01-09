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
	
	public int size() {
		return list.size();
	}
	
	public Expr getAt(int index) {
		if(index < size())
			return list.get(index);
		else
			return null;
	}

	//TODO rivedere con uno stream
	public List<Val> eval(Env env) throws InterpreterException{
		return list.stream().map(expr-> {
			try {
				return expr.eval(env);
			} catch (InterpreterException e) {
				return NilVal.nil;
			}
		}).collect(Collectors.toList());
	}

}
