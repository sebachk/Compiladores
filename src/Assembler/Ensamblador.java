package Assembler;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;
import operaciones.Mult;
import operaciones.Asignacion;
import operaciones.OpBinario;

import ALexico.Estructuras;
import ALexico.TuplaTS;
import CodigoIntermedio.PolacaInversa;

public class Ensamblador {
	private ManejadorRegistros mr;
	private Stack<String> pila;
	private BufferedWriter escritor;
	private PolacaInversa pi;
	
	private OpBinario suma;
	private OpBinario resta;
	private OpBinario multi;
	private OpBinario divi;
	private OpBinario asig;
	private OpBinario comp;
	
	private String comparadorUsado;
	
	public Ensamblador(){
		suma = new OpBinario("ADD");
		resta = new OpBinario("SUB");
		multi = new Mult("MUL");
		divi = new OpBinario("DIV");
		asig = new Asignacion("MOV");
		comp = new Asignacion("CMP");
		
		mr = new ManejadorRegistros();
		pila = new Stack<String>();
		escritor = null;
		comparadorUsado="";
		try {
			escritor = new BufferedWriter(new FileWriter(new File("cod_assembler.txt")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void ensamblar(PolacaInversa poli){
		
		ensamblarVariables();
		pi=poli;
		String elem = pi.readPolaco();
 
	
		while(elem != null){
			if (!doOperacion(elem)){
				pila.push(elem);
			}	
			elem = pi.readPolaco();
			}
		
		try {
			
			escritor.write("invoke ExitProcess, 0");
			escritor.newLine();
			escritor.write("end start");
			escritor.flush();
			escritor.close();
		} catch (IOException e) {e.printStackTrace();}
 
	}
		
	
	public void ensamblarVariables(){
		Vector<String> variables = new Vector<String>();
		int i=1;
		try{
			escritor.write(".386 \n.model flat, stdcall \noption casemap :none\ninclude \\masm32\\include\\windows.inc \ninclude \\masm32\\include\\kernel32.inc \ninclude \\masm32\\include\\user32.inc \nincludelib \\masm32\\lib\\kernel32.lib \nincludelib \\masm32\\lib\\user32.lib");
			escritor.write("\n.data");
			escritor.newLine();
		for(TuplaTS tupla:Estructuras.Tabla_Simbolos){
			if(tupla.uso.equals(Estructuras.USO_REF) || tupla.uso.equals(Estructuras.USO_VAR)){//Si tiene ambito, es variable
				escritor.write("_"+tupla.valor+" DW ?");
				escritor.newLine();
			}
			if(tupla.valor.contains("'")){
				String nvalor="cadena"+i++;
				escritor.write(""+nvalor+" DW "+"\""+tupla.valor+"\"");
				escritor.newLine();
				tupla.valor=nvalor;
			}
			
		}
		escritor.write(".code");
		escritor.newLine();
		escritor.write ("start:");
		escritor.newLine();
		}
		catch(IOException e){e.printStackTrace();}
		
		
		
	}
		
	
	public boolean doOperacion(String s){
		if(s.equals("+")){return(suma.execute(escritor,pila,mr,true));} //el boolean es para determinar conmutatividad
		if(s.equals("-")){return(resta.execute(escritor,pila,mr,false));}
		if(s.equals("*")){return(multi.execute(escritor,pila,mr,true));}
		if(s.equals("/")){return(divi.execute(escritor,pila,mr,false));}
		if(s.equals("=")){return(asig.execute(escritor,pila,mr,false));}
		if(s.equals("<")||s.equals(">")||s.equals("==")||s.equals("!=")
				||s.equals("<=")||s.equals(">=")){
			comparadorUsado=s;
			return comp.execute(escritor, pila, mr, false);
		}
		
		if(s.contains("Label")){ return WriteS(s);}
		if(s.contains("Func")){return CargaFunc(s);}
		if(s.equals(PolacaInversa.BRANCH_FALSO)){return AddJump();}
		if(s.equals(PolacaInversa.BRANCH_INC)){ return JumpInc();}
		if(s.equals(PolacaInversa.PRINT)){return Print();}
		if(s.equals(PolacaInversa.CALL)){return CallReturn(true);}
		if(s.equals(PolacaInversa.RETURN)){return CallReturn(false);}
		return false;
	}
	
	public boolean CargaFunc(String s){
		if(s.contains("#PARAM")){
			Stack<String> param=new Stack<String>();
			while(!pila.empty())
				param.push(pila.pop());
			try {
			while(!param.empty()){	
				String pop=param.pop();
					escritor.write("pop "+pop.substring(1,pop.length()-1)+"\n");
			}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			WriteS(s);
		return true;
	}
	
	
	
	public boolean CallReturn(boolean call){
		try {
			if(call){
				Stack<String> parametros=new Stack<String>();
				while(pila.peek().contains("_"))
					parametros.push(pila.pop());
				while(!parametros.empty()){
					escritor.write("push ["+parametros.pop()+"] \n");
				}
				int pos=Integer.parseInt(pila.pop());
				
				escritor.write("CALL "+pi.getFunction(pos));
				
				
			}
			else{
				if(!pila.isEmpty()){
					String param=pila.pop();
					String operator = asig.esParametro(escritor, param, mr);
					if(param.equals(operator))
						escritor.write("MOV ax, "+ operator);
					else
						escritor.write("MOV eax, "+ operator);
					escritor.newLine();
				}
				escritor.write("RET");
			}
			escritor.newLine();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			// TODO: handle exception
			return false;
		}
	}
	
	
	public boolean Print(){
		try {
			escritor.write("invoke StdOut, addr "+pila.pop());
			escritor.newLine();
			return true;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return false;
		}
		
	}
	
	public boolean JumpInc(){
		try{
			escritor.write("JMP Label"+pila.pop());
			escritor.newLine();
			return true;
		}catch(IOException e){e.printStackTrace();}
		return false;
	}
	public boolean WriteS(String s){
		
		try {
			escritor.write(s+":");
			escritor.newLine();
			return true;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
	}

	public boolean AddJump(){
		try{
			String dir=pila.pop();
			if(comparadorUsado.equals("<")){
				escritor.write("JB Label"+dir);
			}
			
			if(comparadorUsado.equals("<=")){
				escritor.write("JBE Label"+dir);
			}
			
			if(comparadorUsado.equals(">")){
				escritor.write("JNBE Label"+dir);
			}
			
			if(comparadorUsado.equals(">=")){
				escritor.write("JNB Label"+dir);
			}
			
			if(comparadorUsado.equals("==")){
				escritor.write("JE Label"+dir);
			}
			
			if(comparadorUsado.equals("!=")){
				escritor.write("JNE Label"+dir);
			}
			
			escritor.newLine();
			return true;
		}
		catch(IOException e){
			e.printStackTrace();
			return false;
		}
	}
}
