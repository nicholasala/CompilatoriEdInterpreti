package exception;

public class InterpreterException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public InterpreterException(String message){
		System.err.println(message);
	}
}
