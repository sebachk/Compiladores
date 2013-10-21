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
   11,    7,   13,    7,    7,    7,    8,    8,   12,   16,
   12,   17,   12,   15,   15,    5,    5,   19,   19,   23,
   19,   24,   19,   18,   18,   14,   14,   25,   28,   25,
   29,   25,   25,   30,   27,   27,   33,   27,   27,   22,
   22,   26,   26,   38,   35,   35,   41,   40,   42,   40,
   43,   40,   44,   40,   45,   40,   46,   40,   48,   36,
   47,   47,   49,   49,   49,   51,   31,   31,   52,   21,
   21,   39,   53,   39,   39,   37,   37,   37,   37,   37,
   37,   10,   54,   10,   50,   50,   55,   34,   32,   32,
   32,   56,   56,   56,   57,   57,   57,   20,
};
final static short yylen[] = {                            2,
    0,    0,    5,    1,    2,    1,    2,    1,    0,    2,
    0,    7,    0,    6,    3,    3,    1,    1,    3,    0,
    4,    0,    4,    1,    2,    2,    1,    3,    3,    0,
    4,    0,    4,    2,    1,    1,    1,    1,    0,    3,
    0,    3,    1,    0,    5,    1,    0,    3,    1,    2,
    2,    1,    1,    0,    7,    1,    0,    4,    0,    6,
    0,    6,    0,    5,    0,    6,    0,    4,    0,    3,
    3,    1,    2,    3,    2,    0,    5,    3,    0,    4,
    1,    1,    0,    4,    2,    3,    3,    3,    3,    3,
    3,    2,    0,    5,    3,    1,    1,    3,    3,    3,
    1,    3,    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    1,    0,    0,    0,  108,    2,    4,    0,    0,    8,
    0,    0,    0,    0,    0,    0,    5,    7,    0,    0,
    0,   44,   47,    0,   69,   43,   10,   18,   17,   38,
   36,   37,    0,   46,   49,   52,   53,   56,   26,    0,
    0,    0,   15,   16,    0,    3,   51,    0,    0,   67,
    0,  106,   57,    0,  107,    0,    0,    0,  104,    0,
    0,    0,    0,    0,   24,    0,   50,    0,    0,   29,
   79,   28,    0,    0,   13,    0,   78,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   59,    0,    0,    0,    0,    0,   25,   34,   19,    0,
    0,    0,   70,   72,   40,    0,   42,    0,   31,   33,
    0,    0,    0,   97,    0,    0,    0,    0,   68,   58,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,  102,  103,    0,   21,   23,   75,    0,   73,
   80,   14,    0,    0,   77,    0,   85,   83,    0,    0,
    0,    0,   64,   45,   74,   71,   12,   93,   95,    0,
    0,   66,   62,   60,    0,   84,   55,   94,
};
final static short yydgoto[] = {                          2,
    6,    3,   16,    7,    8,    9,   10,  118,   11,  112,
   76,   28,  111,   29,   64,  100,  101,   65,   12,   13,
   42,   30,   73,   74,   31,   32,   33,   68,   69,   60,
   34,   56,   61,   35,   36,   37,   57,  149,  119,   38,
   81,  131,  122,   92,  150,   80,  103,   66,  104,  115,
   78,  108,  160,  165,  116,   58,   59,
};
final static short yysindex[] = {                      -255,
    0,    0, -220, -184,    0,    0,    0, -248, -248,    0,
  230, -245, -180,  -32,   -9, -212,    0,    0,   -2,  -28,
  140,    0,    0,  -56,    0,    0,    0,    0,    0,    0,
    0,    0,    6,    0,    0,    0,    0,    0,    0,    8,
   36,   33,    0,    0,   41,    0,    0,   60, -171,    0,
   67,    0,    0, -171,    0,   23,   70,    9,    0,   79,
 -171,  271,  271, -147,    0,  230,    0, -210, -233,    0,
    0,    0, -210, -245,    0, -245,    0, -133,  -11,  243,
  243,   88, -171, -171, -171, -171, -171, -171, -171, -171,
    0, -128, -171, -171, -122,  -11,    0,    0,    0, -125,
 -248, -243,    0,    0,    0,    6,    0, -119,    0,    0,
 -125,  101, -114,    0,  104,  102,  127, -109,    0,    0,
    0, -106,  -11,  -11,  -11,  -11,  -11,  -11,    9,    9,
 -105,  243,    0,    0,  118,    0,    0,    0, -167,    0,
    0,    0, -125,  116,    0, -133,    0,    0, -100,  243,
  243,  243,    0,    0,    0,    0,    0,    0,    0,  230,
  243,    0,    0,    0, -245,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  257,    0,    0,    0,    0,  257,  170,    0,
    0,  190,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -129,    0,    0,    0,    0,    0,    0,    0,
  143, -227,    0,    0, -108,    0,    0,  -94,    0,    0,
  -41,    0,    0,    0,    0,    0,  -97,  -21,    0,    0,
    0,    0, -170, -166,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  285,    0,
    0,  -96,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  299,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  125,    0,  152,    0,    0,
  210,    0,   43,   57,   71,   85,   99,  113,   -1,   19,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,  128,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,   97,    4,    0,   69,   -3,    0,    3,
    0,   -7,    0,  -14,    0,    0,    0,   47,  100,  -71,
   63,    1,    0,    0,    0,  106,    2,    0,    0,    0,
  489,   64,    0,    0,    0,    0,  -31,    0,  -29,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   27,
    0,    0,    0,    0,    0,   25,   24,
};
final static int YYTABLESIZE=628;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        105,
  105,  105,   26,  105,  113,  105,   44,   27,   43,   63,
    1,   48,  138,   51,   52,   39,    4,  105,  105,  101,
  105,  101,   82,  101,    5,  139,   21,   62,   30,   30,
   45,   89,   49,   90,   25,   30,   30,  101,  101,   99,
  101,   99,   32,   99,    4,   19,   20,   63,   63,    5,
   93,  120,   22,   23,   46,   94,   47,   99,   99,  100,
   99,  100,  102,  100,   67,   89,   70,   90,  105,  106,
  140,   14,   15,  109,  106,   40,   41,  100,  100,   71,
  100,   75,   87,   89,   88,   51,   52,  147,  155,   51,
   52,   72,  136,  113,   35,   35,   35,   88,   22,   20,
   77,   89,  153,  142,   17,   18,   48,  156,   97,   98,
   91,   91,   79,  129,  130,   88,  133,  134,   95,   99,
  162,  163,  164,  114,   96,   90,   39,   39,  121,   91,
   41,  167,  132,   39,   39,  157,  135,   41,   41,   86,
   24,  143,  144,   90,  145,  146,  123,  124,  125,  126,
  127,  128,  148,   87,  151,  152,  166,   86,  154,  158,
  161,   11,   76,   63,   61,   96,   48,  168,   92,  137,
  141,   87,  159,  110,  107,    0,    0,    0,    0,   54,
    0,    0,    0,    0,    0,   26,    0,   49,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   19,
   20,   81,    0,   21,    0,    0,   22,   23,    0,    0,
   82,   25,    0,    5,  105,  105,  105,    0,  105,  105,
  105,  105,  105,  105,  105,  105,  105,  105,    9,  105,
  105,  105,  105,   24,  101,  101,  101,    0,  101,  101,
  101,  101,  101,  101,  101,  101,  101,  101,   27,  101,
  101,  101,  101,    0,   99,   99,   99,    0,   99,   99,
   99,   99,   99,   99,   99,   99,   99,   99,   65,   99,
   99,   99,   99,    0,  100,  100,  100,    0,  100,  100,
  100,  100,  100,  100,  100,  100,  100,  100,   26,  100,
  100,  100,  100,   83,   84,   85,   86,    0,   89,   89,
   89,   26,   89,   89,   89,   89,   89,   89,   89,   89,
   89,   89,   88,   88,   88,    9,   88,   88,   88,   88,
   88,   88,   88,   88,   88,   88,   91,   91,   91,   26,
   91,   91,   91,   91,   91,   91,   91,   91,   91,   91,
   90,   90,   90,   98,   90,   90,   90,   90,   90,   90,
   90,   90,   90,   90,   86,   86,   86,   48,   86,   86,
   86,   86,   86,   86,   86,   86,   86,   86,   87,   87,
   87,    0,   87,   87,   87,   87,   87,   87,   87,   87,
   87,   87,   19,  117,    0,    0,   21,    0,    0,   22,
   23,    0,   24,    0,   25,   50,   51,   52,   81,   81,
   53,    0,    0,    0,    0,   81,   81,   82,   82,   82,
    0,   82,   81,    0,   82,   82,   82,   82,   82,   82,
   82,    0,    0,    0,    0,    9,    9,    0,    0,    9,
    0,    0,    9,    9,    0,    9,    6,    9,    0,    0,
    0,    0,    0,    0,    0,   27,   27,    0,    0,   27,
    0,    0,   27,   27,   27,   27,    0,   27,    0,    0,
    0,    0,    0,    0,    0,   65,   65,    0,    0,   65,
   54,    0,   65,   65,    0,   65,    0,   65,    0,    0,
    0,    0,    0,    0,    0,   19,   20,    0,    0,   21,
    0,    0,   22,   23,    0,   24,    0,   25,   19,  117,
    0,    0,   21,    0,    0,   22,   23,    0,   24,   55,
   25,    0,    9,    9,    0,    0,    9,    0,    0,    9,
    9,    0,    9,    0,    9,    0,   19,   20,    0,    0,
   21,    0,    0,   22,   23,    0,    0,   55,   25,    0,
   98,   98,   55,    0,   98,    0,    0,   98,   98,   55,
    0,    0,   98,    0,   48,   48,    0,    0,   48,    0,
    0,   48,   48,    0,    0,    0,   48,    0,    0,    0,
    0,   55,   55,   55,   55,   55,   55,   55,   55,    0,
    0,   55,   55,    0,    0,    0,    0,    0,    0,    0,
   55,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   55,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   59,   45,   76,   47,   14,   11,   41,   24,
  266,   40,  256,  257,  258,   12,  265,   59,   60,   41,
   62,   43,   54,   45,  270,  269,  260,   24,  256,  257,
   40,   43,   61,   45,  268,  263,  264,   59,   60,   41,
   62,   43,  270,   45,  265,  256,  257,   62,   63,  270,
   42,   81,  263,  264,  267,   47,   59,   59,   60,   41,
   62,   43,   66,   45,   59,   43,   59,   45,   68,   68,
  102,  256,  257,   73,   73,  256,  257,   59,   60,   44,
   62,   41,   60,   41,   62,  257,  258,  117,  256,  257,
  258,   59,  100,  165,  265,  266,  267,   41,  265,  266,
   41,   59,  132,  111,    8,    9,   40,  139,   62,   63,
   41,   41,   49,   89,   90,   59,   93,   94,   40,  267,
  150,  151,  152,  257,   61,   41,  256,  257,   41,   59,
  260,  161,  261,  263,  264,  143,  259,  257,  268,   41,
  266,   41,  257,   59,   41,   44,   83,   84,   85,   86,
   87,   88,  262,   41,  261,  261,  160,   59,   41,   44,
  261,  270,  257,  261,  261,   41,   40,  165,   41,  101,
  108,   59,  146,   74,   69,   -1,   -1,   -1,   -1,   40,
   -1,   -1,   -1,   -1,   -1,   59,   -1,   61,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  256,
  257,   59,   -1,  260,   -1,   -1,  263,  264,   -1,   -1,
   59,  268,   -1,  270,  256,  257,  258,   -1,  260,  261,
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
  261,   -1,   -1,   -1,   -1,  263,  264,  256,  257,  258,
   -1,  260,  270,   -1,  263,  264,  265,  266,  267,  268,
  269,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,   -1,  266,  267,  268,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,  265,  266,   -1,  268,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,  260,
  261,   -1,  263,  264,   -1,  266,   -1,  268,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,   -1,  266,   -1,  268,  256,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,   -1,  266,   21,
  268,   -1,  256,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  256,  257,   -1,   -1,
  260,   -1,   -1,  263,  264,   -1,   -1,   49,  268,   -1,
  256,  257,   54,   -1,  260,   -1,   -1,  263,  264,   61,
   -1,   -1,  268,   -1,  256,  257,   -1,   -1,  260,   -1,
   -1,  263,  264,   -1,   -1,   -1,  268,   -1,   -1,   -1,
   -1,   83,   84,   85,   86,   87,   88,   89,   90,   -1,
   -1,   93,   94,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  102,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  139,
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
"sentencia_declar_funcion : FUNCTION ID '(' $$4 parametros ')' bloque_funcion",
"$$5 :",
"sentencia_declar_funcion : FUNCTION ID '(' ')' $$5 bloque_funcion",
"sentencia_declar_funcion : FUNCTION error ')'",
"sentencia_declar_funcion : FUNCTION error bloque_funcion",
"bloque_sent : sentencia_ejec",
"bloque_sent : bloque_funcion",
"bloque_funcion : BEGIN lista_sentencias END",
"$$6 :",
"bloque_funcion : BEGIN lista_sentencias $$6 bloque_funcion",
"$$7 :",
"bloque_funcion : BEGIN lista_sentencias $$7 sentencia_declar_funcion",
"lista_sentencias : lista_sent_ejec",
"lista_sentencias : lista_sent_declar lista_sent_ejec",
"lista_sent_declar : sentencia_declarativa_tipo lista_sent_declar",
"lista_sent_declar : sentencia_declarativa_tipo",
"sentencia_declarativa_tipo : tipo lista_var ';'",
"sentencia_declarativa_tipo : tipo error ';'",
"$$8 :",
"sentencia_declarativa_tipo : tipo lista_var $$8 sent_correcta",
"$$9 :",
"sentencia_declarativa_tipo : tipo lista_var $$9 sentencia_declarativa_tipo",
"lista_sent_ejec : sentencia_ejec lista_sent_ejec",
"lista_sent_ejec : sentencia_ejec",
"sentencia_ejec : sentencia_simple",
"sentencia_ejec : sentencia_comp",
"sentencia_simple : sent_correcta",
"$$10 :",
"sentencia_simple : sent_abierta $$10 sent_correcta",
"$$11 :",
"sentencia_simple : sent_abierta $$11 sentencia_comp",
"sentencia_simple : ';'",
"$$12 :",
"sent_abierta : PRINT $$12 '(' cadena ')'",
"sent_abierta : llamada_funcion",
"$$13 :",
"sent_abierta : RETURN $$13 expresion",
"sent_abierta : asignacion",
"sent_correcta : sent_abierta ';'",
"sent_correcta : error ';'",
"sentencia_comp : sentencia_if",
"sentencia_comp : sentencia_loop",
"$$14 :",
"sentencia_if : IF '(' cond ')' $$14 THEN bloque_IF",
"sentencia_if : sentencia_if_error",
"$$15 :",
"sentencia_if_error : IF THEN $$15 bloque_IF",
"$$16 :",
"sentencia_if_error : IF cond ')' $$16 THEN bloque_IF",
"$$17 :",
"sentencia_if_error : IF '(' cond $$17 THEN bloque_IF",
"$$18 :",
"sentencia_if_error : IF cond $$18 THEN bloque_IF",
"$$19 :",
"sentencia_if_error : IF '(' cond ')' $$19 bloque_IF",
"$$20 :",
"sentencia_if_error : IF error $$20 bloque_IF",
"$$21 :",
"sentencia_loop : LOOP $$21 bloque_loop",
"bloque_loop : bloque_sent UNTIL cond",
"bloque_loop : loop_error",
"loop_error : bloque_sent cond",
"loop_error : bloque_sent UNTIL error",
"loop_error : bloque_sent error",
"$$22 :",
"llamada_funcion : ID '(' $$22 lista_parametros ')'",
"llamada_funcion : ID '(' ')'",
"$$23 :",
"lista_var : ID ',' $$23 lista_var",
"lista_var : ID",
"bloque_IF : bloque_sent",
"$$24 :",
"bloque_IF : bloque_sent ELSE $$24 bloque_sent",
"bloque_IF : ID bloque_IF",
"cond : expresion '<' expresion",
"cond : expresion '>' expresion",
"cond : expresion MEN_IG expresion",
"cond : expresion MAY_IG expresion",
"cond : expresion IGUAL expresion",
"cond : expresion DIST expresion",
"parametros : tipo ID",
"$$25 :",
"parametros : tipo ID ',' $$25 parametros",
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

