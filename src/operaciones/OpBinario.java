package operaciones;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

import Assembler.ManejadorRegistros;

public class OpBinario {
	
	protected String Operador;

	
 
	
	public OpBinario(String Op){
		Operador=Op;
	}
	public String operacion(){
		return Operador;
	}
	
	public void OpReg1(BufferedWriter file,Stack<String> pila,ManejadorRegistros mr,String primero,String segundo){
		try {
			if (segundo.startsWith("#")){
				file.write(this.operacion()+" "+primero.substring(1,primero.length())+", "+segundo.substring(1,segundo.length()));
				mr.liberar(segundo);
			}
			else{
				String param=esParametro(file, segundo, mr);
				file.write(this.operacion()+" "+primero.substring(1,primero.length())+", "+param);
				if(!param.equals(segundo))
					mr.liberar(param);
			}
			file.newLine();
			pila.push(primero);
		
		} catch (IOException e) {e.printStackTrace();}
		 //PRIMERO REG y SEG REG
		
	}
	
	public void Op2Var(BufferedWriter file,Stack<String> pila,ManejadorRegistros mr,String primero,String segundo){
		
		int pos = mr.cargar(primero);
		try {
			String pp= esParametro(file, primero, mr);
			file.write("MOV "+mr.getRegAss(pos)+", "+pp);
			file.newLine();
			if(!pp.equals(primero))
				mr.liberar(pp);
			String sp = esParametro(file, segundo, mr);
			file.write(this.operacion()+" "+mr.getRegAss(pos)+", "+sp);
			file.newLine();
			if(!sp.equals(segundo))
				mr.liberar(sp);
			
			
			pila.push("#"+mr.getRegAss(pos));
			} catch (IOException e) {e.printStackTrace();}
	}
	
	public String esParametro(BufferedWriter file,String param,ManejadorRegistros mr){
		
		if(param.contains("[")){
			try {
				int pos = mr.cargar(param);
				String pelado = param.substring(1,param.length()-1);
				file.write("MOV e"+ mr.getRegAss(pos)+", "+pelado);
				file.newLine();
				return "[e"+mr.getRegAss(pos)+"]";
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		return param;
		
	}
	
	public void VarRegConm(BufferedWriter file,String segundo,Stack<String> pila,String pp){
		try {
			file.write(this.operacion()+" "+segundo.substring(1,segundo.length())+", "+pp);
			file.newLine();
			pila.push(segundo);
		} catch (IOException e) {e.printStackTrace();}
		
	}
	
	public void VarRegnoConm(BufferedWriter file,String segundo,Stack<String> pila,String pp,ManejadorRegistros mr,String primero){
		int pos = mr.cargar(primero);
		try {
			
			file.write("MOV "+mr.getRegAss(pos)+", "+pp);
			file.newLine();
			file.write(this.operacion()+" "+mr.getRegAss(pos)+", "+segundo.substring(1,segundo.length()));
			file.newLine();
			pila.push("#"+mr.getRegAss(pos));
			mr.liberar(segundo);
			} catch (IOException e) {e.printStackTrace();}
	}
	
	public boolean execute(BufferedWriter file,Stack<String> pila,ManejadorRegistros mr,boolean conmut){
		if(!pila.empty()){
		String segundo = pila.pop();
		if(!pila.empty()){
		String primero = pila.pop();
		if (primero.startsWith("#")){ //PRIMERO ES REGISTRO

			OpReg1(file,pila,mr,primero,segundo);
		}
		else 
			if(segundo.startsWith("#")){ //SEGUNDO ES REG y PRIMER VAR
				String pp = esParametro(file, primero, mr);
				
				if(conmut){ // ES CONMUTATIVA
					VarRegConm(file, segundo, pila, pp);
				}
				else{ // NO CONMUTATIVA
					VarRegnoConm(file, segundo, pila, pp, mr, primero);
				}
				if(!pp.equals(primero)){
					mr.liberar(pp);
				}
				
			}
			else{ // AMBAS VARS
 
					Op2Var(file,pila,mr,primero,segundo);
				}
			}
		}
		return true;
	}
}
