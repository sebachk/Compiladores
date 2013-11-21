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
_a_Programa_promedio2_2 DD ?, 0 ;parametro por referencia, 32bits
_b_Programa_promedio2_2 DD ?, 0 ;parametro por referencia, 32bits
_b_Programa_Main DW ? ; variables uint, 16bits
_c_Programa_Main DW ? ; variables uint, 16bits
_d_Programa_Main DW ? ; variables uint, 16bits
_e_Programa_Main DW ? ; variables uint, 16bits
cadena1 DB "'La multiplicacion anda'", 0 ; cadenas, tamanio variable
cadena2 DB "'la mult no anda'", 0 ; cadenas, tamanio variable
cadena3 DB "'La division anda bien'", 0 ; cadenas, tamanio variable
cadena4 DB "'la division no anda'", 0 ; cadenas, tamanio variable
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


MOV ax, _g_Programa_promedio_2
RET


;Inicio Division
push dx 
MOV dx, 0
MOV ebx, _a_Programa_promedio_2
MOV ax, [ebx]
MOV bx, 2
DIV bx
pop dx
;Fin Division




;Inicio Asignacion
MOV ebx, _a_Programa_promedio_2
MOV [ebx], ax
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
MOV ecx, _a_Programa_promedio2_2
MOV bx, [ecx]
MOV ecx, _b_Programa_promedio2_2
ADD bx, [ecx]
JO overflow
;Fin Suma




;Inicio Asignacion
MOV ecx, _a_Programa_promedio2_2
MOV [ecx], bx
;Fin Asignacion




;Inicio Division
push dx 
MOV dx, 0
MOV ebx, _a_Programa_promedio2_2
MOV ax, [ebx]
MOV bx, 2
DIV bx
pop dx
;Fin Division




;Inicio Asignacion
MOV ebx, _a_Programa_promedio2_2
MOV [ebx], ax
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
MOV bx, 10
MOV _a_Programa, bx
;Fin Asignacion


MOV ax, 0
RET


;
main:


;Inicio Asignacion
MOV bx, 5
MOV _b_Programa_Main, bx
;Fin Asignacion




;Inicio Asignacion
MOV bx, 10
MOV _c_Programa_Main, bx
;Fin Asignacion




;Inicio Multiplicacion
MOV ax, _b_Programa_Main
MOV bx, 2
MUL bx
jo overflow
;Fin Multiplicacion




;Inicio Asignacion
MOV _d_Programa_Main, ax
;Fin Asignacion




;Sentencia de Comparacion
MOV bx, _d_Programa_Main
CMP bx, _c_Programa_Main
;Sentencia de Comparacion


JNE Label67
invoke StdOut, addr cadena1
JMP Label70
Label67:
invoke StdOut, addr cadena2
Label70:


;Inicio Asignacion
MOV bx, 20
MOV _e_Programa_Main, bx
;Fin Asignacion




;Inicio Division
push dx 
MOV dx, 0
MOV ax, _e_Programa_Main
MOV bx, 2
DIV bx
pop dx
;Fin Division




;Inicio Asignacion
MOV _d_Programa_Main, ax
;Fin Asignacion




;Inicio Asignacion
MOV bx, 6553
MOV _a_Programa, bx
;Fin Asignacion




;Inicio Resta
MOV bx, 10
SUB bx, _a_Programa
JS signo
;Fin Resta




;Inicio Asignacion
MOV _b_Programa_Main, bx
;Fin Asignacion




;Sentencia de Comparacion
MOV bx, _d_Programa_Main
CMP bx, _c_Programa_Main
;Sentencia de Comparacion


JNE Label96
invoke StdOut, addr cadena3
JMP Label99
Label96:
invoke StdOut, addr cadena4
Label99:


;Inicio Asignacion
MOV bx, 0
MOV _a_Programa, bx
;Fin Asignacion


Label103:


;Inicio Suma
MOV bx, _a_Programa
ADD bx, 1
JO overflow
;Fin Suma




;Inicio Asignacion
MOV _a_Programa, bx
;Fin Asignacion




;Sentencia de Comparacion
MOV bx, _a_Programa
CMP bx, 5
;Sentencia de Comparacion


JBE Label103
JMP Label116
Label116:
invoke ExitProcess, 0
overflow: invoke StdOut, addr errorOverflow ; viene aca en caso de overflow 
invoke ExitProcess, 0
signo: invoke StdOut, addr errorSigno ; viene aca en caso de resultado negativo 
invoke ExitProcess, 0
end start