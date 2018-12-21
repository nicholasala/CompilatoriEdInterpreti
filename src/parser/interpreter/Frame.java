package parser.interpreter;

import java.util.HashMap;
import java.util.List;

import exception.InterpreterException;

public class Frame {
	private final HashMap<String, Val> bindings = new HashMap<String, Val>();
	
	Frame(List<String> params, List<String> locals, List<Val> argvals ){
		
		for(int i=0; i< params.size(); i++)
			bindings.put(params.get(i), argvals.get(i));
		
		for(String s : locals)
			bindings.put(s, NilVal.nil);
	}
	
	public Val getValue(String id){
		if(bindings.keySet().contains(id))
			return bindings.get(id);
		
		return null;
	}
	
	public void setValue(String id, Val value) throws InterpreterException {
		if(bindings.keySet().contains(id)) {
			bindings.remove(id);
			bindings.put(id, value);
		}
			
		throw new InterpreterException("ID "+id+" not found");
	}
	
	public void addValue(String id, Val value) throws InterpreterException {
		bindings.put(id, value);
	}
	
	public boolean exist(String id) {
		return bindings.keySet().contains(id);
	}
}
