package compiler;

public class AnalizadorSintactico {
	public static Token t;
	
	public static void tokenear(){
		System.out.println(t.getIdentif_tt()+", "+t.getIndice_ts()+"\n");
	}
	
	public static boolean pueda_tokenear(AnalizadorLexico AL){
		t = AL.GetToken();
		if (t == null) return false;
		return true;
	}
}
