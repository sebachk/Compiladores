
%token ID , CTE, cadena , if, then, else, print, return, function, begin, end, loop, until, uint
%left else if
%%

coso	: sentencia_declar_funcion
		| bloque_sent
		;
		
bloque_sent	:	sentencia
			| bloque_funcion
			;

lista_sent	: sentencia lista_sent
			| sentencia
			;
			
sentencia	: sentencia_ejec
			| sentencia_declarativa_tipo
			;
			
sentencia_ejec 	: asignacion
				| if '('cond')' then bloque_if
				| print '('cadena')'';'
				| ID '('lista_parametros')'';'
				| return expresion';'
				| loop bloque_sent until cond
				;
				
sentencia_declarativa_tipo	: tipo lista_var
							;

lista_var	: ID,lista_var
			|ID';'
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
								
bloque_funcion : begin lista_sent end

asignacion 	:	ID'='expresion';'
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
		| '('expresion')'
		;

tipo 	:uint
		;
%%
