package parser.interpreter;

import java.util.ArrayList;
import java.util.List;

import exception.InterpreterException;

public class SeqExpr extends Expr{
		private List<Expr> exprList;
	
		SeqExpr(){
			this.exprList = new ArrayList<>();
		}
		
		public SeqExpr(List<Expr> exprList) {
	        this.exprList = exprList;
	    }

		@Override
		public Val eval(Env env) throws InterpreterException {
			for(Expr e: exprList)
	            e.eval(env);
	        return NilVal.nil;
		}
}
