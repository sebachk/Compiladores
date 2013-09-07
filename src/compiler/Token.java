package compiler;
/**
 * Clase que representa una dupla de un identificador y la posicion en la tabla de simbolos
 */
public class Token {
	private int identif_tt;
	private int indice_ts;
	
	public Token(int tt,int ts){
		setIdentif_tt(tt);
		setIndice_ts(ts);
	}
	
	public int getIdentif_tt() {
		return identif_tt;
	}
	public void setIdentif_tt(int identif_tt) {
		this.identif_tt = identif_tt;
	}
	public int getIndice_ts() {
		return indice_ts;
	}
	public void setIndice_ts(int indice_ts) {
		this.indice_ts = indice_ts;
	}
	
	public boolean esError(){
		return getIdentif_tt()==Estructuras.Tabla_Token.get(Estructuras.ERROR);
		
	}
	
}
