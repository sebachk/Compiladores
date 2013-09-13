
%token ID , CTE, cadena , IF, THEN, ELSE, PRINT, RETURN, FUNCTION, BEGIN, END, LOOP, UNTIL, uint

%right  else
%%

declaracion	: sentencia_declar_funcion
			| bloque_sent
			;
		
bloque_sent	:	sentencia_ejec
			| bloque_funcion
			;

lista_sent	: sentencia_ejec lista_sent
			| sentencia_ejec
			;
			
sentencia_ejec 	: asignacion';'
				| IF '('cond')' THEN bloque_IF
				| PRINT '('cadena')'';'
				| llamada_funcion';'
				| RETURN expresion';'
				| LOOP bloque_sent UNTIL cond
				;
				
llamada_funcion : ID '('lista_parametros')'
				
sentencia_declarativa_tipo	: tipo lista_var';'
							;

lista_var	: ID,lista_var
			|ID
			;
			
<<<<<<< HEAD
bloque_if 	: bloque_sent %prec else
			| bloque_sent else bloque_sent
=======
bloque_IF 	: bloque_sent
			| bloque_sent ELSE bloque_sent
>>>>>>> 77c3def
			;

cond 	: expresion comparador expresion
		;

comparador 	: '<'
			| '>'
			| '<''='
			| '>''='
			| '=''='
			| '!''='
			;

sentencia_declar_funcion 	: FUNCTION ID '('parametros')' bloque_funcion
							;
parametros	: tipo parametro
			| tipo parametro , parametros
			;

lista_parametros 	: parametro, lista_parametros
					| parametro
					;

parametro 	: ID
			;
								
bloque_funcion 	: BEGIN lista_sent END
				| BEGIN lista_sent_declar lista_sent END
				;

lista_sent_declar	: sentencia_declarativa_tipo lista_sent_declar
					| sentencia_declarativa_tipo
					;

asignacion 	:	ID'='expresion
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


String prueba_Codigo_De_gramatica
