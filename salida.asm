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
sysout db 'var para mostrar por pantalla'
_retorno dd ? ;variable utilizada para salvar el puntero de retorno en funciones 
_a_Programa DW ?
_a_Programa_promedio_2 DD ?, 0
_b_Programa_promedio_2 DD ?, 0
_g_Programa_promedio_2 DW ?
_a_Programa_promedio2_2 DD ?, 0
_b_Programa_promedio2_2 DD ?, 0
_a_Programa_Main DW ?
_b_Programa_Main DW ?
_c_Programa_Main DW ?
cadena1 DB "'funco'", 0
cadena2 DB "'no funco'", 0
errorOverflow DB " Hubo overflow al realizar una miltplicacion o suma, resultado mayor a 65535" ,0
errorSigno DB " El resultado de la resta es menor a 0, queda fuera de rango" ,0
.code
start:
jmp main
Func_promedio_2:
pop _retorno
pop _b_Programa_promedio_2
pop _a_Programa_promedio_2
push _retorno
MOV ecx, _a_Programa_promedio_2
MOV bx, [ecx]
MOV ecx, _b_Programa_promedio_2
ADD bx, [ecx]
JO overflow
MOV _g_Programa_promedio_2, bx
push dx 
MOV dx, 0
MOV ax, _g_Programa_promedio_2
MOV bx, 2
DIV bx
pop dx
MOV _g_Programa_promedio_2, ax
MOV ax, _g_Programa_promedio_2
RET
RET
Func_promedio2_2:
pop _retorno
pop _b_Programa_promedio2_2
pop _a_Programa_promedio2_2
push _retorno
MOV ecx, _a_Programa_promedio2_2
MOV bx, [ecx]
MOV ecx, _b_Programa_promedio2_2
ADD bx, [ecx]
JO overflow
MOV ecx, _a_Programa_promedio2_2
MOV [ecx], bx
push dx 
MOV dx, 0
MOV ebx, _a_Programa_promedio2_2
MOV ax, [ebx]
MOV cx, 2
DIV cx
pop dx
MOV ecx, _a_Programa_promedio2_2
MOV [ecx], ax
RET
main:
MOV cx, 4
MOV _a_Programa_Main, cx
MOV cx, 6
MOV _b_Programa_Main, cx
push offset _a_Programa_Main 
push offset _b_Programa_Main 
CALL Func_promedio_2
MOV _c_Programa_Main, ax
push offset _a_Programa_Main 
push offset _b_Programa_Main 
CALL Func_promedio2_2
MOV cx, _a_Programa_Main
CMP cx, _c_Programa_Main
JNE Label58
invoke StdOut, addr cadena1
JMP Label61
Label58:
invoke StdOut, addr cadena2
Label61:
invoke ExitProcess, 0
overflow: invoke StdOut, addr errorOverflow
invoke ExitProcess, 0
signo: invoke StdOut, addr errorSigno
invoke ExitProcess, 0
end start