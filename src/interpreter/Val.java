package interpreter;

public class Val {
	
	BoolVal checkBool(){
		throw new InterpreterException(this + " Is not a bool");
	}
}
