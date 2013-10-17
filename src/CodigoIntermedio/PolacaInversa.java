package CodigoIntermedio;

import java.util.Stack;

import ALexico.Estructuras;

public class PolacaInversa {
	
	public static final String SENT="#sentencia";
	public static final String BRANCH_FALSO="#BF";
	public static final String BRANCH_INC="#BI";
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
			System.out.println("pos "+i+"= "+polaca[i]);
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
		pila.push(size+"");
		addPolaco("");
		addPolaco(BRANCH_FALSO);
	}
	
	public void FinThenElse(){
		String ptr=pila.pop();
		pila.push(size+"");
		addPolaco("");
		addPolaco(BRANCH_INC);
		addPolaco(size+"-saltobi",Integer.parseInt(ptr));	
	}
	
	public void FinIf(){
		String ptr=pila.pop();
		addPolaco(size+"-saltofin",Integer.parseInt(ptr));
	}
	
	public void InicLoop(){
		pila.push(size+"");
		System.out.println("PUTO");
		
	}
	
	public void FinLoop(){
		System.out.println("REPUTO");
		String ptr=pila.pop();
		addPolaco((size+4)+"-saltobfWhile");			
		addPolaco(BRANCH_FALSO);
		addPolaco(Integer.parseInt(ptr));
		addPolaco(BRANCH_INC);
	}
	
}

