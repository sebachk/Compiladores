package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;

//import compiler.Token;

public interface AccionSemantica {

	/*private String cadena;
	private Token tok;
	public String getCadena(){
		return cadena;
	}
	
	
	public Token getToken(){
		return tok;
	}
	*/
	public abstract void Execute(BufferedInputStream f, char c, TokenCreator tc);
}
