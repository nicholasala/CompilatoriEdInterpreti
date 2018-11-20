package exception;

public class ParserException extends Exception{
	
	public ParserException(String message){
		System.err.println(message);
	}
}
