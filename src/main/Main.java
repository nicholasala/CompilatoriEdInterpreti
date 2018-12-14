package main;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import exception.ParserException;
import exception.TokenizerException;
import parser.interpreter.Expr;
import parser.interpreter.Parser;
import tokenizer.Tokenizer;

public class Main {
	//Prima si impara a non ruttare a tavola e poi si puo anche ruttare
	public static void main(String[] args) throws IOException, TokenizerException, ParserException {
		/*
		 * { ->
			print("ci siamo");
			}
		 * 
		 */
		
		
		byte[] data = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/funny.txt"));
		Tokenizer t = new Tokenizer(new StringReader(new String(data)));
		
		/*Token temp;
		do {
			temp = t.next();
			System.out.println(temp);
		}while(temp.getType() != Type.EOS);*/
		
		Parser parser = new Parser(new StringReader(new String(data)));
		Expr program = parser.program();
	    //program.eval(env);
	}

}
