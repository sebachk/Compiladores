package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;

import ALexico.AnalizadorLexico;
import ALexico.Estructuras;
import ALexico.TokenCreator;


public class ASErrorCadena implements AccionSemantica{

	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
		Estructuras.addError("Error lexico en linea "+AnalizadorLexico.LineasContadas+": No se admiten cadenas multilinea");
		int pos = Estructuras.addTupla("Cadena erronea");
		tc.createToken(Estructuras.ERROR, pos);
	}

}
