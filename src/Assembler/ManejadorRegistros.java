package Assembler;

import java.util.Vector;

public class ManejadorRegistros {
	private Vector<Registro> registros;
	private final int size =4;
	
	public ManejadorRegistros(){
		registros = new Vector<Registro>(size);
		registros.add(new Registro());
		registros.add(new Registro());
		registros.add(new Registro());
		registros.add(new Registro());
	}
	
	/**Carga un registro y devuelve la posicion del registro cargado**/
	public int cargar(String s){
		for(int i=1;i<size;i++){
			if(!(registros.elementAt(i).isOcupado())){
				registros.elementAt(i).setOcupado(true);
				registros.elementAt(i).setValor(s);
				return i;
			}
		}
		//Entrego eax solo cuando no me queda otra
		if(!(registros.elementAt(0).isOcupado())){
			registros.elementAt(0).setOcupado(true);
			registros.elementAt(0).setValor(s);
			return 0;
		}
		return -1;  //Valor de carga fallida
	}
	
	public String getRegAss(int pos){
		switch(pos){
		
			case 0:return "ax";
			case 1:return "bx";
			case 2:return "cx";
			case 3:return "dx";
			default:return "ax";
		}
		
	}
	
	public String getValor(int pos){
		return registros.elementAt(pos).getValor();
	}
	
	public void liberar(int pos){registros.elementAt(pos).setOcupado(false);}
	
	public void liberar(String reg){
		if(reg.contains("ax")) {liberar(0);return;}
		else if(reg.contains("bx")) {liberar(1);return;}
		else if(reg.contains("cx")) {liberar(2);return;}
		else if(reg.contains("dx")) {liberar(3);return;}
		
		
		System.out.println("No libero "+ reg);
	}
	
}
