//### This file created by BYACC 1.8(/Java extension  1.15)
//### Java capabilities added 7 Jan 97, Bob Jamison
//### Updated : 27 Nov 97  -- Bob Jamison, Joe Nieten
//###           01 Jan 98  -- Bob Jamison -- fixed generic semantic constructor
//###           01 Jun 99  -- Bob Jamison -- added Runnable support
//###           06 Aug 00  -- Bob Jamison -- made state variables class-global
//###           03 Jan 01  -- Bob Jamison -- improved flags, tracing
//###           16 May 01  -- Bob Jamison -- added custom stack sizing
//###           04 Mar 02  -- Yuval Oren  -- improved java performance, added options
//###           14 Mar 02  -- Tomas Hurka -- -d support, static initializer workaround
//### Please send bug reports to tom@hukatronic.cz
//### static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";



package ASintactico;



//#line 2 "Gramatica.nueva.y"
	import ALexico.*;
	import java.io.*;
//#line 20 "Parser.java"




public class Parser
{

boolean yydebug;        //do I want debug output?
int yynerrs;            //number of errors so far
int yyerrflag;          //was there an error?
int yychar;             //the current working character

//########## MESSAGES ##########
//###############################################################
// method: debug
//###############################################################
void debug(String msg)
{
  if (yydebug)
    System.out.println(msg);
}

//########## STATE STACK ##########
final static int YYSTACKSIZE = 500;  //maximum stack size
int statestk[] = new int[YYSTACKSIZE]; //state stack
int stateptr;
int stateptrmax;                     //highest index of stackptr
int statemax;                        //state when highest index reached
//###############################################################
// methods: state stack push,pop,drop,peek
//###############################################################
final void state_push(int state)
{
  try {
		stateptr++;
		statestk[stateptr]=state;
	 }
	 catch (ArrayIndexOutOfBoundsException e) {
     int oldsize = statestk.length;
     int newsize = oldsize * 2;
     int[] newstack = new int[newsize];
     System.arraycopy(statestk,0,newstack,0,oldsize);
     statestk = newstack;
     statestk[stateptr]=state;
  }
}
final int state_pop()
{
  return statestk[stateptr--];
}
final void state_drop(int cnt)
{
  stateptr -= cnt; 
}
final int state_peek(int relative)
{
  return statestk[stateptr-relative];
}
//###############################################################
// method: init_stacks : allocate and prepare stacks
//###############################################################
final boolean init_stacks()
{
  stateptr = -1;
  val_init();
  return true;
}
//###############################################################
// method: dump_stacks : show n levels of the stacks
//###############################################################
void dump_stacks(int count)
{
int i;
  System.out.println("=index==state====value=     s:"+stateptr+"  v:"+valptr);
  for (i=0;i<count;i++)
    System.out.println(" "+i+"    "+statestk[i]+"      "+valstk[i]);
  System.out.println("======================");
}


//########## SEMANTIC VALUES ##########
//public class ParserVal is defined in ParserVal.java


String   yytext;//user variable to return contextual strings
ParserVal yyval; //used to return semantic vals from action routines
ParserVal yylval;//the 'lval' (result) I got from yylex()
ParserVal valstk[];
int valptr;
//###############################################################
// methods: value stack push,pop,drop,peek.
//###############################################################
void val_init()
{
  valstk=new ParserVal[YYSTACKSIZE];
  yyval=new ParserVal();
  yylval=new ParserVal();
  valptr=-1;
}
void val_push(ParserVal val)
{
  if (valptr>=YYSTACKSIZE)
    return;
  valstk[++valptr]=val;
}
ParserVal val_pop()
{
  if (valptr<0)
    return new ParserVal();
  return valstk[valptr--];
}
void val_drop(int cnt)
{
int ptr;
  ptr=valptr-cnt;
  if (ptr<0)
    return;
  valptr = ptr;
}
ParserVal val_peek(int relative)
{
int ptr;
  ptr=valptr-relative;
  if (ptr<0)
    return new ParserVal();
  return valstk[ptr];
}
final ParserVal dup_yyval(ParserVal val)
{
  ParserVal dup = new ParserVal();
  dup.ival = val.ival;
  dup.dval = val.dval;
  dup.sval = val.sval;
  dup.obj = val.obj;
  return dup;
}
//#### end semantic value section ####
public final static short ID=257;
public final static short CTE=258;
public final static short cadena=259;
public final static short IF=260;
public final static short THEN=261;
public final static short ELSE=262;
public final static short PRINT=263;
public final static short RETURN=264;
public final static short FUNCTION=265;
public final static short BEGIN=266;
public final static short END=267;
public final static short LOOP=268;
public final static short UNTIL=269;
public final static short uint=270;
public final static short MAY_IG=271;
public final static short MEN_IG=272;
public final static short DIST=273;
public final static short IGUAL=274;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    1,    1,    2,    2,    3,    3,    3,    4,    4,
    6,    6,    6,    9,    9,   10,   13,   10,   15,   10,
   16,   10,    8,    8,    7,    7,   17,   20,   17,   21,
   17,   22,   19,   19,   25,   19,   19,   14,   14,   18,
   18,   30,   27,   27,   33,   32,   34,   32,   35,   32,
   36,   32,   37,   32,   38,   32,   40,   28,   39,   39,
   41,   41,   41,   23,   23,   12,   12,   31,   31,   29,
   43,   43,   43,   43,   43,   43,    5,    5,   42,   42,
   44,   26,   24,   24,   24,   45,   45,   45,   46,   46,
   46,   11,
};
final static short yylen[] = {                            2,
    3,    1,    2,    1,    1,    6,    5,    3,    1,    1,
    3,    4,    2,    2,    1,    3,    0,    4,    0,    4,
    0,    4,    2,    1,    1,    1,    1,    0,    3,    0,
    3,    0,    5,    1,    0,    3,    1,    2,    2,    1,
    1,    0,    7,    1,    0,    4,    0,    6,    0,    6,
    0,    5,    0,    6,    0,    4,    0,    3,    3,    1,
    2,    3,    2,    4,    3,    3,    1,    1,    3,    3,
    1,    1,    1,    1,    1,    1,    2,    4,    3,    1,
    1,    3,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   32,   35,    0,    0,   57,
    0,    0,    4,    5,   10,    9,   27,   25,   26,    0,
   34,   37,   40,   41,   44,   13,   39,    0,    0,   55,
    0,   90,   45,    0,   91,    0,    0,    0,   88,    0,
    0,    0,    0,    0,   92,    0,    0,    0,    0,    0,
    0,    1,    3,   38,    0,    0,   65,    0,    0,    0,
    0,    0,    0,    0,   74,   73,   76,   75,   71,   72,
    0,    0,    0,   47,    0,    0,    0,    0,    0,    8,
    0,   23,   11,    0,   14,   17,    0,    0,    0,   58,
   60,   29,    0,   31,   64,    0,    0,   56,   46,    0,
    0,    0,    0,    0,    0,    0,   86,   87,    0,    0,
    0,    0,   12,    0,    0,   16,    0,    0,   63,    0,
   61,   79,    0,    0,    0,    0,    0,   52,   33,    0,
    7,    0,    0,   18,   66,   20,   22,   62,   59,   69,
    0,   54,   50,   48,    6,    0,   43,   78,
};
final static short yydgoto[] = {                          2,
   11,   12,   13,   97,  111,   15,   16,   47,   48,   49,
   50,   88,  114,   17,  117,  118,   18,   19,   20,   55,
   56,   40,   21,   36,   41,   22,   23,   24,   37,  124,
   98,   25,   63,  105,  101,   75,  125,   62,   90,   51,
   91,   59,   73,   60,   38,   39,
};
final static short yysindex[] = {                      -238,
  -90,    0,  -29,  -26,   57,    0,    0, -207, -152,    0,
 -212,  -90,    0,    0,    0,    0,    0,    0,    0,   10,
    0,    0,    0,    0,    0,    0,    0,   59, -148,    0,
   42,    0,    0, -148,    0,   23,   51,    5,    0,   55,
 -148,   74,   58,   71,    0,  -62, -146,  -62, -153, -128,
   78,    0,    0,    0, -170, -231,    0,   32,   82,   75,
   32,   78,   78,   95,    0,    0,    0,    0,    0,    0,
 -148, -148, -148,    0, -119, -148, -148, -116,   32,    0,
  -36,    0,    0, -123,    0,    0,  101,   87, -224,    0,
    0,    0,   10,    0,    0, -148, -115,    0,    0,    0,
 -113,    5,    5,   32, -112,   78,    0,    0,  109, -220,
  114, -100,    0,  110,  -89,    0, -170, -153,    0, -131,
    0,    0,   78,  -84,   78,   78,   78,    0,    0,  -96,
    0, -220,  128,    0,    0,    0,    0,    0,    0,    0,
   78,    0,    0,    0,    0, -153,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -86,    0,    0,    0,    0,    0,    0,    0,   91,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -41,    0,    0,    0,    0,    0,  -76,  -21,    0,    0,
    0,    0,    0,    0,    0,  -79,    0,    0,   93,    0,
    0,    0,    0,    0,    0,    0,    0,    7,    0,   74,
   63,    0,    0,  -71,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   65,    0,
    0,    0,    0,    0,    0,    0,  -52, -150,    0,    0,
    0,    0,    0,    0,    0,    0, -104,    0,    0,  -77,
    0,   -1,   19,   43,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  151,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  181,    0,    0,   14,   50, -101,    8,   53,  148,   81,
  -70,   85,    0,  -47,    0,    0,    0,  147,  -28,    0,
    0,    0,   62,  -16,    0,    0,    0,    0,  -18,    0,
  -53,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  111,    0,    0,   60,   64,
};
final static int YYTABLESIZE=361;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         89,
   89,   89,   89,   89,  110,   89,   67,   92,  131,   99,
  112,   58,   61,   28,   14,   64,   46,   89,   89,   85,
   89,   85,   85,   85,   79,   14,   93,    1,    5,   27,
  145,  119,   31,   32,   29,  130,   10,   85,   85,   83,
   85,   83,   83,   83,  120,    9,   76,   81,   42,   43,
   81,   77,  128,   46,   52,   46,  104,   83,   83,   84,
   83,   84,   84,   84,   89,   71,   35,   72,   54,  136,
  121,  142,  143,  144,   71,  112,   72,   84,   84,   58,
   84,   28,   69,   70,   70,   44,    4,  147,   93,   35,
   35,   74,    6,    7,   78,   35,   34,   81,   82,   57,
   84,  139,   35,   44,    4,   19,   19,    5,   31,   32,
    6,    7,   19,   19,   80,   10,   45,   45,   96,   21,
   83,   82,   95,   36,  138,   31,   32,   86,   87,   27,
  102,  103,   35,   35,   35,  100,  140,   35,   35,  107,
  108,  106,  109,  113,  115,  116,  123,  126,  127,  129,
   35,   68,   68,   68,  132,   68,  133,   35,   68,   68,
   68,   68,   68,   68,   68,    3,    4,   87,  134,    5,
   26,  146,    6,    7,    8,    9,  141,   10,   53,   53,
    2,   35,   53,   42,   51,   53,   53,   24,   53,   49,
   53,   77,   53,   44,    4,  148,   85,    5,  137,  135,
    6,    7,   94,   67,   67,   10,  122,    0,    0,    0,
   67,   67,    0,    0,   89,   89,   89,   67,   89,   89,
   89,   89,   89,   89,   89,   89,   89,   89,    0,   89,
   89,   89,   89,   45,   85,   85,   85,   26,   85,   85,
   85,   85,   85,   85,   85,   85,   85,   85,    0,   85,
   85,   85,   85,    0,   83,   83,   83,    0,   83,   83,
   83,   83,   83,   83,   83,   83,   83,   83,    0,   83,
   83,   83,   83,    0,   84,   84,   84,    0,   84,   84,
   84,   84,   84,   84,   84,   84,   84,   84,    0,   84,
   84,   84,   84,   65,   66,   67,   68,    0,   70,   70,
   70,    0,   70,   70,   70,   70,   70,   70,   70,   70,
   70,   70,   30,   31,   32,   31,   32,   33,   82,   82,
   36,   36,   82,    0,   36,   82,   82,   36,   36,    0,
   82,    0,   36,    3,    4,    0,    0,    5,    0,    0,
    6,    7,    0,    9,    0,   10,   28,   28,   15,   15,
   30,    0,   15,   28,   28,   15,   15,    0,   30,    0,
   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   59,   55,  110,   63,
   81,   28,   29,   40,    1,   34,    9,   59,   60,   41,
   62,   43,   44,   45,   41,   12,   55,  266,  260,   59,
  132,  256,  257,  258,   61,  256,  268,   59,   60,   41,
   62,   43,   44,   45,  269,  266,   42,   41,  256,  257,
   44,   47,  106,   46,  267,   48,   73,   59,   60,   41,
   62,   43,   44,   45,   51,   43,    5,   45,   59,  117,
   89,  125,  126,  127,   43,  146,   45,   59,   60,   96,
   62,   40,   60,   41,   62,  256,  257,  141,  117,   28,
   29,   41,  263,  264,   40,   34,   40,   40,   46,   41,
   48,  120,   41,  256,  257,  256,  257,  260,  257,  258,
  263,  264,  263,  264,   41,  268,  270,  270,   44,  270,
  267,   59,   41,   59,  256,  257,  258,  256,  257,   59,
   71,   72,   71,   72,   73,   41,  123,   76,   77,   76,
   77,  261,  259,  267,   44,   59,  262,  261,  261,   41,
   89,  256,  257,  258,   41,  260,  257,   96,  263,  264,
  265,  266,  267,  268,  269,  256,  257,  257,   59,  260,
  267,   44,  263,  264,  265,  266,  261,  268,  256,  257,
  267,  120,  260,  261,  261,  263,  264,  267,  266,  261,
  268,   41,   12,  256,  257,  146,   49,  260,  118,  115,
  263,  264,   56,  256,  257,  268,   96,   -1,   -1,   -1,
  263,  264,   -1,   -1,  256,  257,  258,  270,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,  270,  256,  257,  258,  267,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,   -1,  256,  257,  258,   -1,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,   -1,  256,  257,  258,   -1,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,  271,  272,  273,  274,   -1,  256,  257,
  258,   -1,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  256,  257,  258,  257,  258,  261,  256,  257,
  256,  257,  260,   -1,  260,  263,  264,  263,  264,   -1,
  268,   -1,  268,  256,  257,   -1,   -1,  260,   -1,   -1,
  263,  264,   -1,  266,   -1,  268,  256,  257,  256,  257,
  260,   -1,  260,  263,  264,  263,  264,   -1,  268,   -1,
  268,
};
}
final static short YYFINAL=2;
final static short YYMAXTOKEN=274;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'","','",
"'-'",null,"'/'",null,null,null,null,null,null,null,null,null,null,null,"';'",
"'<'","'='","'>'",null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,"ID","CTE","cadena","IF","THEN","ELSE","PRINT",
"RETURN","FUNCTION","BEGIN","END","LOOP","UNTIL","uint","MAY_IG","MEN_IG",
"DIST","IGUAL",
};
final static String yyrule[] = {
"$accept : programa",
"programa : BEGIN lista_declaraciones END",
"lista_declaraciones : declaracion",
"lista_declaraciones : declaracion lista_declaraciones",
"declaracion : sentencia_declar_funcion",
"declaracion : bloque_sent",
"sentencia_declar_funcion : FUNCTION ID '(' parametros ')' bloque_funcion",
"sentencia_declar_funcion : FUNCTION ID '(' ')' bloque_funcion",
"sentencia_declar_funcion : FUNCTION error ')'",
"bloque_sent : sentencia_ejec",
"bloque_sent : bloque_funcion",
"bloque_funcion : BEGIN lista_sent END",
"bloque_funcion : BEGIN lista_sent_declar lista_sent END",
"bloque_funcion : error END",
"lista_sent_declar : sentencia_declarativa_tipo lista_sent_declar",
"lista_sent_declar : sentencia_declarativa_tipo",
"sentencia_declarativa_tipo : tipo lista_var ';'",
"$$1 :",
"sentencia_declarativa_tipo : tipo error $$1 ';'",
"$$2 :",
"sentencia_declarativa_tipo : tipo lista_var $$2 sent_correcta",
"$$3 :",
"sentencia_declarativa_tipo : tipo lista_var $$3 sentencia_declarativa_tipo",
"lista_sent : sentencia_ejec lista_sent",
"lista_sent : sentencia_ejec",
"sentencia_ejec : sentencia_simple",
"sentencia_ejec : sentencia_comp",
"sentencia_simple : sent_correcta",
"$$4 :",
"sentencia_simple : sent_abierta $$4 sent_correcta",
"$$5 :",
"sentencia_simple : sent_abierta $$5 sentencia_comp",
"$$6 :",
"sent_abierta : PRINT $$6 '(' cadena ')'",
"sent_abierta : llamada_funcion",
"$$7 :",
"sent_abierta : RETURN $$7 expresion",
"sent_abierta : asignacion",
"sent_correcta : sent_abierta ';'",
"sent_correcta : error ';'",
"sentencia_comp : sentencia_if",
"sentencia_comp : sentencia_loop",
"$$8 :",
"sentencia_if : IF '(' cond ')' $$8 THEN bloque_IF",
"sentencia_if : sentencia_if_error",
"$$9 :",
"sentencia_if_error : IF THEN $$9 bloque_IF",
"$$10 :",
"sentencia_if_error : IF cond ')' $$10 THEN bloque_IF",
"$$11 :",
"sentencia_if_error : IF '(' cond $$11 THEN bloque_IF",
"$$12 :",
"sentencia_if_error : IF cond $$12 THEN bloque_IF",
"$$13 :",
"sentencia_if_error : IF '(' cond ')' $$13 bloque_IF",
"$$14 :",
"sentencia_if_error : IF error $$14 bloque_IF",
"$$15 :",
"sentencia_loop : LOOP $$15 bloque_loop",
"bloque_loop : bloque_sent UNTIL cond",
"bloque_loop : loop_error",
"loop_error : bloque_sent cond",
"loop_error : bloque_sent UNTIL error",
"loop_error : bloque_sent error",
"llamada_funcion : ID '(' lista_parametros ')'",
"llamada_funcion : ID '(' ')'",
"lista_var : ID ',' lista_var",
"lista_var : ID",
"bloque_IF : bloque_sent",
"bloque_IF : bloque_sent ELSE bloque_sent",
"cond : expresion comparador expresion",
"comparador : '<'",
"comparador : '>'",
"comparador : MEN_IG",
"comparador : MAY_IG",
"comparador : IGUAL",
"comparador : DIST",
"parametros : tipo ID",
"parametros : tipo ID ',' parametros",
"lista_parametros : parametro ',' lista_parametros",
"lista_parametros : parametro",
"parametro : expresion",
"asignacion : ID '=' expresion",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : CTE",
"factor : llamada_funcion",
"tipo : uint",
};

