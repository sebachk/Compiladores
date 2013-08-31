package accionesSemanticas;
import compiler.Token;


public class TokenCreator {
	private String cadena;
	
	public TokenCreator(){
		cadena = new String();
	}
	
	public void addChar(char c){cadena = cadena + c;}
	
	public String getString(){return cadena;}
	
	public void clearString(){cadena = new String();}
	
	public Token createToken(){
		
	}
}