//#line 169 "Gramatica.nueva.y"


AnalizadorLexico Al = new ALexico.AnalizadorLexico(new File("Docs/codigo.txt"));
PolacaInversa PI = new PolacaInversa();
int contador=1;

int yylex(){
	ALexico.Token t; 
	int val= this.YYERRCODE;
	while(val==this.YYERRCODE){
		t = Al.GetToken();
		if(t!=null){
			ALexico.Estructuras.addLog("Token"+ contador++ +" = " + ALexico.Estructuras.getStringToken(t.getIdentif_tt()));
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
  PI.ImprimirPolaca();
}




//#line 587 "Parser.java"
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
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(1).sval)) val_peek(1).ival=Estructuras.addTupla(val_peek(1).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.FUNCTION);ManejadorAmbitos.NewAmbito(val_peek(1).sval); PI.beginFunction(val_peek(1).sval);}
break;
case 12:
//#line 28 "Gramatica.nueva.y"
{/*PI.endFunction();*/ManejadorAmbitos.EndAmbito(); }
break;
case 13:
//#line 29 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(2).sval))  val_peek(2).ival=Estructuras.addTupla(val_peek(2).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.FUNCTION);  ManejadorAmbitos.NewAmbito(val_peek(2).sval);}
break;
case 14:
//#line 29 "Gramatica.nueva.y"
{/*PI.endFunction();*/ManejadorAmbitos.EndAmbito();}
break;
case 20:
//#line 39 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 22:
//#line 40 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 28:
//#line 51 "Gramatica.nueva.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");}
break;
case 30:
//#line 53 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 32:
//#line 54 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 39:
//#line 68 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 41:
//#line 69 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 44:
//#line 73 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'print'");}
break;
case 45:
//#line 73 "Gramatica.nueva.y"
{PI.callPrint(val_peek(1).sval);}
break;
case 46:
//#line 74 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
break;
case 47:
//#line 75 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'return'");}
break;
case 48:
//#line 75 "Gramatica.nueva.y"
{PI.endFunction();}
break;
case 54:
//#line 87 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'if'"); PI.FinCondicion();ManejadorAmbitos.NewAmbito();}
break;
case 55:
//#line 87 "Gramatica.nueva.y"
{ManejadorAmbitos.EndAmbito();}
break;
case 57:
//#line 91 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la condicion del IF");}
break;
case 59:
//#line 92 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta abrir parentesis");}
break;
case 61:
//#line 93 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta cerrar parentesis");}
break;
case 63:
//#line 94 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta  parentesis");}
break;
case 65:
//#line 95 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la palabra THEN");}
break;
case 67:
//#line 96 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": errores múltiples en IF");}
break;
case 69:
//#line 98 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de iteracion");PI.InicLoop();ManejadorAmbitos.NewAmbito();}
break;
case 70:
//#line 98 "Gramatica.nueva.y"
{ManejadorAmbitos.EndAmbito();}
break;
case 71:
//#line 101 "Gramatica.nueva.y"
{PI.FinLoop();}
break;
case 73:
//#line 105 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la palabra UNTIL");}
break;
case 74:
//#line 106 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la condicion luego de UNTIL");}
break;
case 75:
//#line 107 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Se olvido del UNTIL?");}
break;
case 76:
//#line 110 "Gramatica.nueva.y"
{ManejadorAmbitos.isDeclarada(val_peek(1).sval); PI.callFunction(val_peek(1).sval);}
break;
case 78:
//#line 111 "Gramatica.nueva.y"
{ManejadorAmbitos.isDeclarada(val_peek(2).sval);PI.callFunction(val_peek(2).sval);}
break;
case 79:
//#line 115 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(1).sval))  val_peek(1).ival=Estructuras.addTupla(val_peek(1).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);}
break;
case 81:
//#line 116 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(0).sval))  val_peek(0).ival=Estructuras.addTupla(val_peek(0).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);}
break;
case 82:
//#line 120 "Gramatica.nueva.y"
{PI.FinIf();}
break;
case 83:
//#line 121 "Gramatica.nueva.y"
{PI.FinThenElse();}
break;
case 84:
//#line 121 "Gramatica.nueva.y"
{PI.FinIf();}
break;
case 85:
//#line 122 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Then mal escrito");}
break;
case 86:
//#line 126 "Gramatica.nueva.y"
{PI.addPolaco("<");}
break;
case 87:
//#line 127 "Gramatica.nueva.y"
{PI.addPolaco(">");}
break;
case 88:
//#line 128 "Gramatica.nueva.y"
{PI.addPolaco("<=");}
break;
case 89:
//#line 129 "Gramatica.nueva.y"
{PI.addPolaco(">=");}
break;
case 90:
//#line 130 "Gramatica.nueva.y"
{PI.addPolaco("==");}
break;
case 91:
//#line 131 "Gramatica.nueva.y"
{PI.addPolaco("!=");}
break;
case 92:
//#line 134 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(0).sval)) val_peek(0).ival=Estructuras.addTupla(val_peek(0).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);}
break;
case 93:
//#line 135 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(1).sval)) val_peek(1).ival=Estructuras.addTupla(val_peek(1).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);}
break;
case 97:
//#line 143 "Gramatica.nueva.y"
{ManejadorAmbitos.isDeclarada(val_peek(0).sval);}
break;
case 98:
//#line 148 "Gramatica.nueva.y"
{int pos= ManejadorAmbitos.isDeclarada(val_peek(2).sval); Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia de asignacion"); PI.addPolaco("TS("+pos+")"); PI.addPolaco("=");}
break;
case 99:
//#line 151 "Gramatica.nueva.y"
{PI.addPolaco("+");}
break;
case 100:
//#line 152 "Gramatica.nueva.y"
{PI.addPolaco("-");}
break;
case 102:
//#line 156 "Gramatica.nueva.y"
{PI.addPolaco("*");}
break;
case 103:
//#line 157 "Gramatica.nueva.y"
{PI.addPolaco("/");}
break;
case 105:
//#line 161 "Gramatica.nueva.y"
{int pos = ManejadorAmbitos.isDeclarada(val_peek(0).sval); PI.addPolaco("TS("+pos+")");}
break;
case 106:
//#line 162 "Gramatica.nueva.y"
{PI.addPolaco("TS("+val_peek(0).ival+")");}
break;
//#line 972 "Parser.java"
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
