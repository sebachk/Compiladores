.386 
.model flat, stdcall 
option casemap :none
include \masm32\include\windows.inc 
include \masm32\include\masm32.inc
include \masm32\include\kernel32.inc 
include \masm32\include\user32.inc 
includelib \masm32\lib\kernel32.lib 
includelib \masm32\lib\user32.lib
includelib \masm32\lib\masm32.lib
.data
sysout db 'var para mostrar por pantalla' ; string para mostrar variables por pantalla 
_retorno dd ? ;variable utilizada para salvar el puntero de retorno en funciones 
_a_Programa DW ? ; variables uint, 16bits
_a_Programa_promedio_2 DD ?, 0 ;parametro por referencia, 32bits
_b_Programa_promedio_2 DD ?, 0 ;parametro por referencia, 32bits
_g_Programa_promedio_2 DW ? ; variables uint, 16bits
_z_Programa_promedio_2 DW ? ; variables uint, 16bits
_c_Programa_promedio_2 DW ? ; variables uint, 16bits
cadena1 DB "'z es 11'", 0 ; cadenas, tamanio variable
cadena2 DB "'z no es 11'", 0 ; cadenas, tamanio variable
_a_Programa_promedio2_2 DD ?, 0 ;parametro por referencia, 32bits
_b_Programa_promedio2_2 DD ?, 0 ;parametro por referencia, 32bits
_b_Programa_Main DW ? ; variables uint, 16bits
_c_Programa_Main DW ? ; variables uint, 16bits
_d_Programa_Main DW ? ; variables uint, 16bits
_e_Programa_Main DW ? ; variables uint, 16bits
cadena3 DB "'La multiplicacion anda'", 0 ; cadenas, tamanio variable
cadena4 DB "'la mult no anda'", 0 ; cadenas, tamanio variable
cadena5 DB "'La division anda bien'", 0 ; cadenas, tamanio variable
cadena6 DB "'la division no anda'", 0 ; cadenas, tamanio variable
errorOverflow DB " Hubo overflow al realizar una miltplicacion o suma, resultado mayor a 65535" ,0
errorSigno DB " El resultado de la resta es menor a 0, queda fuera de rango" ,0
.code
start:
jmp main
;Comienzo de programa




;Comienzo de funcion
Func_promedio_2:


;Pasaje de parametros
pop _retorno
pop _b_Programa_promedio_2
pop _a_Programa_promedio_2
push _retorno
;Pasaje de parametros




;Inicio Asignacion
MOV bx, 2
MOV _c_Programa_promedio_2, bx
;Fin Asignacion




;Inicio Suma
MOV ecx, _a_Programa_promedio_2
MOV bx, [ecx]
MOV ecx, _b_Programa_promedio_2
ADD bx, [ecx]
JO overflow
;Fin Suma




;Inicio Asignacion
MOV _g_Programa_promedio_2, bx
;Fin Asignacion




;Inicio Division
push dx 
MOV dx, 0
MOV ax, _g_Programa_promedio_2
MOV bx, 2
DIV bx
pop dx
;Fin Division




;Inicio Asignacion
MOV _g_Programa_promedio_2, ax
;Fin Asignacion




;Inicio Asignacion
MOV ecx, _a_Programa_promedio_2
MOV bx, [ecx]
MOV _g_Programa_promedio_2, bx
;Fin Asignacion




;Inicio Multiplicacion
MOV ebx, _b_Programa_promedio_2
MOV ax, [ebx]
MOV ebx, _a_Programa_promedio_2
MOV cx, [ebx]
MUL cx
jo overflow
;Fin Multiplicacion




;Inicio Multiplicacion
MOV cx, ax
MOV ax, _c_Programa_promedio_2
MUL _g_Programa_promedio_2
jo overflow
;Fin Multiplicacion




;Inicio Suma
ADD ax, ax
JO overflow
;Fin Suma




;Inicio Asignacion
MOV _z_Programa_promedio_2, ax
;Fin Asignacion




;Sentencia de Comparacion
MOV dx, _z_Programa_promedio_2
CMP dx, 34
;Sentencia de Comparacion


JNE Label38
invoke StdOut, addr cadena1
JMP Label41
Label38:
invoke StdOut, addr cadena2
Label41:
MOV ax, _g_Programa_promedio_2
RET


