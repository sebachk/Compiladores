package CodigoIntermedio;

import java.util.Stack;

import ALexico.Estructuras;

public class PolacaInversa {
	
	public static final String SENT="#sentencia";
	public static final String BRANCH_FALSO="#BF";
	public static final String BRNCH_INC="#BI";
	public static final String CUERPO_IF="#cpoif";
	public static final String CUERPO_LOOP="#cpoloop";
	public static final String CONDICION="#condicion";
	
	
	
	
	String []polaca;
	int size;
	int pos;
	Stack<String> pila;
	
	public PolacaInversa(){
		polaca = new String[100];
		pila = new Stack<String>();
		size=0;
		pos=0;
	}
	
	public void addPolaco(int elem){
		addPolaco(""+elem);
	}
	public void addPolaco(String elem){
		
		polaca[size]=elem;
		SalvarRebalse();
		size++;
	}
	
	public void addPolaco(String elem, int pos){
	 if(pos<size)
		 polaca[pos]=elem;
	 if(pos==size)addPolaco(elem);
	}
	
	public void SalvarRebalse(){
		if(size==polaca.length){
			String aux[]= new String[size*2];
			for(int i=0;i<size;i++){
				aux[i]=polaca[i];
			}
			polaca=aux;
		}
	}
	
	public void readPolaco(){
		int i=Estructuras.getValorToken(polaca[pos]);
		if(i==257||i==258||polaca[pos].contains("p("))//ID
			pila.push(polaca[pos]);
		else{
			if(polaca[pos]=="#BI"){
				String op=pila.pop();
				pos= Integer.parseInt(op);
			}
			else{
				String op2=pila.pop();
				String op1=pila.pop();
				
			}
		}
		
				
	}
	
	public void ImprimirPolaca(){
		for(int i=0; i<size;i++){
			System.out.println(polaca[i]);
		}
	}
	
	public boolean opUnario(String op){
		return (op==SENT || op==BRANCH_FALSO || op==CUERPO_LOOP);
	}
	
	public boolean opBinario(String op){
		return (op=="+"||op=="-"||op=="/"||op=="*"||op=="="||op=="<"||op==">"||op=="<="||op==">="||op=="!="
				||op==CONDICION||op=="==");
	}
	
	public void FinCondicion(){
		addPolaco("");
		pila.push(size+"");
		addPolaco(BRANCH_FALSO);
	}
	
	
}

