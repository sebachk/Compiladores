
%token ID , CTE, cadena , if, then, else, print, return, function, begin, end, loop, until, uint

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
				| if '('cond')' then bloque_if
				| print '('cadena')'';'
				| llamada_funcion';'
				| return expresion';'
				| loop bloque_sent until cond
				;
				
llamada_funcion : ID '('lista_parametros')'
				
sentencia_declarativa_tipo	: tipo lista_var';'
							;

lista_var	: ID,lista_var
			|ID
			;
			
bloque_if 	: bloque_sent
			| bloque_sent else bloque_sent
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

sentencia_declar_funcion 	: function ID '('parametros')' bloque_funcion
							;
parametros	: tipo parametro
			| tipo parametro , parametros
			;

lista_parametros 	: parametro, lista_parametros
					| parametro
					;

parametro 	: ID
			;
								
bloque_funcion 	: begin lista_sent end
				| begin lista_sent_declar lista_sent end
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
