package compiler;
import java.util.*;

public class Estructuras {
	/** Esta clase contiene todas las estructuras globales que se necesitan**/
	
	
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
	
	
	
	public static Vector<TuplaTS> Tabla_Simbolos=new Vector<TuplaTS>();
	public static final Hashtable<String, Integer> Tabla_Token=new Hashtable<>();
	
	
	public Estructuras(){
		Tabla_Token.put("ID",1 );
		Tabla_Token.put("CTE",2 );
		Tabla_Token.put("if", 3);
		Tabla_Token.put("then",4 );
		Tabla_Token.put("else",5 );
		Tabla_Token.put("function",6 );
		Tabla_Token.put("begin", 7);
		Tabla_Token.put("end", 8);
		Tabla_Token.put("print",9 );
		Tabla_Token.put("return",10 );
		Tabla_Token.put("uint", 11);
		Tabla_Token.put("loop", 12);
		Tabla_Token.put("until", 13);
		Tabla_Token.put("+", 14);
		Tabla_Token.put("*", 15);
		Tabla_Token.put("/", 16);
		Tabla_Token.put("-", 17);
		Tabla_Token.put("=", 18);
		Tabla_Token.put("<", 19);
		Tabla_Token.put(">", 20);
		Tabla_Token.put(">=", 21);
		Tabla_Token.put("<=", 22);
		Tabla_Token.put("(", 23);
		Tabla_Token.put(")", 24);
		Tabla_Token.put(",", 25);
		Tabla_Token.put(";", 26);
		Tabla_Token.put("!=", 27);
		Tabla_Token.put("==", 28);
		Tabla_Token.put("CADENA", 29);
		Tabla_Token.put(this.ERROR, 30);
		Tabla_Token.put(this.WARNING, 31);
		
	}
	
	
	
	
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
	
	public static int getValorToken(String key){
		Integer resultado=Tabla_Token.get(key);
		return (int)resultado;
	}
	
	public void PrintTablaS(){
		System.out.println("Tabla de simbolos:");
		System.out.println("Nombre");
		for(int i =0;i<Tabla_Simbolos.size();i++){
			System.out.println(Tabla_Simbolos.elementAt(i).print());
		}
	}
	
	
}
