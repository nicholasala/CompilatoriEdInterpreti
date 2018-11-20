package parser;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;

import tokenizer.*;
import exception.*;

public class Parser {
	//portarsi in giro i nomi esterni per poter controllare che i nomi vengano usati in modo appropriato
	//una variabile scope, che cresce con i nomi delle variabili dichiarate prima di entrare in un blocco 
	//uno scope è qualcosa che ha dei nomi e lo scope circostante (variabili del blocco superiore), è fatto a nodi ogni nodo ha le sue variabili 
	//uile per differenziare i nomi in base a dove verranno utilizzati
	//passando lo scope di funzione in funzione quando esco da uno scope, non devo fare niente 
	//scope.checkInScope(id)
	private Tokenizer tokenizer;
	private Token actual = null;
	
	Parser(StringReader reader) throws IOException{
		tokenizer = new Tokenizer(reader);
	}
	
	public Expr program() throws ParserException, IOException, TokenizerException {
		Expr ret = function(null);
		checkEOS();
		return ret;
	}

	private Expr function(Scope scope) throws ParserException, IOException, TokenizerException {
		next();
		//{ check
		checkType(Type.OBRACES);
		scope = new Scope(optParams(scope), optLocals(scope));
		Expr expr = optSequence(scope);
		next();
		//} check
		checkType(Type.CBRACES);
		return expr;
	}

	private ArrayList<String> optParams(Scope scope) throws ParserException, IOException, TokenizerException {
		next();
		ArrayList<String> ids = new ArrayList<String>();
		if(isType(Type.OPAREN)) {
			ids.addAll(optIds(scope));
			next();
			checkType(Type.CPAREN);
		}else {
			ids.addAll(optIds(scope));
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
			scope.checkId(actual);
			ids.add(actual.getTextValue());
			next();
		}
		return ids;
	}
	
	private Expr sequence(Scope scope) throws IOException, TokenizerException, ParserException {
		Expr expr = optAssignment(scope);
		
		while(isType(Type.SEMICOLON)) {
			next();
			expr = new SeqExpr(expr, optAssignment(scope));
		}
		return expr;
	}
	
	private Expr optAssignment(Scope scope) throws ParserException, IOException, TokenizerException {
		return isInFirstOfAssignment() ? assignment(scope) : null;
	}		

	private Expr assignment(Scope scope) throws ParserException, IOException, TokenizerException {
		if(isType(Type.ID)) {
			scope.checkId(actual);
			String id = actual.getTextValue();
			next();
			if(isAssignmentOperator()) {
				Type operator = actual.getType();
				next();
				return new SetVarExpr(id, operator, assignment(scope));
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
		next();
		if(isType(Type.OR)) {
			next();
			expr = new IfExpr(expr, Type.OR, logicalOr(scope));
		}
		return expr;
	}
	
	private Expr logicalAnd(Scope scope) throws ParserException, IOException, TokenizerException {
		Expr expr = equality(scope);
		next();
		if(isType(Type.AND)) {
			next();
			expr = new IfExpr(expr, Type.AND, logicalAnd(scope));
		}
		return expr;
	}
	
	private Expr equality(Scope scope) throws ParserException, IOException, TokenizerException {
		Expr expr = comparison(scope);
		next();
		if(isType(Type.EQEQ) | isType(Type.NOTEQ)) {
			Type operation = actual.getType();
			next();
			expr = new BinaryExpr(expr, operation, comparison(scope));
		}
		return expr;
	}
	
	private Expr comparison(Scope scope) throws ParserException, IOException, TokenizerException {
		Expr expr = add(scope);
		next();
		if(isType(Type.LEFTTAG) | isType(Type.LEFTEQUALSTAG) | isType(Type.RIGHTTAG) | isType(Type.RIGHTEQUALSTAG)) {
			Type operation = actual.getType();
			next();
			expr = new BinaryExpr(expr, operation, add(scope));
		}
		return expr;
	}
	
	private Expr add(Scope scope) throws ParserException, IOException, TokenizerException {
		Expr expr = mult(scope);
		next();
		while(isType(Type.PLUS) | isType(Type.MINUS)) {
			Type operation = actual.getType();
			next();
			expr = new BinaryExpr(expr, operation, mult(scope));
		}
		return expr;
	}
	
	private Expr mult(Scope scope) throws ParserException, IOException, TokenizerException {
		Expr expr = unary(scope);
		next();
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
			return postfix(scope); //TODO da rivedere
		}
	}
	
	private Expr postfix(Scope scope) throws ParserException, IOException, TokenizerException {
		Expr expr = primary(scope);
		
		while(isType(Type.OPAREN)) {
			expr = new InvokeExpr(expr, args(scope));
		}
		
		return expr;
	}
	
	private ExprList args(Scope scope) throws ParserException, IOException, TokenizerException {
		ExprList list = new ExprList();
		checkType(Type.OPAREN);
		next();
		
		if(isType(Type.CPAREN))
			return list;
		
		list.add(sequence(scope));
		
		while(isType(Type.COMMA)) {
			next();
			list.add(sequence(scope));
		}
		
		checkType(Type.CPAREN);
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
				return function(scope);
			case OBRACES:
				return getSubSequence(scope);
			default:
				throw new ParserException("ParserException: error in primary");
		}
	}
	
	private Expr getNumber(Scope scope) {
    }

    private Expr getBoolean(Scope scope) {
    }

    private Expr getNil(Scope scope) {
    }

    private Expr getString(Scope scope) {
    }

    private Expr getId(Scope scope) {
    }

    private Expr getIf(Scope scope) {
    }

    private Expr getWhile(Scope scope) {
    }

    private Expr getPrint(Scope scope) {
    }

    private Expr getSubSequence(Scope scope) {
}

	///////////////////////////////////////////////////////////////////////////////////////////////////////////////

	private void next() throws IOException, TokenizerException {
		tokenizer.setPrev(actual);
		actual = tokenizer.next();
	}
	
	private void previus() {
		actual = tokenizer.prev(actual);
	}
	
	private boolean isText(String val) throws IOException, TokenizerException {
		return actual.getTextValue().equals(val);
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
	
	private void checkText(String val) throws ParserException, IOException, TokenizerException {
		if(!actual.getTextValue().equals(val))
			throw new ParserException("ParserException: expected value: "+val+" not found");
	}
	
	private void checkType(Type t) throws ParserException {
		if(!(actual.getType() == t))
			throw new ParserException("ParserException: expected type: "+t.toString()+" not found");
	}

	private void checkEOS() throws ParserException {
		if(!tokenizer.isEOS())
			throw new ParserException("ParserException: end of stream not reached");
	}
}
