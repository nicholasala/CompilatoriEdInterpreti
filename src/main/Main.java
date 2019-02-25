package main;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import parser.interpreter.Expr;
import parser.interpreter.Parser;
import exception.InterpreterException;
import exception.ParserException;
import exception.TokenizerException;

public class Main {
	//Prima si impara a non ruttare a tavola e poi si puo anche ruttare
	public static void main(String[] args) throws IOException, TokenizerException, ParserException, InterpreterException {
		
	    BufferedReader reader = new BufferedReader(new FileReader(System.getProperty("user.dir") + "/funny.txt"));
		Parser parser = new Parser(reader);
		Expr program = parser.program();
		
	    program.eval(null).checkClosure().apply(new ArrayList<>());
	}
}
