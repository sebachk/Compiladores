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
   34,   41,   43,   30,   42,   42,   44,   44,   44,   25,
   25,   15,   15,   33,   46,   33,   33,   31,   31,   31,
   31,   31,   31,    5,    5,   45,   45,   47,   48,   28,
   26,   26,   26,   49,   49,   49,   50,   50,   50,   14,
};
final static short yylen[] = {                            2,
    3,    1,    2,    1,    1,    6,    5,    3,    3,    1,
    1,    3,    0,    4,    0,    4,    1,    2,    2,    1,
    3,    3,    0,    4,    0,    4,    2,    1,    1,    1,
    1,    0,    3,    0,    3,    1,    0,    5,    1,    0,
    3,    1,    2,    2,    1,    1,    0,    7,    1,    0,
    4,    0,    6,    0,    6,    0,    5,    0,    6,    0,
    4,    0,    0,    4,    3,    1,    2,    3,    2,    4,
    3,    3,    1,    1,    0,    4,    2,    3,    3,    3,
    3,    3,    3,    2,    4,    3,    1,    1,    0,    4,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,   37,   40,    0,    0,   62,
   36,    0,    0,    4,    5,   11,   10,   31,   29,   30,
    0,   39,   42,   45,   46,   49,   44,    0,    0,   60,
    0,   98,   50,    0,   99,    0,    0,    0,   96,    0,
    0,    0,    0,  100,    0,    0,   17,    0,    0,    0,
   63,    1,    3,   43,    0,    0,   71,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   52,    0,    0,    0,    0,    0,    8,    9,
    0,   27,   12,    0,    0,   18,   19,    0,    0,    0,
    0,   33,    0,   35,   70,    0,   90,    0,    0,   61,
   51,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,   94,   95,    0,    0,    0,    0,   14,
   16,   22,    0,   21,    0,    0,    0,   64,   66,   86,
   77,   75,    0,    0,    0,    0,   57,   38,    7,    0,
    0,   72,   24,   26,   69,    0,   67,    0,    0,   59,
   55,   53,    6,    0,   68,   65,   76,   48,   85,
};
final static short yydgoto[] = {                          2,
   12,   13,   14,   99,  118,   16,   17,   46,   84,   85,
   47,   48,   49,   50,   90,   18,  125,  126,   19,   20,
   21,   55,   56,   40,   35,   36,   41,   23,   24,   25,
   37,  133,  100,   26,   63,  112,  103,   74,  134,   62,
   51,  128,   91,  129,   59,  148,   60,   97,   38,   39,
};
final static short yysindex[] = {                      -250,
  190,    0,  -13,  -25,  140,    0,    0, -226,  143,    0,
    0, -202,  190,    0,    0,    0,    0,    0,    0,    0,
   28,    0,    0,    0,    0,    0,    0,  128, -140,    0,
   70,    0,    0, -140,    0,   23,   50,    7,    0,   79,
 -140,  -28,   87,    0,  257, -170,    0,  257, -141, -134,
    0,    0,    0,    0, -229, -231,    0,   68,   65,   89,
   68,  230,  230,   98, -140, -140, -140, -140, -140, -140,
 -140, -140,    0, -120, -140, -140, -114,   68,    0,    0,
  -36,    0,    0, -119, -117,    0,    0,   90,  102,   91,
  243,    0,   28,    0,    0, -140,    0,  127, -111,    0,
    0,    0, -109,   68,   68,   68,   68,   68,   68,    7,
    7, -108,  230,    0,    0,  114, -119,  115, -100,    0,
    0,    0,  -98,    0, -229, -141, -164,    0,    0,    0,
    0,    0, -101,  230,  230,  230,    0,    0,    0, -119,
  118,    0,    0,    0,    0, -201,    0,  243,  230,    0,
    0,    0,    0, -141,    0,    0,    0,    0,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -104,    0,    0,    0,    0,    0,    0,    0,
 -132,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  -41,    0,    0,    0,    0,    0,  -97,  -21,    0,    0,
    0,    0,    0,    0, -190, -131,    0,    0,  271,    0,
    0,    0,    0,    0,    0,    0,    0,    6,    0,  124,
  285,    0,    0,  -95,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,  299,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,  -52, -149,
    0,    0,    0,    0,    0,    0,    0,    0,  170,    0,
    0,  210,    0,   43,   57,   71,   85,   99,  113,   -1,
   19,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
  129,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
  155,    0,   86,   13,   20,  -31,    3,    0,    0,    0,
   51,  126,   47,  -72,   53,  -45,    0,    0,    0,  121,
  -30,    0,    0,    0,  488,    4,    0,    0,    0,    0,
  -26,    0,  -46,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,   82,    0,    0,    0,   66,   67,
};
final static int YYTABLESIZE=637;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         97,
   97,   97,   97,   97,  117,   97,   73,   64,  119,   92,
   80,   45,   79,   15,   28,    1,  101,   97,   97,   93,
   97,   93,   93,   93,   93,   15,    3,    4,    5,   42,
   43,   58,   61,    6,    7,   29,   10,   93,   93,   91,
   93,   91,   91,   91,   78,   27,   88,   45,   75,   88,
   45,  131,  120,   76,  155,   31,   32,   91,   91,   92,
   91,   92,   92,   92,   52,   71,  137,   72,  104,  105,
  106,  107,  108,  109,   28,   28,   28,   92,   92,  143,
   92,  119,   69,   81,   70,  139,   54,  150,  151,  152,
   73,  145,   31,   32,   93,   82,   83,   80,   86,   58,
  147,   81,  158,  127,  146,   95,   23,   23,  153,   28,
   71,   83,   72,   23,   23,   80,   31,   32,   77,  156,
   25,   88,   89,   32,   32,   82,   81,   34,   44,   83,
   32,   32,   96,   15,   13,   34,  110,  111,  102,   78,
  113,  114,  115,   82,  116,  123,    9,    8,  122,  124,
  132,  135,  136,   79,  138,  140,  141,   78,   89,  149,
  157,  154,    2,   56,   87,   54,   28,   53,   57,   84,
  121,   79,  144,  159,   87,  142,   94,  130,    0,   34,
    0,    0,    0,    0,    0,   11,    0,   29,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   11,    0,   73,   73,    0,    0,    0,    0,    0,
   73,   73,    0,    0,   97,   97,   97,   73,   97,   97,
   97,   97,   97,   97,   97,   97,   97,   97,   74,   97,
   97,   97,   97,   44,   93,   93,   93,    9,   93,   93,
   93,   93,   93,   93,   93,   93,   93,   93,   11,   93,
   93,   93,   93,    0,   91,   91,   91,    0,   91,   91,
   91,   91,   91,   91,   91,   91,   91,   91,   58,   91,
   91,   91,   91,    0,   92,   92,   92,    0,   92,   92,
   92,   92,   92,   92,   92,   92,   92,   92,   11,   92,
   92,   92,   92,   65,   66,   67,   68,    0,   81,   81,
   81,   11,   81,   81,   81,   81,   81,   81,   81,   81,
   81,   81,   80,   80,   80,   11,   80,   80,   80,   80,
   80,   80,   80,   80,   80,   80,   83,   83,   83,   20,
   83,   83,   83,   83,   83,   83,   83,   83,   83,   83,
   82,   82,   82,   89,   82,   82,   82,   82,   82,   82,
   82,   82,   82,   82,   78,   78,   78,   41,   78,   78,
   78,   78,   78,   78,   78,   78,   78,   78,   79,   79,
   79,    0,   79,   79,   79,   79,   79,   79,   79,   79,
   79,   79,    3,   98,   31,   32,    5,    0,    0,    6,
    7,    0,    9,    0,   10,   30,   31,   32,    3,    4,
   33,    0,    5,    0,    0,    6,    7,    0,    0,    0,
   10,    0,   44,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   74,   74,   74,    0,   74,
    0,    0,   74,   74,   74,   74,   74,   74,   74,    0,
    0,    0,    0,    0,    0,    3,    4,    0,    0,    5,
    0,    0,    6,    7,    8,    9,    0,   10,    0,    0,
    0,    0,    0,    0,    0,   58,   58,    0,    0,   58,
   47,    0,   58,   58,    0,   58,    0,   58,    0,    0,
    0,    0,    0,    0,    0,    3,   98,    0,   22,    5,
    0,    0,    6,    7,    0,    9,   22,   10,    3,    4,
   22,    0,    5,    0,    0,    6,    7,    0,    9,    0,
   10,    0,    3,    4,    0,    0,    5,    0,    0,    6,
    7,    0,    0,    0,   10,    0,   20,   20,    0,    0,
   20,    0,   22,   20,   20,   22,    0,    0,   20,    0,
   89,   89,   22,    0,   89,    0,    0,   89,   89,   22,
   22,    0,   89,    0,   41,   41,    0,    0,   41,    0,
    0,   41,   41,    0,    0,    0,   41,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   22,    0,
    0,    0,    0,    0,    0,   22,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   22,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   22,    0,    0,    0,    0,    0,    0,    0,
    0,   22,   22,   22,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   22,   22,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,   59,   34,   81,   55,
   42,    9,   41,    1,   40,  266,   63,   59,   60,   41,
   62,   43,   44,   45,   55,   13,  256,  257,  260,  256,
  257,   28,   29,  263,  264,   61,  268,   59,   60,   41,
   62,   43,   44,   45,   41,   59,   41,   45,   42,   44,
   48,   98,   84,   47,  256,  257,  258,   59,   60,   41,
   62,   43,   44,   45,  267,   43,  113,   45,   65,   66,
   67,   68,   69,   70,  265,  266,  267,   59,   60,  125,
   62,  154,   60,   41,   62,  117,   59,  134,  135,  136,
   41,  256,  257,  258,  125,   45,  267,   41,   48,   96,
  127,   59,  149,   91,  269,   41,  256,  257,  140,   40,
   43,   41,   45,  263,  264,   59,  257,  258,   40,  146,
  270,  256,  257,  256,  257,   41,   40,  260,  270,   59,
  263,  264,   44,  265,  266,  268,   71,   72,   41,   41,
  261,   75,   76,   59,  259,   44,  266,  265,   59,   59,
  262,  261,  261,   41,   41,   41,  257,   59,  257,  261,
  148,   44,  267,  261,   41,  261,   40,   13,   41,   41,
   85,   59,  126,  154,   49,  123,   56,   96,   -1,   40,
   -1,   -1,   -1,   -1,   -1,   59,   -1,   61,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   59,   -1,  256,  257,   -1,   -1,   -1,   -1,   -1,
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
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,    1,  260,
   -1,   -1,  263,  264,   -1,  266,    9,  268,  256,  257,
   13,   -1,  260,   -1,   -1,  263,  264,   -1,  266,   -1,
  268,   -1,  256,  257,   -1,   -1,  260,   -1,   -1,  263,
  264,   -1,   -1,   -1,  268,   -1,  256,  257,   -1,   -1,
  260,   -1,   45,  263,  264,   48,   -1,   -1,  268,   -1,
  256,  257,   55,   -1,  260,   -1,   -1,  263,  264,   62,
   63,   -1,  268,   -1,  256,  257,   -1,   -1,  260,   -1,
   -1,  263,  264,   -1,   -1,   -1,  268,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   91,   -1,
   -1,   -1,   -1,   -1,   -1,   98,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  113,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  125,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  134,  135,  136,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  148,  149,
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
"$$17 :",
"sentencia_loop : LOOP $$16 $$17 bloque_loop",
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
"$$18 :",
"bloque_IF : bloque_sent ELSE $$18 bloque_sent",
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
"$$19 :",
"asignacion : ID '=' expresion $$19",
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
//#line 570 "Parser.java"
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
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 15:
//#line 35 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas)+": falta el 'end' al final del bloque");}
break;
case 21:
//#line 46 "Gramatica.nueva.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");}
break;
case 23:
//#line 48 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 25:
//#line 49 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 32:
//#line 63 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 34:
//#line 64 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+(Al.LineasContadas-1)+": falta el ;");}
break;
case 37:
//#line 68 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'print'");}
break;
case 39:
//#line 69 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
break;
case 40:
//#line 70 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'return'");}
break;
case 47:
//#line 82 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia 'if'"); PI.FinCondicion();}
break;
case 50:
//#line 86 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la condicion del IF");}
break;
case 52:
//#line 87 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta abrir parentesis");}
break;
case 54:
//#line 88 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta cerrar parentesis");}
break;
case 56:
//#line 89 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta  parentesis");}
break;
case 58:
//#line 90 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Falta la palabra THEN");}
break;
case 60:
//#line 91 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": errores múltiples en IF");}
break;
case 62:
//#line 93 "Gramatica.nueva.y"
{Estructuras.addLog("Línea "+Al.LineasContadas+": Sentencia de iteracion");}
break;
case 63:
//#line 93 "Gramatica.nueva.y"
{PI.InicLoop();}
break;
case 65:
//#line 96 "Gramatica.nueva.y"
{PI.FinLoop();}
break;
case 67:
//#line 100 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la palabra UNTIL");}
break;
case 68:
//#line 101 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": falta la condicion luego de UNTIL");}
break;
case 69:
//#line 102 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Se olvido del UNTIL?");}
break;
case 74:
//#line 115 "Gramatica.nueva.y"
{PI.FinIf();}
break;
case 75:
//#line 116 "Gramatica.nueva.y"
{PI.FinThenElse();}
break;
case 76:
//#line 116 "Gramatica.nueva.y"
{PI.FinIf();}
break;
case 77:
//#line 117 "Gramatica.nueva.y"
{Estructuras.addError("syntax error en línea "+Al.LineasContadas+": Then mal escrito");}
break;
case 78:
//#line 121 "Gramatica.nueva.y"
{PI.addPolaco("<");}
break;
case 79:
//#line 122 "Gramatica.nueva.y"
{PI.addPolaco(">");}
break;
case 80:
//#line 123 "Gramatica.nueva.y"
{PI.addPolaco("<=");}
break;
case 81:
//#line 124 "Gramatica.nueva.y"
{PI.addPolaco(">=");}
break;
case 82:
//#line 125 "Gramatica.nueva.y"
{PI.addPolaco("==");}
break;
case 83:
//#line 126 "Gramatica.nueva.y"
{PI.addPolaco("!=");}
break;
case 89:
//#line 143 "Gramatica.nueva.y"
{Estructuras.addLog("Linea "+Al.LineasContadas+": Sentencia de asignacion");}
break;
case 90:
//#line 143 "Gramatica.nueva.y"
{PI.addPolaco(val_peek(3).sval); PI.addPolaco("=");}
break;
case 91:
//#line 146 "Gramatica.nueva.y"
{PI.addPolaco("+");}
break;
case 92:
//#line 147 "Gramatica.nueva.y"
{PI.addPolaco("-");}
break;
case 94:
//#line 151 "Gramatica.nueva.y"
{PI.addPolaco("*");}
break;
case 95:
//#line 152 "Gramatica.nueva.y"
{PI.addPolaco("/");}
break;
case 97:
//#line 156 "Gramatica.nueva.y"
{PI.addPolaco(val_peek(0).sval);}
break;
case 98:
//#line 157 "Gramatica.nueva.y"
{PI.addPolaco(val_peek(0).sval);}
break;
//#line 887 "Parser.java"
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
