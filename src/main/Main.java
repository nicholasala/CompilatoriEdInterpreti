package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import exception.InterpreterException;
import exception.ParserException;
import exception.TokenizerException;
import parser.interpreter.Env;
import parser.interpreter.Expr;
import parser.interpreter.ExprList;
import parser.interpreter.InvokeExpr;
import parser.interpreter.Parser;
import parser.interpreter.Val;

public class Main {
	//Prima si impara a non ruttare a tavola e poi si puo anche ruttare
	public static void main(String[] args) throws IOException, TokenizerException, ParserException, InterpreterException {
		
	    BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/funny.txt"));
		
	    /*Tokenizer t = new Tokenizer(new StringReader(new String(data)));
		Token temp;
		do {
			temp = t.next();
			System.out.println(temp);
		}while(temp.getType() != Type.EOS);*/
			
		Parser parser = new Parser(reader);
		Expr program = parser.program();
		System.out.println("Programma: \n\n");
	    program.eval(null).checkClosure().apply(new ArrayList<>());
	}
}

//creare una classe launcher che chiama l'invoke senza argomenti (invoke expr)
//tutti i compilatori utilizzano indici, invece che un hash map chiave valore, potremo utilizzare
//un array