//#line 159 "Gramatica.nueva.y"

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
//#line 488 "Parser.java"
//###############################################################
// method: yylexdebug : check lexer state
//###############################################################
void yylexdebug(int state,int ch)
{
String s=null;
  if (ch < 0) ch=0;
  if (ch <= YYMAXTOKEN) //check index bounds
     s = yyname[ch];    //now get it
  if (s==null)
    s = "illegal-symbol";
  debug("state "+state+", reading "+ch+" ("+s+")");
}





//The following are now global, to aid in error reporting
int yyn;       //next next thing to do
int yym;       //
int yystate;   //current parsing state from state table
String yys;    //current token string


//###############################################################
// method: yyparse : parse input and execute indicated items
//###############################################################
int yyparse()
{
boolean doaction;
  init_stacks();
  yynerrs = 0;
  yyerrflag = 0;
  yychar = -1;          //impossible char forces a read
  yystate=0;            //initial state
  state_push(yystate);  //save it
  val_push(yylval);     //save empty value
  while (true) //until parsing is done, either correctly, or w/error
    {
    doaction=true;
    if (yydebug) debug("loop"); 
    //#### NEXT ACTION (from reduction table)
    for (yyn=yydefred[yystate];yyn==0;yyn=yydefred[yystate])
      {
      if (yydebug) debug("yyn:"+yyn+"  state:"+yystate+"  yychar:"+yychar);
      if (yychar < 0)      //we want a char?
        {
        yychar = yylex();  //get next token
        if (yydebug) debug(" next yychar:"+yychar);
        //#### ERROR CHECK ####
        if (yychar < 0)    //it it didn't work/error
          {
          yychar = 0;      //change it to default string (no -1!)
          if (yydebug)
            yylexdebug(yystate,yychar);
          }
        }//yychar<0
      yyn = yysindex[yystate];  //get amount to shift by (shift index)
      if ((yyn != 0) && (yyn += yychar) >= 0 &&
          yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        {
        if (yydebug)
          debug("state "+yystate+", shifting to state "+yytable[yyn]);
        //#### NEXT STATE ####
        yystate = yytable[yyn];//we are in a new state
        state_push(yystate);   //save it
        val_push(yylval);      //push our lval as the input for next rule
        yychar = -1;           //since we have 'eaten' a token, say we need another
        if (yyerrflag > 0)     //have we recovered an error?
           --yyerrflag;        //give ourselves credit
        doaction=false;        //but don't process yet
        break;   //quit the yyn=0 loop
        }

    yyn = yyrindex[yystate];  //reduce
    if ((yyn !=0 ) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
      {   //we reduced!
      if (yydebug) debug("reduce");
      yyn = yytable[yyn];
      doaction=true; //get ready to execute
      break;         //drop down to actions
      }
    else //ERROR RECOVERY
      {
      if (yyerrflag==0)
        {
        yyerror("syntax error");
        yynerrs++;
        }
      if (yyerrflag < 3) //low error count?
        {
        yyerrflag = 3;
        while (true)   //do until break
          {
          if (stateptr<0)   //check for under & overflow here
            {
            yyerror("stack underflow. aborting...");  //note lower case 's'
            return 1;
            }
          yyn = yysindex[state_peek(0)];
          if ((yyn != 0) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
            if (yydebug)
              debug("state "+state_peek(0)+", error recovery shifting to state "+yytable[yyn]+" ");
            yystate = yytable[yyn];
            state_push(yystate);
            val_push(yylval);
            doaction=false;
            break;
            }
          else
            {
            if (yydebug)
              debug("error recovery discarding state "+state_peek(0)+" ");
            if (stateptr<0)   //check for under & overflow here
              {
              yyerror("Stack underflow. aborting...");  //capital 'S'
              return 1;
              }
            state_pop();
            val_pop();
            }
          }
        }
      else            //discard this token
        {
        if (yychar == 0)
          return 1; //yyabort
        if (yydebug)
          {
          yys = null;
          if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
          if (yys == null) yys = "illegal-symbol";
          debug("state "+yystate+", error recovery discards token "+yychar+" ("+yys+")");
          }
        yychar = -1;  //read another
        }
      }//end error recovery
    }//yyn=0 loop
    if (!doaction)   //any reason not to proceed?
      continue;      //skip action
    yym = yylen[yyn];          //get count of terminals on rhs
    if (yydebug)
      debug("state "+yystate+", reducing "+yym+" by rule "+yyn+" ("+yyrule[yyn]+")");
    if (yym>0)                 //if count of rhs not 'nil'
      yyval = val_peek(yym-1); //get current semantic value
    yyval = dup_yyval(yyval); //duplicate yyval if ParserVal is used as semantic value
    switch(yyn)
      {
//########## USER-SUPPLIED ACTIONS ##########
case 4:
//#line 18 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia declarativa de funcion");}
break;
case 8:
//#line 24 "Gramatica.nueva.y"
{System.out.println("Error funcion");}
break;
case 13:
//#line 33 "Gramatica.nueva.y"
{System.out.println("Error en bloque de funcion");}
break;
case 16:
//#line 40 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");}
break;
case 17:
//#line 41 "Gramatica.nueva.y"
{System.out.println("Error tipo");}
break;
case 19:
//#line 42 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 21:
//#line 43 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 28:
//#line 57 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 30:
//#line 58 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 32:
//#line 61 "Gramatica.nueva.y"
{System.out.println("syntax error en línea "+Al.LineasContadas+": Sentencia 'print'");}
break;
case 34:
//#line 62 "Gramatica.nueva.y"
{System.out.println("syntax error en línea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
break;
case 35:
//#line 63 "Gramatica.nueva.y"
{System.out.println("syntax error en línea "+Al.LineasContadas+": Sentencia 'return'");}
break;
case 42:
//#line 75 "Gramatica.nueva.y"
{System.out.println("Línea "+Al.LineasContadas+": Sentencia 'if'");}
break;
case 45:
//#line 79 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la condicion del IF");}
break;
case 47:
//#line 80 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta abrir parentesis");}
break;
case 49:
//#line 81 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta cerrar parentesis");}
break;
case 51:
//#line 82 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta  parentesis");}
break;
case 53:
//#line 83 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la palabra THEN");}
break;
case 55:
//#line 84 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": errores múltiples en IF");}
break;
case 57:
//#line 86 "Gramatica.nueva.y"
{System.out.println("Línea "+Al.LineasContadas+": Sentencia de iteracion");}
break;
case 61:
//#line 93 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la palabra UNTIL");}
break;
case 62:
//#line 94 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la condicion luego de UNTIL");}
break;
case 63:
//#line 95 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Se olvido del UNTIL?");}
break;
case 82:
//#line 138 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia de asignacion");}
break;
//#line 733 "Parser.java"
//########## END OF USER-SUPPLIED ACTIONS ##########
    }//switch
    //#### Now let's reduce... ####
    if (yydebug) debug("reduce");
    state_drop(yym);             //we just reduced yylen states
    yystate = state_peek(0);     //get new state
    val_drop(yym);               //corresponding value drop
    yym = yylhs[yyn];            //select next TERMINAL(on lhs)
    if (yystate == 0 && yym == 0)//done? 'rest' state and at first TERMINAL
      {
      if (yydebug) debug("After reduction, shifting from state 0 to state "+YYFINAL+"");
      yystate = YYFINAL;         //explicitly say we're done
      state_push(YYFINAL);       //and save it
      val_push(yyval);           //also save the semantic value of parsing
      if (yychar < 0)            //we want another character?
        {
        yychar = yylex();        //get next character
        if (yychar<0) yychar=0;  //clean, if necessary
        if (yydebug)
          yylexdebug(yystate,yychar);
        }
      if (yychar == 0)          //Good exit (if lex returns 0 ;-)
         break;                 //quit the loop--all DONE
      }//if yystate
    else                        //else not done yet
      {                         //get next state and push, for next yydefred[]
      yyn = yygindex[yym];      //find out where to go
      if ((yyn != 0) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn]; //get new state
      else
        yystate = yydgoto[yym]; //else go to new defred
      if (yydebug) debug("after reduction, shifting from state "+state_peek(0)+" to state "+yystate+"");
      state_push(yystate);     //going again, so push state & val...
      val_push(yyval);         //for next action
      }
    }//main loop
  return 0;//yyaccept!!
}
//## end of method parse() ######################################



//## run() --- for Thread #######################################
//## The -Jnorun option was used ##
//## end of method run() ########################################



//## Constructors ###############################################
/**
 * Default constructor.  Turn off with -Jnoconstruct .

 */
public Parser()
{
  //nothing to do
}


/**
 * Create a parser, setting the debug to true or false.
 * @param debugMe true for debugging, false for no debug.
 */
public Parser(boolean debugMe)
{
  yydebug=debugMe;
}
//###############################################################



}
//################### END OF CLASS ##############################
