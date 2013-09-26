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

declaracion	: sentencia_declar_funcion {System.out.println("Linea "+Al.LineasContadas+": Sentencia declarativa de funcion");}
			| bloque_sent
			;

sentencia_declar_funcion 	: FUNCTION ID '('parametros')' bloque_funcion
							|FUNCTION ID '('')' bloque_funcion
							|FUNCTION error ')'{System.out.println("Error funcion");}
							;

bloque_sent	: sentencia_ejec
			| bloque_funcion
			;

bloque_funcion 	: BEGIN lista_sent END 
				| BEGIN lista_sent_declar lista_sent END
				| error END {System.out.println("Error en bloque de funcion");}
				;

lista_sent_declar	: sentencia_declarativa_tipo lista_sent_declar
					| sentencia_declarativa_tipo
					;
					
sentencia_declarativa_tipo	: tipo lista_var';' {System.out.println("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");} 
							| tipo error{System.out.println("Error tipo");}';'
							| tipo lista_var {Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");} sent_correcta   
							| tipo lista_var {Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");} sentencia_declarativa_tipo  
							;

					
lista_sent	: sentencia_ejec lista_sent
			| sentencia_ejec
			;
			
			
sentencia_ejec 	: sentencia_simple
				| sentencia_comp
				;
				
sentencia_simple: sent_correcta 
				| sent_abierta {Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");} sent_correcta  
				|sent_abierta {Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");} sentencia_comp
				;
				
sent_abierta 	:PRINT {System.out.println("syntax error en línea "+Al.LineasContadas+": Sentencia 'print'");} '('cadena')' 
				| llamada_funcion {System.out.println("syntax error en línea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
				| RETURN {System.out.println("syntax error en línea "+Al.LineasContadas+": Sentencia 'return'");} expresion 
				| asignacion
				;
				
sent_correcta 	: sent_abierta ';'				
				| error ';'
				;	
					
sentencia_comp	: sentencia_if
				| sentencia_loop 
				;	
				
sentencia_if 	: IF '('cond')'{System.out.println("Línea "+Al.LineasContadas+": Sentencia 'if'");}  THEN bloque_IF 
				| sentencia_if_error
				;
				
sentencia_if_error	: IF THEN {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la condicion del IF");}  bloque_IF 
					| IF cond')'{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta abrir parentesis");} THEN bloque_IF 
					| IF '('cond  {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta cerrar parentesis");}THEN bloque_IF 
					| IF cond {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta  parentesis");} THEN bloque_IF 
					| IF '('cond')'  {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la palabra THEN");} bloque_IF 
					| IF error {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": errores múltiples en IF");} bloque_IF 
				;
sentencia_loop: LOOP {System.out.println("Línea "+Al.LineasContadas+": Sentencia de iteracion");} bloque_loop 
				
				
bloque_loop	: bloque_sent UNTIL cond	
			|loop_error			
			;	

loop_error	: bloque_sent cond {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la palabra UNTIL");}
			| bloque_sent UNTIL error {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la condicion luego de UNTIL");}
			|bloque_sent error{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Se olvido del UNTIL?");}
			;			
				
llamada_funcion : ID '('lista_parametros')'
				|ID '('')'
				;


lista_var	: ID ','lista_var
			|ID			
			;

		
bloque_IF 	: bloque_sent %prec ELSE  	
			| bloque_sent ELSE bloque_sent
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



asignacion 	:	ID'='expresion {System.out.println("Linea "+Al.LineasContadas+": Sentencia de asignacion");}
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
			System.out.println("Token = "+ALexico.Estructuras.getStringToken(t.getIdentif_tt()));
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
	
	//System.err.println ("Token leído : "+yyname[yychar]);
	error+="Token(s) que se esperaba(n) :";

    String  nombresTokens = "" ;

    int yyn ;

    // añadir en 'nombresTokens' los tokens que permitirian desplazar
     for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ )
     {  yyn = yysindex[yystate] ;  
        if ((yyn != 0) && (yyn += yychar) >= 0 &&
           yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {   nombresTokens += yyname[yychar] + " ";
        }
     }

     // añadir tokens que permitirian reducir
     for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ )
     {   yyn = yyrindex[yystate] ;  
         if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
         {  nombresTokens += yyname[yychar] + " " ;
         }
     }

    error+=nombresTokens;
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
  System.out.println(yyparse());
  Estructuras.PrintTablaS();
}
