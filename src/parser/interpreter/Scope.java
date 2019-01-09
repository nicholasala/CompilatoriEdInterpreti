package parser.interpreter;

import java.util.ArrayList;

import exception.ParserException;
import tokenizer.Token;

public class Scope {
	private ArrayList<String> ids;
	
	Scope(){
		ids = new ArrayList<String>();
	}
	
	Scope(ArrayList<String> ids){
		this.ids = ids;
	}
	
	Scope(ArrayList<String> ids, Scope additionalScope){
		this.ids = ids;
		this.ids.addAll(additionalScope.ids);
	}
	
	Scope(ArrayList<String> first, ArrayList<String> second){
		ids = first;
		ids.addAll(second);
	}
	
	public void checkId(Token t) throws ParserException {
		System.out.println("\nCheck "+t+" token in "+ids+"\n");
		if(!ids.contains(t.getTextValue()))
			throw new ParserException("ParserException: expected var: "+t.getTextValue()+" not declared");
	}
}
