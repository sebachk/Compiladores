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
   24,   20,   25,   20,   26,   20,   19,   19,   19,   15,
   15,   27,   30,   27,   31,   27,   27,   32,   29,   29,
   35,   29,   29,   23,   23,   28,   28,   40,   37,   37,
   43,   42,   44,   42,   45,   42,   46,   42,   47,   42,
   48,   42,   50,   38,   49,   49,   51,   51,   51,   53,
   54,   33,   33,   55,   22,   22,   41,   56,   41,   41,
   39,   39,   39,   39,   39,   39,   10,   57,   10,   52,
   52,   58,   36,   34,   34,   34,   59,   59,   59,   60,
   60,   60,   21,
};
final static short yylen[] = {                            2,
    0,    0,    5,    1,    2,    1,    2,    1,    0,    2,
    0,    0,    8,    0,    6,    3,    3,    1,    1,    3,
    0,    4,    0,    4,    1,    2,    2,    1,    3,    3,
    0,    4,    0,    4,    0,    4,    2,    1,    2,    1,
    1,    1,    0,    3,    0,    3,    1,    0,    5,    1,
    0,    3,    1,    2,    2,    1,    1,    0,    7,    1,
    0,    4,    0,    6,    0,    6,    0,    5,    0,    6,
    0,    4,    0,    3,    3,    1,    2,    3,    2,    0,
    0,    6,    3,    0,    4,    1,    1,    0,    4,    2,
    3,    3,    3,    3,    3,    3,    2,    0,    5,    3,
    1,    1,    3,    3,    3,    1,    3,    3,    1,    1,
    1,    1,    1,
};
final static short yydefred[] = {                         0,
    1,    0,    0,    0,  113,    2,    4,    0,    0,    8,
    0,    0,    0,    0,    0,    0,    5,    7,    0,    0,
    0,   48,   51,    0,   73,   47,   10,   19,   18,   42,
   40,   41,    0,   50,   53,   56,   57,   60,   27,    0,
    0,    0,   16,   17,    0,    3,   55,    0,    0,   71,
    0,  111,   61,    0,  112,    0,    0,    0,  109,    0,
    0,    0,    0,    0,   25,    0,   54,    0,    0,   30,
   84,   29,    0,    0,    0,   14,    0,   83,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   63,    0,    0,    0,    0,    0,   26,    0,   37,
   20,    0,    0,    0,   74,   76,   44,    0,   46,    0,
   32,   34,   36,    0,   12,    0,  102,   81,    0,    0,
    0,   72,   62,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,  107,  108,    0,   22,   24,
   79,    0,   77,   85,   15,    0,    0,    0,    0,   90,
   88,    0,    0,    0,    0,   68,   49,   78,   75,    0,
   98,   82,  100,    0,    0,   70,   66,   64,   13,    0,
   89,   59,   99,
};
final static short yydgoto[] = {                          2,
    6,    3,   16,    7,    8,    9,   10,  121,   11,  115,
   77,  146,   28,  114,   29,   64,  102,  103,   65,   12,
   13,   42,   30,   73,   74,   75,   31,   32,   33,   68,
   69,   60,   34,   56,   61,   35,   36,   37,   57,  152,
  122,   38,   82,  134,  125,   93,  153,   81,  105,   66,
  106,  118,   79,  148,  110,  164,  170,  119,   58,   59,
};
final static short yysindex[] = {                      -236,
    0,    0, -198, -244,    0,    0,    0, -229, -229,    0,
  230, -227, -166,  -32,   37, -187,    0,    0,  -25,  -30,
  140,    0,    0,  -56,    0,    0,    0,    0,    0,    0,
    0,    0,   42,    0,    0,    0,    0,    0,    0,   66,
  101,   84,    0,    0,   69,    0,    0,  105, -126,    0,
  110,    0,    0, -126,    0,   23,  116,   50,    0,  120,
 -126,  271,  285, -102,    0,  230,    0, -168, -233,    0,
    0,    0, -168, -227, -229,    0, -227,    0,  -91,   70,
  243,  243,  129, -126, -126, -126, -126, -126, -126, -126,
 -126,    0,  -93, -126, -126,  -88,   70,    0,  -25,    0,
    0,  -92, -229, -183,    0,    0,    0,   42,    0,  -84,
    0,    0,    0,  -92,    0,  -81,    0,    0,  133,  127,
  -83,    0,    0,    0,  -80,   70,   70,   70,   70,   70,
   70,   50,   50,  -79,  243,    0,    0,  137,    0,    0,
    0, -201,    0,    0,    0,  142,  141,  146,  -91,    0,
    0,  -77,  243,  243,  243,    0,    0,    0,    0,  -92,
    0,    0,    0,  230,  243,    0,    0,    0,    0, -227,
    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,  257,    0,    0,    0,    0,  257,  170,    0,
    0,  190,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -231,    0,    0,    0,    0,    0,    0,    0,
  143, -101,    0,    0,  -78,    0,    0,  -68,    0,    0,
  -41,    0,    0,    0,    0,    0,  -71,  -21,    0,    0,
    0,    0, -196, -132,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,  299,
    0,    0,  -67,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  313,    0, -158,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  150,    0,
  154,    0,    0,  210,    0,   43,   57,   71,   85,   99,
  113,   -1,   19,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  152,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    0,    0,  130,    4,    0,  -58,   -3,    0,   25,
    0,    0,   -9,    0,  -10,    0,    0,    0,   73,  122,
  -70,   87,   31,    0,    0,    0,    0,  134,   38,    0,
    0,    0,   33,  -38,    0,    0,    0,    0,  -39,    0,
   -6,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   49,    0,    0,    0,    0,    0,    0,   51,   58,
};
final static int YYTABLESIZE=581;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                        110,
  110,  110,   26,  110,   44,  110,  116,   27,   43,   48,
   80,   14,   15,   63,   83,   39,  113,  110,  110,  106,
  110,  106,   97,  106,   43,   43,   21,   62,   45,    1,
   49,   43,   43,   47,   25,    4,   45,  106,  106,  104,
  106,  104,    5,  104,  140,  126,  127,  128,  129,  130,
  131,   63,   63,   55,  158,   51,   52,  104,  104,  105,
  104,  105,  104,  105,  143,   90,    4,   91,   38,   38,
   38,    5,  141,   51,   52,  123,   45,  105,  105,   46,
  105,   55,   88,   94,   89,  142,   55,   19,   20,   40,
   41,   94,  139,   55,   22,   23,   95,   93,  107,  116,
   67,   94,  159,  111,  145,  108,   39,   39,   39,   76,
  108,   96,   90,  150,   91,   93,   55,   55,   55,   55,
   55,   55,   55,   55,   70,   95,   55,   55,  156,   96,
   51,   52,   23,   21,   98,  100,   55,   17,   18,   91,
  132,  133,   72,   95,   71,   78,  166,  167,  168,   48,
  169,  136,  137,   92,   31,   31,   92,   91,  172,   96,
  171,   31,   31,   35,  101,  117,   48,  135,   33,  124,
  138,   92,   41,   24,   55,  147,  149,  157,  151,   54,
  154,  155,  160,  165,  161,   26,  162,   49,   80,   67,
  101,   11,   97,   65,  173,  112,  144,  163,    0,   19,
   20,   86,  109,   21,    0,    0,   22,   23,    0,    0,
    0,   25,   87,    5,  110,  110,  110,    0,  110,  110,
  110,  110,  110,  110,  110,  110,  110,  110,    9,  110,
  110,  110,  110,   24,  106,  106,  106,    0,  106,  106,
  106,  106,  106,  106,  106,  106,  106,  106,   28,  106,
  106,  106,  106,    0,  104,  104,  104,    0,  104,  104,
  104,  104,  104,  104,  104,  104,  104,  104,   69,  104,
  104,  104,  104,    0,  105,  105,  105,    0,  105,  105,
  105,  105,  105,  105,  105,  105,  105,  105,   26,  105,
  105,  105,  105,   84,   85,   86,   87,    0,   94,   94,
   94,   26,   94,   94,   94,   94,   94,   94,   94,   94,
   94,   94,   93,   93,   93,    9,   93,   93,   93,   93,
   93,   93,   93,   93,   93,   93,   96,   96,   96,   26,
   96,   96,   96,   96,   96,   96,   96,   96,   96,   96,
   95,   95,   95,   26,   95,   95,   95,   95,   95,   95,
   95,   95,   95,   95,   91,   91,   91,  103,   91,   91,
   91,   91,   91,   91,   91,   91,   91,   91,   92,   92,
   92,   52,   92,   92,   92,   92,   92,   92,   92,   92,
   92,   92,   19,  120,    0,    0,   21,    0,    0,   22,
   23,    0,   24,    0,   25,   50,   51,   52,   86,   86,
   53,    0,    0,    0,    0,   86,   86,   86,    0,   87,
   87,   87,   86,   87,    0,    0,   87,   87,   87,   87,
   87,   87,   87,    0,    0,    9,    9,    0,    0,    9,
    0,    0,    9,    9,    0,    9,    6,    9,    0,    0,
    0,    0,    0,    0,    0,   28,   28,    0,    0,   28,
    0,    0,   28,   28,   28,   28,    0,   28,    0,    0,
    0,    0,    0,    0,    0,   69,   69,    0,    0,   69,
   58,    0,   69,   69,    0,   69,    0,   69,    0,    0,
    0,    0,    0,    0,    0,   19,   20,    0,    0,   21,
    0,    0,   22,   23,    0,   24,    0,   25,   19,  120,
    0,    0,   21,    0,    0,   22,   23,    0,   24,    0,
   25,    0,    9,    9,    0,    0,    9,    0,    0,    9,
    9,    0,    9,    0,    9,    0,   19,   20,    0,    0,
   21,    0,    0,   22,   23,    0,    0,    0,   25,    0,
   99,   20,    0,    0,   21,    0,    0,   22,   23,    0,
    0,    0,   25,    0,  103,  103,    0,    0,  103,    0,
    0,  103,  103,    0,    0,    0,  103,    0,   52,   52,
    0,    0,   52,    0,    0,   52,   52,    0,    0,    0,
   52,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   59,   45,   14,   47,   77,   11,   41,   40,
   49,  256,  257,   24,   54,   12,   75,   59,   60,   41,
   62,   43,   61,   45,  256,  257,  260,   24,  260,  266,
   61,  263,  264,   59,  268,  265,  268,   59,   60,   41,
   62,   43,  270,   45,  103,   84,   85,   86,   87,   88,
   89,   62,   63,   21,  256,  257,  258,   59,   60,   41,
   62,   43,   66,   45,  104,   43,  265,   45,  265,  266,
  267,  270,  256,  257,  258,   82,   40,   59,   60,  267,
   62,   49,   60,   41,   62,  269,   54,  256,  257,  256,
  257,   42,  102,   61,  263,  264,   47,   41,   68,  170,
   59,   59,  142,   73,  114,   68,  265,  266,  267,   41,
   73,   41,   43,  120,   45,   59,   84,   85,   86,   87,
   88,   89,   90,   91,   59,   41,   94,   95,  135,   59,
  257,  258,  265,  266,   62,   63,  104,    8,    9,   41,
   90,   91,   59,   59,   44,   41,  153,  154,  155,   40,
  160,   94,   95,   41,  256,  257,   41,   59,  165,   40,
  164,  263,  264,  265,  267,  257,   40,  261,  270,   41,
  259,   59,  257,  266,  142,  257,   44,   41,  262,   40,
  261,  261,   41,  261,   44,   59,   41,   61,  257,  261,
   41,  270,   41,  261,  170,   74,  110,  149,   -1,  256,
  257,   59,   69,  260,   -1,   -1,  263,  264,   -1,   -1,
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
  258,   59,  260,  261,  262,  263,  264,  265,  266,  267,
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
   -1,  263,  264,   -1,   -1,   -1,  268,   -1,  256,  257,
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
"lista_sent_ejec : sentencia_ejec error",
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

