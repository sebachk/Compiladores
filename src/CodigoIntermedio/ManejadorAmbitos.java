package CodigoIntermedio;

public class ManejadorAmbitos {

	private String ambitos[];
	private int max_amb;
	private int cant_ambitos;
	private int anonimos;
	
	private static ManejadorAmbitos instance;
	
	public ManejadorAmbitos(){
		this(10);
	}
	
	public ManejadorAmbitos(int max){
		max_amb=max;
		ambitos = new String[max];
		cant_ambitos=0;
		anonimos=1;
	}
	
	public static ManejadorAmbitos getInstance(){
		if(instance==null)
			instance=new ManejadorAmbitos();
		return instance;
	}
	
	public void SalvarRebalse(){
		if(cant_ambitos==max_amb){
			String aux[]= new String[max_amb*2];
			for(int i=0;i<max_amb;i++){
				aux[i]=ambitos[i];
			}
			max_amb*=2;
			ambitos=aux;
		}
	}
	
	public void NuevoAmbito(String nombre){
		ambitos[cant_ambitos++]=nombre;
		SalvarRebalse();
	}
	
	public void NuevoAmbito(){
		ambitos[cant_ambitos++]="bloque"+anonimos;
		anonimos++;
		SalvarRebalse();
	}
	
	public String getName(){
		String result="_";
		for(int i=0;i<cant_ambitos;i++){
			result+=ambitos[i]+"_";
		}
		result=result.substring(0, result.length()-1);
		return result;
	}
	
	public void FinAmbito(){
		cant_ambitos--;
	}
	
	public static void NewAmbito(String name){
		getInstance().NuevoAmbito(name);
	}
	
	public static void NewAmbito(){
		getInstance().NuevoAmbito();
	}
	
	public static void EndAmbito(){
		getInstance().FinAmbito();
	}
	
	
}
