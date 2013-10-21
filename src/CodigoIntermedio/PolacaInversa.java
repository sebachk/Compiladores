package CodigoIntermedio;

import java.util.Hashtable;
import java.util.Stack;

import ALexico.Estructuras;
import ALexico.TuplaTS;

public class PolacaInversa {
	
	public static final String SENT="#sentencia";
	public static final String BRANCH_FALSO="#BF";
	public static final String BRANCH_INC="#BI";
	public static final String CALL="#CALL";
	public static final String RETURN="#RET";
	public static final String CONDICION="#condicion";
	public static final String PRINT="#print";
	
	
	
	String []polaca;
	Hashtable<String, Integer> funciones;
	int size;
	int lector;
	Stack<String> pila;
	
	public PolacaInversa(){
		polaca = new String[100];
		pila = new Stack<String>();
		size=0;
		lector=0;
		funciones= new Hashtable<String,Integer>();
	}
	
	public void beginFunction(String key){
		funciones.put(key, (Integer)size);
	}
	
	public void endFunction(){//String key){
		addPolaco(RETURN);
		//addPolaco(key);
	}
	
	public void callFunction(String key){
		int pos = funciones.get(key);
		addPolaco("PI("+pos+")");
		addPolaco(CALL);
	}
	
	public int getFunction(String key){
		return funciones.get(key);
	}
	
	public void addPolaco(int elem){
		addPolaco(""+elem);
	}
	public void addPolaco(String elem){
		polaca[size++]=elem;
		SalvarRebalse();
		//size++;
	}
	
	public void addPolaco(String elem, int pos){
	 if(pos<size)
		 polaca[pos]=elem;
	 if(pos==size)addPolaco(elem);
	}
	
	public void SalvarRebalse(){
		if(size>=polaca.length){
			//System.out.println("HOLA MUNDO");
			String aux[]= new String[size*2];
			for(int i=0;i<size;i++){
				aux[i]=polaca[i];
			}
			polaca=aux;
		}
	}
	
	public void callPrint(String cadena){
		System.out.println("PUCHA CHE"+cadena);
		addPolaco(cadena);
		addPolaco(PRINT);
	}
	
	public void readPolaco(){
		int i=Estructuras.getValorToken(polaca[lector]);
		if(i==257||i==258||polaca[lector].contains("p("))//ID
			pila.push(polaca[lector]);
		else{
			if(polaca[lector]=="#BI"){
				String op=pila.pop();
				lector= Integer.parseInt(op);
			}
			else{
				String op2=pila.pop();
				String op1=pila.pop();
				
			}
		}
		
				
	}
	
	public void ImprimirPolaca(){
		System.out.println("Posicion | Valor | IndiceTS");
		for(int i=0; i<size;i++){
			int finpar=0;
			if(polaca[i].indexOf("TS")!=-1){
				finpar=polaca[i].indexOf(")");
				TuplaTS tupla = Estructuras.Tabla_Simbolos.elementAt(Integer.parseInt(polaca[i].substring(3,finpar)));
				System.out.println(i+"   |  "+tupla.valor+"   |  "+ polaca[i].substring(3,finpar));
			}
			else if(polaca[i].indexOf("PI")!=-1){
				finpar=polaca[i].indexOf(")");
				System.out.println(i+"   |  "+polaca[i].substring(3,finpar)+"   |  "+"null");
				
			}
			else
				System.out.println(i+"   |  "+polaca[i]+"   |   null");
				
		}
	}
	
	public boolean opUnario(String op){
		return (op==SENT || op==BRANCH_FALSO);
	}
	
	public boolean opBinario(String op){
		return (op=="+"||op=="-"||op=="/"||op=="*"||op=="="||op=="<"||op==">"||op=="<="||op==">="||op=="!="
				||op==CONDICION||op=="==");
	}
	
	public void FinCondicion(){
		pila.push(size+"");
		addPolaco("");
		addPolaco(BRANCH_FALSO);
	}
	
	public void FinThenElse(){
		String ptr=pila.pop();
		pila.push(size+"");
		addPolaco("");
		addPolaco(BRANCH_INC);
		addPolaco("PI("+size+")"+"-saltobi",Integer.parseInt(ptr));	
	}
	
	public void FinIf(){
		String ptr=pila.pop();
		addPolaco("PI("+size+")"+"-saltofin",Integer.parseInt(ptr));
	}
	
	public void InicLoop(){
		pila.push(size+"");
	
	}
	
	public void FinLoop(){
		String ptr=pila.pop();
		addPolaco("PI("+(size+4)+")-saltobfWhile");			
		addPolaco(BRANCH_FALSO);
		addPolaco("PI("+ptr+")");
		addPolaco(BRANCH_INC);
	}
	
}

