package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.IOException;

import ALexico.Estructuras;
import ALexico.TokenCreator;


public class ASErrorComentario implements AccionSemantica {

	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
		f.mark(0);
		int pos = Estructuras.addTupla("Error: EOF leido en medio de un comentario");
		tc.createToken(Estructuras.ERROR, pos);
	}
}
