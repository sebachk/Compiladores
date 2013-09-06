package accionesSemanticas;

import java.io.FileReader;
import java.io.IOException;

public class ASInicializadora implements AccionSemantica{
	/**inicializa el arreglo y agrega el primer caracter**/
	@Override
	public void Execute(FileReader f, char c, TokenCreator tc) {
		try {f.mark(0);} catch (IOException e) {e.printStackTrace();}
		tc.clearString();
		tc.addChar(c);
	}

}
