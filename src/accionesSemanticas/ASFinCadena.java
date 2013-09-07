package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;

import compiler.Estructuras;

public class ASFinCadena implements AccionSemantica{

	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
		f.mark(0);
		tc.addChar(c);
		int pos=Estructuras.addTupla(tc.getString());
		tc.createToken(Estructuras.CADENA,pos );
		
	}

}
