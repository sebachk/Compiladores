package accionesSemanticas;

public abstract class AccionSemantica {

	private String cadena;
	
	public String getCadena(){
		return cadena;
	}
	
	public abstract void Execute();
}
