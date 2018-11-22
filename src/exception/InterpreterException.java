package exception;

public class InterpreterException extends Exception{
	
	public InterpreterException(String message){
		System.err.println(message);
	}
}
