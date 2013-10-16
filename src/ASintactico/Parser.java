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
    0,    1,    1,    2,    2,    3,    3,    3,    3,    4,
    4,    6,    9,    6,   10,    6,    8,    8,   12,   12,
   13,   13,   17,   13,   18,   13,   11,   11,    7,    7,
   19,   22,   19,   23,   19,   24,   21,   21,   27,   21,
   21,   16,   16,   20,   20,   32,   29,   29,   35,   34,
   36,   34,   37,   34,   38,   34,   39,   34,   40,   34,
   42,   30,   41,   41,   43,   43,   43,   25,   25,   15,
   15,   33,   33,   33,   31,   45,   45,   45,   45,   45,
   45,    5,    5,   44,   44,   46,   28,   26,   26,   26,
   47,   47,   47,   48,   48,   48,   14,
};
final static short yylen[] = {                            2,
    3,    1,    2,    1,    1,    6,    5,    3,    3,    1,
    1,    3,    0,    4,    0,    4,    1,    2,    2,    1,
    3,    3,    0,    4,    0,    4,    2,    1,    1,    1,
    1,    0,    3,    0,    3,    0,    5,    1,    0,    3,
    1,    2,    2,    1,    1,    0,    7,    1,    0,    4,
    0,    6,    0,    6,    0,    5,    0,    6,    0,    4,
    0,    3,    3,    1,    2,    3,    2,    4,    3,    3,
    1,    1,    3,    2,    3,    1,    1,    1,    1,    1,
    1,    2,    4,    3,    1,    1,    3,    3,    3,    1,
    3,    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   36,   39,    0,    0,   61,
    0,    0,    4,    5,   11,   10,   31,   29,   30,    0,
   38,   41,   44,   45,   48,   43,    0,    0,   59,    0,
   95,   49,    0,   96,    0,    0,    0,   93,    0,    0,
    0,    0,   97,    0,    0,   17,    0,    0,    0,    0,
    1,    3,   42,    0,    0,   69,    0,    0,    0,    0,
    0,    0,    0,   79,   78,   81,   80,   76,   77,    0,
    0,    0,   51,    0,    0,    0,    0,    0,    8,    9,
    0,   27,   12,    0,    0,   18,   19,    0,    0,    0,
    0,   62,   64,   33,    0,   35,   68,    0,    0,    0,
   60,   50,    0,    0,    0,    0,    0,    0,    0,   91,
   92,    0,    0,    0,    0,   14,   16,   22,    0,   21,
    0,    0,   67,    0,   65,   84,   74,    0,    0,    0,
    0,    0,   56,   37,    7,    0,    0,   70,   24,   26,
   66,   63,   73,    0,   58,   54,   52,    6,    0,   47,
   83,
};
final static short yydgoto[] = {                          2,
   11,   12,   13,  100,  114,   15,   16,   45,   84,   85,
   46,   47,   48,   49,   90,   17,  121,  122,   18,   19,
   20,   54,   55,   39,   21,   35,   40,   22,   23,   24,
   36,  129,  101,   25,   62,  108,  104,   74,  130,   61,
   92,   50,   93,   58,   72,   59,   37,   38,
};
final static short yysindex[] = {                      -240,
  -86,    0,   13,  -29,   70,    0,    0, -242, -155,    0,
 -192,  -86,    0,    0,    0,    0,    0,    0,    0,   31,
    0,    0,    0,    0,    0,    0,   58, -227,    0,   54,
    0,    0, -227,    0,   23,   66,  -30,    0,   71, -227,
  -28,   77,    0, -134, -169,    0, -134, -139, -220,   91,
    0,    0,    0, -207, -213,    0,   69,   92,   98,   69,
  104,  104,   94,    0,    0,    0,    0,    0,    0, -227,
 -227, -227,    0,  -89, -227, -227, -115,   69,    0,    0,
  -36,    0,    0, -105,  -90,    0,    0,  122,  132,  126,
 -223,    0,    0,    0,   31,    0,    0, -227,   57,  -76,
    0,    0,    0,  -72,  -30,  -30,   69,  -69,  104,    0,
    0,  153, -105,  155,  -60,    0,    0,    0,  -59,    0,
 -207, -139,    0, -170,    0,    0,    0,   91,  -62,  104,
  104,  104,    0,    0,    0, -105,  156,    0,    0,    0,
    0,    0,    0,  104,    0,    0,    0,    0, -139,    0,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -66,    0,    0,    0,    0,    0,    0,    0,  -54,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  -41,
    0,    0,    0,    0,    0,  -53,  -21,    0,    0,    0,
    0,    0,    0, -117, -138,    0,    0,  117,    0,    0,
    0,    0,    0,    0,    0,    0,   29,    0,  166,   73,
    0,    0,  -48,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   82,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -52, -111,
    0,    0,    0,    0,    0,    0,    0,    0,    0, -100,
    0,    0,  -73,    0,   -1,   19,   43,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  188,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  237,    0,  169,   15,  109,  -33,    1,    0,    0,    0,
   27,  221,  152,  -56,  159,  -26,    0,    0,    0,  234,
  -25,    0,    0,    0,   49,   64,    0,    0,    0,    0,
  -24,    0,    7,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  200,    0,    0,  -18,   79,
};
final static int YYTABLESIZE=385;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         94,
   94,   94,   94,   94,  113,   94,   71,   80,   63,   44,
   27,   75,   79,   41,   42,   14,   76,   94,   94,   90,
   94,   90,   90,   90,  115,    1,   14,   94,   95,   30,
   31,   28,  123,   30,   31,   88,   89,   90,   90,   88,
   90,   88,   88,   88,   44,  124,    5,   44,    3,    4,
  116,  105,  106,   34,   10,    6,    7,   88,   88,   89,
   88,   89,   89,   89,   91,   70,  125,   71,  102,   86,
   82,   26,   86,   86,   51,   34,   34,   89,   89,  135,
   89,   34,   68,   75,   69,  141,   30,   31,   34,   53,
   57,   60,  115,   27,  139,   95,   27,   83,   56,  142,
    3,    4,  148,   78,    5,  127,   73,    6,    7,   33,
   77,   70,   10,   71,   43,  133,   81,   28,   34,   34,
   34,    3,    4,   34,   34,    5,   15,   13,    6,    7,
   43,   87,   97,   10,  103,  107,  145,  146,  147,   34,
   40,   98,  143,  112,   23,   23,   34,   28,   28,   28,
  150,   23,   23,  110,  111,   72,   72,   72,   25,   72,
    9,   57,   72,   72,   72,   72,   72,   72,   72,    3,
    4,  109,   34,    5,    8,  119,    6,    7,    8,    9,
  118,   10,   57,   57,  120,  128,   57,   46,  131,   57,
   57,  132,   57,  134,   57,  136,  137,   89,  144,  149,
    2,   32,   32,   71,   71,   34,   85,   55,   32,   32,
   71,   71,   53,   34,   94,   94,   94,   71,   94,   94,
   94,   94,   94,   94,   94,   94,   94,   94,   82,   94,
   94,   94,   94,   43,   90,   90,   90,    9,   90,   90,
   90,   90,   90,   90,   90,   90,   90,   90,   52,   90,
   90,   90,   90,  117,   88,   88,   88,  151,   88,   88,
   88,   88,   88,   88,   88,   88,   88,   88,   87,   88,
   88,   88,   88,  140,   89,   89,   89,  138,   89,   89,
   89,   89,   89,   89,   89,   89,   89,   89,   96,   89,
   89,   89,   89,   64,   65,   66,   67,  126,   75,   75,
   75,    0,   75,   75,   75,   75,   75,   75,   75,   75,
   75,   75,    3,   99,   30,   31,    5,    0,    0,    6,
    7,    0,    9,    0,   10,   29,   30,   31,   87,   87,
   32,    0,   87,    0,    0,   87,   87,   40,   40,    0,
   87,   40,    0,    0,   40,   40,    3,    4,    0,   40,
    5,    0,    0,    6,    7,    0,    9,    0,   10,    3,
   99,    0,    0,    5,    0,    0,    6,    7,    0,    9,
    0,   10,   20,   20,    0,    0,   20,    0,    0,   20,
   20,    0,    0,    0,   20,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   59,   41,   33,    9,
   40,   42,   41,  256,  257,    1,   47,   59,   60,   41,
   62,   43,   44,   45,   81,  266,   12,   54,   54,  257,
  258,   61,  256,  257,  258,  256,  257,   59,   60,   41,
   62,   43,   44,   45,   44,  269,  260,   47,  256,  257,
   84,   70,   71,    5,  268,  263,  264,   59,   60,   41,
   62,   43,   44,   45,   50,   43,   91,   45,   62,   41,
   44,   59,   44,   47,  267,   27,   28,   59,   60,  113,
   62,   33,   60,   41,   62,  256,  257,  258,   40,   59,
   27,   28,  149,   40,  121,  121,   40,  267,   41,  124,
  256,  257,  136,   40,  260,   99,   41,  263,  264,   40,
   40,   43,  268,   45,  270,  109,   40,   61,   70,   71,
   72,  256,  257,   75,   76,  260,  265,  266,  263,  264,
  270,   59,   41,  268,   41,   72,  130,  131,  132,   91,
   59,   44,  128,  259,  256,  257,   98,  265,  266,  267,
  144,  263,  264,   75,   76,  256,  257,  258,  270,  260,
  266,   98,  263,  264,  265,  266,  267,  268,  269,  256,
  257,  261,  124,  260,  265,   44,  263,  264,  265,  266,
   59,  268,  256,  257,   59,  262,  260,  261,  261,  263,
  264,  261,  266,   41,  268,   41,  257,  257,  261,   44,
  267,  256,  257,  256,  257,  260,   41,  261,  263,  264,
  263,  264,  261,  268,  256,  257,  258,  270,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   41,  271,
  272,  273,  274,  270,  256,  257,  258,  266,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   12,  271,
  272,  273,  274,   85,  256,  257,  258,  149,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   48,  271,
  272,  273,  274,  122,  256,  257,  258,  119,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   55,  271,
  272,  273,  274,  271,  272,  273,  274,   98,  256,  257,
  258,   -1,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  256,  257,  257,  258,  260,   -1,   -1,  263,
  264,   -1,  266,   -1,  268,  256,  257,  258,  256,  257,
  261,   -1,  260,   -1,   -1,  263,  264,  256,  257,   -1,
  268,  260,   -1,   -1,  263,  264,  256,  257,   -1,  268,
  260,   -1,   -1,  263,  264,   -1,  266,   -1,  268,  256,
  257,   -1,   -1,  260,   -1,   -1,  263,  264,   -1,  266,
   -1,  268,  256,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,   -1,   -1,   -1,  268,
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
"sentencia_declar_funcion : FUNCTION error bloque_funcion",
"bloque_sent : sentencia_ejec",
"bloque_sent : bloque_funcion",
"bloque_funcion : BEGIN lista_sentencias END",
"$$1 :",
"bloque_funcion : BEGIN lista_sentencias $$1 bloque_funcion",
"$$2 :",
"bloque_funcion : BEGIN lista_sentencias $$2 sentencia_declar_funcion",
"lista_sentencias : lista_sent_ejec",
"lista_sentencias : lista_sent_declar lista_sent_ejec",
"lista_sent_declar : sentencia_declarativa_tipo lista_sent_declar",
"lista_sent_declar : sentencia_declarativa_tipo",
"sentencia_declarativa_tipo : tipo lista_var ';'",
"sentencia_declarativa_tipo : tipo error ';'",
"$$3 :",
"sentencia_declarativa_tipo : tipo lista_var $$3 sent_correcta",
"$$4 :",
"sentencia_declarativa_tipo : tipo lista_var $$4 sentencia_declarativa_tipo",
"lista_sent_ejec : sentencia_ejec lista_sent_ejec",
"lista_sent_ejec : sentencia_ejec",
"sentencia_ejec : sentencia_simple",
"sentencia_ejec : sentencia_comp",
"sentencia_simple : sent_correcta",
"$$5 :",
"sentencia_simple : sent_abierta $$5 sent_correcta",
"$$6 :",
"sentencia_simple : sent_abierta $$6 sentencia_comp",
"$$7 :",
"sent_abierta : PRINT $$7 '(' cadena ')'",
"sent_abierta : llamada_funcion",
"$$8 :",
"sent_abierta : RETURN $$8 expresion",
"sent_abierta : asignacion",
"sent_correcta : sent_abierta ';'",
"sent_correcta : error ';'",
"sentencia_comp : sentencia_if",
"sentencia_comp : sentencia_loop",
"$$9 :",
"sentencia_if : IF '(' cond ')' $$9 THEN bloque_IF",
"sentencia_if : sentencia_if_error",
"$$10 :",
"sentencia_if_error : IF THEN $$10 bloque_IF",
"$$11 :",
"sentencia_if_error : IF cond ')' $$11 THEN bloque_IF",
"$$12 :",
"sentencia_if_error : IF '(' cond $$12 THEN bloque_IF",
"$$13 :",
"sentencia_if_error : IF cond $$13 THEN bloque_IF",
"$$14 :",
"sentencia_if_error : IF '(' cond ')' $$14 bloque_IF",
"$$15 :",
"sentencia_if_error : IF error $$15 bloque_IF",
"$$16 :",
"sentencia_loop : LOOP $$16 bloque_loop",
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
"bloque_IF : ID bloque_IF",
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

