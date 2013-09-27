package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;

import ALexico.AnalizadorLexico;
import ALexico.Estructuras;
import ALexico.TokenCreator;


public class ASErrorCarInv implements AccionSemantica{

	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
		Estructuras.addError("Error lexico en linea "+AnalizadorLexico.LineasContadas +": Caracter invalido");
		int pos=Estructuras.addTupla("error de caracter");
		tc.createToken(Estructuras.ERROR, pos);
	}

}
