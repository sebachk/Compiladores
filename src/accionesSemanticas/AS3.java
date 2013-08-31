package accionesSemanticas;

import java.io.FileReader;
import java.io.IOException;
import java.util.Vector;

public class AS3 implements AccionSemantica {
	/**Vuelve la marca hacia atras **/
	@Override
	public void Execute(FileReader f, char c, TokenCreator tc) {
		try {f.reset();} catch (IOException e) {e.printStackTrace();}
	}

}
