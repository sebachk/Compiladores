package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;

import compiler.AnalizadorLexico;
import compiler.Estructuras;

public class ASFinCTE implements AccionSemantica{

	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
			try {
				f.reset();
				//Cequear Rango >0 <2^16-1;
				String cadena=tc.getString();
				int valor= Integer.parseInt(cadena);
				if(valor<0 || valor>Math.pow(2, 16)-1){
					int pos=Estructuras.addTupla("Linea "+AnalizadorLexico.LineasContadas+": El entero se encuentra fuera del rango permitido");
					tc.createToken(Estructuras.ERROR,pos);
				}
				else{
					int posicion=Estructuras.addTupla(cadena);
					tc.createToken(Estructuras.CTE,posicion);
				}
				
			} catch (IOException e) {e.printStackTrace();}
	}

}
