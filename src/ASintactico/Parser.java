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
	import CodigoIntermedio.*;
//#line 21 "Parser.java"




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
    2,    3,    0,    1,    1,    4,    4,    6,    9,    6,
   11,   12,    7,   14,    7,    7,    7,    8,    8,   13,
   17,   13,   18,   13,   16,   16,    5,    5,   20,   20,
   24,   20,   25,   20,   26,   20,   19,   19,   15,   15,
   27,   30,   27,   31,   27,   27,   32,   29,   29,   35,
   29,   29,   23,   23,   28,   28,   40,   37,   37,   43,
   42,   44,   42,   45,   42,   46,   42,   47,   42,   48,
   42,   50,   38,   49,   49,   51,   51,   51,   53,   54,
   33,   33,   55,   22,   22,   41,   56,   41,   41,   39,
   39,   39,   39,   39,   39,   10,   57,   10,   52,   52,
   58,   36,   34,   34,   34,   59,   59,   59,   60,   60,
   60,   21,
};
final static short yylen[] = {                            2,
    0,    0,    5,    1,    2,    1,    2,    1,    0,    2,
    0,    0,    8,    0,    6,    3,    3,    1,    1,    3,
    0,    4,    0,    4,    1,    2,    2,    1,    3,    3,
    0,    4,    0,    4,    0,    4,    2,    1,    1,    1,
    1,    0,    3,    0,    3,    1,    0,    5,    1,    0,
    3,    1,    2,    2,    1,    1,    0,    7,    1,    0,
    4,    0,    6,    0,    6,    0,    5,    0,    6,    0,
    4,    0,    3,    3,    1,    2,    3,    2,    0,    0,
    6,    3,    0,    4,    1,    1,    0,    4,    2,    3,
    3,    3,    3,    3,    3,    2,    0,    5,    3,    1,
    1,    3,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    1,    0,    0,    0,  112,    2,    4,    0,    0,    8,
    0,    0,    0,    0,    0,    0,    5,    7,    0,    0,
    0,   47,   50,    0,   72,   46,   10,   19,   18,   41,
   39,   40,    0,   49,   52,   55,   56,   59,   27,    0,
    0,    0,   16,   17,    0,    3,   54,    0,    0,   70,
    0,  110,   60,    0,  111,    0,    0,    0,  108,    0,
    0,    0,    0,    0,   25,    0,   53,    0,    0,   30,
   83,   29,    0,    0,    0,   14,    0,   82,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   62,    0,    0,    0,    0,    0,   26,   37,   20,
    0,    0,    0,   73,   75,   43,    0,   45,    0,   32,
   34,   36,    0,   12,    0,  101,   80,    0,    0,    0,
   71,   61,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  106,  107,    0,   22,   24,   78,
    0,   76,   84,   15,    0,    0,    0,    0,   89,   87,
    0,    0,    0,    0,   67,   48,   77,   74,    0,   97,
   81,   99,    0,    0,   69,   65,   63,   13,    0,   88,
   58,   98,
};
final static short yydgoto[] = {                          2,
    6,    3,   16,    7,    8,    9,   10,  120,   11,  114,
   77,  145,   28,  113,   29,   64,  101,  102,   65,   12,
   13,   42,   30,   73,   74,   75,   31,   32,   33,   68,
   69,   60,   34,   56,   61,   35,   36,   37,   57,  151,
  121,   38,   82,  133,  124,   93,  152,   81,  104,   66,
  105,  117,   79,  147,  109,  163,  169,  118,   58,   59,
};
final static short yysindex[] = {                      -254,
    0,    0, -234, -147,    0,    0,    0, -235, -235,    0,
  230, -225, -125,  -32,    7, -210,    0,    0,   12,  -29,
  140,    0,    0,  -56,    0,    0,    0,    0,    0,    0,
    0,    0,   29,    0,    0,    0,    0,    0,    0,   48,
   67,   74,    0,    0,   88,    0,    0,   98, -120,    0,
  105,    0,    0, -120,    0,   23,  108,   25,    0,  111,
 -120,  271,  271, -110,    0,  230,    0, -187, -245,    0,
    0,    0, -187, -225, -235,    0, -225,    0,  -98,   63,
  243,  243,  120, -120, -120, -120, -120, -120, -120, -120,
 -120,    0,  -95, -120, -120,  -91,   63,    0,    0,    0,
  -97, -235, -223,    0,    0,    0,   29,    0,  -87,    0,
    0,    0,  -97,    0,  -86,    0,    0,  129,  127,  -85,
    0,    0,    0,  -83,   63,   63,   63,   63,   63,   63,
   25,   25,  -82,  243,    0,    0,  134,    0,    0,    0,
 -229,    0,    0,    0,  135,  137,  141,  -98,    0,    0,
  -78,  243,  243,  243,    0,    0,    0,    0,  -97,    0,
    0,    0,  230,  243,    0,    0,    0,    0, -225,    0,
    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  257,    0,    0,    0,    0,  257,  170,    0,
    0,  190,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -167,    0,    0,    0,    0,    0,    0,    0,
  143, -122,    0,    0,  -81,    0,    0,  -73,    0,    0,
  -41,    0,    0,    0,    0,    0,  -76,  -21,    0,    0,
    0,    0, -192, -119,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  285,
    0,    0,  -74,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  299,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  149,    0,  154,
    0,    0,  210,    0,   43,   57,   71,   85,   99,  113,
   -1,   19,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  150,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,  144,    2,    0,  -65,   -3,    0,   24,
    0,    0,   -9,    0,   -7,    0,    0,    0,   93,  118,
  -70,   86,   18,    0,    0,    0,    0,  125,   32,    0,
    0,    0,   33,  -36,    0,    0,    0,    0,  -38,    0,
  -39,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   49,    0,    0,    0,    0,    0,    0,   72,   70,
};
final static int YYTABLESIZE=567;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        109,
  109,  109,   26,  109,   44,  109,  115,   27,   43,  112,
   48,    1,   80,   39,   21,   83,   63,  109,  109,  105,
  109,  105,   25,  105,   97,   62,  157,   51,   52,    4,
    4,   49,  140,   51,   52,    5,  139,  105,  105,  103,
  105,  103,  122,  103,    5,  141,   45,  125,  126,  127,
  128,  129,  130,   55,   63,   63,   46,  103,  103,  104,
  103,  104,  103,  104,  142,   90,   94,   91,   19,   20,
   47,   95,   38,   38,   38,   22,   23,  104,  104,  149,
  104,   55,   88,   93,   89,  106,   55,   67,   42,   42,
  110,  138,   44,   55,  155,   42,   42,   92,  115,  107,
   44,   93,  158,  144,  107,   90,   70,   91,   14,   15,
   71,   95,  165,  166,  167,   92,   55,   55,   55,   55,
   55,   55,   55,   55,  171,   94,   55,   55,   76,   95,
   40,   41,   72,   31,   31,   55,   51,   52,   78,   90,
   31,   31,   35,   94,   48,   23,   21,   33,   92,  168,
   96,   17,   18,   91,   98,   99,  100,   90,  116,  170,
  123,  131,  132,  135,  136,  134,   48,  137,   24,   41,
  146,   91,  148,   55,  156,  159,  150,  153,  154,   54,
  160,  161,  164,   79,   66,   26,   64,   49,   11,  100,
   96,  111,  172,  108,  143,    0,  162,    0,    0,   19,
   20,   85,    0,   21,    0,    0,   22,   23,    0,    0,
    0,   25,   86,    5,  109,  109,  109,    0,  109,  109,
  109,  109,  109,  109,  109,  109,  109,  109,    9,  109,
  109,  109,  109,   24,  105,  105,  105,    0,  105,  105,
  105,  105,  105,  105,  105,  105,  105,  105,   28,  105,
  105,  105,  105,    0,  103,  103,  103,    0,  103,  103,
  103,  103,  103,  103,  103,  103,  103,  103,   68,  103,
  103,  103,  103,    0,  104,  104,  104,    0,  104,  104,
  104,  104,  104,  104,  104,  104,  104,  104,   26,  104,
  104,  104,  104,   84,   85,   86,   87,    0,   93,   93,
   93,   26,   93,   93,   93,   93,   93,   93,   93,   93,
   93,   93,   92,   92,   92,    9,   92,   92,   92,   92,
   92,   92,   92,   92,   92,   92,   95,   95,   95,   26,
   95,   95,   95,   95,   95,   95,   95,   95,   95,   95,
   94,   94,   94,  102,   94,   94,   94,   94,   94,   94,
   94,   94,   94,   94,   90,   90,   90,   51,   90,   90,
   90,   90,   90,   90,   90,   90,   90,   90,   91,   91,
   91,    0,   91,   91,   91,   91,   91,   91,   91,   91,
   91,   91,   19,  119,    0,    0,   21,    0,    0,   22,
   23,    0,   24,    0,   25,   50,   51,   52,   85,   85,
   53,    0,    0,    0,    0,   85,   85,   85,    0,   86,
   86,   86,   85,   86,    0,    0,   86,   86,   86,   86,
   86,   86,   86,    0,    0,    9,    9,    0,    0,    9,
    0,    0,    9,    9,    0,    9,    6,    9,    0,    0,
    0,    0,    0,    0,    0,   28,   28,    0,    0,   28,
    0,    0,   28,   28,   28,   28,    0,   28,    0,    0,
    0,    0,    0,    0,    0,   68,   68,    0,    0,   68,
   57,    0,   68,   68,    0,   68,    0,   68,    0,    0,
    0,    0,    0,    0,    0,   19,   20,    0,    0,   21,
    0,    0,   22,   23,    0,   24,    0,   25,   19,  119,
    0,    0,   21,    0,    0,   22,   23,    0,   24,    0,
   25,    0,    9,    9,    0,    0,    9,    0,    0,    9,
    9,    0,    9,    0,    9,    0,   19,   20,    0,    0,
   21,    0,    0,   22,   23,    0,    0,    0,   25,    0,
  102,  102,    0,    0,  102,    0,    0,  102,  102,    0,
    0,    0,  102,    0,   51,   51,    0,    0,   51,    0,
    0,   51,   51,    0,    0,    0,   51,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   59,   45,   14,   47,   77,   11,   41,   75,
   40,  266,   49,   12,  260,   54,   24,   59,   60,   41,
   62,   43,  268,   45,   61,   24,  256,  257,  258,  265,
  265,   61,  256,  257,  258,  270,  102,   59,   60,   41,
   62,   43,   82,   45,  270,  269,   40,   84,   85,   86,
   87,   88,   89,   21,   62,   63,  267,   59,   60,   41,
   62,   43,   66,   45,  103,   43,   42,   45,  256,  257,
   59,   47,  265,  266,  267,  263,  264,   59,   60,  119,
   62,   49,   60,   41,   62,   68,   54,   59,  256,  257,
   73,  101,  260,   61,  134,  263,  264,   41,  169,   68,
  268,   59,  141,  113,   73,   43,   59,   45,  256,  257,
   44,   41,  152,  153,  154,   59,   84,   85,   86,   87,
   88,   89,   90,   91,  164,   41,   94,   95,   41,   59,
  256,  257,   59,  256,  257,  103,  257,  258,   41,   41,
  263,  264,  265,   59,   40,  265,  266,  270,   41,  159,
   40,    8,    9,   41,   62,   63,  267,   59,  257,  163,
   41,   90,   91,   94,   95,  261,   40,  259,  266,  257,
  257,   59,   44,  141,   41,   41,  262,  261,  261,   40,
   44,   41,  261,  257,  261,   59,  261,   61,  270,   41,
   41,   74,  169,   69,  109,   -1,  148,   -1,   -1,  256,
  257,   59,   -1,  260,   -1,   -1,  263,  264,   -1,   -1,
   -1,  268,   59,  270,  256,  257,  258,   -1,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   59,  271,
  272,  273,  274,  266,  256,  257,  258,   -1,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   59,  271,
  272,  273,  274,   -1,  256,  257,  258,   -1,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   59,  271,
  272,  273,  274,   -1,  256,  257,  258,   -1,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   59,  271,
  272,  273,  274,  271,  272,  273,  274,   -1,  256,  257,
  258,   59,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  256,  257,  258,   59,  260,  261,  262,  263,
  264,  265,  266,  267,  268,  269,  256,  257,  258,   59,
  260,  261,  262,  263,  264,  265,  266,  267,  268,  269,
  256,  257,  258,   59,  260,  261,  262,  263,  264,  265,
  266,  267,  268,  269,  256,  257,  258,   59,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,  256,  257,
  258,   -1,  260,  261,  262,  263,  264,  265,  266,  267,
  268,  269,  256,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,   -1,  266,   -1,  268,  256,  257,  258,  256,  257,
  261,   -1,   -1,   -1,   -1,  263,  264,  265,   -1,  256,
  257,  258,  270,  260,   -1,   -1,  263,  264,  265,  266,
  267,  268,  269,   -1,   -1,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,   -1,  266,  267,  268,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,  265,  266,   -1,  268,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,  260,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,   -1,  266,   -1,  268,  256,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  256,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  256,  257,   -1,   -1,
  260,   -1,   -1,  263,  264,   -1,   -1,   -1,  268,   -1,
  256,  257,   -1,   -1,  260,   -1,   -1,  263,  264,   -1,
   -1,   -1,  268,   -1,  256,  257,   -1,   -1,  260,   -1,
   -1,  263,  264,   -1,   -1,   -1,  268,
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
"$$1 :",
"$$2 :",
"programa : BEGIN $$1 lista_declaraciones_prog $$2 END",
"lista_declaraciones_prog : lista_declaraciones",
"lista_declaraciones_prog : lista_sent_declar lista_declaraciones",
"lista_declaraciones : declaracion",
"lista_declaraciones : declaracion lista_declaraciones",
"declaracion : sentencia_declar_funcion",
"$$3 :",
"declaracion : $$3 bloque_sent",
"$$4 :",
"$$5 :",
"sentencia_declar_funcion : FUNCTION ID '(' $$4 parametros $$5 ')' bloque_funcion",
"$$6 :",
"sentencia_declar_funcion : FUNCTION ID '(' ')' $$6 bloque_funcion",
"sentencia_declar_funcion : FUNCTION error ')'",
"sentencia_declar_funcion : FUNCTION error bloque_funcion",
"bloque_sent : sentencia_ejec",
"bloque_sent : bloque_funcion",
"bloque_funcion : BEGIN lista_sentencias END",
"$$7 :",
"bloque_funcion : BEGIN lista_sentencias $$7 bloque_funcion",
"$$8 :",
"bloque_funcion : BEGIN lista_sentencias $$8 sentencia_declar_funcion",
"lista_sentencias : lista_sent_ejec",
"lista_sentencias : lista_sent_declar lista_sent_ejec",
"lista_sent_declar : sentencia_declarativa_tipo lista_sent_declar",
"lista_sent_declar : sentencia_declarativa_tipo",
"sentencia_declarativa_tipo : tipo lista_var ';'",
"sentencia_declarativa_tipo : tipo error ';'",
"$$9 :",
"sentencia_declarativa_tipo : tipo lista_var $$9 sent_correcta",
"$$10 :",
"sentencia_declarativa_tipo : tipo lista_var $$10 sentencia_declarativa_tipo",
"$$11 :",
"sentencia_declarativa_tipo : tipo lista_var $$11 sentencia_declar_funcion",
"lista_sent_ejec : sentencia_ejec lista_sent_ejec",
"lista_sent_ejec : sentencia_ejec",
"sentencia_ejec : sentencia_simple",
"sentencia_ejec : sentencia_comp",
"sentencia_simple : sent_correcta",
"$$12 :",
"sentencia_simple : sent_abierta $$12 sent_correcta",
"$$13 :",
"sentencia_simple : sent_abierta $$13 sentencia_comp",
"sentencia_simple : ';'",
"$$14 :",
"sent_abierta : PRINT $$14 '(' cadena ')'",
"sent_abierta : llamada_funcion",
"$$15 :",
"sent_abierta : RETURN $$15 expresion",
"sent_abierta : asignacion",
"sent_correcta : sent_abierta ';'",
"sent_correcta : error ';'",
"sentencia_comp : sentencia_if",
"sentencia_comp : sentencia_loop",
"$$16 :",
"sentencia_if : IF '(' cond ')' $$16 THEN bloque_IF",
"sentencia_if : sentencia_if_error",
"$$17 :",
"sentencia_if_error : IF THEN $$17 bloque_IF",
"$$18 :",
"sentencia_if_error : IF cond ')' $$18 THEN bloque_IF",
"$$19 :",
"sentencia_if_error : IF '(' cond $$19 THEN bloque_IF",
"$$20 :",
"sentencia_if_error : IF cond $$20 THEN bloque_IF",
"$$21 :",
"sentencia_if_error : IF '(' cond ')' $$21 bloque_IF",
"$$22 :",
"sentencia_if_error : IF error $$22 bloque_IF",
"$$23 :",
"sentencia_loop : LOOP $$23 bloque_loop",
"bloque_loop : bloque_sent UNTIL cond",
"bloque_loop : loop_error",
"loop_error : bloque_sent cond",
"loop_error : bloque_sent UNTIL error",
"loop_error : bloque_sent error",
"$$24 :",
"$$25 :",
"llamada_funcion : ID '(' $$24 lista_parametros $$25 ')'",
"llamada_funcion : ID '(' ')'",
"$$26 :",
"lista_var : ID ',' $$26 lista_var",
"lista_var : ID",
"bloque_IF : bloque_sent",
"$$27 :",
"bloque_IF : bloque_sent ELSE $$27 bloque_sent",
"bloque_IF : ID bloque_IF",
"cond : expresion '<' expresion",
"cond : expresion '>' expresion",
"cond : expresion MEN_IG expresion",
"cond : expresion MAY_IG expresion",
"cond : expresion IGUAL expresion",
"cond : expresion DIST expresion",
"parametros : tipo ID",
"$$28 :",
"parametros : tipo ID ',' $$28 parametros",
"lista_parametros : parametro ',' lista_parametros",
"lista_parametros : parametro",
"parametro : ID",
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

