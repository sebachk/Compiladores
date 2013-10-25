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



//#line 2 "Gramatica.reentrega.y"
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
    0,    1,    1,    2,    2,    4,    4,    5,    5,    5,
    5,    6,    6,    8,   11,    8,   12,    8,   10,   10,
    3,    3,   14,   14,   18,   14,   19,   14,   13,   13,
    9,    9,   20,   23,   20,   24,   20,   25,   22,   22,
   28,   22,   22,   17,   17,   21,   21,   33,   30,   30,
   36,   35,   37,   35,   38,   35,   39,   35,   40,   35,
   41,   35,   43,   31,   42,   42,   44,   44,   44,   26,
   26,   16,   16,   34,   34,   34,   32,   46,   46,   46,
   46,   46,   46,    7,    7,   45,   45,   47,   29,   27,
   27,   27,   48,   48,   48,   49,   49,   49,   15,
};
final static short yylen[] = {                            2,
    3,    1,    2,    1,    2,    1,    1,    6,    5,    3,
    3,    1,    1,    3,    0,    4,    0,    4,    1,    2,
    2,    1,    3,    3,    0,    4,    0,    4,    2,    1,
    1,    1,    1,    0,    3,    0,    3,    0,    5,    1,
    0,    3,    1,    2,    2,    1,    1,    0,    7,    1,
    0,    4,    0,    6,    0,    6,    0,    5,    0,    6,
    0,    4,    0,    3,    3,    1,    2,    3,    2,    4,
    3,    3,    1,    1,    3,    2,    3,    1,    1,    1,
    1,    1,    1,    2,    4,    3,    1,    1,    3,    3,
    3,    1,    3,    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   38,   41,    0,    0,   63,
   99,    0,    2,    0,    0,    6,    7,   13,   12,    0,
    0,   33,   31,   32,    0,   40,   43,   46,   47,   50,
   45,    0,    0,   61,    0,   97,   51,    0,   98,    0,
    0,    0,   95,    0,    0,    0,    0,    0,    0,    0,
   19,    0,    1,    3,    5,   21,    0,    0,    0,   44,
    0,    0,   71,    0,    0,    0,    0,    0,    0,    0,
   81,   80,   83,   82,   78,   79,    0,    0,    0,   53,
    0,    0,    0,    0,    0,   10,   11,    0,   20,   29,
   14,    0,    0,    0,   64,   66,   24,    0,   23,    0,
    0,   35,    0,   37,   70,    0,    0,    0,   62,   52,
    0,    0,    0,    0,    0,    0,    0,   93,   94,    0,
    0,    0,    0,   16,   18,   69,    0,   67,   72,   26,
   28,   86,   76,    0,    0,    0,    0,    0,   58,   39,
    9,    0,    0,   68,   65,   75,    0,   60,   56,   54,
    8,    0,   49,   85,
};
final static short yydgoto[] = {                          2,
   12,   13,   14,   15,   16,  108,  122,   18,   19,   50,
   92,   93,   51,   20,   21,   59,   22,  100,  101,   23,
   24,   25,   61,   62,   44,   26,   40,   45,   27,   28,
   29,   41,  135,  109,   30,   69,  116,  112,   81,  136,
   68,   95,   52,   96,   65,   79,   66,   42,   43,
};
final static short yysindex[] = {                      -220,
  -92,    0,   -7,  -31,   70,    0,    0, -126, -162,    0,
    0, -213,    0,  -77,  -77,    0,    0,    0,    0, -203,
 -123,    0,    0,    0,   14,    0,    0,    0,    0,    0,
    0,   58, -119,    0,   56,    0,    0, -119,    0,   23,
   59,   46,    0,   63, -119,  -28,   65,  -62,  -62, -153,
    0,  117,    0,    0,    0,    0,   67,   84,   76,    0,
 -187, -243,    0,  -14,   99,  101,  -14,  130,  130,  122,
    0,    0,    0,    0,    0,    0, -119, -119, -119,    0,
 -144, -119, -119,  -93,  -14,    0,    0,  -36,    0,    0,
    0,  -96,  -90, -224,    0,    0,    0,  -67,    0, -187,
 -203,    0,   14,    0,    0, -119,   57,  -85,    0,    0,
    0,  -65,   46,   46,  -14,  -64,  130,    0,    0,  158,
  -96,  159,  -54,    0,    0,    0, -166,    0,    0,    0,
    0,    0,    0,  117,  -53,  130,  130,  130,    0,    0,
    0,  -96,  163,    0,    0,    0,  130,    0,    0,    0,
    0, -203,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -58,    0,    0,    0,    0,   91,
    0,    0,    0,    0,  143,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  -41,    0,    0,    0,    0,    0,
  -51,  -21,    0,    0,    0,    0,    0,    0, -154, -106,
    0,    0,    0,    0,    0,    0,    0,  -52, -141,    0,
    0,    0,    0,   12,    0,  172,   73,    0,    0,  -47,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   82,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -114,    0,    0,
  104,    0,   -1,   19,   43,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  188,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  147,   17,    0,  156,   13,  102,  -35,    1,    0,
    0,    0,  133,  157,  -80,  171,  -49,    0,    0,    0,
  212,  -45,    0,    0,    0,   42,    3,    0,    0,    0,
    0,  -23,    0,   20,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  183,    0,    0,  107,  110,
};
final static int YYTABLESIZE=411;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         96,
   96,   96,   96,   96,  121,   96,   73,  123,   32,   49,
   87,  102,   86,   17,   70,  103,    5,   96,   96,   92,
   96,   92,   92,   92,   10,   48,   17,   17,   77,   33,
   78,  126,   35,   36,   64,   67,   56,   92,   92,   90,
   92,   90,   90,   90,  127,    1,   39,   85,   49,   49,
  130,   31,   88,   53,  103,   88,  124,   90,   90,   91,
   90,   91,   91,   91,   94,   77,   11,   78,    3,    4,
  128,  123,   60,   39,   39,    6,    7,   91,   91,   39,
   91,  115,   75,   77,   76,  141,   39,   82,  110,  144,
   35,   36,   83,    3,    4,   32,   32,    5,   63,   80,
    6,    7,   84,  145,   88,   10,  151,   11,   64,   38,
   30,   30,   30,   91,   25,   25,  117,   33,   39,   39,
   39,   25,   25,   39,   39,   97,  133,   98,   27,   46,
   47,   89,   57,   58,   99,   39,  139,   35,   36,  105,
   42,   74,   74,   74,  106,   74,  146,   39,   74,   74,
   74,   74,   74,   74,   74,  148,  149,  150,   17,   15,
   54,   55,  111,    3,    4,  120,  153,    5,   39,    9,
    6,    7,    8,    9,    8,   10,  134,   11,    3,    4,
   89,   90,    5,  113,  114,    6,    7,    8,    9,   58,
   10,  118,  119,    3,    4,  137,  138,    5,  140,  142,
    6,    7,  143,   73,   73,   10,  152,  147,    4,   57,
   73,   73,   87,   55,   96,   96,   96,   73,   96,   96,
   96,   96,   96,   96,   96,   96,   96,   96,   84,   96,
   96,   96,   96,   11,   92,   92,   92,    9,   92,   92,
   92,   92,   92,   92,   92,   92,   92,   92,  125,   92,
   92,   92,   92,  154,   90,   90,   90,  131,   90,   90,
   90,   90,   90,   90,   90,   90,   90,   90,  129,   90,
   90,   90,   90,  104,   91,   91,   91,    0,   91,   91,
   91,   91,   91,   91,   91,   91,   91,   91,  132,   91,
   91,   91,   91,   71,   72,   73,   74,    0,   77,   77,
   77,    0,   77,   77,   77,   77,   77,   77,   77,   77,
   77,   77,    3,  107,   35,   36,    5,    0,    0,    6,
    7,    0,    9,    0,   10,   34,   35,   36,   89,   89,
   37,    0,   89,    0,    0,   89,   89,   42,   42,    0,
   89,   42,    0,    0,   42,   42,   22,   22,    0,   42,
   22,    0,    0,   22,   22,   22,   22,    0,   22,   59,
   59,    0,    0,   59,   48,    0,   59,   59,    0,   59,
    0,   59,    3,    4,    0,    0,    5,    0,    0,    6,
    7,    0,    9,    0,   10,    3,  107,    0,    0,    5,
    0,    0,    6,    7,    0,    9,    0,   10,   34,   34,
    0,    0,   36,    0,    0,   34,   34,    0,    0,    0,
   36,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   59,   88,   40,    9,
   46,   61,   41,    1,   38,   61,  260,   59,   60,   41,
   62,   43,   44,   45,  268,    9,   14,   15,   43,   61,
   45,  256,  257,  258,   32,   33,   20,   59,   60,   41,
   62,   43,   44,   45,  269,  266,    5,   45,   48,   49,
  100,   59,   41,  267,  100,   44,   92,   59,   60,   41,
   62,   43,   44,   45,   52,   43,  270,   45,  256,  257,
   94,  152,   59,   32,   33,  263,  264,   59,   60,   38,
   62,   79,   60,   41,   62,  121,   45,   42,   69,  256,
  257,  258,   47,  256,  257,   40,   40,  260,   41,   41,
  263,  264,   40,  127,   40,  268,  142,  270,  106,   40,
  265,  266,  267,  267,  256,  257,  261,   61,   77,   78,
   79,  263,  264,   82,   83,   59,  107,   44,  270,  256,
  257,   59,  256,  257,   59,   94,  117,  257,  258,   41,
   59,  256,  257,  258,   44,  260,  134,  106,  263,  264,
  265,  266,  267,  268,  269,  136,  137,  138,  265,  266,
   14,   15,   41,  256,  257,  259,  147,  260,  127,  266,
  263,  264,  265,  266,  265,  268,  262,  270,  256,  257,
   48,   49,  260,   77,   78,  263,  264,  265,  266,  257,
  268,   82,   83,  256,  257,  261,  261,  260,   41,   41,
  263,  264,  257,  256,  257,  268,   44,  261,  267,  261,
  263,  264,   41,  261,  256,  257,  258,  270,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   41,  271,
  272,  273,  274,  270,  256,  257,  258,  266,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   93,  271,
  272,  273,  274,  152,  256,  257,  258,  101,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   98,  271,
  272,  273,  274,   62,  256,  257,  258,   -1,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  106,  271,
  272,  273,  274,  271,  272,  273,  274,   -1,  256,  257,
  258,   -1,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  256,  257,  257,  258,  260,   -1,   -1,  263,
  264,   -1,  266,   -1,  268,  256,  257,  258,  256,  257,
  261,   -1,  260,   -1,   -1,  263,  264,  256,  257,   -1,
  268,  260,   -1,   -1,  263,  264,  256,  257,   -1,  268,
  260,   -1,   -1,  263,  264,  265,  266,   -1,  268,  256,
  257,   -1,   -1,  260,  261,   -1,  263,  264,   -1,  266,
   -1,  268,  256,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,   -1,  266,   -1,  268,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,   -1,  266,   -1,  268,  256,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,   -1,   -1,   -1,
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
"programa : BEGIN lista_declaraciones_prog END",
"lista_declaraciones_prog : lista_declaraciones",
"lista_declaraciones_prog : lista_sent_declar lista_declaraciones",
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

