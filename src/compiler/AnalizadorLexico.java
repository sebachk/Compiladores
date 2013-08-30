package compiler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;


import accionesSemanticas.AccionSemantica;


public class AnalizadorLexico {
	
	private final static int  ESTADOFINAL=-1;
	int [][] estados;
	int estado_actual;
	
	
	AccionSemantica [][] acciones;
	AccionSemantica acc_Actual;
	FileReader lector;
	
	
	public AnalizadorLexico(File txt){
		
		estados = new int[9][17];
		acciones = new AccionSemantica[9][17];
		estado_actual=0;
		try {
			lector = new FileReader(txt);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
	}
	
	public Token GetToken(){
		
		while(estado_actual!=ESTADOFINAL){
			int caracter=-1;
			try {
				caracter = lector.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(caracter!=-1){
				int indice=hash((char)caracter);
				if(indice!=-1){
					acc_Actual = acciones[estado_actual][indice];
					acc_Actual.Execute(lector);
					estado_actual=estados[estado_actual][indice];
					
				}
				else{
					//CARACTER INVALIDO
				}
			}
			return null;
		
		}
		return acc_Actual.getToken();
	}
	
	
	public int getToken(){
	//(	String sToken= this.getStringToken();
		 return 0;
		
	}
	
	public static int hash(char indice){
		if(indice>='A' && indice<='z'){
			return 0; //Retorna letra
		}
		
		if(indice>='0' && indice<='9')
			return 1;//Retorna digito
		
		if(indice=='>')
			return 2;
		if(indice=='<')
			return 3;
		if(indice=='=')
			return 4;
		if(indice=='!')
			return 5;
		if(indice=='\'')
			return 6;
		if(indice=='(')
			return 7;
		if(indice==',')
			return 8;
		if(indice==';')
			return 9;
		if(indice==')')
			return 10;
		if(indice=='+')
			return 11;
		if(indice=='-')
			return 12;
		if(indice=='*')
			return 13;
		if(indice=='/')
			return 14;
		if(indice==' ' || indice=='	')
			return 15;
		if(indice=='\n')
			return 16;
		
		return -1;
	}
	
	
	
}