//#line 172 "Gramatica.nueva.y"


AnalizadorLexico Al = new ALexico.AnalizadorLexico(new File("Docs/codigo.txt"));
PolacaInversa PI = new PolacaInversa();
int contador=1;
int cant_param=0;

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

//**********************************
public PolacaInversa getPolaca(){
return PI;
}
//**********************************

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
  	
  if(!Estructuras.HayErrores()){
  	PI.ImprimirPolaca();
  }
}




//#line 594 "Parser.java"
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
case 1:
//#line 12 "Gramatica.nueva.y"
{ManejadorAmbitos.NewAmbito("Programa");}
break;
case 2:
//#line 12 "Gramatica.nueva.y"
{ManejadorAmbitos.EndAmbito();}
break;
case 8:
//#line 24 "Gramatica.nueva.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de funcion");}
break;
case 9:
//#line 25 "Gramatica.nueva.y"
{ManejadorAmbitos.NewAmbito("Main");}
break;
case 10:
//#line 25 "Gramatica.nueva.y"
{ManejadorAmbitos.EndAmbito();}
break;
case 11:
//#line 28 "Gramatica.nueva.y"
{cant_param=0;if(ManejadorAmbitos.PuedoDeclarar(val_peek(1).sval)) val_peek(1).ival=Estructuras.addTupla(val_peek(1).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.FUNCTION);ManejadorAmbitos.NewAmbito(val_peek(1).sval); PI.beginFunction();}
break;
case 12:
//#line 28 "Gramatica.nueva.y"
{PI.finParam(val_peek(3).sval,cant_param);
							Estructuras.Tabla_Simbolos.elementAt(val_peek(3).ival).valor=val_peek(3).sval+"_"+cant_param+"_"+ManejadorAmbitos.getInstance().FirstAmbito();
							Estructuras.SumAmbito(ManejadorAmbitos.getInstance(),"_"+cant_param+"");}
