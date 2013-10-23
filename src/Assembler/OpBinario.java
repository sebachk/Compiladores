package Assembler;

import java.io.BufferedWriter;

public abstract class OpBinario {
	
	public abstract void operacion();
	public static void execute(BufferedWriter file,stack<String> pila){
		String primer = pila.pop();
		String segundo = pila.pop();
		int pos = ManejadorRegistros.cargar(segundo);
		if(pos == -1){ //Si hubo carga fallida
			
		}
		file.write("MOV R"+(pos+1)+", _"+segundo);
		file.write(operacion()+(pos+1)+", _"+primer);
		return;
	}
}
