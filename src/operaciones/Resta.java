package operaciones;


public class Resta extends OpBinario {

	protected static final String SUB="SUB";
	@Override
	public String operacion() {
		// TODO Auto-generated method stub
		return SUB;
	}
	
	public OpBinario getInstance(){
		if(this.Instance==null){
			Instance=new Resta();
		}
		return Instance;
	}
}
