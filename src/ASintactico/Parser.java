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
    0,    1,    1,    2,    2,    3,    3,    3,    3,    4,
    4,    6,    9,    6,   10,    6,    8,    8,   12,   12,
   13,   13,   17,   13,   18,   13,   11,   11,    7,    7,
   19,   22,   19,   23,   19,   19,   24,   21,   21,   27,
   21,   21,   16,   16,   20,   20,   32,   29,   29,   35,
   34,   36,   34,   37,   34,   38,   34,   39,   34,   40,
   34,   42,   30,   41,   41,   43,   43,   43,   25,   25,
   15,   15,   33,   33,   33,   31,   31,   31,   31,   31,
   31,    5,    5,   44,   44,   45,   46,   28,   26,   26,
   26,   47,   47,   47,   48,   48,   48,   14,
};
final static short yylen[] = {                            2,
    3,    1,    2,    1,    1,    6,    5,    3,    3,    1,
    1,    3,    0,    4,    0,    4,    1,    2,    2,    1,
    3,    3,    0,    4,    0,    4,    2,    1,    1,    1,
    1,    0,    3,    0,    3,    1,    0,    5,    1,    0,
    3,    1,    2,    2,    1,    1,    0,    7,    1,    0,
    4,    0,    6,    0,    6,    0,    5,    0,    6,    0,
    4,    0,    3,    3,    1,    2,    3,    2,    4,    3,
    3,    1,    1,    3,    2,    3,    3,    3,    3,    3,
    3,    2,    4,    3,    1,    1,    0,    4,    3,    3,
    1,    3,    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   37,   40,    0,    0,   62,
   36,    0,    0,    4,    5,   11,   10,   31,   29,   30,
    0,   39,   42,   45,   46,   49,   44,    0,    0,   60,
    0,   96,   50,    0,   97,    0,    0,    0,   94,    0,
    0,    0,    0,   98,    0,    0,   17,    0,    0,    0,
    0,    1,    3,   43,    0,    0,   70,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   52,    0,    0,    0,    0,    0,    8,    9,
    0,   27,   12,    0,    0,   18,   19,    0,    0,    0,
    0,   63,   65,   33,    0,   35,   69,    0,   88,    0,
    0,   61,   51,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   92,   93,    0,    0,    0,
    0,   14,   16,   22,    0,   21,    0,    0,   68,    0,
   66,   84,   75,    0,    0,    0,    0,    0,   57,   38,
    7,    0,    0,   71,   24,   26,   67,   64,   74,    0,
   59,   55,   53,    6,    0,   48,   83,
};
final static short yydgoto[] = {                          2,
   12,   13,   14,  101,  120,   16,   17,   46,   84,   85,
   47,   48,   49,   50,   90,   18,  127,  128,   19,   20,
   21,   55,   56,   40,   35,   36,   41,   23,   24,   25,
   37,  135,  102,   26,   63,  114,  105,   74,  136,   62,
   92,   51,   93,   59,   60,   99,   38,   39,
};
final static short yysindex[] = {                      -254,
  190,    0,   -7,  -29,  140,    0,    0, -157,  143,    0,
    0, -211,  190,    0,    0,    0,    0,    0,    0,    0,
   11,    0,    0,    0,    0,    0,    0,  128, -134,    0,
   40,    0,    0, -134,    0,   23,   54,  -17,    0,   68,
 -134,  -28,   94,    0,  257, -150,    0,  257, -145, -120,
  230,    0,    0,    0, -230, -223,    0,   10,  101,  109,
   10,  243,  243,  104, -134, -134, -134, -134, -134, -134,
 -134, -134,    0, -106, -134, -134, -102,   10,    0,    0,
  -36,    0,    0, -107, -105,    0,    0,  102,  118,  105,
 -136,    0,    0,    0,   11,    0,    0, -134,    0,  127,
  -99,    0,    0,    0,  -96,   10,   10,   10,   10,   10,
   10,  -17,  -17,  -95,  243,    0,    0,  129, -107,  132,
  -89,    0,    0,    0,  -83,    0, -230, -145,    0, -164,
    0,    0,    0,  230,  -86,  243,  243,  243,    0,    0,
    0, -107,  133,    0,    0,    0,    0,    0,    0,  243,
    0,    0,    0,    0, -145,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  -91,    0,    0,    0,    0,    0,    0,    0,
 -125,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -41,    0,    0,    0,    0,    0,  -82,  -21,    0,    0,
    0,    0,    0,    0, -152, -119,    0,    0,  271,    0,
    0,    0,    0,    0,    0,    0,    0,  -13,    0,  137,
  285,    0,    0,  -80,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  299,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -52, -167,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  170,    0,    0,  210,    0,   43,   57,   71,   85,   99,
  113,   -1,   19,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  141,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  171,    0,  100,   16,   32,  -33,    1,    0,    0,    0,
    2,  142,   61,  -73,   70,  -40,    0,    0,    0,  144,
  -39,    0,    0,    0,   56,    7,    0,    0,    0,    0,
  -20,    0,   -9,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   98,    0,    0,   77,   76,
};
final static int YYTABLESIZE=567;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         95,
   95,   95,   95,   95,  119,   95,   72,  121,   80,   45,
   28,    1,   79,   64,   94,   95,   15,   95,   95,   91,
   95,   91,   91,   91,   75,    3,    4,   86,   15,   76,
   86,   29,    6,    7,   58,   61,    5,   91,   91,   89,
   91,   89,   89,   89,   10,   45,   82,   78,   45,   86,
  122,   27,   71,  103,   72,   52,   22,   89,   89,   90,
   89,   90,   90,   90,   22,   71,   91,   72,   22,   54,
  131,  106,  107,  108,  109,  110,  111,   90,   90,   28,
   90,  121,   69,   79,   70,  141,  145,   95,   23,   23,
  133,  147,   31,   32,   73,   23,   23,   78,   42,   43,
   22,   79,   25,   22,   58,  139,   22,   77,  154,  148,
   22,   81,   28,   28,   28,   78,   83,   22,   22,  129,
   31,   32,   31,   32,   44,   80,  151,  152,  153,   81,
   32,   32,  130,   81,   34,   88,   89,   32,   32,   76,
  156,   97,   34,   80,  104,   15,   13,  112,  113,  149,
  116,  117,   98,   77,  115,   22,  118,   76,    9,    8,
  124,  125,  134,  126,  137,  138,   28,  143,   57,  140,
   22,   77,  142,   89,  150,    2,  155,   85,   56,   34,
   54,   82,   22,   53,  123,   11,  157,   29,  146,   22,
   87,   22,   22,   22,  144,  132,    0,    0,    0,   96,
    0,   11,    0,   72,   72,   22,    0,    0,    0,    0,
   72,   72,    0,    0,   95,   95,   95,   72,   95,   95,
   95,   95,   95,   95,   95,   95,   95,   95,   73,   95,
   95,   95,   95,   44,   91,   91,   91,    9,   91,   91,
   91,   91,   91,   91,   91,   91,   91,   91,   11,   91,
   91,   91,   91,    0,   89,   89,   89,    0,   89,   89,
   89,   89,   89,   89,   89,   89,   89,   89,   58,   89,
   89,   89,   89,    0,   90,   90,   90,    0,   90,   90,
   90,   90,   90,   90,   90,   90,   90,   90,   11,   90,
   90,   90,   90,   65,   66,   67,   68,    0,   79,   79,
   79,   11,   79,   79,   79,   79,   79,   79,   79,   79,
   79,   79,   78,   78,   78,   11,   78,   78,   78,   78,
   78,   78,   78,   78,   78,   78,   81,   81,   81,   20,
   81,   81,   81,   81,   81,   81,   81,   81,   81,   81,
   80,   80,   80,   87,   80,   80,   80,   80,   80,   80,
   80,   80,   80,   80,   76,   76,   76,   41,   76,   76,
   76,   76,   76,   76,   76,   76,   76,   76,   77,   77,
   77,    0,   77,   77,   77,   77,   77,   77,   77,   77,
   77,   77,    3,  100,   31,   32,    5,    0,    0,    6,
    7,    0,    9,    0,   10,   30,   31,   32,    3,    4,
   33,    0,    5,    0,    0,    6,    7,    0,    0,    0,
   10,    0,   44,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   73,   73,   73,    0,   73,
    0,    0,   73,   73,   73,   73,   73,   73,   73,    0,
    0,    0,    0,    0,    0,    3,    4,    0,    0,    5,
    0,    0,    6,    7,    8,    9,    0,   10,    0,    0,
    0,    0,    0,    0,    0,   58,   58,    0,    0,   58,
   47,    0,   58,   58,    0,   58,    0,   58,    0,    0,
    0,    0,    0,    0,    0,    3,    4,    0,    0,    5,
    0,    0,    6,    7,    0,    9,    0,   10,    3,  100,
    0,    0,    5,    0,    0,    6,    7,    0,    9,    0,
   10,    0,    3,    4,    0,    0,    5,    0,    0,    6,
    7,    0,    0,    0,   10,    0,   20,   20,    0,    0,
   20,    0,    0,   20,   20,    0,    0,    0,   20,    0,
   87,   87,    0,    0,   87,    0,    0,   87,   87,    0,
    0,    0,   87,    0,   41,   41,    0,    0,   41,    0,
    0,   41,   41,    0,    0,    0,   41,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   59,   81,   42,    9,
   40,  266,   41,   34,   55,   55,    1,   59,   60,   41,
   62,   43,   44,   45,   42,  256,  257,   41,   13,   47,
   44,   61,  263,  264,   28,   29,  260,   59,   60,   41,
   62,   43,   44,   45,  268,   45,   45,   41,   48,   48,
   84,   59,   43,   63,   45,  267,    1,   59,   60,   41,
   62,   43,   44,   45,    9,   43,   51,   45,   13,   59,
   91,   65,   66,   67,   68,   69,   70,   59,   60,   40,
   62,  155,   60,   41,   62,  119,  127,  127,  256,  257,
  100,  256,  257,  258,   41,  263,  264,   41,  256,  257,
   45,   59,  270,   48,   98,  115,   51,   40,  142,  130,
   55,   41,  265,  266,  267,   59,  267,   62,   63,  256,
  257,  258,  257,  258,  270,   41,  136,  137,  138,   59,
  256,  257,  269,   40,  260,  256,  257,  263,  264,   41,
  150,   41,  268,   59,   41,  265,  266,   71,   72,  134,
   75,   76,   44,   41,  261,  100,  259,   59,  266,  265,
   59,   44,  262,   59,  261,  261,   40,  257,   41,   41,
  115,   59,   41,  257,  261,  267,   44,   41,  261,   40,
  261,   41,  127,   13,   85,   59,  155,   61,  128,  134,
   49,  136,  137,  138,  125,   98,   -1,   -1,   -1,   56,
   -1,   59,   -1,  256,  257,  150,   -1,   -1,   -1,   -1,
  263,  264,   -1,   -1,  256,  257,  258,  270,  260,  261,
  262,  263,  264,  265,  266,  267,  268,  269,   59,  271,
  272,  273,  274,  270,  256,  257,  258,  266,  260,  261,
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
  268,  269,  256,  257,  257,  258,  260,   -1,   -1,  263,
  264,   -1,  266,   -1,  268,  256,  257,  258,  256,  257,
  261,   -1,  260,   -1,   -1,  263,  264,   -1,   -1,   -1,
  268,   -1,  270,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,  258,   -1,  260,
   -1,   -1,  263,  264,  265,  266,  267,  268,  269,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,  265,  266,   -1,  268,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,  260,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,   -1,  266,   -1,  268,  256,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  256,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,   -1,   -1,   -1,  268,   -1,  256,  257,   -1,   -1,
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
"sentencia_simple : ';'",
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
"cond : expresion '<' expresion",
"cond : expresion '>' expresion",
"cond : expresion MEN_IG expresion",
"cond : expresion MAY_IG expresion",
"cond : expresion IGUAL expresion",
"cond : expresion DIST expresion",
"parametros : tipo ID",
"parametros : tipo ID ',' parametros",
"lista_parametros : parametro ',' lista_parametros",
"lista_parametros : parametro",
"parametro : expresion",
"$$17 :",
"asignacion : ID '=' expresion $$17",
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

