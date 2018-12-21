package main;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import exception.ParserException;
import exception.TokenizerException;
import parser.interpreter.Env;
import parser.interpreter.Expr;
import parser.interpreter.ExprList;
import parser.interpreter.Frame;
import parser.interpreter.InvokeExpr;
import parser.interpreter.Parser;
import tokenizer.Tokenizer;

public class Main {
	//Prima si impara a non ruttare a tavola e poi si puo anche ruttare
	public static void main(String[] args) throws IOException, TokenizerException, ParserException {
		
		//TODO input streamreader
		byte[] data = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/funny.txt"));
		Tokenizer t = new Tokenizer(new StringReader(new String(data)));
		
		/*Token temp;
		do {
			temp = t.next();
			System.out.println(temp);
		}while(temp.getType() != Type.EOS);*/
		
		Parser parser = new Parser(new StringReader(new String(data)));
		Expr program = parser.program();
	    InvokeExpr invoke = new InvokeExpr(program, new ExprList());
	    //invoke.eval();
	}
}

//creare una classe launcher che chiama l'invoke senza argomenti (invoke expr)
//tutti i compilatori utilizzano indici, invece che un hash map chiave valore, potremo utilizzare
//un array
