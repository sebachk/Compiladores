package accionesSemanticas;

import java.io.FileReader;
import java.io.IOException;

public class ASOperador implements AccionSemantica{

	@Override
	public void Execute(FileReader f, char c, TokenCreator tc) {
		try {f.mark(0);
		tc.clearString();
		tc.addChar(c);
		tc.createToken(tc.getString(), -1);
		} catch (IOException e) {e.printStackTrace();}
	}

}
