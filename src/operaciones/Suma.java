package operaciones;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

import Assembler.ManejadorRegistros;

public class Suma extends OpBinario {
	public Suma(){
		super("ADD");
	}
    @Override
    public boolean execute(BufferedWriter file, Stack<String> pila,
    		ManejadorRegistros mr, boolean conmut) {
    	boolean resultado =	super.execute(file, pila, mr, conmut);
       	try{
       		file.write("JO overflow\n");
       	}
       	catch(IOException e){}
    return resultado;
    }
}
