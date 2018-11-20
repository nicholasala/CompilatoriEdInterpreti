package main;

import java.io.IOException;
import java.io.StringReader;
import java.nio.file.Files;
import java.nio.file.Paths;

import exception.TokenizerException;
import tokenizer.Token;
import tokenizer.Tokenizer;
import tokenizer.Type;

public class Main {

	public static void main(String[] args) throws IOException, TokenizerException {
		byte[] data = Files.readAllBytes(Paths.get(System.getProperty("user.dir") + "/funny.txt"));
		Tokenizer t = new Tokenizer(new StringReader(new String(data)));
		Token temp;
		do {
			temp = t.next();
			System.out.println(temp);
		}while(temp.getType() != Type.EOS);
	}

}