//#line 172 "Gramatica.reentrega.y"


AnalizadorLexico Al = new ALexico.AnalizadorLexico(new File("Docs/codigo.txt"));
//PolacaInversa PI = new PolacaInversa();
int contador=1;

int yylex(){
	ALexico.Token t; 
	int val= this.YYERRCODE;
	while(val==this.YYERRCODE){
		t = Al.GetToken();
		if(t!=null){
			ALexico.Estructuras.addToken("Token"+ contador++ +" = " + ALexico.Estructuras.getStringToken(t.getIdentif_tt()));
			val =t.getIdentif_tt();
			if(val!=this.YYERRCODE){
				yylval= new ParserVal();
				yylval.ival=t.getIndice_ts();
				if(yylval.ival!=-1){
					yylval.sval=Estructuras.Tabla_Simbolos.elementAt(yylval.ival).valor;
					}
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
  yyparse();
  Estructuras.PrintTablaS();
  //PI.ImprimirPolaca();
}


//#line 526 "Parser.java"
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
case 6:
//#line 25 "Gramatica.reentrega.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de funcion");}
break;
case 15:
//#line 40 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 17:
//#line 41 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 23:
//#line 52 "Gramatica.reentrega.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");}
break;
case 25:
//#line 54 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 27:
//#line 55 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 34:
//#line 69 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 36:
//#line 70 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 38:
//#line 73 "Gramatica.reentrega.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'print'");}
break;
case 40:
//#line 74 "Gramatica.reentrega.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
break;
case 41:
//#line 75 "Gramatica.reentrega.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'return'");}
break;
case 48:
//#line 87 "Gramatica.reentrega.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'if'");}
break;
case 51:
//#line 91 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la condicion del IF");}
break;
case 53:
//#line 92 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta abrir parentesis");}
break;
case 55:
//#line 93 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta cerrar parentesis");}
break;
case 57:
//#line 94 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta  parentesis");}
break;
case 59:
//#line 95 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la palabra THEN");}
break;
case 61:
//#line 96 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": errores múltiples en IF");}
break;
case 63:
//#line 98 "Gramatica.reentrega.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de iteracion");}
break;
case 67:
//#line 105 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la palabra UNTIL");}
break;
case 68:
//#line 106 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la condicion luego de UNTIL");}
break;
case 69:
//#line 107 "Gramatica.reentrega.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Se olvido del UNTIL?");}
break;
case 89:
//#line 151 "Gramatica.reentrega.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia de asignacion");}
break;
//#line 767 "Parser.java"
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
