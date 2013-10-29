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
   19,   24,   19,   25,   19,   18,   18,   14,   14,   26,
   29,   26,   30,   26,   26,   31,   28,   28,   34,   28,
   28,   22,   22,   27,   27,   39,   36,   36,   42,   41,
   43,   41,   44,   41,   45,   41,   46,   41,   47,   41,
   49,   37,   48,   48,   50,   50,   50,   52,   32,   32,
   53,   21,   21,   40,   54,   40,   40,   38,   38,   38,
   38,   38,   38,   10,   55,   10,   51,   51,   56,   35,
   33,   33,   33,   57,   57,   57,   58,   58,   58,   20,
};
final static short yylen[] = {                            2,
    0,    0,    5,    1,    2,    1,    2,    1,    0,    2,
    0,    7,    0,    6,    3,    3,    1,    1,    3,    0,
    4,    0,    4,    1,    2,    2,    1,    3,    3,    0,
    4,    0,    4,    0,    4,    2,    1,    1,    1,    1,
    0,    3,    0,    3,    1,    0,    5,    1,    0,    3,
    1,    2,    2,    1,    1,    0,    7,    1,    0,    4,
    0,    6,    0,    6,    0,    5,    0,    6,    0,    4,
    0,    3,    3,    1,    2,    3,    2,    0,    5,    3,
    0,    4,    1,    1,    0,    4,    2,    3,    3,    3,
    3,    3,    3,    2,    0,    5,    3,    1,    1,    3,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    1,    0,    0,    0,  110,    2,    4,    0,    0,    8,
    0,    0,    0,    0,    0,    0,    5,    7,    0,    0,
    0,   46,   49,    0,   71,   45,   10,   18,   17,   40,
   38,   39,    0,   48,   51,   54,   55,   58,   26,    0,
    0,    0,   15,   16,    0,    3,   53,    0,    0,   69,
    0,  108,   59,    0,  109,    0,    0,    0,  106,    0,
    0,    0,    0,    0,   24,    0,   52,    0,    0,   29,
   81,   28,    0,    0,    0,   13,    0,   80,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   61,    0,    0,    0,    0,    0,   25,   36,   19,
    0,    0,    0,   72,   74,   42,    0,   44,    0,   31,
   33,   35,    0,    0,    0,   99,    0,    0,    0,    0,
   70,   60,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,  104,  105,    0,   21,   23,   77,
    0,   75,   82,   14,    0,    0,   79,    0,   87,   85,
    0,    0,    0,    0,   66,   47,   76,   73,   12,   95,
   97,    0,    0,   68,   64,   62,    0,   86,   57,   96,
};
final static short yydgoto[] = {                          2,
    6,    3,   16,    7,    8,    9,   10,  120,   11,  114,
   77,   28,  113,   29,   64,  101,  102,   65,   12,   13,
   42,   30,   73,   74,   75,   31,   32,   33,   68,   69,
   60,   34,   56,   61,   35,   36,   37,   57,  151,  121,
   38,   82,  133,  124,   93,  152,   81,  104,   66,  105,
  117,   79,  109,  162,  167,  118,   58,   59,
};
final static short yysindex[] = {                      -254,
    0,    0, -198, -226,    0,    0,    0, -222, -222,    0,
  230, -225, -143,  -32,    7, -213,    0,    0,   -2,  -29,
  140,    0,    0,  -56,    0,    0,    0,    0,    0,    0,
    0,    0,   12,    0,    0,    0,    0,    0,    0,   15,
   38,   40,    0,    0,   74,    0,    0,   84, -139,    0,
   89,    0,    0, -139,    0,   23,   90,   33,    0,   95,
 -139,  271,  271, -128,    0,  230,    0, -187, -245,    0,
    0,    0, -187, -225, -222,    0, -225,    0, -114,   66,
  243,  243,  104, -139, -139, -139, -139, -139, -139, -139,
 -139,    0, -113, -139, -139, -110,   66,    0,    0,    0,
 -116, -222, -223,    0,    0,    0,   12,    0, -106,    0,
    0,    0, -116,  111, -104,    0,  115,  116,  127, -107,
    0,    0,    0, -100,   66,   66,   66,   66,   66,   66,
   33,   33,  -99,  243,    0,    0,  122,    0,    0,    0,
 -229,    0,    0,    0, -116,  120,    0, -114,    0,    0,
  -96,  243,  243,  243,    0,    0,    0,    0,    0,    0,
    0,  230,  243,    0,    0,    0, -225,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  257,    0,    0,    0,    0,  257,  170,    0,
    0,  190,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -136,    0,    0,    0,    0,    0,    0,    0,
  143, -170,    0,    0, -102,    0,    0,  -91,    0,    0,
  -41,    0,    0,    0,    0,    0,  -92,  -21,    0,    0,
    0,    0, -176, -132,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  285,
    0,    0,  -90,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  299,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  129,    0,  154,
    0,    0,  210,    0,   43,   57,   71,   85,   99,  113,
   -1,   19,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  132,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,  114,    2,    0,  -65,   -3,    0,  -10,
    0,   -9,    0,   -7,    0,    0,    0,   75,  100,  -70,
   67,   28,    0,    0,    0,    0,  106,   37,    0,    0,
    0,  489,  -36,    0,    0,    0,    0,  -38,    0,  -46,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   29,    0,    0,    0,    0,    0,   51,   52,
};
final static int YYTABLESIZE=630;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        107,
  107,  107,   26,  107,   44,  107,  115,   27,   43,  112,
   48,    1,   80,   39,   21,   83,   63,  107,  107,  103,
  107,  103,   25,  103,   97,   62,  157,   51,   52,   14,
   15,   49,  140,   51,   52,  122,  139,  103,  103,  101,
  103,  101,    4,  101,    5,  141,   45,  125,  126,  127,
  128,  129,  130,   46,   63,   63,   47,  101,  101,  102,
  101,  102,  103,  102,  142,   90,    4,   91,   19,   20,
   67,    5,  149,   70,   94,   22,   23,  102,  102,   95,
  102,   71,   88,   91,   89,   30,   30,  155,   37,   37,
   37,  138,   30,   30,   34,  106,  115,   90,   72,   32,
  110,   91,  158,  144,  107,  164,  165,  166,   90,  107,
   91,   93,   40,   41,   76,   90,  169,   51,   52,   41,
   41,   17,   18,   43,   78,   92,   41,   41,   48,   93,
   92,   43,   22,   20,   96,  159,   98,   99,  100,   88,
  131,  132,  116,   92,  123,  135,  136,  134,  137,   24,
   41,  145,  146,   89,  150,  147,  170,   88,  168,  148,
  153,  154,  156,  160,  163,   78,   48,   11,   65,   98,
   63,   89,   94,  111,  108,  143,  161,    0,    0,   54,
    0,    0,    0,    0,    0,   26,    0,   49,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   19,
   20,   83,    0,   21,    0,    0,   22,   23,    0,    0,
    0,   25,   84,    5,  107,  107,  107,    0,  107,  107,
  107,  107,  107,  107,  107,  107,  107,  107,    9,  107,
  107,  107,  107,   24,  103,  103,  103,    0,  103,  103,
  103,  103,  103,  103,  103,  103,  103,  103,   27,  103,
  103,  103,  103,    0,  101,  101,  101,    0,  101,  101,
  101,  101,  101,  101,  101,  101,  101,  101,   67,  101,
  101,  101,  101,    0,  102,  102,  102,    0,  102,  102,
  102,  102,  102,  102,  102,  102,  102,  102,   26,  102,
  102,  102,  102,   84,   85,   86,   87,    0,   91,   91,
   91,   26,   91,   91,   91,   91,   91,   91,   91,   91,
   91,   91,   90,   90,   90,    9,   90,   90,   90,   90,
   90,   90,   90,   90,   90,   90,   93,   93,   93,   26,
   93,   93,   93,   93,   93,   93,   93,   93,   93,   93,
   92,   92,   92,  100,   92,   92,   92,   92,   92,   92,
   92,   92,   92,   92,   88,   88,   88,   50,   88,   88,
   88,   88,   88,   88,   88,   88,   88,   88,   89,   89,
   89,    0,   89,   89,   89,   89,   89,   89,   89,   89,
   89,   89,   19,  119,    0,    0,   21,    0,    0,   22,
   23,    0,   24,    0,   25,   50,   51,   52,   83,   83,
   53,    0,    0,    0,    0,   83,   83,   83,    0,   84,
   84,   84,   83,   84,    0,    0,   84,   84,   84,   84,
   84,   84,   84,    0,    0,    9,    9,    0,    0,    9,
    0,    0,    9,    9,    0,    9,    6,    9,    0,    0,
    0,    0,    0,    0,    0,   27,   27,    0,    0,   27,
    0,    0,   27,   27,   27,   27,    0,   27,    0,    0,
    0,    0,    0,    0,    0,   67,   67,    0,    0,   67,
   56,    0,   67,   67,    0,   67,    0,   67,    0,    0,
    0,    0,    0,    0,    0,   19,   20,    0,    0,   21,
    0,    0,   22,   23,    0,   24,    0,   25,   19,  119,
    0,    0,   21,    0,    0,   22,   23,    0,   24,   55,
   25,    0,    9,    9,    0,    0,    9,    0,    0,    9,
    9,    0,    9,    0,    9,    0,   19,   20,    0,    0,
   21,    0,    0,   22,   23,    0,    0,   55,   25,    0,
  100,  100,   55,    0,  100,    0,    0,  100,  100,   55,
    0,    0,  100,    0,   50,   50,    0,    0,   50,    0,
    0,   50,   50,    0,    0,    0,   50,    0,    0,    0,
    0,    0,   55,   55,   55,   55,   55,   55,   55,   55,
    0,    0,   55,   55,    0,    0,    0,    0,    0,    0,
    0,   55,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   55,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   59,   45,   14,   47,   77,   11,   41,   75,
   40,  266,   49,   12,  260,   54,   24,   59,   60,   41,
   62,   43,  268,   45,   61,   24,  256,  257,  258,  256,
  257,   61,  256,  257,  258,   82,  102,   59,   60,   41,
   62,   43,  265,   45,  270,  269,   40,   84,   85,   86,
   87,   88,   89,  267,   62,   63,   59,   59,   60,   41,
   62,   43,   66,   45,  103,   43,  265,   45,  256,  257,
   59,  270,  119,   59,   42,  263,  264,   59,   60,   47,
   62,   44,   60,   41,   62,  256,  257,  134,  265,  266,
  267,  101,  263,  264,  265,   68,  167,   41,   59,  270,
   73,   59,  141,  113,   68,  152,  153,  154,   43,   73,
   45,   41,  256,  257,   41,   59,  163,  257,  258,  256,
  257,    8,    9,  260,   41,   41,  263,  264,   40,   59,
   41,  268,  265,  266,   40,  145,   62,   63,  267,   41,
   90,   91,  257,   59,   41,   94,   95,  261,  259,  266,
  257,   41,  257,   41,  262,   41,  167,   59,  162,   44,
  261,  261,   41,   44,  261,  257,   40,  270,  261,   41,
  261,   59,   41,   74,   69,  109,  148,   -1,   -1,   40,
   -1,   -1,   -1,   -1,   -1,   59,   -1,   61,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  256,
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
   -1,   -1,  260,   -1,   -1,  263,  264,   -1,  266,   21,
  268,   -1,  256,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,   -1,  266,   -1,  268,   -1,  256,  257,   -1,   -1,
  260,   -1,   -1,  263,  264,   -1,   -1,   49,  268,   -1,
  256,  257,   54,   -1,  260,   -1,   -1,  263,  264,   61,
   -1,   -1,  268,   -1,  256,  257,   -1,   -1,  260,   -1,
   -1,  263,  264,   -1,   -1,   -1,  268,   -1,   -1,   -1,
   -1,   -1,   84,   85,   86,   87,   88,   89,   90,   91,
   -1,   -1,   94,   95,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  103,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  141,
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
"$$10 :",
"sentencia_declarativa_tipo : tipo lista_var $$10 sentencia_declar_funcion",
"lista_sent_ejec : sentencia_ejec lista_sent_ejec",
"lista_sent_ejec : sentencia_ejec",
"sentencia_ejec : sentencia_simple",
"sentencia_ejec : sentencia_comp",
"sentencia_simple : sent_correcta",
"$$11 :",
"sentencia_simple : sent_abierta $$11 sent_correcta",
"$$12 :",
"sentencia_simple : sent_abierta $$12 sentencia_comp",
"sentencia_simple : ';'",
"$$13 :",
"sent_abierta : PRINT $$13 '(' cadena ')'",
"sent_abierta : llamada_funcion",
"$$14 :",
"sent_abierta : RETURN $$14 expresion",
"sent_abierta : asignacion",
"sent_correcta : sent_abierta ';'",
"sent_correcta : error ';'",
"sentencia_comp : sentencia_if",
"sentencia_comp : sentencia_loop",
"$$15 :",
"sentencia_if : IF '(' cond ')' $$15 THEN bloque_IF",
"sentencia_if : sentencia_if_error",
"$$16 :",
"sentencia_if_error : IF THEN $$16 bloque_IF",
"$$17 :",
"sentencia_if_error : IF cond ')' $$17 THEN bloque_IF",
"$$18 :",
"sentencia_if_error : IF '(' cond $$18 THEN bloque_IF",
"$$19 :",
"sentencia_if_error : IF cond $$19 THEN bloque_IF",
"$$20 :",
"sentencia_if_error : IF '(' cond ')' $$20 bloque_IF",
"$$21 :",
"sentencia_if_error : IF error $$21 bloque_IF",
"$$22 :",
"sentencia_loop : LOOP $$22 bloque_loop",
"bloque_loop : bloque_sent UNTIL cond",
"bloque_loop : loop_error",
"loop_error : bloque_sent cond",
"loop_error : bloque_sent UNTIL error",
"loop_error : bloque_sent error",
"$$23 :",
"llamada_funcion : ID '(' $$23 lista_parametros ')'",
"llamada_funcion : ID '(' ')'",
"$$24 :",
"lista_var : ID ',' $$24 lista_var",
"lista_var : ID",
"bloque_IF : bloque_sent",
"$$25 :",
"bloque_IF : bloque_sent ELSE $$25 bloque_sent",
"bloque_IF : ID bloque_IF",
"cond : expresion '<' expresion",
"cond : expresion '>' expresion",
"cond : expresion MEN_IG expresion",
"cond : expresion MAY_IG expresion",
"cond : expresion IGUAL expresion",
"cond : expresion DIST expresion",
"parametros : tipo ID",
"$$26 :",
"parametros : tipo ID ',' $$26 parametros",
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

