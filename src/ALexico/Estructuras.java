package ALexico;
import java.util.*;

/** Esta clase contiene todas las estructuras globales que se necesitan
 * Tabla de simbolos y tabla de tokens
 * **/
public class Estructuras {
	
	
	//*********************************************************************
	// TABLA DE TOKENS - IMPLEMENTADA CON UNA HASH
	//*********************************************************************
	
	public static final String ID="ID";
	public static final String CTE="CTE";
	public static final String IF ="if";
	public static final String THEN ="then";
	public static final String ELSE ="else";
	public static final String FUNCTION ="function";
	public static final String BEGIN ="begin";
	public static final String END ="end";
	public static final String RETURN="return";
	public static final String UNTIL="until";
	public static final String LOOP="loop";
	public static final String UINT ="uint";
	public static final String PRINT ="print";
	public static final String MAS="+";
	public static final String MENOS="-";
	public static final String POR ="*";
	public static final String DIV="/";
	public static final String PAR_IZQ="(";
	public static final String PAR_DER=")";
	public static final String MENOR="<";
	public static final String MAYOR=">";
	public static final String ASIG="=";
	public static final String IGUAL="==";
	public static final String MENOR_IGUAL="<=";
	public static final String MAYOR_IGUAL=">=";
	public static final String COMA=",";
	public static final String PCOMA=";";
	public static final String DISTINTO="!=";
	public static final String CADENA="CADENA";
	public static final String ERROR="ERROR";
	public static final String WARNING="WARNING";
	
	public static final Hashtable<String, Integer> Tabla_Token=new Hashtable<>();
	
	public Estructuras(){
		Tabla_Token.put("ID",257 );
		Tabla_Token.put("CTE",258 );
		Tabla_Token.put("if", 260);
		Tabla_Token.put("then",261 );
		Tabla_Token.put("else",262 );
		Tabla_Token.put("function",265 );
		Tabla_Token.put("begin", 266);
		Tabla_Token.put("end", 267);
		Tabla_Token.put("print",263 );
		Tabla_Token.put("return",264 );
		Tabla_Token.put("uint", 270);
		Tabla_Token.put("loop", 268);
		Tabla_Token.put("until", 269);
		Tabla_Token.put("+", (int)'+');
		Tabla_Token.put("*", (int)'*');
		Tabla_Token.put("/", (int)'/');
		Tabla_Token.put("-", (int)'-');
		Tabla_Token.put("=", (int)'=');
		Tabla_Token.put("<", (int)'<');
		Tabla_Token.put(">", (int)'>');
		Tabla_Token.put(">=", 271); 
		Tabla_Token.put("<=", 272);
		Tabla_Token.put("(", (int)'(');
		Tabla_Token.put(")", (int)')');
		Tabla_Token.put(",", (int)',');
		Tabla_Token.put(";", (int)';');
		Tabla_Token.put("!=", 273);
		Tabla_Token.put("==", 274);
		Tabla_Token.put("CADENA", 259);
		Tabla_Token.put(this.ERROR, 256);
		Tabla_Token.put(this.WARNING, 31);
		
	}
	public static int getValorToken(String key){
		Integer resultado=Tabla_Token.get(key);
		return (int)resultado;
	}
	
	public static String getStringToken(int key){
		Enumeration e=Tabla_Token.keys();
		while(e.hasMoreElements()){
			String r=(String)e.nextElement();
			if(Tabla_Token.get(r)==key)
				return r;
				
		}
		return null;
	}
	
	//*********************************************************************
	// TABLA DE SIMBOLOS - IMPLEMENTADA CON UN VECTOR
	//*********************************************************************

	public static Vector<TuplaTS> Tabla_Simbolos=new Vector<TuplaTS>();
	
	public static int enTupla(String cadena){
		for(int i=0;i<Tabla_Simbolos.size();i++){
			if(Tabla_Simbolos.get(i).valor.equals(cadena))
				return i;
		}
		return -1;
	}
	
	public static int addTupla(String cadena){
		TuplaTS nueva= new TuplaTS();
		nueva.valor=cadena;
		Tabla_Simbolos.add(nueva);
		return Tabla_Simbolos.size()-1;
	}
	
	public static void PrintTablaS(){
		System.out.println("Tabla de simbolos:");
		System.out.println("Nombre");
		for(int i =0;i<Tabla_Simbolos.size();i++){
			System.out.println(Tabla_Simbolos.elementAt(i).print());
		}
	}
	
	
}
