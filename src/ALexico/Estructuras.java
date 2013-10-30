package ALexico;
import java.util.*;

import CodigoIntermedio.ManejadorAmbitos;

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
	public static final String USO_VAR="VARIABLE";
	public static final String USO_CONST="CONSTANTE";
	public static final String PUNT="PUNTERO";
	public static final String USO_REF="REFERENCIA";
	


	
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
	
	public static Vector<String> log_Sintactico = new Vector<String>();
	
	public static Vector<String> log_general = new Vector<String>();
	
	public static Vector<String> log_token = new Vector<String>();
	
	public static int enTupla(String cadena){
		for(int i=0;i<Tabla_Simbolos.size();i++){
			if(Tabla_Simbolos.get(i).valor.equals(cadena))
				return i;
		}
		return -1;
	}
	
	public static void SumAmbito(ManejadorAmbitos ma, String agregado){
		for(TuplaTS t:Tabla_Simbolos){
			if(t.valor.endsWith(ma.getName()))
				t.valor+=agregado;
		}
		
		String amb=ma.lastAmbito();
		ma.FinAmbito();
		ma.NuevoAmbito(amb+agregado);
		
		
	}
	
	public static void addError(String s){
		log_Sintactico.add(s);
	}
	
	public static void addLog(String s){
		log_general.add(s);
	}
	
	public static void addToken(String s){
		log_token.add(s);
	}
	
	
	public static int addTupla(String cadena){
		return addTupla(cadena,"NaN","NaN",-1);
	}
	
	public static int addTupla(String var,String tipo,String uso){
		return addTupla(var, tipo, uso, -1);
	}
	
	public static int addTupla(String var,String tipo,String uso,int referencia){
		TuplaTS nueva= new TuplaTS();
		nueva.valor=var;
		nueva.tipo=tipo;
		nueva.uso=uso;
		nueva.referencia=referencia;
		Tabla_Simbolos.add(nueva);
		return Tabla_Simbolos.size()-1;
	
	}
	
	public static boolean HayErrores(){
		return log_Sintactico.size()>0;
	}
	
	
	
	public static void PrintTablaS(){
		System.out.println("Tabla de simbolos:");
		System.out.println("ииииииииииииииииии");
		System.out.println("pos	|Nombre	|Tipo	|Uso	");
		for(int i =0;i<Tabla_Simbolos.size();i++){
			System.out.println(i+"	"+Tabla_Simbolos.elementAt(i).print());
		}
		System.out.println();
		System.out.println();
		System.out.println();
		
		System.out.println("Log de Errores:");
		System.out.println("иииииииииииииии");
		for(int i =0;i<log_Sintactico.size();i++){
			System.out.println(log_Sintactico.elementAt(i));
			System.out.println("---------------");
		}
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Log del Compilador:");
		System.out.println("иииииииииииииииииии");
		for(int i =0;i<log_general.size();i++){
			System.out.println(log_general.elementAt(i));
			System.out.println("**********************");
		}
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Tokens:");
		System.out.println("иииииииииииииииииии");
		for(int i =0;i<log_token.size();i++){
			System.out.println(log_token.elementAt(i));
			System.out.println("**********************");
		}
		
	}
	
	
}
