package exception;

public class TokenizerException extends Exception{
	
	public TokenizerException(String message){
		System.err.println(message);
	}
}
