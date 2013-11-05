package operaciones;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

import Assembler.ManejadorRegistros;

public class Comparacion extends OpBinario{

	
	public Comparacion(){
		super("CMP");
	}
	
	
	@Override
	public boolean execute(BufferedWriter file, Stack<String> pila,
			ManejadorRegistros mr, boolean conmut) {
		
		boolean resultado=super.execute(file, pila, mr, conmut);
		String reg=pila.pop();
		mr.liberar(reg);
		
		return resultado;
	}
		
}
