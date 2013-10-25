package operaciones;

public class Divi extends OpBinario {

	protected static final String DIV="DIV";
	@Override
	public String operacion() {
		// TODO Auto-generated method stub
		return DIV;
	}
	
	@Override
	public OpBinario getInstance(){
		if(this.Instance==null){
			Instance=new Divi();
		}
		return Instance;
	}
}