//#line 165 "Gramatica.nueva.y"

AnalizadorLexico Al = new ALexico.AnalizadorLexico(new File("Docs/errores.txt"));
int contador=1;


int yylex(){
	ALexico.Token t; 
	int val= this.YYERRCODE;
	while(val==this.YYERRCODE){
		t = Al.GetToken();
		if(t!=null){
			ALexico.Estructuras.addLog("Token "+ contador++ +" = " + ALexico.Estructuras.getStringToken(t.getIdentif_tt()));
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
  System.out.println(yyparse());
  Estructuras.PrintTablaS();
}
//#line 510 "Parser.java"
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
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de funcion");}
break;
case 13:
//#line 33 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 15:
//#line 34 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 21:
//#line 45 "Gramatica.nueva.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");}
break;
case 23:
//#line 47 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 25:
//#line 48 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 32:
//#line 62 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 34:
//#line 63 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 36:
//#line 66 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'print'");}
break;
case 38:
//#line 67 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
break;
case 39:
//#line 68 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'return'");}
break;
case 46:
//#line 80 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'if'");}
break;
case 49:
//#line 84 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la condicion del IF");}
break;
case 51:
//#line 85 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta abrir parentesis");}
break;
case 53:
//#line 86 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta cerrar parentesis");}
break;
case 55:
//#line 87 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta  parentesis");}
break;
case 57:
//#line 88 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la palabra THEN");}
break;
case 59:
//#line 89 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": errores múltiples en IF");}
break;
case 61:
//#line 91 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de iteracion");}
break;
case 65:
//#line 98 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la palabra UNTIL");}
break;
case 66:
//#line 99 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la condicion luego de UNTIL");}
break;
case 67:
//#line 100 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Se olvido del UNTIL?");}
break;
case 87:
//#line 144 "Gramatica.nueva.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia de asignacion");}
break;
//#line 751 "Parser.java"
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