//#line 175 "Gramatica.nueva.y"


AnalizadorLexico Al;
PolacaInversa PI = new PolacaInversa();
int contador=1;
int cant_param=0;

int yylex(){
	ALexico.Token t; 
	int val= this.YYERRCODE;
		t = Al.GetToken();
		if(t!=null){
		//	ALexico.Estructuras.addToken("Token"+ contador++ +" = " + ALexico.Estructuras.getStringToken(t.getIdentif_tt()));
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
				return YYERRCODE;
			}
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
  
}
public void setFuente(String s){
	Al= new AnalizadorLexico(new File(s));
}




//#line 596 "Parser.java"
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
{ManejadorAmbitos.NewAmbito("Main");PI.addPolaco("Label_main");}
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
{cant_param=0;if(ManejadorAmbitos.PuedoDeclarar(val_peek(2).sval))  val_peek(2).ival=Estructuras.addTupla(val_peek(2).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.FUNCTION);  ManejadorAmbitos.NewAmbito(val_peek(2).sval);PI.beginFunction();PI.finParam(val_peek(2).sval,cant_param);
							Estructuras.Tabla_Simbolos.elementAt(val_peek(2).ival).valor=val_peek(2).sval+"_"+cant_param+"_"+ManejadorAmbitos.getInstance().FirstAmbito();
							Estructuras.SumAmbito(ManejadorAmbitos.getInstance(),"_"+cant_param+"");}
