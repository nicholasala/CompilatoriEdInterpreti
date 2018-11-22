package parser.interpreter;

public class Env {
	private final Env enclosing;
	private final Frame frame;
	
	Env(Frame frame, Env env){
		this.enclosing = null;
		this.frame = null;
	}
	
	
}
