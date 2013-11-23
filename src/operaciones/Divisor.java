package operaciones;

import java.io.BufferedWriter;
import java.io.IOError;
import java.nio.file.FileAlreadyExistsException;
import java.util.Stack;

import Assembler.ManejadorRegistros;

public class Divisor extends OpBinario {
	
	public Divisor(){
		super("DIV");
	}
	
	private void dx0(BufferedWriter file){
		try{
			file.write("MOV dx, 0\n");
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	
	private void PrimeroRAX(BufferedWriter file,String segundo,ManejadorRegistros mr){
		try{
		String pp= esParametro(file, segundo, mr);
		if(pp.startsWith("[")){//el segundo es un parametro
			file.write("MOV "+pp.substring(2,4)+" ,"+pp+"\n");
			file.write(this.operacion()+" "+pp.substring(2,4)+"\n");
			mr.liberar(pp);
		}
		else if(!pp.contains("_") && !pp.contains("#")){//pp es constante
			int pos=mr.cargar("");
			
			if(pos==3)System.err.println("AGARRO DX");//Esto no se da nunca
			if(pos==0)System.err.println(pp+" AGARRO AX");//esto tmpoco
			
			file.write("MOV "+mr.getRegAss(pos)+", "+pp+"\n");
			file.write(this.operacion()+" "+mr.getRegAss(pos)+"\n");
			mr.liberar(pos);
		}
		else{
			if(pp.startsWith("#")){
				file.write(this.operacion()+" "+pp.substring(1,pp.length())+"\n");
				mr.liberar(pp);
			}
			else{
				file.write(this.operacion()+" "+pp+"\n");
			}
		}
		}
		catch (Exception e) {
			// TODO: handle exception
		}
	}
	
	@Override
	public boolean execute(BufferedWriter file, Stack<String> pila,
			ManejadorRegistros mr, boolean conmut) {
		// TODO Auto-generated method stub
		String primero=null, segundo=null;
		boolean usabaDX=false;
		if(pila.size()>1){
			segundo=pila.pop();
			primero=pila.pop();
		}
		
		try{
		//POR CUESTIONES DE SEGURIDAD APILAMOS DX
			
			file.write("push dx \n");
			file.write("MOV dx, 0\n");
			if(mr.cargar(3)){
				usabaDX=true;
			}
			String pp= esParametro(file, primero, mr);
			
			if(pp.startsWith("#")){//el primero era un registro desde el principio
				if(primero.contains("ax")){
					PrimeroRAX( file, segundo, mr);
				}
				else{
					if(mr.usado(0)){//AX esta ocupado
						String nuevo=mr.MudarReg(0);
						file.write("MOV "+nuevo+", ax\n");
						reemplazar(nuevo, pila);
						}
					mr.cargar(0);//Si ya estaba cargado no hace nada
					file.write("MOV ax, "+pp.substring(1,pp.length())+"\n");
					mr.liberar(pp);
					PrimeroRAX( file, segundo, mr);
				}
			}
			else{
				if(mr.usado(0)){//AX esta ocupado
					String nuevo=mr.MudarReg(0);
					file.write("MOV "+nuevo+", ax\n");
					reemplazar(nuevo, pila);
					}
				mr.cargar(0);
				file.write("MOV ax, "+pp+"\n");
				if(!pp.equals(primero))
					mr.liberar(pp);
				PrimeroRAX( file, segundo, mr);
				
			}
			if(usabaDX){
				mr.liberar(3);
			}
			file.write("pop dx\n");
			
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		pila.push("#ax");
		return true;
	}
	
}
