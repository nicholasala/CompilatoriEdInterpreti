package parser.interpreter;

import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;

import tokenizer.*;
import exception.*;

public class Parser {
	private Tokenizer tokenizer;
	private Token actual = null;
	
	public Parser(Reader reader) throws IOException{
		tokenizer = new Tokenizer(reader);
	}
	
	public Expr program() throws ParserException, IOException, TokenizerException {
		next();
		Expr ret = function(null);
		next();
		checkEOS();
		return ret;
	}

	private Expr function(Scope scope) throws ParserException, IOException, TokenizerException {
		checkType(Type.OBRACES);
		next();
		
		ArrayList<String> optParams = optParams(scope);
		ArrayList<String> optLocals = optLocals(scope);
		ArrayList<String> temps = new ArrayList<String>();
		temps.addAll(optParams);
		temps.addAll(optLocals);
		scope = new Scope(temps, scope);
		Expr expr = optSequence(scope);
		
		checkType(Type.CBRACES);
		next();
		return new FunExpr(optParams, optLocals, expr);
	}

	private ArrayList<String> optParams(Scope scope) throws ParserException, IOException, TokenizerException {
		ArrayList<String> ids = new ArrayList<String>();
		if(isType(Type.OPAREN)) {
			next();
			ids.addAll(optIds(scope));
			checkType(Type.CPAREN);
			next();
		}
		return ids;
	}

	private ArrayList<String> optLocals(Scope scope) throws ParserException, IOException, TokenizerException {
		return optIds(scope);
	}

	private Expr optSequence(Scope scope) throws IOException, TokenizerException, ParserException {
		if(isType(Type.INDICATOR)) {
			next();
			return sequence(scope);
		}
		return null;
	}
	
	private ArrayList<String> optIds(Scope scope) throws ParserException, IOException, TokenizerException {
		ArrayList<String> ids = new ArrayList<String>();
		while(isType(Type.ID)) {
			if(ids.contains(actual.getType().toString()))
				throw new ParserException("ParserException: repeated key declaration");
			ids.add(actual.getTextValue());
			next();
		}
		return ids;
	}
	
	private Expr sequence(Scope scope) throws IOException, TokenizerException, ParserException {
		//sequence ::= optAssignment ( ";" optAssignment )* .
		List<Expr> exprList = new ArrayList<>();
		exprList.add(optAssignment(scope));
		
		while(isType(Type.SEMICOLON)) {
			next();
			exprList.add(optAssignment(scope));
		}
		
		//if(actual.getType() != Type.CBRACES)
			//next();
		
		if(exprList.size() == 0)
			return NilVal.nil;
		else if(exprList.size() == 1)
			return exprList.get(0);
		else
			return new SeqExpr(exprList);
	}
	
	private Expr optAssignment(Scope scope) throws ParserException, IOException, TokenizerException {
		return isInFirstOfAssignment() ? assignment(scope) : NilVal.nil;
	}		

	private Expr assignment(Scope scope) throws ParserException, IOException, TokenizerException {
		if(isType(Type.ID)) {
			scope.checkId(actual);
			String id = actual.getTextValue();
			next();
			if(isAssignmentOperator()) {
				next();
				return new SetVarExpr(id, assignment(scope));
			}else {
				previus();
				return logicalOr(scope);
			}
		}else {
			return logicalOr(scope);
		}
	}
	
	private Expr logicalOr(Scope scope) throws IOException, TokenizerException, ParserException {
		Expr expr = logicalAnd(scope);
		
		if(isType(Type.OR)) {
			next();
			expr = new IfExpr(expr, Type.OR, logicalOr(scope));
		}
		return expr;
	}
	
	private Expr logicalAnd(Scope scope) throws ParserException, IOException, TokenizerException {
		Expr expr = equality(scope);
		
		if(isType(Type.AND)) {
			next();
			expr = new IfExpr(expr, Type.AND, logicalAnd(scope));
		}
		return expr;
	}
	
	private Expr equality(Scope scope) throws ParserException, IOException, TokenizerException {
		Expr expr = comparison(scope);
		
		if(isType(Type.EQEQ) | isType(Type.NOTEQ)) {
			Type operation = actual.getType();
			next();
			expr = new BinaryExpr(expr, operation, comparison(scope));
		}
		return expr;
	}
	
	private Expr comparison(Scope scope) throws ParserException, IOException, TokenizerException {
		Expr expr = add(scope);
		
		if(isType(Type.LEFTTAG) | isType(Type.LEFTEQUALSTAG) | isType(Type.RIGHTTAG) | isType(Type.RIGHTEQUALSTAG)) {
			Type operation = actual.getType();
			next();
			expr = new BinaryExpr(expr, operation, add(scope));
		}
		return expr;
	}
	
	private Expr add(Scope scope) throws ParserException, IOException, TokenizerException {
		Expr expr = mult(scope);
		
		while(isType(Type.PLUS) | isType(Type.MINUS)) {
			Type operation = actual.getType();
			next();
			expr = new BinaryExpr(expr, operation, mult(scope));
		}
		return expr;
	}
	
	private Expr mult(Scope scope) throws ParserException, IOException, TokenizerException {
		Expr expr = unary(scope);
		
		while(isType(Type.STAR) | isType(Type.SLASH) | isType(Type.MOD)) {
			Type operation = actual.getType();
			next();
			expr = new BinaryExpr(expr, operation, unary(scope));
		}
		return expr;
	}
	
