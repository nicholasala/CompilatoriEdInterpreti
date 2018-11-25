package parser.interpreter;

public class Env {
	//ambiente circostante 
	private final Env enclosing;
	private final Frame frame;
	
	Env(Frame frame, Env env){
		this.enclosing = null;
		this.frame = null;
	}
	
	//implementare metodi per ricerca/ottenimento valore
}

/*
Un ambiente Env è costituito da un Frame e da un ambiente circostante. 
Cercare un identificatore in un ambiente, per leggerne o per modificarne il valore, 
significa cercarlo dapprima nel frame, poi, se necessario, risalire la china cercandolo nel 
frame dell'ambiente circostante, su su fino a scoprire un'associazione in qualche frame. 
Questa ricerca non può fallire se il compilatore ha fatto il suo dovere!
*/