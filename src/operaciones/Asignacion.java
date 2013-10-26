package operaciones;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

import Assembler.ManejadorRegistros;

public class Asignacion extends OpBinario {
	
	public Asignacion(String op){
		super(op);
	}
	public void OpReg1(BufferedWriter file,Stack<String> pila,ManejadorRegistros mr,String primero,String segundo){
		try {
			file.write(this.operacion()+" _"+segundo+", "+mr.getRegAss(Integer.parseInt(primero.substring(2))-1));
			file.newLine();
		} catch (IOException e) {e.printStackTrace();}
		mr.liberar(Integer.parseInt(primero.substring(2))-1);
		
	}
	
	public void Op2Var(BufferedWriter file,Stack<String> pila,ManejadorRegistros mr,String primero,String segundo){
		int pos = mr.cargar(primero);
		try {
			file.write("MOV "+mr.getRegAss(pos)+", _"+primero);
			file.newLine();
			file.write(this.operacion()+"_"+segundo+",  "+mr.getRegAss(pos));
			file.newLine();
			} 
		catch (IOException e) {e.printStackTrace();}
		mr.liberar(pos);
	}
	
	
}
