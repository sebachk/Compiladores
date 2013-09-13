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

bloque_sent	: sentencia_ejec
			| bloque_funcion
			;

lista_sent	: sentencia_ejec lista_sent
			| sentencia_ejec
			;
			
sentencia_ejec 	: asignacion';'
				| IF {System.out.println("Linea "+Al.LineasContadas+": Sentencia 'if'");} '('cond')' THEN bloque_IF 
				| PRINT {System.out.println("Linea "+Al.LineasContadas+": Sentencia 'print'");} '('cadena')'';' 
				| llamada_funcion';'{System.out.println("Linea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
				| RETURN {System.out.println("Linea "+Al.LineasContadas+": Sentencia 'return'");} expresion';' 
				| LOOP {System.out.println("Linea "+Al.LineasContadas+": Sentencia de iteracion");} bloque_sent UNTIL cond 
				|';'
				;
				
llamada_funcion : ID '('lista_parametros')'
				|ID '('')'
				;
sentencia_declarativa_tipo	: tipo lista_var';' {System.out.println("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");}
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

sentencia_declar_funcion 	: FUNCTION ID '('parametros')' bloque_funcion
							|FUNCTION ID '('')' bloque_funcion
							;
parametros	: tipo parametro
			| tipo parametro ',' parametros
			;

lista_parametros 	: parametro ',' lista_parametros
					| parametro 
					;

parametro 	: expresion
			;
								
bloque_funcion 	: BEGIN lista_sent END 
				| BEGIN lista_sent_declar lista_sent END
				;

lista_sent_declar	: sentencia_declarativa_tipo lista_sent_declar
					| sentencia_declarativa_tipo
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

AnalizadorLexico Al = new ALexico.AnalizadorLexico(new File("Docs/codigo.txt"));



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

void yyBaseError(String e){
	System.err.println (e+" en línea "+Al.LineasContadas+": ");
     
}
void yyerror(String e){
		
	yyBaseError(e);	
	System.err.println ("estado: "+yystate);
	
	System.err.println ("Token leído : "+yyname[yychar]);
	System.err.print("Token(s) que se esperaba(n) : ");

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

    System.err.println(nombresTokens);
    
    Recuperarse();
  }

public void Recuperarse()
{
	Token t= Al.GetToken();
	int tNumber = 1;
	while(tNumber!=0 && tNumber!=this.END && tNumber!=(int)';'){
		t =Al.GetToken();
		if(t!=null)
			tNumber= t.getIdentif_tt();
	}
}
public void run()
{
  System.out.println(yyparse());
  Estructuras.PrintTablaS();
}
