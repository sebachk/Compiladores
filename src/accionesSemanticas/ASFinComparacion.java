package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;

public class ASFinComparacion implements AccionSemantica{

	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
		try {
		if(c=='='){
				f.mark(0);
				tc.addChar(c);
		}
		else
			f.reset();
		
		tc.createToken(tc.getString(), -1);
		
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