;Inicio Division
push dx 
MOV dx, 0
MOV eax, _a_Programa_promedio_2
MOV , ax
MOV ax, [eax]
MOV ax, 2
DIV ax
pop dx
;Fin Division




;Inicio Asignacion
MOV edx, _a_Programa_promedio_2
MOV [edx], ax
;Fin Asignacion


MOV ax, 0
RET


;Comienzo de funcion
Func_promedio2_2:


;Pasaje de parametros
pop _retorno
pop _b_Programa_promedio2_2
pop _a_Programa_promedio2_2
push _retorno
;Pasaje de parametros




;Inicio Suma
MOV eax, _a_Programa_promedio2_2
MOV dx, [eax]
MOV eax, _b_Programa_promedio2_2
ADD dx, [eax]
JO overflow
;Fin Suma




;Inicio Asignacion
MOV eax, _a_Programa_promedio2_2
MOV [eax], dx
;Fin Asignacion




;Inicio Division
push dx 
MOV dx, 0
MOV eax, _a_Programa_promedio2_2
MOV , ax
MOV ax, [eax]
MOV ax, 2
DIV ax
pop dx
;Fin Division




;Inicio Asignacion
MOV edx, _a_Programa_promedio2_2
MOV [edx], ax
;Fin Asignacion


MOV ax, 0
RET


;Comienzo de funcion
Func_prueba_0:


;Pasaje de parametros
pop _retorno
push _retorno
;Pasaje de parametros




;Inicio Asignacion
MOV dx, 10
MOV _a_Programa, dx
;Fin Asignacion


MOV ax, 0
RET


;
main:


;Inicio Asignacion
MOV dx, 5
MOV _b_Programa_Main, dx
;Fin Asignacion




;Inicio Asignacion
MOV dx, 10
MOV _c_Programa_Main, dx
;Fin Asignacion




;Inicio Multiplicacion
MOV ax, _b_Programa_Main
MOV dx, 2
MUL dx
jo overflow
;Fin Multiplicacion




;Inicio Asignacion
MOV _d_Programa_Main, ax
;Fin Asignacion




;Sentencia de Comparacion
MOV dx, _d_Programa_Main
CMP dx, _c_Programa_Main
;Sentencia de Comparacion


JNE Label92
invoke StdOut, addr cadena3
JMP Label95
Label92:
invoke StdOut, addr cadena4
Label95:


;Inicio Asignacion
MOV dx, 20
MOV _e_Programa_Main, dx
;Fin Asignacion




;Inicio Division
push dx 
MOV dx, 0
MOV ax, _e_Programa_Main
MOV ax, 2
DIV ax
pop dx
;Fin Division




;Inicio Asignacion
MOV _d_Programa_Main, ax
;Fin Asignacion




;Inicio Asignacion
MOV dx, 6553
MOV _a_Programa, dx
;Fin Asignacion




;Inicio Resta
MOV dx, 10
SUB dx, _a_Programa
JS signo
;Fin Resta




;Inicio Asignacion
MOV _b_Programa_Main, dx
;Fin Asignacion




;Sentencia de Comparacion
MOV dx, _d_Programa_Main
CMP dx, _c_Programa_Main
;Sentencia de Comparacion


JNE Label121
invoke StdOut, addr cadena5
JMP Label124
Label121:
invoke StdOut, addr cadena6
Label124:


;Inicio Asignacion
MOV dx, 0
MOV _a_Programa, dx
;Fin Asignacion


Label128:


;Inicio Suma
MOV dx, _a_Programa
ADD dx, 1
JO overflow
;Fin Suma




;Inicio Asignacion
MOV _a_Programa, dx
;Fin Asignacion




;Sentencia de Comparacion
MOV dx, _a_Programa
CMP dx, 5
;Sentencia de Comparacion


JBE Label128
JMP Label141
Label141:
invoke ExitProcess, 0
overflow: invoke StdOut, addr errorOverflow ; viene aca en caso de overflow 
invoke ExitProcess, 0
signo: invoke StdOut, addr errorSigno ; viene aca en caso de resultado negativo 
invoke ExitProcess, 0
end start