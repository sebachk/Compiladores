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
				file.write(this.operacion()+" "+mr.getRegAss(Integer.parseInt(primero.substring(2))-1)+", "+mr.getRegAss(Integer.parseInt(segundo.substring(2))-1));
				mr.liberar(Integer.parseInt(segundo.substring(2))-1);
			}
			else{
				file.write(this.operacion()+" "+mr.getRegAss(Integer.parseInt(primero.substring(2))-1)+", _"+segundo);
				
			}
			file.newLine();
			pila.push(primero);
		
		} catch (IOException e) {e.printStackTrace();}
		 //PRIMERO REG y SEG REG
		
	}
	
	public void Op2Var(BufferedWriter file,Stack<String> pila,ManejadorRegistros mr,String primero,String segundo){
		int pos = mr.cargar(primero);
		try {
			file.write("MOV "+mr.getRegAss(pos)+", _"+primero);
			file.newLine();
			file.write(this.operacion()+" "+mr.getRegAss(pos)+", _"+segundo);
			file.newLine();
			pila.push("#R"+(pos+1));
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
				if(conmut){ // ES CONMUTATIVA
					try {
 
						file.write(this.operacion()+" "+mr.getRegAss(Integer.parseInt(segundo.substring(2))-1)+", _"+primero);
						file.newLine();
						pila.push(segundo);
					} catch (IOException e) {e.printStackTrace();}
					
				}
				else{ // NO CONMUTATIVA
					int pos = mr.cargar(primero);
					try {
 
						file.write("MOV "+mr.getRegAss(pos)+", _"+primero);
						file.newLine();
						file.write(this.operacion()+" "+mr.getRegAss(pos)+", "+mr.getRegAss(Integer.parseInt(segundo.substring(2))-1));
						file.newLine();
						pila.push("#R"+(pos+1));
						mr.liberar(Integer.parseInt(segundo.substring(2))-1);
						} catch (IOException e) {e.printStackTrace();}
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
