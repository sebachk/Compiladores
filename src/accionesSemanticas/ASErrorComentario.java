package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.IOException;

import ALexico.AnalizadorLexico;
import ALexico.Estructuras;
import ALexico.TokenCreator;


public class ASErrorComentario implements AccionSemantica {

	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
		f.mark(0);
		Estructuras.addError("Error lexico en linea"+AnalizadorLexico.LineasContadas+"Error: EOF leido en medio de un comentario");
		int pos = Estructuras.addTupla("error de comentario");
		tc.createToken(Estructuras.ERROR, pos);
	}
}
