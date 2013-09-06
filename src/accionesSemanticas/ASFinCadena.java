package accionesSemanticas;

import java.io.FileReader;
import java.io.IOException;

import compiler.Estructuras;

public class ASFinCadena implements AccionSemantica{

	@Override
	public void Execute(FileReader f, char c, TokenCreator tc) {
		try {f.mark(0);
		tc.addChar(c);
		int pos=Estructuras.addTupla(tc.getString());
		tc.createToken(Estructuras.CADENA,pos );
		} catch (IOException e) {e.printStackTrace();}
		
	}

}
