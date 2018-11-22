package parser.interpreter;

import tokenizer.Type;

public class IfExpr extends Expr{
	
	//first è forse più corretto chiamarlo condition
	IfExpr(Expr first, Type operator, Expr second){
			
	}
	
	//if second == null -> non presente else
	IfExpr(Expr condition, Type operator, Expr first, Expr second){
		
	}

	@Override
	Val eval(Env env) {
		// TODO Auto-generated method stub
		return null;
	}

}
