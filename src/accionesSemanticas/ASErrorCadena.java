package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;

import compiler.AnalizadorLexico;
import compiler.Estructuras;

public class ASErrorCadena implements AccionSemantica{

	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
		int pos =Estructuras.addTupla("Linea "+AnalizadorLexico.LineasContadas+": No se admiten cadenas multilinea");
		tc.createToken(Estructuras.ERROR, pos);
	}

}
