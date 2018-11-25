package exception;

public class TokenizerException extends Exception{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public TokenizerException(String message){
		System.err.println(message);
	}
}
