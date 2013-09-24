package accionesSemanticas;

import java.io.BufferedInputStream;
import java.io.FileReader;
import java.io.IOException;

import ALexico.AnalizadorLexico;
import ALexico.Estructuras;
import ALexico.TokenCreator;


public class ASFinCTE implements AccionSemantica{

	@Override
	public void Execute(BufferedInputStream f, char c, TokenCreator tc) {
			try {
				f.reset();
				//Chequear Rango >0 <2^16-1;
				String cadena=tc.getString();
				if(!DentroRango(cadena)){
					int pos=Estructuras.addTupla("Linea "+AnalizadorLexico.LineasContadas+": El entero se encuentra fuera del rango permitido");
					tc.createToken(Estructuras.ERROR,pos);
				}
				else{
					int posicion=Estructuras.addTupla(cadena);
					tc.createToken(Estructuras.CTE,posicion);
				}
				
			} catch (IOException e) {e.printStackTrace();}
	}
	
	
	public boolean DentroRango(String cadena){
		//Elimino los 0 a izq
		int first=0;
		for(int i=0;i<cadena.length();i++,first++){
			if(cadena.charAt(i)!='0')
				break;
			
		}
		cadena =cadena.substring(first);
		
		if (cadena.length()>5)
			return false;
		if (cadena.length()<5)
			return true;
		
		//La cadena tiene 5 digitos;
		int max[]={6,5,5,3,5};
		
		for(int i=0;i<max.length;i++){
			if((int)cadena.charAt(i)>max[i])
				return false;
			if((int)cadena.charAt(i)>max[i])
				return true;
		}
		return true;
	}

}
