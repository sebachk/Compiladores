%{
	import ALexico.*;
	import java.io.*;
	import CodigoIntermedio.*;
%}
%token ID , CTE, cadena , IF, THEN, ELSE, PRINT, RETURN, FUNCTION, BEGIN, END, LOOP, UNTIL, uint 
%token MAY_IG , MEN_IG, DIST ,IGUAL

%right ELSE
%%

programa 	: BEGIN {ManejadorAmbitos.NewAmbito("Programa");} lista_declaraciones_prog {ManejadorAmbitos.EndAmbito();}END
			;
			
lista_declaraciones_prog:lista_declaraciones
						| lista_sent_declar lista_declaraciones
						;
			
lista_declaraciones	: declaracion
					| declaracion lista_declaraciones
					
					;

declaracion	: sentencia_declar_funcion {Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de funcion");}
			| {ManejadorAmbitos.NewAmbito("Main");}bloque_sent{ManejadorAmbitos.EndAmbito();}
			;

sentencia_declar_funcion 	:FUNCTION ID '('{if(ManejadorAmbitos.PuedoDeclarar($2.sval)) $2.ival=Estructuras.addTupla($2.sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.FUNCTION);ManejadorAmbitos.NewAmbito($2.sval); PI.beginFunction($2.sval);} parametros')'  bloque_funcion {PI.endFunction($2.sval);ManejadorAmbitos.EndAmbito(); }
							|FUNCTION ID '('')' {if(ManejadorAmbitos.PuedoDeclarar($2.sval))  $2.ival=Estructuras.addTupla($2.sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.FUNCTION);  ManejadorAmbitos.NewAmbito($2.sval);} bloque_funcion {PI.endFunction($2.sval);ManejadorAmbitos.EndAmbito();}
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
				| sent_abierta {Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");} sentencia_comp
				| ';'
				;
				
sent_abierta 	: PRINT {Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'print'");} '('cadena')' {PI.callPrint($4.ival);}
				| llamada_funcion {Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
				| RETURN {Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'return'");} expresion {PI.retorno();}
				| asignacion
				;
				
sent_correcta 	: sent_abierta ';'				
				| error ';'
				;	
					
sentencia_comp	: sentencia_if
				| sentencia_loop 
				;	
				
sentencia_if 	: IF '('cond')'{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'if'"); PI.FinCondicion();ManejadorAmbitos.NewAmbito();}  THEN bloque_IF{ManejadorAmbitos.EndAmbito();} 
				| sentencia_if_error
				;
				
sentencia_if_error	: IF THEN {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la condicion del IF");}  bloque_IF 
					| IF cond')'{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta abrir parentesis");} THEN bloque_IF 
					| IF '('cond  {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta cerrar parentesis");}THEN bloque_IF 
					| IF cond {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta  parentesis");} THEN bloque_IF 
					| IF '('cond')'  {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la palabra THEN");} bloque_IF 
					| IF error {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": errores múltiples en IF");} bloque_IF 
				;
sentencia_loop: LOOP {Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de iteracion");PI.InicLoop();ManejadorAmbitos.NewAmbito();} bloque_loop {ManejadorAmbitos.EndAmbito();}
				
				
bloque_loop	:bloque_sent UNTIL cond{PI.FinLoop();}	
			|loop_error			
			;	

loop_error	: bloque_sent cond {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la palabra UNTIL");}
			| bloque_sent UNTIL error {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la condicion luego de UNTIL");}
			| bloque_sent error {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Se olvido del UNTIL?");}
			;			
				
llamada_funcion :ID '('{ManejadorAmbitos.isDeclarada($1.sval); PI.callFunction($1.sval);}lista_parametros')'
				|ID '('')'{ManejadorAmbitos.isDeclarada($1.sval);PI.callFunction($1.sval);}
				;


lista_var	:ID ',' {if(ManejadorAmbitos.PuedoDeclarar($1.sval))  $1.ival=Estructuras.addTupla($1.sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);} lista_var 
			|ID	{if(ManejadorAmbitos.PuedoDeclarar($1.sval))  $1.ival=Estructuras.addTupla($1.sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);}
			;

		
bloque_IF 	: bloque_sent  {PI.FinIf();} %prec  ELSE  
			| bloque_sent  ELSE {PI.FinThenElse();} bloque_sent {PI.FinIf();}
			| ID bloque_IF {Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Then mal escrito");}
			;
	
	
cond 	: expresion '<'  expresion {PI.addPolaco("<");} 
		| expresion '>'  expresion {PI.addPolaco(">");}
		| expresion MEN_IG expresion {PI.addPolaco("<=");}
		| expresion MAY_IG expresion {PI.addPolaco(">=");}
		| expresion IGUAL expresion {PI.addPolaco("==");}
		| expresion DIST expresion {PI.addPolaco("!=");}
		;		
							
parametros	: tipo ID {if(ManejadorAmbitos.PuedoDeclarar($2.sval)) $2.ival=Estructuras.addTupla($2.sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);}
			| tipo ID ','{if(ManejadorAmbitos.PuedoDeclarar($2.sval)) $2.ival=Estructuras.addTupla($2.sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);} parametros
			;


lista_parametros 	: parametro ',' lista_parametros
					| parametro 
					;

parametro 	:  ID {ManejadorAmbitos.isDeclarada($1.sval);}
			;



asignacion 	: ID  '='expresion {int pos= ManejadorAmbitos.isDeclarada($1.sval); Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia de asignacion"); PI.addPolaco("TS("+pos+")"); PI.addPolaco("=");}
			;
		
expresion 	: expresion '+' termino {PI.addPolaco("+");}
			| expresion '-' termino {PI.addPolaco("-");}
			| termino		
			;
	
termino 	: termino '*' factor {PI.addPolaco("*");}
			| termino '/' factor {PI.addPolaco("/");}
			| factor
			;

factor 	: ID {int pos = ManejadorAmbitos.isDeclarada($1.sval); PI.addPolaco("TS("+pos+")");}
		| CTE{PI.addPolaco("TS("+$1.ival+")");}
		| llamada_funcion
		;

tipo 	:uint
		;
%%


AnalizadorLexico Al = new ALexico.AnalizadorLexico(new File("Docs/codigo.txt"));
PolacaInversa PI = new PolacaInversa();
int contador=1;

int yylex(){
	ALexico.Token t; 
	int val= this.YYERRCODE;
	while(val==this.YYERRCODE){
		t = Al.GetToken();
		if(t!=null){
			ALexico.Estructuras.addToken("Token"+ contador++ +" = " + ALexico.Estructuras.getStringToken(t.getIdentif_tt()));
			val =t.getIdentif_tt();
			if(val!=this.YYERRCODE){
				yylval= new ParserVal();
				yylval.ival=t.getIndice_ts();
				if(yylval.ival!=-1){
					yylval.sval=Estructuras.Tabla_Simbolos.elementAt(yylval.ival).valor;
					}
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

//**********************************
public PolacaInversa getPolaca(){
return PI;
}
//**********************************

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
  	
  if(!Estructuras.HayErrores()){
  	PI.ImprimirPolaca();
  }
}




