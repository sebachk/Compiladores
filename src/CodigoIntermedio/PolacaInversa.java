package CodigoIntermedio;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Stack;
import java.util.Vector;

import ALexico.Estructuras;
import ALexico.TuplaTS;

public class PolacaInversa {
	
	public static final String BRANCH_FALSO="#BF";
	public static final String BRANCH_INC="#BI";
	public static final String CALL="#VOID";
	public static final String CALLRET="#CALL";
	
	public static final String RETURN="#RET";
	public static final String PRINT="#print";
	public static final String FUNC_LABEL="Func_";
	
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
	
	public void beginFunction(){
		addPolaco("");
	}
	
	public void finParam(String key,int cant_param){
		funciones.put(key+"_"+cant_param, (Integer)(size-cant_param-1));
		addPolaco(FUNC_LABEL+key+"_"+cant_param,size-cant_param-1);
		addPolaco("Func_#PARAM");
	}
	
	public void retorno(){
		addPolaco(RETURN);
	}
	
	public void endFunction(String name){
		retorno();
	}
	
	public void beginCall(){
		addPolaco("");
		
	}
	
	public void endcall(String key,int param){
		int pos = funciones.get(key+"_"+param);
		addPolaco("PI("+pos+")",size-param-1);
		addPolaco(CALL);
	}
	
	public void CallConRet(){
		polaca[size-1]=CALLRET;
	}
	
	public String getFunction(int pos){
		if(pos<=size)
			return polaca[pos];
		return "";
//		Enumeration<String> g=funciones.keys();
//		String key;
//		while(g.hasMoreElements()){
//			key=g.nextElement();
//			if(funciones.get(key)==pos){
//				return FUNC_LABEL+key;
//			}
//		}
//		return "";
//	
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
			String aux[]= new String[size*2];
			for(int i=0;i<size;i++){
				aux[i]=polaca[i];
			}
			polaca=aux;
		}
	}
	
	public void callPrint(int cadena){
		addPolaco("TS("+cadena+")");
		addPolaco(PRINT);
	}
	
	public String readPolaco(){
 
		if(lector<size){
			String ts = polaca[lector++];
			
			int finpar;
			if(ts.indexOf("TS") != -1){ //Es un valor de la Tabla de simbolos
				finpar=ts.indexOf(")");
				TuplaTS tupla = Estructuras.Tabla_Simbolos.elementAt(Integer.parseInt(ts.substring(3,finpar)));
				if(tupla.uso.equals(Estructuras.USO_CONST)||tupla.uso.equals(Estructuras.USO_CADENA))
					return tupla.valor;
				if(tupla.uso.equals(Estructuras.USO_VAR))
					return "_"+tupla.valor;
				
					
				
				return "[_"+tupla.valor+"]";
			}
			else	if(ts.indexOf("PI")!=-1){ //es un valor de posicion de la polaca
						finpar=ts.indexOf(")");
						return ts.substring(3,finpar);
					}
			return ts;		// es un operador o tag
		}
		else 
			return null;
	}
	
	
	public void ImprimirPolaca(){
		System.out.println("Polaca Inversa: ");
		System.out.println("ииииииииииииииии");
		
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
	
	public void FinCondicion(){
		pila.push(size+"");
		addPolaco("");
		addPolaco(BRANCH_FALSO);
	}
	
	public void FinThenElse(){
		String ptr="0";
		if(!pila.empty())
			ptr=pila.pop();
		pila.push(size+"");
		addPolaco("");
		addPolaco(BRANCH_INC);
		addPolaco("PI("+size+")"+"-saltobi",Integer.parseInt(ptr));	
		addPolaco("Label"+size);
	}
	
	public void FinIf(){
		String ptr="0";
		if(!pila.empty())
			ptr=pila.pop();
		addPolaco("PI("+size+")"+"-saltofin",Integer.parseInt(ptr));
		addPolaco("Label"+size);
	}
	
	public void InicLoop(){
		pila.push(size+"");
		addPolaco("Label"+size);
		
	}
	
	public void FinLoop(){
		String ptr="";
		if(!pila.empty())
			ptr=pila.pop();
		addPolaco("PI("+ptr+")");
		addPolaco(BRANCH_FALSO);
		addPolaco("PI("+(size+2)+")-saltobfWhile");	
		addPolaco(BRANCH_INC);
		addPolaco("Label"+size);
		
	}
	
}

