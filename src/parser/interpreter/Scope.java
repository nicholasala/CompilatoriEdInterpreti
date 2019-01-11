package parser.interpreter;

import java.util.ArrayList;

import exception.ParserException;
import tokenizer.Token;

public class Scope {
	private final ArrayList<String> ids;
	private final Scope enclosing;
	
	Scope(ArrayList<String> ids, Scope additionalScope){
		this.ids = ids;
		this.enclosing = additionalScope;
	}
	
	public void checkId(Token t) throws ParserException {
		if(ids.contains(t.getTextValue()))
			return;
		if(enclosing!=null)
			enclosing.checkId(t);
		
		throw new ParserException("ParserException: expected var: "+t.getTextValue()+" not declared");
	}
}
