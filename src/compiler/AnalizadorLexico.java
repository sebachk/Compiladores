package compiler;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import accionesSemanticas.ASConsumidora;
import accionesSemanticas.ASFinCadena;
import accionesSemanticas.ASFinId;
import accionesSemanticas.ASInicializadora;
import accionesSemanticas.ASOperador;
import accionesSemanticas.TokenCreator;


import accionesSemanticas.AccionSemantica;


public class AnalizadorLexico {
	
	private final static int  ESTADOFINAL=-1;
	private final static int  ESTADOERROR=-2;
	
	public static int LineasContadas;
	public final static String [] PALABRAS_RESERVADAS={"if","then","else","begin","end","print","function","return","uint","loop","until"};
	int [][] estados;
	int estado_actual;
	
	AccionSemantica [][] acciones;
	AccionSemantica acc_Actual;
	FileReader lector;
	
	
	private void llenarEstados(){
		estados = new int[9][17];

		acciones = new AccionSemantica[9][17];
		
		for(int i=0;i<9;i++)
			for(int j=0;j<17;j++){
				estados[i][j]=-1;
				acciones[i][j]=null;
			}
		estados[0][0]=1;
		estados[0][1]=2;
		estados[0][2]=3;
		estados[0][3]=3;
		estados[0][4]=3;
		estados[0][5]=4;
		estados[0][6]=5;
		estados[0][7]=6;
		estados[0][15]=0;
		estados[0][16]=0;
		estados[1][0]=1;
		estados[1][1]=1;
		estados[2][1]=2;
		estados[5][0]=5;
		estados[5][1]=5;
		estados[5][2]=5;
		estados[5][3]=5;
		estados[5][4]=5;
		estados[5][5]=5;
		estados[5][7]=5;
		estados[5][8]=5;
		estados[5][9]=5;
		estados[5][10]=5;
		estados[5][11]=5;
		estados[5][12]=5;
		estados[5][13]=5;
		estados[5][14]=5;
		estados[5][15]=5;
		estados[6][13]=7;
		estados[7][0]=7;
		estados[7][1]=7;
		estados[7][2]=7;
		estados[7][3]=7;
		estados[7][4]=7;
		estados[7][5]=7;
		estados[7][6]=7;
		estados[7][7]=7;
		estados[7][8]=7;
		estados[7][9]=7;
		estados[7][15]=7;
		estados[7][10]=7;
		estados[7][11]=7;
		estados[7][12]=7;
		estados[7][13]=8;
		estados[7][14]=7;
		estados[7][16]=7;
		estados[8][0]=7;
		estados[8][1]=7;
		estados[8][2]=7;
		estados[8][3]=7;
		estados[8][4]=7;
		estados[8][5]=7;
		estados[8][6]=7;
		estados[8][7]=7;
		estados[8][8]=7;
		estados[8][9]=7;
		estados[8][10]=0;
		estados[8][11]=7;
		estados[8][12]=7;
		estados[8][13]=8;
		estados[8][14]=7;
		estados[8][15]=7;
		estados[8][16]=7;
		for(int j=0;j<17;j++){
			if(j!=4)
				estados[4][j]=ESTADOERROR;
		}
		estados[5][16]=ESTADOERROR;
		
		
		acciones[0][0]=new ASInicializadora();
		acciones[0][1]=acciones[0][0];
		acciones[0][1]=acciones[0][1];
		acciones[0][3]=acciones[0][1];
		acciones[0][4]=acciones[0][1];
		acciones[0][5]=acciones[0][1];
		acciones[0][6]=acciones[0][1];
		acciones[0][7]=acciones[0][1];
		acciones[0][8]=new ASOperador();
		acciones[0][9]=acciones[0][9];
		acciones[0][10]=acciones[0][9];
		acciones[0][11]=acciones[0][9];
		acciones[0][12]=acciones[0][9];
		acciones[0][13]=acciones[0][9];
		acciones[0][14]=acciones[0][9];
		acciones[0][15]=new ASConsumidora();
		acciones[0][16]=acciones[0][15];
		acciones[1][0]=acciones[0][15];
		acciones[1][1]=acciones[0][15];
		acciones[1][2]=new ASFinId();
		acciones[1][3]=acciones[1][2];
		acciones[1][4]=acciones[1][2];
		acciones[1][5]=acciones[1][2];
		acciones[1][6]=acciones[1][2];
		acciones[1][7]=acciones[1][2];
		acciones[1][8]=acciones[1][2];
		acciones[1][9]=acciones[1][2];
		acciones[1][10]=acciones[1][2];
		acciones[1][11]=acciones[1][2];
		acciones[1][12]=acciones[1][2];
		acciones[1][13]=acciones[1][2];
		acciones[1][14]=acciones[1][2];
		acciones[1][15]=acciones[1][2];
		acciones[1][16]=acciones[1][2];
		acciones[2][0]=;
		acciones[][];
		acciones[][];
		acciones[][];
		acciones[][];
		acciones[][];
		acciones[][];
		acciones[][];
		acciones[][];
		
		
		
		
	}
	
	public AnalizadorLexico(File txt){
		estado_actual=0;
		LineasContadas=0;
		try {
			lector = new FileReader(txt);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		
	}
	
	public Token GetToken(){
		TokenCreator tc = new TokenCreator();
		while(estado_actual!=ESTADOFINAL){
			int caracter=-1;
			try {
				caracter = lector.read();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			if(caracter!=-1){// -1 final de linea
				int indice=hash((char)caracter);
				if(indice!=-1){
					acc_Actual = acciones[estado_actual][indice];
					acc_Actual.Execute(lector,(char)caracter,tc);
					estado_actual=estados[estado_actual][indice];
					
					if((char)caracter=='\n')
						LineasContadas++;
					
				}
				else{
					//CARACTER INVALIDO
				}
			}
			return null;
		
		}
		return tc.GetToken();
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