break;
case 13:
//#line 30 "Gramatica.nueva.y"
{PI.endFunction(val_peek(6).sval);ManejadorAmbitos.EndAmbito(); }
break;
case 14:
//#line 31 "Gramatica.nueva.y"
{cant_param=0;if(ManejadorAmbitos.PuedoDeclarar(val_peek(2).sval))  val_peek(2).ival=Estructuras.addTupla(val_peek(2).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.FUNCTION);  ManejadorAmbitos.NewAmbito(val_peek(2).sval);PI.beginFunction();PI.finParam(val_peek(2).sval,cant_param);}
break;
case 15:
//#line 31 "Gramatica.nueva.y"
{PI.endFunction(val_peek(4).sval);ManejadorAmbitos.EndAmbito();}
break;
case 21:
//#line 41 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 23:
//#line 42 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 29:
//#line 53 "Gramatica.nueva.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");}
break;
case 31:
//#line 55 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 33:
//#line 56 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 35:
//#line 57 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 42:
//#line 71 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 44:
//#line 72 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 47:
//#line 76 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'print'");}
break;
case 48:
//#line 76 "Gramatica.nueva.y"
{PI.callPrint(val_peek(1).ival);}
break;
case 49:
//#line 77 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
break;
case 50:
//#line 78 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'return'");}
break;
case 51:
//#line 78 "Gramatica.nueva.y"
{PI.retorno();}
break;
case 57:
//#line 90 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'if'"); PI.FinCondicion();ManejadorAmbitos.NewAmbito();}
break;
case 58:
//#line 90 "Gramatica.nueva.y"
{ManejadorAmbitos.EndAmbito();}
break;
case 60:
//#line 94 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la condicion del IF");}
break;
case 62:
//#line 95 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta abrir parentesis");}
break;
case 64:
//#line 96 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta cerrar parentesis");}
break;
case 66:
//#line 97 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta  parentesis");}
break;
case 68:
//#line 98 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la palabra THEN");}
break;
case 70:
//#line 99 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": errores múltiples en IF");}
break;
case 72:
//#line 101 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de iteracion");PI.InicLoop();ManejadorAmbitos.NewAmbito();}
break;
case 73:
//#line 101 "Gramatica.nueva.y"
{ManejadorAmbitos.EndAmbito();}
break;
case 74:
//#line 104 "Gramatica.nueva.y"
{PI.FinLoop();}
break;
case 76:
//#line 108 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la palabra UNTIL");}
break;
case 77:
//#line 109 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la condicion luego de UNTIL");}
break;
case 78:
//#line 110 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Se olvido del UNTIL?");}
break;
case 79:
//#line 113 "Gramatica.nueva.y"
{cant_param=0;PI.beginCall();}
break;
case 80:
//#line 113 "Gramatica.nueva.y"
{ManejadorAmbitos.isDeclarada(val_peek(3).sval+"_"+cant_param); PI.endcall(val_peek(3).sval,cant_param);}
break;
case 82:
//#line 114 "Gramatica.nueva.y"
{cant_param=0;ManejadorAmbitos.isDeclarada(val_peek(2).sval);PI.beginCall();PI.endcall(val_peek(2).sval,cant_param);}
break;
case 83:
//#line 118 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(1).sval)){  val_peek(1).ival=Estructuras.addTupla(val_peek(1).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);}}
break;
case 85:
//#line 119 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(0).sval)) { val_peek(0).ival=Estructuras.addTupla(val_peek(0).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);}}
break;
case 86:
//#line 123 "Gramatica.nueva.y"
{PI.FinIf();}
break;
case 87:
//#line 124 "Gramatica.nueva.y"
{PI.FinThenElse();}
break;
case 88:
//#line 124 "Gramatica.nueva.y"
{PI.FinIf();}
break;
case 89:
//#line 125 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Then mal escrito");}
break;
case 90:
//#line 129 "Gramatica.nueva.y"
{PI.addPolaco("<");}
break;
case 91:
//#line 130 "Gramatica.nueva.y"
{PI.addPolaco(">");}
break;
case 92:
//#line 131 "Gramatica.nueva.y"
{PI.addPolaco("<=");}
break;
case 93:
//#line 132 "Gramatica.nueva.y"
{PI.addPolaco(">=");}
break;
case 94:
//#line 133 "Gramatica.nueva.y"
{PI.addPolaco("==");}
break;
case 95:
//#line 134 "Gramatica.nueva.y"
{PI.addPolaco("!=");}
break;
case 96:
//#line 137 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(0).sval)) val_peek(0).ival=Estructuras.addTupla(val_peek(0).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.PUNT,Estructuras.USO_REF); cant_param++;PI.addPolaco("TS("+val_peek(0).ival+")");}
break;
case 97:
//#line 138 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(1).sval)) val_peek(1).ival=Estructuras.addTupla(val_peek(1).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.PUNT,Estructuras.USO_REF);cant_param++;PI.addPolaco("TS("+val_peek(1).ival+")");}
break;
case 99:
//#line 142 "Gramatica.nueva.y"
{cant_param++;}
break;
case 100:
//#line 143 "Gramatica.nueva.y"
{cant_param++;}
break;
case 101:
//#line 146 "Gramatica.nueva.y"
{ val_peek(0).ival=ManejadorAmbitos.isDeclarada(val_peek(0).sval); if(val_peek(0).ival!=-1)PI.addPolaco("TS("+val_peek(0).ival+")");}
break;
case 102:
//#line 151 "Gramatica.nueva.y"
{int pos= ManejadorAmbitos.isDeclarada(val_peek(2).sval); Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia de asignacion"); PI.addPolaco("TS("+pos+")"); PI.addPolaco("=");}
break;
case 103:
//#line 154 "Gramatica.nueva.y"
{PI.addPolaco("+");}
break;
case 104:
//#line 155 "Gramatica.nueva.y"
{PI.addPolaco("-");}
break;
case 106:
//#line 159 "Gramatica.nueva.y"
{PI.addPolaco("*");}
break;
case 107:
//#line 160 "Gramatica.nueva.y"
{PI.addPolaco("/");}
break;
case 109:
//#line 164 "Gramatica.nueva.y"
{int pos = ManejadorAmbitos.isDeclarada(val_peek(0).sval); PI.addPolaco("TS("+pos+")");}
break;
case 110:
//#line 165 "Gramatica.nueva.y"
{PI.addPolaco("TS("+val_peek(0).ival+")");}
break;
//#line 1001 "Parser.java"
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
