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

import operaciones.Comparacion;
import operaciones.Divisor;
import operaciones.Mult;
import operaciones.Asignacion;
import operaciones.OpBinario;
import operaciones.Resta;
import operaciones.Suma;

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
		suma = new Suma();
		resta = new Resta();
		multi = new Mult("MUL");
		divi = new Divisor();
		asig = new Asignacion("MOV");
		comp = new Comparacion();
		
		mr = new ManejadorRegistros();
		pila = new Stack<String>();
		escritor = null;
		comparadorUsado="";
		try {
			escritor = new BufferedWriter(new FileWriter(new File("salida.asm")));
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
			escritor.write("overflow: invoke StdOut, addr errorOverflow\n");
			escritor.write("invoke ExitProcess, 0\n");
			escritor.write("signo: invoke StdOut, addr errorSigno\n");
			escritor.write("invoke ExitProcess, 0\n");
			escritor.write("end start");
			escritor.flush();
			escritor.close();
			
			if(!pila.isEmpty()){
				while(!pila.empty())
					System.err.println(pila.pop());
			}
		} catch (IOException e) {e.printStackTrace();}
 
	}
		
	
	public void ensamblarVariables(){
		Vector<String> variables = new Vector<String>();
		int i=1;
		try{
			escritor.write(".386 \n.model flat, stdcall \noption casemap :none\ninclude \\masm32\\include\\windows.inc \ninclude \\masm32\\include\\masm32.inc\ninclude \\masm32\\include\\kernel32.inc \ninclude \\masm32\\include\\user32.inc \nincludelib \\masm32\\lib\\kernel32.lib \nincludelib \\masm32\\lib\\user32.lib\nincludelib \\masm32\\lib\\masm32.lib");
			
			escritor.write("\n.data");
			escritor.newLine();
			escritor.write("sysout db 'var para mostrar por pantalla'\n");
			escritor.write("_retorno dd ? ;variable utilizada para salvar el puntero de retorno en funciones \n");
			
		for(TuplaTS tupla:Estructuras.Tabla_Simbolos){
			if(tupla.uso.equals(Estructuras.USO_REF)){//Si tiene ambito, es variable
				escritor.write("_"+tupla.valor+" DD ?, 0");
				escritor.newLine();
			}
			if(tupla.uso.equals(Estructuras.USO_VAR)){
				escritor.write("_"+tupla.valor+" DW ?");
				escritor.newLine();
			
			}
			if(tupla.uso.equals(Estructuras.USO_CADENA)){
				String nvalor="cadena"+i++;
				escritor.write(""+nvalor+" DB "+"\""+tupla.valor+"\", 0");
				escritor.newLine();
				tupla.valor=nvalor;
			}
			
		}
		escritor.write("errorOverflow DB \" Hubo overflow al realizar una miltplicacion o suma, resultado mayor a 65535\" ,0\n");
		escritor.write("errorSigno DB \" El resultado de la resta es menor a 0, queda fuera de rango\" ,0\n");
		
		escritor.write(".code");
		escritor.newLine();
		escritor.write ("start:");
		escritor.newLine();
		escritor.write("jmp main\n");
		}
		catch(IOException e){e.printStackTrace();}
			
	}
		
	public void imprimirVar(String op){
		try{
			escritor.write("invoke dwtoa, "+op+", addr sysout \n invoke StdOut, addr sysout\n");
		}
		catch (Exception e) {
			// TODO: handle exception
		}
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
		if(s.equals(PolacaInversa.CALL)||s.equals(PolacaInversa.CALLRET)){return CallReturn(true,s);}
		if(s.equals(PolacaInversa.RETURN)){return CallReturn(false,null);}
		return false;
	}
	
	public boolean CargaFunc(String s){
		if(s.contains("#PARAM")){
			try {
				//Salvo el puntero de retorno
				escritor.write("pop _retorno\n");
				while(!pila.empty()&&!pila.peek().contains("#")){	
					String pop=pila.pop();
						escritor.write("pop "+pop.substring(1,pop.length()-1)+"\n");
				}
				//vuelvo a apilar la direccion de retorno
				escritor.write("push _retorno\n");
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		else
			WriteS(s);
		return true;
	}
	
	
	
	public boolean CallReturn(boolean call, String s){
		try {
			if(call){
				Stack<String> parametros=new Stack<String>();
				while(pila.peek().contains("_"))
					parametros.push(pila.pop());
				while(!parametros.empty()){
					escritor.write("push offset "+parametros.pop()+" \n");
				}
				int pos=Integer.parseInt(pila.pop());
				
				escritor.write("CALL "+pi.getFunction(pos));
				if(s.equals(PolacaInversa.CALLRET))
					pila.push("#ax");
				
			}
			else{
				if(!pila.isEmpty()&&!pila.peek().contains("ax")){
					String param=pila.pop();
					String operator = asig.esParametro(escritor, param, mr);
					String ax="ax";
					if(!param.equals(operator))
						ax="eax";
					escritor.write("MOV "+ax+", "+ operator);
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
			if(s.contains("main")){
				escritor.write("main:\n");
				return true;
			}

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
			if(comparadorUsado.equals(">=")){
				escritor.write("JB Label"+dir);
			}
			
			if(comparadorUsado.equals(">")){
				escritor.write("JBE Label"+dir);
			}
			
			if(comparadorUsado.equals("<=")){
				escritor.write("JNBE Label"+dir);
			}
			
			if(comparadorUsado.equals("<")){
				escritor.write("JNB Label"+dir);
			}
			
			if(comparadorUsado.equals("!=")){
				escritor.write("JE Label"+dir);
			}
			
			if(comparadorUsado.equals("==")){
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
