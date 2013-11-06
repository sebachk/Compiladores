package operaciones;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

import Assembler.ManejadorRegistros;

public class Resta extends OpBinario {
	public Resta(){
		super("SUB");
	}
    @Override
    public boolean execute(BufferedWriter file, Stack<String> pila,
    		ManejadorRegistros mr, boolean conmut) {
    	boolean resultado =	super.execute(file, pila, mr, conmut);
       	try{
       		file.write("JS signo\n");
       	}
       	catch(IOException e){}
    return resultado;
    }
}