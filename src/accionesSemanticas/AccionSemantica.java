package accionesSemanticas;

import java.io.FileReader;

import compiler.Token;

public abstract class AccionSemantica {

	private String cadena;
	private Token tok;
	public String getCadena(){
		return cadena;
	}
	
	
	public Token getToken(){
		return tok;
	}
	public abstract void Execute(FileReader lector);
}
