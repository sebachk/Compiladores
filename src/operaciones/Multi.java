package operaciones;


public class Multi extends OpBinario {

	protected static final String MUL="MUL";
	@Override
	public String operacion() {
		// TODO Auto-generated method stub
		return MUL;
	}
	
	@Override
	public OpBinario getInstance(){
		if(this.Instance==null){
			Instance=new Multi();
		}
		return Instance;
	}

}
