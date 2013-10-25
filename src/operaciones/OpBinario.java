package operaciones;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

import Assembler.ManejadorRegistros;

public abstract class OpBinario {
	
	protected OpBinario Instance;

	
	public abstract String operacion();
	public boolean execute(BufferedWriter file,Stack<String> pila,ManejadorRegistros mr,boolean conmut){
		if(!pila.empty()){
		String segundo = pila.pop();
		if(!pila.empty()){
		String primero = pila.pop();
		if (primero.startsWith("#")){ //PRIMERO ES REGISTRO
			try {
				file.write(this.operacion()+" "+primero.substring(1)+", _"+segundo);
				file.newLine();
				pila.push(primero);
			} catch (IOException e) {e.printStackTrace();}
			if (segundo.startsWith("#")){ //PRIMERO REG y SEG REG
				mr.liberar(Integer.parseInt(segundo.substring(2)));
			}
		}
		else 
			if(segundo.startsWith("#")){ //SEGUNDO ES REG y PRIMER VAR
				if(conmut){ // ES CONMUTATIVA
					try {
						file.write(this.operacion()+" "+segundo.substring(1)+", _"+primero);
						file.newLine();
						pila.push(segundo);
					} catch (IOException e) {e.printStackTrace();}
					
				}
				else{ // NO CONMUTATIVA
					int pos = mr.cargar(primero);
					try {
						file.write("MOV R"+(pos+1)+", _"+primero);
						file.newLine();
						file.write(this.operacion()+" R"+(pos+1)+", "+segundo.substring(1));
						file.newLine();
						pila.push("#R"+(pos+1));
						mr.liberar(Integer.parseInt(segundo.substring(2)));
						} catch (IOException e) {e.printStackTrace();}
				}
			}
			else{ // AMBAS VARS
					int pos = mr.cargar(primero);
					try {
						file.write("MOV R"+(pos+1)+", _"+primero);
						file.newLine();
						file.write(this.operacion()+" R"+(pos+1)+", _"+segundo);
						file.newLine();
						pila.push("#R"+(pos+1));
						} catch (IOException e) {e.printStackTrace();}
				}
			}
		}
		return true;
	}
}
