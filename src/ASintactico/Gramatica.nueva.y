%{
	import ALexico.*;
	import java.io.*;
%}
%token ID , CTE, cadena , IF, THEN, ELSE, PRINT, RETURN, FUNCTION, BEGIN, END, LOOP, UNTIL, uint 
%token MAY_IG , MEN_IG, DIST ,IGUAL

%right ELSE
%%

programa 	: BEGIN lista_declaraciones END
			;
			
lista_declaraciones	: declaracion
					| declaracion lista_declaraciones
					;

declaracion	: sentencia_declar_funcion {Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de funcion");}
			| bloque_sent
			;

sentencia_declar_funcion 	: FUNCTION ID '('parametros')' bloque_funcion
							|FUNCTION ID '('')' bloque_funcion
							|FUNCTION error ')'
							|FUNCTION error bloque_funcion
							;

bloque_sent	: sentencia_ejec
			| bloque_funcion
			;

bloque_funcion 	: BEGIN lista_sentencias END 
				| BEGIN lista_sentencias {Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");} bloque_funcion
				| BEGIN lista_sentencias {Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");} sentencia_declar_funcion
				;

lista_sentencias: lista_sent_ejec
				| lista_sent_declar lista_sent_ejec
				;

lista_sent_declar	: sentencia_declarativa_tipo lista_sent_declar
					| sentencia_declarativa_tipo
					;
					
sentencia_declarativa_tipo	: tipo lista_var';' {Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");} 
							| tipo error';'
							| tipo lista_var {Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");} sent_correcta   
							| tipo lista_var {Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");} sentencia_declarativa_tipo  
							;

					
lista_sent_ejec	: sentencia_ejec lista_sent_ejec
				| sentencia_ejec
				;
			
			
sentencia_ejec 	: sentencia_simple
				| sentencia_comp
				;
				
sentencia_simple: sent_correcta 
				| sent_abierta {Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");} sent_correcta  
				|sent_abierta {Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");} sentencia_comp
				|';'
				;
				
sent_abierta 	: PRINT {Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'print'");} '('cadena')' 
				| llamada_funcion {Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
				| RETURN {Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'return'");} expresion 
				| asignacion
				;
				
sent_correcta 	: sent_abierta ';'				
				| error ';'
				;	
					
sentencia_comp	: sentencia_if
				| sentencia_loop 
				;	
				
sentencia_if 	: IF '('cond')'{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'if'");}  THEN bloque_IF 
				| sentencia_if_error
				;
				
sentencia_if_error	: IF THEN {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la condicion del IF");}  bloque_IF 
					| IF cond')'{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta abrir parentesis");} THEN bloque_IF 
					| IF '('cond  {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta cerrar parentesis");}THEN bloque_IF 
					| IF cond {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta  parentesis");} THEN bloque_IF 
					| IF '('cond')'  {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la palabra THEN");} bloque_IF 
					| IF error {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": errores múltiples en IF");} bloque_IF 
				;
sentencia_loop: LOOP {Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de iteracion");} bloque_loop 
				
				
bloque_loop	: bloque_sent UNTIL cond	
			|loop_error			
			;	

loop_error	: bloque_sent cond {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la palabra UNTIL");}
			| bloque_sent UNTIL error {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la condicion luego de UNTIL");}
			| bloque_sent error {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Se olvido del UNTIL?");}
			;			
				
llamada_funcion : ID '('lista_parametros')'
				|ID '('')'
				;


lista_var	: ID ','lista_var
			|ID			
			;

		
bloque_IF 	: bloque_sent %prec ELSE  	
			| bloque_sent ELSE bloque_sent
			| ID bloque_IF
			;
	
	
cond 	: expresion comparador expresion 
		;

comparador 	: '<'
			| '>'
			| MEN_IG
			| MAY_IG
			| IGUAL
			| DIST
			;
							
parametros	: tipo ID
			| tipo ID ',' parametros
			;


lista_parametros 	: parametro ',' lista_parametros
					| parametro 
					;

parametro 	: expresion
			;



asignacion 	:	ID'='expresion {Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia de asignacion");}
			;
		
expresion 	: expresion '+' termino
			| expresion '-' termino
			| termino		
			;
	
termino 	: termino '*' factor
			| termino '/' factor
			| factor
			;

factor 	: ID
		| CTE
		| llamada_funcion
		;

tipo 	:uint
		;
%%

AnalizadorLexico Al = new ALexico.AnalizadorLexico(new File("Docs/errores.txt"));



int yylex(){
	ALexico.Token t; 
	int val= this.YYERRCODE;
	while(val==this.YYERRCODE){
		t = Al.GetToken();
		if(t!=null){
			val =t.getIdentif_tt();
			if(val!=this.YYERRCODE){
				yylval.ival=t.getIndice_ts();
				return t.getIdentif_tt();
			}
			else{
				yyBaseError("Error lexico");
				
			}
		}
		else break;
	}
	yylval.ival=0;
	return 0;
}

String yyBaseError(String e){
	String err=e+" en línea "+Al.LineasContadas+": ";
	return err;
}
void yyerror(String e){
		
	String error="";
	error+=yyBaseError(e);	
	
	//System.err.println ("Token leído : "+);
	String leido= yyname[yychar];
	
	String espera= "Token que se esperaba: ";
	String esperas ="Tokens que se esperaban: ";
    String  nombresTokens = "" ;

    int yyn ;
	int varios=0;
    // añadir en 'nombresTokens' los tokens que permitirian desplazar
     for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ )
     {  yyn = yysindex[yystate] ;  
        if ((yyn != 0) && (yyn += yychar) >= 0 &&
           yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        	{
        		varios++;
        	   	nombresTokens += yyname[yychar] + " ";
        	}
     }

     // añadir tokens que permitirian reducir
     for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ )
     {   yyn = yyrindex[yystate] ;  
         if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
         {  
         	varios++;
         	nombresTokens += yyname[yychar] + " " ;
         }
     }
     if(varios==1)
     	error+=espera+nombresTokens;
     else
     	error +=esperas+nombresTokens;

    error+= " en lugar de "+leido;
    Estructuras.addError(error);
   	
   
  }

/*public void Recuperarse()
{
	Token t= Al.GetToken();
	int tNumber = 1;
	while(tNumber!=0 && tNumber!=this.END && tNumber!=(int)';'){
		t =Al.GetToken();
		if(t!=null)
			tNumber= t.getIdentif_tt();
	}
}*/

public void run()
{
  yyparse();
  Estructuras.PrintTablaS();
}
