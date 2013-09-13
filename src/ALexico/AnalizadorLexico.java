package ALexico;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;

import accionesSemanticas.ASConsumidora;
import accionesSemanticas.ASErrorCadena;
import accionesSemanticas.ASErrorCarInv;
import accionesSemanticas.ASErrorComentario;
import accionesSemanticas.ASFinCTE;
import accionesSemanticas.ASFinCadena;
import accionesSemanticas.ASFinComparacion;
import accionesSemanticas.ASFinDistinto;
import accionesSemanticas.ASFinId;
import accionesSemanticas.ASFinParAbre;
import accionesSemanticas.ASInicializadora;
import accionesSemanticas.ASOperador;


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
	BufferedInputStream lector;
	
	/**
	 * Carga las matrices de Estados y de Acciones Semanticas
	 * Las matrices podrian variar de un tipo de lenguaje a otro (CUAK!)
	 */
	private void llenarEstados(){
		estados = new int[9][18];

		acciones = new AccionSemantica[9][18];
		
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
		acciones[0][2]=acciones[0][1];
		acciones[0][3]=acciones[0][1];
		acciones[0][4]=acciones[0][1];
		acciones[0][5]=acciones[0][1];
		acciones[0][6]=acciones[0][1];
		acciones[0][7]=acciones[0][1];
		acciones[0][8]=new ASOperador();
		acciones[0][9]=acciones[0][8];
		acciones[0][10]=acciones[0][8];
		acciones[0][11]=acciones[0][8];
		acciones[0][12]=acciones[0][8];
		acciones[0][13]=acciones[0][8];
		acciones[0][14]=acciones[0][8];
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
		acciones[2][0]=new ASFinCTE();
		acciones[2][1]=acciones[1][0];
		acciones[2][2]=acciones[2][0];
		acciones[2][3]=acciones[2][0];
		acciones[2][4]=acciones[2][0];
		acciones[2][5]=acciones[2][0];
		acciones[2][6]=acciones[2][0];
		acciones[2][7]=acciones[2][0];
		acciones[2][8]=acciones[2][0];
		acciones[2][9]=acciones[2][0];
		acciones[2][10]=acciones[2][0];
		acciones[2][11]=acciones[2][0];
		acciones[2][12]=acciones[2][0];
		acciones[2][13]=acciones[2][0];
		acciones[2][14]=acciones[2][0];
		acciones[2][15]=acciones[2][0];
		acciones[2][16]=acciones[2][0];
		acciones[3][0]=new ASFinComparacion();
		acciones[3][1]=acciones[3][0];
		acciones[3][2]=acciones[3][0];
		acciones[3][3]=acciones[3][0];
		acciones[3][4]=acciones[3][0];
		acciones[3][5]=acciones[3][0];
		acciones[3][6]=acciones[3][0];
		acciones[3][7]=acciones[3][0];
		acciones[3][8]=acciones[3][0];
		acciones[3][9]=acciones[3][0];
		acciones[3][10]=acciones[3][0];
		acciones[3][11]=acciones[3][0];
		acciones[3][12]=acciones[3][0];
		acciones[3][13]=acciones[3][0];
		acciones[3][14]=acciones[3][0];
		acciones[3][15]=acciones[3][0];
		acciones[3][16]=acciones[3][0];
		acciones[4][0]=new ASErrorCarInv();
		acciones[4][1]=acciones[4][0];
		acciones[4][2]=acciones[4][0];
		acciones[4][3]=acciones[4][0];
		acciones[4][4]=new ASFinDistinto();
		acciones[4][5]=acciones[4][0];
		acciones[4][6]=acciones[4][0];
		acciones[4][7]=acciones[4][0];
		acciones[4][8]=acciones[4][0];
		acciones[4][9]=acciones[4][0];
		acciones[4][10]=acciones[4][0];
		acciones[4][11]=acciones[4][0];
		acciones[4][12]=acciones[4][0];
		acciones[4][13]=acciones[4][0];
		acciones[4][14]=acciones[4][0];
		acciones[4][15]=acciones[4][0];
		acciones[4][16]=acciones[4][0];
		acciones[5][6]=new ASFinCadena();
		acciones[5][0]=acciones[1][0];
		acciones[5][1]=acciones[1][0];
		acciones[5][2]=acciones[1][0];
		acciones[5][3]=acciones[1][0];
		acciones[5][4]=acciones[1][0];
		acciones[5][5]=acciones[1][0];
		acciones[5][7]=acciones[1][0];
		acciones[5][8]=acciones[1][0];
		acciones[5][9]=acciones[1][0];
		acciones[5][10]=acciones[1][0];
		acciones[5][11]=acciones[1][0];
		acciones[5][12]=acciones[1][0];
		acciones[5][13]=acciones[1][0];
		acciones[5][14]=acciones[1][0];
		acciones[5][15]=acciones[1][0];
		acciones[5][16]=new ASErrorCadena();
		acciones[6][0]=new ASFinParAbre();
		acciones[6][1]=acciones[6][0];
		acciones[6][2]=acciones[6][0];
		acciones[6][3]=acciones[6][0];
		acciones[6][4]=acciones[6][0];
		acciones[6][5]=acciones[6][0];
		acciones[6][6]=acciones[6][0];
		acciones[6][7]=acciones[6][0];
		acciones[6][8]=acciones[6][0];
		acciones[6][9]=acciones[6][0];
		acciones[6][10]=acciones[6][0];
		acciones[6][11]=acciones[6][0];
		acciones[6][12]=acciones[6][0];
		acciones[6][14]=acciones[6][0];
		acciones[6][15]=acciones[6][0];
		acciones[6][16]=acciones[6][0];
		acciones[6][13]=acciones[1][0];
		acciones[7][0]=acciones[1][0];
		acciones[7][1]=acciones[1][0];
		acciones[7][2]=acciones[1][0];
		acciones[7][3]=acciones[1][0];
		acciones[7][4]=acciones[1][0];
		acciones[7][5]=acciones[1][0];
		acciones[7][6]=acciones[1][0];
		acciones[7][7]=acciones[1][0];
		acciones[7][8]=acciones[1][0];
		acciones[7][9]=acciones[1][0];
		acciones[7][10]=acciones[1][0];
		acciones[7][11]=acciones[1][0];
		acciones[7][12]=acciones[1][0];
		acciones[7][13]=acciones[1][0];
		acciones[7][14]=acciones[1][0];
		acciones[7][15]=acciones[1][0];
		acciones[7][16]=acciones[1][0];
		acciones[8][0]=acciones[1][0];
		acciones[8][1]=acciones[1][0];
		acciones[8][2]=acciones[1][0];
		acciones[8][3]=acciones[1][0];
		acciones[8][4]=acciones[1][0];
		acciones[8][5]=acciones[1][0];
		acciones[8][6]=acciones[1][0];
		acciones[8][7]=acciones[1][0];
		acciones[8][8]=acciones[1][0];
		acciones[8][9]=acciones[1][0];
		acciones[8][10]=acciones[1][0];
		acciones[8][11]=acciones[1][0];
		acciones[8][12]=acciones[1][0];
		acciones[8][13]=acciones[1][0];
		acciones[8][14]=acciones[1][0];
		acciones[8][15]=acciones[1][0];
		acciones[8][16]=acciones[1][0];
		
		acciones[7][17]= new ASErrorComentario();
		acciones[8][17] = acciones [7][17];
		
		
	}
	
	public AnalizadorLexico(File txt){
		estado_actual=0;
		LineasContadas=1;
		llenarEstados();
		try {
			lector = new BufferedInputStream(new FileInputStream(txt));
			if(lector.markSupported()){}
			} 
		catch (FileNotFoundException e) {e.printStackTrace();}
	}
	
	
	boolean fin_de_arch = false;
	
	/**
	 * Pone en funcionamiento el Analizador Lexico. Recorre el archivo y genera un Token
	 * Se mueve en la matriz de Estados y de Acciones Semanticas
	 * @return
	 */
	public Token GetToken(){
		TokenCreator tc = new TokenCreator();
		estado_actual=0;
		while(estado_actual!=ESTADOFINAL && estado_actual!=ESTADOERROR && !fin_de_arch){
			int caracter=-1;
			
			try {caracter = lector.read();} 
			catch (IOException e) {e.printStackTrace();}
			
			if(caracter!=-1){// NO ES FIN DE ARCHIVO?
				int indice=hash((char)caracter);
				if(indice!=-1){ // CARACTER VALIDO
					acc_Actual = acciones[estado_actual][indice];
					acc_Actual.Execute(lector,(char)caracter,tc);
					estado_actual=estados[estado_actual][indice];
					
					if(caracter == 10)
						LineasContadas++;
				}
				else{ //CARACTER INVALIDO
					System.out.println("Caracter leido: "+caracter+" "+(char)caracter );
					System.out.println("Estado actual: "+estado_actual+" ");
					return null;				
				}
			}
			else{
				fin_de_arch = true;
				return null;
			} // FIN DE ARCHIVO	
		} // END WHILE
		return tc.GetToken();
	}
	
	/**
	 * Dado un indice devuelve el tipo de caracter (como int): letra, digito, comparador, signo, etc.
	 * Cada tipo tiene un numero asignado que es el retorno.
	 * Se usa para cambiar de estado en el Automata
	 * @param indice
	 * @return
	 */
	public static int hash(char indice){
		if(indice>='A' && indice<='z') return 0; //Es letra
		
		if(indice>='0' && indice<='9') return 1;//Es digito
		
		if(indice=='>') return 2;
		if(indice=='<') return 3;
		if(indice=='=') return 4;
		if(indice=='!') return 5;
		if(indice=='\'') return 6;
		if(indice=='(') return 7;
		if(indice==',') return 8;
		if(indice==';') return 9;
		if(indice==')') return 10;
		if(indice=='+') return 11;
		if(indice=='-') return 12;
		if(indice=='*') return 13;
		if(indice=='/') return 14;
		if(indice==(char)13 || indice==(char)32 || indice==(char)9) return 15; //Retorno de carro || Espacio || Tab
		if(indice==(char)10) return 16; // Caracter NL
		if(indice==(char)3) return 17;
		
		System.out.println((int)indice);
		
		return -1; //Caracter Desconocido
	}
	
}
