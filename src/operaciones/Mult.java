package operaciones;

import java.io.BufferedWriter;
import java.io.IOException;
import java.util.Stack;

import Assembler.ManejadorRegistros;

public class Mult extends OpBinario {

	public Mult(String Op) {
		super(Op);
		// TODO Auto-generated constructor stub
	}
	
	
	protected void PrimeroRAX(String sp,BufferedWriter file,ManejadorRegistros mr){
		try{
		if(sp.startsWith("#")){ //2do es REG
			file.write(this.operacion()+" "+sp.substring(1,sp.length())+"\n");
			mr.liberar(sp);
		}
		else{
			if(!sp.startsWith("_")){ //el segundo es una CTE
				int pos=mr.cargar("");
				if(pos!=-1){
					file.write("MOV "+mr.getRegAss(pos)+", "+sp+"\n");
					file.write(this.operacion()+" "+mr.getRegAss(pos)+"\n");
					mr.liberar(pos);
				}
				else{
					System.err.println("MACHO, TE QUEDASTE SIN REGISTROS");
					file.write("ACA HUBO ERROR\n");
					//TODO me quedo sin registros
					}
				}
			
			else {//2do es VAR, VAR-REF
					file.write("MOV "+sp.substring(2,4)+", "+sp+"\n");
					file.write(this.operacion()+" "+sp.substring(2,4)+"\n");	
			}
		}
		file.write("jo overflow\n");
		}
		catch  (IOException e){
			e.printStackTrace();
		}
	}
	
	@Override
	public boolean execute(BufferedWriter file, Stack<String> pila,
			ManejadorRegistros mr, boolean conmut) {
		// TODO Auto-generated method stub
		if(!pila.empty()){
			String segundo = pila.pop();
			if(!pila.empty()){
			String primero = pila.pop();
			if (primero.startsWith("#")){ //PRIMERO ES REGISTRO

				OpReg1(file,pila,mr,primero,segundo);
			}
			else 
				if(segundo.startsWith("#")){ //SEGUNDO ES REG y PRIMER VAR
					OpReg1(file,pila,mr,segundo,primero);				
				}
				else{ // AMBAS VARS
					try{
					String nuevo= mr.UtilizarReg(0);
					if(!nuevo.equals(mr.getRegAss(0))){
						file.write("MOV "+nuevo+", ax\n");
						mr.liberar("ax");
					}
					
					String pp=esParametro(file, primero, mr);
					file.write("MOV ax, "+pp+"\n");
					if(!pp.equals(primero))
						mr.liberar(pp);
					mr.cargar(0);
					OpReg1(file, pila, mr, "#ax", segundo);
					}
					catch (Exception e) {
						// TODO: handle exception
					}
				}
			}
			pila.push("#ax");
			}
			return true;

	}
	
	@Override
	public void OpReg1(BufferedWriter file, Stack<String> pila,
			ManejadorRegistros mr, String primero, String segundo) {
		// TODO Auto-generated method stub
		String sp= esParametro(file, segundo, mr);
		
		try{
		if(primero.contains("ax")){
			PrimeroRAX(sp, file, mr);
		}
		else{
			if(!sp.contains("ax")){//si es una ref y tiene ax no quedan reg libres
				String nuevo=mr.UtilizarReg(0);
				if(!nuevo.equals("")){//habia un reg libre
					if((!nuevo.equals(mr.getRegAss(0))))//AX estaba ocupado
						file.write("MOV "+nuevo+", ax\n");
					file.write("MOV ax, "+primero);
					mr.cargar(0);
					mr.liberar(primero);
					PrimeroRAX(sp, file, mr);
				}
				
			}
			else{
				file.write(this.operacion()+" "+primero);
				file.write("jo overflow\n");
				mr.liberar(primero);
				}

		}
		
		}
		catch (Exception e) {
			// TODO: handle exception
		}
		
	}

}
