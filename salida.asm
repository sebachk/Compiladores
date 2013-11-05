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
_a_Programa DW ?
_a_Programa_promedio_2 DD ?, 0
_b_Programa_promedio_2 DD ?, 0
_g_Programa_promedio_2 DW ?
_a_Programa_Main DW ?
_b_Programa_Main DW ?
_c_Programa_Main DW ?
_d_Programa_Main DW ?
errorOverflow DB " Hubo overflow al realizar una miltplicacion o suma, resultado mayor a 65535" ,0
errorSigno DB " El resultado de la resta es menor a 0, queda fuera de rango" ,0
.code
start:
Func_promedio_2:
pop _a_Programa_promedio_2
pop _b_Programa_promedio_2
MOV ecx, _a_Programa_promedio_2
MOV bx, [ecx]
MOV ecx, _b_Programa_promedio_2
ADD bx, [ecx]
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
Func_promedio_0:
MOV ax, 0
RET
RET
MOV bx, 4
MOV _a_Programa_Main, bx
MOV bx, 6
MOV _b_Programa_Main, bx
push [_a_Programa_Main] 
push [_b_Programa_Main] 
CALL Func_promedio_2
invoke ExitProcess, 0
overflow: invoke StdOut, addr errorOverflow
invoke ExitProcess, 0
signo: invoke StdOut, addr errorSigno
invoke ExitProcess, 0
end start