//#line 164 "Gramatica.nueva.y"

<<<<<<< HEAD
AnalizadorLexico Al = new ALexico.AnalizadorLexico(new File("Docs/errores.txt"));
int contador=1;
=======
AnalizadorLexico Al = new ALexico.AnalizadorLexico(new File("Docs/codigo.txt"));
PolacaInversa PI = new PolacaInversa();

>>>>>>> d2ebec3b395378a268d34b08a135d8cba793d51f


int yylex(){
	ALexico.Token t; 
	int val= this.YYERRCODE;
	while(val==this.YYERRCODE){
		t = Al.GetToken();
		if(t!=null){
			ALexico.Estructuras.addLog("Token "+ contador++ +" = " + ALexico.Estructuras.getStringToken(t.getIdentif_tt()));
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
	String err=e+" en l�nea "+Al.LineasContadas+": ";
	return err;
}
void yyerror(String e){
		
	String error="";
	error+=yyBaseError(e);	
	
	//System.err.println ("Token le�do : "+);
	String leido= yyname[yychar];
	
	String espera= "Token que se esperaba: ";
	String esperas ="Tokens que se esperaban: ";
    String  nombresTokens = "" ;

    int yyn ;
	int varios=0;
    // a�adir en 'nombresTokens' los tokens que permitirian desplazar
     for( yychar = 0 ; yychar < YYMAXTOKEN ; yychar++ )
     {  yyn = yysindex[yystate] ;  
        if ((yyn != 0) && (yyn += yychar) >= 0 &&
           yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
        	{
        		varios++;
        	   	nombresTokens += yyname[yychar] + " ";
        	}
     }

     // a�adir tokens que permitirian reducir
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
  PI.ImprimirPolaca();
}
<<<<<<< HEAD
//#line 510 "Parser.java"
=======
//#line 555 "Parser.java"
>>>>>>> d2ebec3b395378a268d34b08a135d8cba793d51f
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
//#line 19 "Gramatica.nueva.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de funcion");}
break;
case 13:
//#line 34 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 15:
//#line 35 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 21:
//#line 46 "Gramatica.nueva.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");}
break;
case 23:
//#line 48 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 25:
//#line 49 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 32:
//#line 63 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 34:
//#line 64 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 37:
//#line 68 "Gramatica.nueva.y"
{Estructuras.addLog("L�nea "+Al.LineasContadas+": Sentencia 'print'");}
break;
case 39:
//#line 69 "Gramatica.nueva.y"
{Estructuras.addLog("L�nea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
break;
case 40:
//#line 70 "Gramatica.nueva.y"
{Estructuras.addLog("L�nea "+Al.LineasContadas+": Sentencia 'return'");}
break;
case 47:
//#line 82 "Gramatica.nueva.y"
{Estructuras.addLog("L�nea "+Al.LineasContadas+": Sentencia 'if'");}
break;
case 50:
//#line 86 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+Al.LineasContadas+": Falta la condicion del IF");}
break;
case 52:
//#line 87 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+Al.LineasContadas+": Falta abrir parentesis");}
break;
case 54:
//#line 88 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+Al.LineasContadas+": Falta cerrar parentesis");}
break;
case 56:
//#line 89 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+Al.LineasContadas+": Falta  parentesis");}
break;
case 58:
//#line 90 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+Al.LineasContadas+": Falta la palabra THEN");}
break;
case 60:
//#line 91 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+Al.LineasContadas+": errores m�ltiples en IF");}
break;
case 62:
//#line 93 "Gramatica.nueva.y"
{Estructuras.addLog("L�nea "+Al.LineasContadas+": Sentencia de iteracion");}
break;
case 66:
//#line 100 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+Al.LineasContadas+": falta la palabra UNTIL");}
break;
case 67:
//#line 101 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+Al.LineasContadas+": falta la condicion luego de UNTIL");}
break;
case 68:
//#line 102 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en l�nea "+Al.LineasContadas+": Se olvido del UNTIL?");}
break;
case 76:
//#line 121 "Gramatica.nueva.y"
{PI.addPolaco("<");}
break;
case 77:
//#line 122 "Gramatica.nueva.y"
{PI.addPolaco(">");}
break;
case 78:
//#line 123 "Gramatica.nueva.y"
{PI.addPolaco("<=");}
break;
case 79:
//#line 124 "Gramatica.nueva.y"
{PI.addPolaco(">=");}
break;
case 80:
//#line 125 "Gramatica.nueva.y"
{PI.addPolaco("==");}
break;
case 81:
//#line 126 "Gramatica.nueva.y"
{PI.addPolaco("!=");}
break;
case 87:
//#line 143 "Gramatica.nueva.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia de asignacion");}
break;
<<<<<<< HEAD
//#line 751 "Parser.java"
=======
case 88:
//#line 143 "Gramatica.nueva.y"
{PI.addPolaco(val_peek(3).sval); PI.addPolaco("=");}
break;
case 89:
//#line 146 "Gramatica.nueva.y"
{PI.addPolaco("+");}
break;
case 90:
//#line 147 "Gramatica.nueva.y"
{PI.addPolaco("-");}
break;
case 92:
//#line 151 "Gramatica.nueva.y"
{PI.addPolaco("*");}
break;
case 93:
//#line 152 "Gramatica.nueva.y"
{PI.addPolaco("/");}
break;
case 95:
//#line 156 "Gramatica.nueva.y"
{PI.addPolaco(val_peek(0).sval);}
break;
case 96:
//#line 157 "Gramatica.nueva.y"
{PI.addPolaco(val_peek(0).sval);}
break;
//#line 848 "Parser.java"
>>>>>>> d2ebec3b395378a268d34b08a135d8cba793d51f
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
