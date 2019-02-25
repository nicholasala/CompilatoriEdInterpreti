package tokenizer;

import java.io.IOException;
import java.io.Reader;
import java.math.BigDecimal;
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
    	//skip whitespaces
    	skipWhiteSpaces();
    	
    	if(actual == null) {
			markAndRead(1);   
    		
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
    	    	case '%':
    	    		return getTokenWith('=', Type.MODEQUALS, Type.MOD);
    	    	case '<':
    	    		return getTokenWith('=', Type.LEFTEQUALSTAG, Type.LEFTTAG);
    	    	case '>':
    	    		return getTokenWith('=', Type.RIGHTEQUALSTAG, Type.RIGHTTAG);
    	    	case '=':
    	    		return getTokenWith('=', Type.EQEQ, Type.EQUALS);
    	    	case '|':
    	    		return getTokenWith('|', Type.OR, Type.UNKNOW);
    	    	case '&':
    	    		return getTokenWith('&', Type.AND, Type.UNKNOW);
    	    	case '"':
    	    		return getString();
    	    	case '_':
    	    		return getVariable();
    	    	case (char)-1:
    	    		return new Token(0, Type.EOS);
    	    	case '/':
    	    		markAndRead(1);
    	    		if((char)c == '/') {
    	    			skipInlineComment();
    	    			return next();
    	    		}else if((char)c == '*') {
    	    			skipMultiLineComment();
    	    			return next();
    	    		}else{
    	    			reader.reset();
    	    			return getTokenWith('=', Type.SLASHEQUALS, Type.SLASH);
    	    		}
    	    	case '-':
    	    		markAndRead(1);
    	    		if((char)c == '>') {
    	    			return new Token(0, Type.INDICATOR);
    	    		}else if(Character.isDigit(c)) {
    	    			reader.reset();
    	    			return getDigit(false);
    	    		}else{
    	    			reader.reset();
    	    			return getTokenWith('=', Type.MINUSEQUALS, Type.MINUS);
    	    		}
    	    	default: {
    	    		if(Character.isLetter((char)c)) {
    	    			return getKeyWordOrVar();
    	    		}else if(Character.isDigit(c)) {
    	    			reader.reset();
    	    			return getDigit(true);
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

	//skip white spaces
    private void skipWhiteSpaces() throws IOException, TokenizerException {
        while(Character.isWhitespace((char)markAndRead(1)) && c != -1);
        reader.reset();
    }

	//skip inline comments
	private void skipInlineComment() throws IOException {
		while(c != '\n') 
			markAndRead(1);
	}
	
	//skip multi line comments
    private void skipMultiLineComment() throws IOException, TokenizerException {
    	int previus;
    	
    	do {
    		previus = c;
    		markAndRead(1);
    		
    		if(c == '*' && previus == '/')
    			skipMultiLineComment(); 
    		
    	}while(!(c == '/' && previus == '*') && c != -1);
    	
    	checkEOS("Comment not closed");
    	markAndRead(1);
    }
    
    //mark and read
    private int markAndRead(int ahead) throws IOException {
    	reader.mark(ahead);
        c = reader.read();
        return c;
	}

	//return the correct Token with or without passed char
    private Token getTokenWith(char compared, Type a, Type b) throws IOException {
		markAndRead(1);
		if((char)c == compared) {
			return new Token(0, a);
		}else {
			reader.reset();
			return new Token(0, b);
		}
    }
    
    //get string token
    private Token getString() throws IOException, TokenizerException {
    	StringBuilder sb = new StringBuilder();
    	
    	while((c = reader.read()) != '"'  && c != -1)
            sb.append((char)c);
        
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
    	StringBuilder sb = new StringBuilder();
    	
    	do {
    		sb.append((char)c);
    		markAndRead(1);
    	}while(Character.isLetter(c) || c == '_');
    	
    	reader.reset();
    	return new Token(sb.toString(), Type.ID);
    }
    
    //get digits
    private Token getDigit(boolean isPositive) throws IOException, TokenizerException {
    	StringBuilder sb = new StringBuilder();
    	BigDecimal ret;
		
		while(Character.isDigit((char)markAndRead(1)) || (char)c == 'e' || (char)c == '-' || (char)c == '.')
			sb.append((char)c);
		
		reader.reset();
		String[] split = sb.toString().split("e");
		
		//default case
		if(split.length <= 1) {
			ret = new BigDecimal(sb.toString());
		}
		//power case
		else{
			if(split.length == 2) {
				ret = new BigDecimal(split[0]);
				
				if(split[1].toCharArray()[0] == '-')
					ret = BigDecimal.ONE.divide(ret.pow(Integer.valueOf(split[1].substring(1, split[1].length()))));
				else
					ret = ret.pow(Integer.valueOf(split[1]));				
			}else {
				throw new TokenizerException("Not correct number format");
			}
		}
		
		try {
			return isPositive ? new Token(ret, Type.NUM) : new Token(ret.negate(), Type.NUM);
		}catch(Exception e) {
			throw new TokenizerException("Not correct number format");
		}
  	}
    
    //throw TokenizerException when reach the end of stream
    private void checkEOS(String message) throws TokenizerException {
		if(c == -1)
			throw new TokenizerException("Tokenizer exception: "+message);
	}
}