//#line 170 "Gramatica.nueva.y"


AnalizadorLexico Al = new ALexico.AnalizadorLexico(new File("Docs/codigo.txt"));
PolacaInversa PI = new PolacaInversa();
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




//#line 598 "Parser.java"
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
{PI.endFunction(val_peek(5).sval);ManejadorAmbitos.EndAmbito(); }
break;
case 13:
//#line 29 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(2).sval))  val_peek(2).ival=Estructuras.addTupla(val_peek(2).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.FUNCTION);  ManejadorAmbitos.NewAmbito(val_peek(2).sval);PI.beginFunction(val_peek(2).sval);}
break;
case 14:
//#line 29 "Gramatica.nueva.y"
{PI.endFunction(val_peek(4).sval);ManejadorAmbitos.EndAmbito();}
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
case 34:
//#line 55 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 41:
//#line 69 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 43:
//#line 70 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 46:
//#line 74 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'print'");}
break;
case 47:
//#line 74 "Gramatica.nueva.y"
{PI.callPrint(val_peek(1).ival);}
break;
case 48:
//#line 75 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
break;
case 49:
//#line 76 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'return'");}
break;
case 50:
//#line 76 "Gramatica.nueva.y"
{PI.retorno();}
break;
case 56:
//#line 88 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'if'"); PI.FinCondicion();ManejadorAmbitos.NewAmbito();}
break;
case 57:
//#line 88 "Gramatica.nueva.y"
{ManejadorAmbitos.EndAmbito();}
break;
case 59:
//#line 92 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la condicion del IF");}
break;
case 61:
//#line 93 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta abrir parentesis");}
break;
case 63:
//#line 94 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta cerrar parentesis");}
break;
case 65:
//#line 95 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta  parentesis");}
break;
case 67:
//#line 96 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la palabra THEN");}
break;
case 69:
//#line 97 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": errores múltiples en IF");}
break;
case 71:
//#line 99 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de iteracion");PI.InicLoop();ManejadorAmbitos.NewAmbito();}
break;
case 72:
//#line 99 "Gramatica.nueva.y"
{ManejadorAmbitos.EndAmbito();}
break;
case 73:
//#line 102 "Gramatica.nueva.y"
{PI.FinLoop();}
break;
case 75:
//#line 106 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la palabra UNTIL");}
break;
case 76:
//#line 107 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la condicion luego de UNTIL");}
break;
case 77:
//#line 108 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Se olvido del UNTIL?");}
break;
case 78:
//#line 111 "Gramatica.nueva.y"
{ManejadorAmbitos.isDeclarada(val_peek(1).sval); PI.callFunction(val_peek(1).sval);}
break;
case 80:
//#line 112 "Gramatica.nueva.y"
{ManejadorAmbitos.isDeclarada(val_peek(2).sval);PI.callFunction(val_peek(2).sval);}
break;
case 81:
//#line 116 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(1).sval)){  val_peek(1).ival=Estructuras.addTupla(val_peek(1).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);}}
break;
case 83:
//#line 117 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(0).sval)) { val_peek(0).ival=Estructuras.addTupla(val_peek(0).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);}}
break;
case 84:
//#line 121 "Gramatica.nueva.y"
{PI.FinIf();}
break;
case 85:
//#line 122 "Gramatica.nueva.y"
{PI.FinThenElse();}
break;
case 86:
//#line 122 "Gramatica.nueva.y"
{PI.FinIf();}
break;
case 87:
//#line 123 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Then mal escrito");}
break;
case 88:
//#line 127 "Gramatica.nueva.y"
{PI.addPolaco("<");}
break;
case 89:
//#line 128 "Gramatica.nueva.y"
{PI.addPolaco(">");}
break;
case 90:
//#line 129 "Gramatica.nueva.y"
{PI.addPolaco("<=");}
break;
case 91:
//#line 130 "Gramatica.nueva.y"
{PI.addPolaco(">=");}
break;
case 92:
//#line 131 "Gramatica.nueva.y"
{PI.addPolaco("==");}
break;
case 93:
//#line 132 "Gramatica.nueva.y"
{PI.addPolaco("!=");}
break;
case 94:
//#line 135 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(0).sval)) val_peek(0).ival=Estructuras.addTupla(val_peek(0).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.PUNT,Estructuras.USO_REF);}
break;
case 95:
//#line 136 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(1).sval)) val_peek(1).ival=Estructuras.addTupla(val_peek(1).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.PUNT,Estructuras.USO_REF);}
break;
case 99:
//#line 144 "Gramatica.nueva.y"
{ManejadorAmbitos.isDeclarada(val_peek(0).sval);}
break;
case 100:
//#line 149 "Gramatica.nueva.y"
{int pos= ManejadorAmbitos.isDeclarada(val_peek(2).sval); Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia de asignacion"); PI.addPolaco("TS("+pos+")"); PI.addPolaco("=");}
break;
case 101:
//#line 152 "Gramatica.nueva.y"
{PI.addPolaco("+");}
break;
case 102:
//#line 153 "Gramatica.nueva.y"
{PI.addPolaco("-");}
break;
case 104:
//#line 157 "Gramatica.nueva.y"
{PI.addPolaco("*");}
break;
case 105:
//#line 158 "Gramatica.nueva.y"
{PI.addPolaco("/");}
break;
case 107:
//#line 162 "Gramatica.nueva.y"
{int pos = ManejadorAmbitos.isDeclarada(val_peek(0).sval); PI.addPolaco("TS("+pos+")");}
break;
case 108:
//#line 163 "Gramatica.nueva.y"
{PI.addPolaco("TS("+val_peek(0).ival+")");}
break;
//#line 987 "Parser.java"
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
