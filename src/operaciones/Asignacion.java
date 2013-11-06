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
			String sp= esParametro(file, segundo, mr);
			file.write(this.operacion()+" "+sp+", "+primero.substring(1,primero.length()));
			file.newLine();
			if(!segundo.equals(sp))
				mr.liberar(sp);
			
		} catch (IOException e) {e.printStackTrace();}
		
		mr.liberar(primero);
		
	}
	
	public void Op2Var(BufferedWriter file,Stack<String> pila,ManejadorRegistros mr,String primero,String segundo){
		int pos = mr.cargar(primero);
		try {
			String pp= esParametro(file, primero, mr);
			file.write("MOV "+mr.getRegAss(pos)+", "+pp);
			file.newLine();
			if(!pp.equals(primero)){
				mr.liberar(pp);
			}
			String sp= esParametro(file, segundo, mr);
			
			file.write(this.operacion()+" "+sp+", "+mr.getRegAss(pos));
			file.newLine();
			if(!sp.equals(segundo)){
				mr.liberar(sp);
			}
			
			} 
		catch (IOException e) {e.printStackTrace();}
		mr.liberar(pos);
	}
	
	
}
