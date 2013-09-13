package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;

import ALexico.TokenCreator;


public interface AccionSemantica {
	public abstract void Execute(BufferedInputStream f, char c, TokenCreator tc);
}
