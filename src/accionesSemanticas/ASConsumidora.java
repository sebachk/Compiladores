package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;

public class ASConsumidora implements AccionSemantica {
	/**Agrega un caracter al arreglo. Consume el caracter**/
	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
		f.mark(0);
		tc.addChar(c);
	}

}
