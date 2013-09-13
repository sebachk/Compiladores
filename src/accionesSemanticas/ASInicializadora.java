package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;

import ALexico.TokenCreator;


public class ASInicializadora implements AccionSemantica{
	/**inicializa el arreglo y agrega el primer caracter**/
	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
		f.mark(0);
		tc.clearString();
		tc.addChar(c);
	}
}
