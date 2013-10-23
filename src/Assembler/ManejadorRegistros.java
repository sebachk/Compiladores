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
		for(int i=0;i<size;i++){
			if(!(registros.elementAt(i).isOcupado())){
				registros.elementAt(i).setOcupado(true);
				registros.elementAt(i).setValor(s);
				return i;
			}
		}
		return -1;  //Valor de carga fallida
	}
	
	public String getValor(int pos){
		return registros.elementAt(pos).getValor();
	}
	
	public void liberar(int pos){registros.elementAt(pos).setOcupado(false);}
	
}
