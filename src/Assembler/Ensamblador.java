package Assembler;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;
import java.util.Vector;

import operaciones.Asignacion;
import operaciones.OpBinario;

import ALexico.Estructuras;
import ALexico.TuplaTS;
import CodigoIntermedio.PolacaInversa;

public class Ensamblador {
	private ManejadorRegistros mr;
	private Stack<String> pila;
	private BufferedWriter escritor;
	
	
	private OpBinario suma;
	private OpBinario resta;
	private OpBinario multi;
	private OpBinario divi;
	private OpBinario asig;
	
	public Ensamblador(){
		suma = new OpBinario("ADD");
		resta = new OpBinario("SUB");
		multi = new OpBinario("MUL");
		divi = new OpBinario("DIV");
		asig = new Asignacion("MOV");
		
		mr = new ManejadorRegistros();
		pila = new Stack<String>();
		escritor = null;
		try {
			escritor = new BufferedWriter(new FileWriter(new File("cod_assembler.txt")));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	
	public void ensamblar(PolacaInversa pi){
		
		ensamblarVariables();
		String elem = pi.readPolaco();
 
	
		while(elem != null){
			if (!doOperacion(elem)){
				pila.push(elem);
			}	
			elem = pi.readPolaco();
			}
		
		try {
			escritor.flush();
			escritor.close();
		} catch (IOException e) {e.printStackTrace();}
 
	}
		
	
	public void ensamblarVariables(){
		Vector<String> variables = new Vector<String>();
		int i=1;
		try{
			escritor.write(".data");
			escritor.newLine();
		for(TuplaTS tupla:Estructuras.Tabla_Simbolos){
			if(tupla.valor.contains("_") && tupla.uso.equals(Estructuras.USO_VAR)){//Si tiene ambito, es variable
				escritor.write("_"+tupla.valor+" DW ?");
				escritor.newLine();
			}
			if(tupla.valor.contains("'")){
				String nvalor="cadena"+i++;
				escritor.write(""+nvalor+" DW "+"\""+tupla.valor+"\"");
				escritor.newLine();
				tupla.valor=nvalor;
			}
			
		}
		escritor.write(".code");
		escritor.newLine();
		
		}
		catch(IOException e){e.printStackTrace();}
		
		
		
	}
		
	
	public boolean doOperacion(String s){
		if(s.equals("+")){return(suma.execute(escritor,pila,mr,true));} //el boolean es para determinar conmutatividad
		if(s.equals("-")){return(resta.execute(escritor,pila,mr,false));}
		if(s.equals("*")){return(multi.execute(escritor,pila,mr,true));}
		if(s.equals("/")){return(divi.execute(escritor,pila,mr,false));}
 
		if(s.equals("=")){return(asig.execute(escritor,pila,mr,false));}
		
		
		if(s.equals(PolacaInversa.BRANCH_FALSO)){return true;}
		if(s.equals(PolacaInversa.BRANCH_INC)){return true;}
		if(s.equals(PolacaInversa.PRINT)){return true;}
		if(s.equals(PolacaInversa.CALL)){return true;}
		if(s.equals(PolacaInversa.RETURN)){return true;}
		return false;
	}
	

}