	private Expr unary(Scope scope) throws ParserException, IOException, TokenizerException {
		if(isType(Type.PLUS) | isType(Type.MINUS) | isType(Type.EXCLAMATION)) {
			Type operation = actual.getType();
			next();
			return new UnaryExpr(operation, unary(scope));
		}else {
			return postfix(scope);
		}
	}
	
	private Expr postfix(Scope scope) throws ParserException, IOException, TokenizerException {
		Expr expr = primary(scope);
		
		while(isType(Type.OPAREN))
			expr = new InvokeExpr(expr, args(scope));
		
		return expr;
	}
	
	private ExprList args(Scope scope) throws ParserException, IOException, TokenizerException {
		ExprList list = new ExprList();
		checkType(Type.OPAREN);
		next();
		
		if(isType(Type.CPAREN)) {
			next();
			return list;
		}
		
		list.add(sequence(scope));
		
		while(isType(Type.COMMA)) {
			next();
			list.add(sequence(scope));
		}
        
		checkType(Type.CPAREN);
		next();
		return list;
	}
	
	private Expr primary(Scope scope) throws ParserException, IOException, TokenizerException {
		switch(actual.getType()) {
			case NUM:
				return getNumber(scope);
			case TRUE:
			case FALSE:
				return getBoolean(scope);
			case NIL:
				return getNil(scope);
			case STRING:
				return getString(scope);
			case ID:
				return getId(scope);
			case IF:
			case IFNOT:
				return getIf(scope);
			case WHILE:
			case WHILENOT:
				return getWhile(scope);
			case PRINT:
			case PRINTLN:
				return getPrint(scope);
			case OPAREN:
				return getSubSequence(scope);
			case OBRACES:
				return function(scope);
			default:
				throw new ParserException("ParserException: error in primary");
		}
	}
	
	private Expr getNumber(Scope scope) throws IOException, TokenizerException {
		NumVal nVal = new NumVal(actual.getValue());
		next();
		return nVal;
    }

    private Expr getBoolean(Scope scope) throws ParserException, IOException, TokenizerException {
    	BoolVal bVal = new BoolVal(actual.getType());
    	next();
    	return bVal;
    }

    private Expr getNil(Scope scope) throws IOException, TokenizerException {
    	next();
    	return NilVal.nil;
    }

    private Expr getString(Scope scope) throws IOException, TokenizerException {
    	StringVal sVal = new StringVal(actual.getTextValue());
    	next();
    	return sVal;
    }

    private Expr getId(Scope scope) throws ParserException, IOException, TokenizerException {
    	scope.checkId(actual);
    	GetVarExpr getVarExpr = new GetVarExpr(actual.getTextValue());
    	next();
		return getVarExpr;
    }

    private Expr getIf(Scope scope) throws ParserException, IOException, TokenizerException {
    	Expr first, second = null;
    	Type operator = actual.getType();
    	next();
    	Expr condition = sequence(scope);
    	next();
    	checkType(Type.THEN);
    	next();
    	first = sequence(scope);
    	next();
    	
    	if(isType(Type.ELSE)) {
    		next();
    		second = sequence(scope);
    	}
    	
    	checkType(Type.FI);
    	next();
    	return new IfExpr(condition, operator, first, second);
    }

    private Expr getWhile(Scope scope) throws ParserException, IOException, TokenizerException {
    	Expr operation = null;
    	Type operator = actual.getType();
    	next();
    	Expr condition = sequence(scope);
    	
    	if(isType(Type.DO)) {
    		next();
    		operation = sequence(scope);
    	}
    	
    	checkType(Type.OD);
    	next();
    	return new WhileExpr(operator, condition, operation);
    }

    private Expr getPrint(Scope scope) throws IOException, TokenizerException, ParserException {
    	Type operator = actual.getType();
    	next();
    	ExprList exprList = args(scope);
    	return new PrintExpr(operator, exprList);
    }

    private Expr getSubSequence(Scope scope) throws ParserException, IOException, TokenizerException {
    	//subsequence ::= "(" sequence ")" .
    	checkType(Type.OPAREN);
    	next();
    	Expr expr = sequence(scope);
    	checkType(Type.CPAREN);
    	next();
    	return expr;
    }

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void next() throws IOException, TokenizerException {
		tokenizer.setPrev(actual);
		actual = tokenizer.next();
	}
	
	private void previus() {
		actual = tokenizer.prev(actual);
	}
	
	private boolean isType(Type t) throws ParserException {
		return actual.getType() == t;
	}
	
	private boolean isAssignmentOperator() throws ParserException {
		switch(actual.getType()) {
			case EQUALS: case PLUSEQUALS: case MINUSEQUALS: case STAREQUALS: case SLASHEQUALS: case MODEQUALS:
				return true;
			default: return false;
	    }
	}
	
	private boolean isInFirstOfAssignment() {
		switch(actual.getType()) {
			case ID:
			case PLUS: case MINUS:
			case NOTEQ: case EXCLAMATION:
			case NUM:
			case TRUE: case FALSE:
			case NIL: case STRING:
			case OBRACES: case OPAREN:
			case IF: case IFNOT: case WHILE: case WHILENOT: case PRINT: case PRINTLN:
				return true;
			default: return false;
		}
	}
	
	private void checkType(Type t) throws ParserException {
		if(!(actual.getType() == t))
			throw new ParserException("ParserException: expected type: "+t.toString()+" not found, actual: "
					+actual.getType()+" "+actual.getValue()+" "+actual.getTextValue());
	}

	private void checkEOS() throws ParserException {
		if(!tokenizer.isEOS())
			throw new ParserException("ParserException: end of stream not reached");
	}
}
