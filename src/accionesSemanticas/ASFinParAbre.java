package accionesSemanticas;

import java.io.FileReader;
import java.io.IOException;

import compiler.Estructuras;

public class ASFinParAbre implements AccionSemantica{

	@Override
	public void Execute(FileReader f, char c, TokenCreator tc) {
		try {
		f.reset();
		tc.createToken(tc.getString(),-1);
		} catch (IOException e) {e.printStackTrace();}
		
	}

}
