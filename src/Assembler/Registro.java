package Assembler;

public class Registro {
	private String valor;
	private boolean ocupado;
	
	public Registro(){
		valor = new String();
		ocupado=false;
	}
	
	public String getValor() {
		return valor;
	}
	public void setValor(String valor) {
		this.valor = valor;
	}
	public boolean isOcupado() {
		return ocupado;
	}
	public void setOcupado(boolean ocupado) {
		this.ocupado = ocupado;
	}
	
	
	
}
