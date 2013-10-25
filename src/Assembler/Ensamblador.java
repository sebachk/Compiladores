package Assembler;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Stack;

import operaciones.Divi;
import operaciones.Multi;
import operaciones.OpBinario;
import operaciones.Resta;
import operaciones.Suma;

import ALexico.Estructuras;
import CodigoIntermedio.PolacaInversa;

public class Ensamblador {
	private ManejadorRegistros mr;
	private Stack<String> pila;
	private BufferedWriter escritor;
	
	
	private OpBinario suma;
	private OpBinario resta;
	private OpBinario multi;
	private OpBinario divi;
	
	public Ensamblador(){
		suma = new Suma();
		resta = new Resta();
		multi = new Multi();
		divi = new Divi();
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
		String elem = pi.readPolaco();
		if (!doOperacion(elem)){
			pila.push(elem);
		}	
	}
	
	public boolean doOperacion(String s){
		if(s.equals("+")){suma.execute(escritor,pila);}
		if(s.equals("-")){resta.execute(escritor,pila);}
		if(s.equals("*")){multi.execute(escritor,pila);}
		if(s.equals("/")){divi.execute(escritor,pila);}
		
		if(s.equals(PolacaInversa.BRANCH_FALSO)){}
		if(s.equals(PolacaInversa.BRANCH_INC)){}
		if(s.equals(PolacaInversa.PRINT)){}
		if(s.equals(PolacaInversa.CALL)){}
		if(s.equals(PolacaInversa.RETURN)){}
		return false;
	}
	

}
