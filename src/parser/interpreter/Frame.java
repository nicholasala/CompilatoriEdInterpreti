package parser.interpreter;

import java.util.HashMap;
import java.util.List;

public class Frame {
	private final HashMap<String, Val> bindings = new HashMap<String, Val>();
	
	Frame(List<String> params, List<String> locals, List<Val> argvals ){
		
		for(int i=0; i< params.size(); i++)
			bindings.put(params.get(i), argvals.get(i));
		
		for(String s : locals)
			bindings.put(s, new NilVal());
	}
	
	//metodi lettura e modifica valori id
}
