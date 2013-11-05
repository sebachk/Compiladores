package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;

import ALexico.Estructuras;
import ALexico.TokenCreator;


public class ASFinCadena implements AccionSemantica{

	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
		f.mark(0);
		tc.addChar(c);
		int pos=Estructuras.addTupla(tc.getString(),"NaN",Estructuras.USO_CADENA);
		tc.createToken(Estructuras.CADENA,pos);
		
	}

}
