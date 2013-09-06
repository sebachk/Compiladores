package accionesSemanticas;

import java.io.FileReader;
import java.io.IOException;

public class ASConsumidora implements AccionSemantica {
	/**Agrega un caracter al arreglo. Consume el caracter**/
	@Override
	public void Execute(FileReader f, char c, TokenCreator tc) {
		try {f.mark(0);} catch (IOException e) {e.printStackTrace();}
		tc.addChar(c);
	}

}
