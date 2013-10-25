package operaciones;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

import Assembler.ManejadorRegistros;

public abstract class OpBinario {
	
	protected OpBinario Instance;

	
	public abstract String operacion();
	public void execute(BufferedWriter file,Stack<String> pila,ManejadorRegistros mr){
		String segundo = pila.pop();
		String primero = pila.pop();
		int pos = mr.cargar(primero);
		if(pos == -1){ //Si hubo carga fallida
			
		}
		try {
			file.write("MOV R"+(pos+1)+", _"+primero);
			file.write(this.operacion()+" "+(pos+1)+", _"+segundo);
			pila.push("R"+(pos+1));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
