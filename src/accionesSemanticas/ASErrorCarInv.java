package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;

import ALexico.AnalizadorLexico;
import ALexico.Estructuras;
import ALexico.TokenCreator;


public class ASErrorCarInv implements AccionSemantica{

	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
		int pos=Estructuras.addTupla("linea "+AnalizadorLexico.LineasContadas +": caracter invalido, se esperaba '=' luego del '!'");
		tc.createToken(Estructuras.ERROR, pos);
	}

}
