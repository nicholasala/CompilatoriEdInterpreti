package tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;

import exception.TokenizerException;

public class Tokenizer
{
	public static ArrayList<Type> keyWord = new ArrayList<Type>();
	Reader reader;
	Token previus;
	Token actual = null;
	int c;
	
	public Tokenizer(Reader reader) throws IOException {
		this.reader = reader;
		
		//initialize keyword list
		Type end = Type.ENDOFKEYWORD;
		for(Type t : Type.values()) {
			if(t != end) {
				keyWord.add(t);
			}else { break; }
		} 
	}
	
    public Token next() throws IOException, TokenizerException {
    	if(actual == null) {
    		skip();
            markAndRead();
            
            switch((char)c) {
    	    	case ';':
    	    		return new Token(0, Type.SEMICOLON);
    	    	case ',':
    	    		return new Token(0, Type.COMMA);
    	    	case '(':
    	    		return new Token(0, Type.OPAREN); 
    	    	case ')':
    	    		return new Token(0, Type.CPAREN);
    	    	case '{':
    	    		return new Token(0, Type.OBRACES); 
    	    	case '}':
    	    		return new Token(0, Type.CBRACES);
    	    	case '!':
    	    		return getTokenWith('=', Type.NOTEQ, Type.EXCLAMATION);
    	    	case '+':
    	    		return getTokenWith('=', Type.PLUSEQUALS, Type.PLUS);
    	    	case '*':
    	    		return getTokenWith('=', Type.STAREQUALS, Type.STAR);
    	    	case '/':
    	    		return getTokenWith('=', Type.SLASHEQUALS, Type.SLASH);
    	    	case '%':
    	    		return getTokenWith('=', Type.MODEQUALS, Type.MOD);
    	    	case '<':
    	    		return getTokenWith('=', Type.LEFTEQUALSTAG, Type.LEFTTAG);
    	    	case '>':
    	    		return getTokenWith('=', Type.RIGHTEQUALSTAG, Type.RIGHTTAG);
    	    	case '=':
    	    		return getTokenWith('=', Type.EQEQ, Type.EQUALS);
    	    	case '"':
    	    		return getString();
    	    	case '_':
    	    		return getVariable();
    	    	case (char)-1:
    	    		return new Token(0, Type.EOS);
    	    	case '-':
    	    		markAndRead();
    	    		if((char)c == '>') {
    	    			return new Token(0, Type.INDICATOR);
    	    		}else {
    	    			reader.reset();
    	    			return getTokenWith('=', Type.MINUSEQUALS, Type.MINUS);
    	    		}
    	    	default: {
    	    		if(Character.isLetter((char)c)) {
    	    			return getKeyWordOrVar();
    	    		}else if(Character.isDigit(c)) {
    	    			return getDigit();
    	    		}else {
    	    			return new Token(0, Type.UNKNOW);
    	    		}
    	    	}
            }
    	}else{
            	Token ret = actual;
            	actual = null;
            	return ret;
        }
    }
    
    public void setPrev(Token prev) {
    	previus = prev;
    }
    
    public Token prev(Token actual) {
    	this.actual = actual;
    	return previus;
    }
    
    public boolean isEOS() { return c == -1; }

	//skip white spaces and comments
    private void skip() throws IOException, TokenizerException {
    	int previus;
    	
    	do {
    		previus = c;
    		markAndRead();
    		
    		if(c == '/' && previus == '/')
    			skipInlineComments();
    		else if(c == '*' && previus == '/')
    			skipMultiLineComments();
    		
    	}while(Character.isWhitespace((char) c) || c == '/');
    	
    	reader.reset();
    }

	//skip inline comments
	private void skipInlineComments() throws IOException {
		while(c != '\n') {  
			markAndRead();
		}
	}
	
	//skip multi line comments
    private void skipMultiLineComments() throws IOException, TokenizerException {
    	int previus;
    	
    	do {
    		previus = c;
    		markAndRead();
    		
    		if(c == '*' && previus == '/')
    			skipMultiLineComments(); 
    		
    	}while(!(c == '/' && previus == '*') && c != -1);
    	
    	checkEOS("Comment not closed");
    }
    
    //mark and read
    private void markAndRead() throws IOException {
    	reader.mark(1);
        c = reader.read();
	}

	//return the correct Token with or without passed char
    private Token getTokenWith(char compared, Type a, Type b) throws IOException {
		markAndRead();
		if((char)c == compared) {
			return new Token(0, a);
		}else {
			reader.reset();
			return new Token(0, b);
		}
    }
    
    //get string token
    private Token getString() throws IOException, TokenizerException {
    	StringBuilder sb = new StringBuilder("");
    	while((c = reader.read()) != '"'  && c != -1){
            sb.append((char)c);
        }
    	checkEOS("String not closed");
    	return new Token(sb.toString(), Type.STRING);
	}

	//get the key word or the variable
    private Token getKeyWordOrVar() throws IOException {
    	StringBuilder sb = new StringBuilder("");
    	reader.reset();
    	
    	while(Character.isLetter(c = reader.read()) || c =='_'){
            sb.append((char)c);
            reader.mark(1);
        }
    	
    	reader.reset();
    	
    	for(Type t : keyWord) {
        	if(sb.toString().equals(t.toString().toLowerCase())) {
        		return new Token(0, t);
        	}
        }
    	
    	//var case
    	return new Token(sb.toString(), Type.ID);
    }
    
    //get variable
    private Token getVariable() throws IOException {
    	StringBuilder sb = new StringBuilder("");
    	
    	do {
    		sb.append((char)c);
    		c = reader.read();
    	}while(Character.isLetter(c) || c == '_');
    	
    	return new Token(sb.toString(), Type.ID);
    }
    
    //get digits
    private Token getDigit() throws IOException {
    	int num = 0;
		
		while(Character.isDigit(c)) {
			reader.mark(1);
			num *= 10;
			num += Character.getNumericValue((char)c);
			c = reader.read();
		}
		reader.reset();
		return new Token(num, Type.NUM);
  	}
    
    //throw TokenizerException when reach the end of stream
    private void checkEOS(String message) throws TokenizerException {
		if(c == -1)
			throw new TokenizerException("Tokenizer exception: "+message);
	}
}