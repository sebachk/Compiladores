package accionesSemanticas;

import java.io.FileReader;

import compiler.AnalizadorLexico;
import compiler.Estructuras;

public class ASErrorCarInv implements AccionSemantica{

	@Override
	public void Execute(FileReader f, char c, TokenCreator tc) {
		int pos=Estructuras.addTupla("linea "+AnalizadorLexico.LineasContadas+": caracter invalido, se esperaba '=' luego del '!'");
		tc.createToken(Estructuras.ERROR, pos);
	}

}