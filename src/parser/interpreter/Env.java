package parser.interpreter;

import exception.InterpreterException;

public class Env {
	//ambiente circostante 
	private final Env enclosing;
	private final Frame frame;
	
	Env(Frame frame, Env env){
		this.frame = frame;
		this.enclosing = env;
	}
	
	public Val getValue(String id) {
		Val value = frame.getValue(id);
		if(value != null)
			return value;
		else
			return enclosing.getValue(id);
	}
	
	public void setValue(String id, Val value) throws InterpreterException {
		if(frame.exist(id))
			frame.setValue(id, value);
		else
			enclosing.setValue(id, value);
	}
}

/*
Un ambiente Env è costituito da un Frame e da un ambiente circostante. 
Cercare un identificatore in un ambiente, per leggerne o per modificarne il valore, 
significa cercarlo dapprima nel frame, poi, se necessario, risalire la china cercandolo nel 
frame dell'ambiente circostante, su su fino a scoprire un'associazione in qualche frame. 
Questa ricerca non può fallire se il compilatore ha fatto il suo dovere!
*/