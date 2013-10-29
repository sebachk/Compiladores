package ALexico;
/**Tupla de la tabla de simbolos**/
public class TuplaTS {
	
	public String valor;
	public String tipo;
	public String uso;
	public int referencia;
	
	public String print(){
		String print="";
		print+=valor+"	"+tipo+"	"+uso+"	"+referencia;
		return print;
	}
}