break;
case 15:
//#line 33 "Gramatica.nueva.y"
{PI.endFunction(val_peek(4).sval);ManejadorAmbitos.EndAmbito();}
break;
case 21:
//#line 43 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 23:
//#line 44 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 29:
//#line 55 "Gramatica.nueva.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");}
break;
case 31:
//#line 57 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 33:
//#line 58 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 35:
//#line 59 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 43:
//#line 74 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 45:
//#line 75 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 48:
//#line 79 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'print'");}
break;
case 49:
//#line 79 "Gramatica.nueva.y"
{PI.callPrint(val_peek(1).ival);}
break;
case 50:
//#line 80 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
break;
case 51:
//#line 81 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'return'");}
break;
case 52:
//#line 81 "Gramatica.nueva.y"
{PI.retorno();}
break;
case 58:
//#line 93 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'if'"); PI.FinCondicion();ManejadorAmbitos.NewAmbito();}
break;
case 59:
//#line 93 "Gramatica.nueva.y"
{ManejadorAmbitos.EndAmbito();}
break;
case 61:
//#line 97 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la condicion del IF");PI.FinCondicion();}
break;
case 63:
//#line 98 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta abrir parentesis");PI.FinCondicion();}
break;
case 65:
//#line 99 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta cerrar parentesis");PI.FinCondicion();}
break;
case 67:
//#line 100 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta  parentesis");PI.FinCondicion();}
break;
case 69:
//#line 101 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la palabra THEN");PI.FinCondicion();}
break;
case 71:
//#line 102 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": errores múltiples en IF");PI.FinCondicion();}
break;
case 73:
//#line 104 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de iteracion");PI.InicLoop();ManejadorAmbitos.NewAmbito();}
break;
case 74:
//#line 104 "Gramatica.nueva.y"
{ManejadorAmbitos.EndAmbito();}
break;
case 75:
//#line 107 "Gramatica.nueva.y"
{PI.FinLoop();}
break;
case 77:
//#line 111 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la palabra UNTIL");}
break;
case 78:
//#line 112 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la condicion luego de UNTIL");}
break;
case 79:
//#line 113 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Se olvido del UNTIL?");}
break;
case 80:
//#line 116 "Gramatica.nueva.y"
{cant_param=0;PI.beginCall();}
break;
case 81:
//#line 116 "Gramatica.nueva.y"
{ManejadorAmbitos.isDeclarada(val_peek(3).sval+"_"+cant_param); PI.endcall(val_peek(3).sval,cant_param);}
break;
case 83:
//#line 117 "Gramatica.nueva.y"
{cant_param=0;ManejadorAmbitos.isDeclarada(val_peek(2).sval+"_"+cant_param);PI.beginCall();PI.endcall(val_peek(2).sval,cant_param);}
break;
case 84:
//#line 121 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(1).sval)){  val_peek(1).ival=Estructuras.addTupla(val_peek(1).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);}}
break;
case 86:
//#line 122 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(0).sval)) { val_peek(0).ival=Estructuras.addTupla(val_peek(0).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.UINT,Estructuras.USO_VAR);}}
break;
case 87:
//#line 126 "Gramatica.nueva.y"
{PI.FinIf();}
break;
case 88:
//#line 127 "Gramatica.nueva.y"
{PI.FinThenElse();}
break;
case 89:
//#line 127 "Gramatica.nueva.y"
{PI.FinIf();}
break;
case 90:
//#line 128 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Then mal escrito");}
break;
case 91:
//#line 132 "Gramatica.nueva.y"
{PI.addPolaco("<");}
break;
case 92:
//#line 133 "Gramatica.nueva.y"
{PI.addPolaco(">");}
break;
case 93:
//#line 134 "Gramatica.nueva.y"
{PI.addPolaco("<=");}
break;
case 94:
//#line 135 "Gramatica.nueva.y"
{PI.addPolaco(">=");}
break;
case 95:
//#line 136 "Gramatica.nueva.y"
{PI.addPolaco("==");}
break;
case 96:
//#line 137 "Gramatica.nueva.y"
{PI.addPolaco("!=");}
break;
case 97:
//#line 140 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(0).sval)) val_peek(0).ival=Estructuras.addTupla(val_peek(0).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.PUNT,Estructuras.USO_REF); cant_param++;PI.addPolaco("TS("+val_peek(0).ival+")");}
break;
case 98:
//#line 141 "Gramatica.nueva.y"
{if(ManejadorAmbitos.PuedoDeclarar(val_peek(1).sval)) val_peek(1).ival=Estructuras.addTupla(val_peek(1).sval+ManejadorAmbitos.getInstance().getName(),Estructuras.PUNT,Estructuras.USO_REF);cant_param++;PI.addPolaco("TS("+val_peek(1).ival+")");}
break;
case 100:
//#line 145 "Gramatica.nueva.y"
{cant_param++;}
break;
case 101:
//#line 146 "Gramatica.nueva.y"
{cant_param++;}
break;
case 102:
//#line 149 "Gramatica.nueva.y"
{ val_peek(0).ival=ManejadorAmbitos.isDeclarada(val_peek(0).sval); if(val_peek(0).ival!=-1)PI.addPolaco("TS("+val_peek(0).ival+")");}
break;
case 103:
//#line 154 "Gramatica.nueva.y"
{int pos= ManejadorAmbitos.isDeclarada(val_peek(2).sval); Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia de asignacion"); PI.addPolaco("TS("+pos+")"); PI.addPolaco("=");}
break;
case 104:
//#line 157 "Gramatica.nueva.y"
{PI.addPolaco("+");}
break;
case 105:
//#line 158 "Gramatica.nueva.y"
{PI.addPolaco("-");}
break;
case 107:
//#line 162 "Gramatica.nueva.y"
{PI.addPolaco("*");}
break;
case 108:
//#line 163 "Gramatica.nueva.y"
{PI.addPolaco("/");}
break;
case 110:
//#line 167 "Gramatica.nueva.y"
{int pos = ManejadorAmbitos.isDeclarada(val_peek(0).sval); PI.addPolaco("TS("+pos+")");}
break;
case 111:
//#line 168 "Gramatica.nueva.y"
{PI.addPolaco("TS("+val_peek(0).ival+")");}
break;
case 112:
//#line 169 "Gramatica.nueva.y"
{PI.CallConRet();Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
break;
//#line 1009 "Parser.java"
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
