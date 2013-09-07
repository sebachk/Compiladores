package accionesSemanticas;
import compiler.Estructuras;
import compiler.Token;

/**
 * Creador de Tokens. Almacena internamente los caracteres que el AL va leyendo
 *
 */
public class TokenCreator {
	private String cadena;
	private Token token;
	
	public TokenCreator(){
		cadena = new String();
	}
	
	public void addChar(char c){cadena = cadena + c;}
	
	public String getString(){return cadena;}
	
	public void clearString(){cadena = new String();}
	
	public void createToken(String chain,int indice){
		token= new Token(Estructuras.getValorToken(chain),indice);
		
		
	}
	
	public Token GetToken(){
		return token;
	
	}
}
