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
    6,    6,    6,    9,    9,    8,    8,    7,   12,    7,
   15,    7,    7,   18,    7,   19,    7,    7,    7,   16,
   16,   10,   10,   22,   22,   14,   14,   13,   23,   23,
   23,   23,   23,   23,    5,    5,   20,   20,   24,   11,
   17,   17,   17,   25,   25,   25,   26,   26,   26,   21,
};
final static short yylen[] = {                            2,
    3,    1,    2,    1,    1,    6,    5,    3,    1,    1,
    3,    4,    2,    2,    1,    2,    1,    2,    0,    7,
    0,    6,    2,    0,    4,    0,    5,    1,    2,    4,
    3,    3,    3,    3,    1,    1,    3,    3,    1,    1,
    1,    1,    1,    1,    2,    4,    3,    1,    1,    3,
    3,    3,    1,    3,    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,   19,   21,   24,    0,    0,   26,
   28,    0,    0,    4,    5,   10,    9,    0,    0,   13,
   29,    0,    0,    0,    0,    0,    0,    0,    0,   60,
    0,    0,    0,    0,    0,    0,    1,    3,   18,   23,
    0,   58,   31,   59,    0,    0,    0,    0,   56,    0,
    0,    0,    0,    8,    0,   16,   11,    0,   14,    0,
    0,    0,    0,    0,    0,   30,    0,    0,    0,    0,
    0,    0,   25,    0,    0,    0,   12,   33,    0,   32,
    0,    0,    0,   47,   54,   55,    0,   42,   41,   44,
   43,   39,   40,    0,    0,    0,    7,    0,    0,   34,
   27,    0,    0,   22,    6,    0,    0,   20,   46,    0,
   37,
};
final static short yydgoto[] = {                          2,
   12,   13,   14,   15,   75,   16,   17,   32,   33,   34,
   18,   24,   70,  108,   25,   44,   45,   26,   36,   46,
   35,   62,   94,   47,   48,   49,
};
final static short yysindex[] = {                      -253,
   73,    0,  -30,  -25,    0,    0,    0, -205,  -50,    0,
    0, -251,   73,    0,    0,    0,    0,  -32,  -26,    0,
    0,   56, -204,   25,   30, -204,   32,   40,   28,    0,
   99, -175,   99, -177, -181,   86,    0,    0,    0,    0,
   54,    0,    0,    0,  -31,   55,   51,  -17,    0,  -31,
 -204, -161,   12,    0,  -36,    0,    0, -167,    0,   42,
   60,   44, -164, -204, -204,    0, -204, -204, -204,   65,
   23,   66,    0, -249,   67, -148,    0,    0, -147,    0,
 -204,  -17,  -17,    0,    0,    0, -150,    0,    0,    0,
    0,    0,    0, -204,   53, -154,    0, -249,   70,    0,
    0,   86,  -31,    0,    0, -177, -146,    0,    0,   86,
    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0, -152,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
 -145,    0,    0,  101,    0,    0,    0,    0,    0,    0,
  -41,    0,    0,    0,    6,    0,   76,  -21,    0,   61,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   62,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   -1,   19,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   78,    0,
    0,    0,   43,    0,    0,    0,   59,    0,    0,    0,
    0,
};
final static short yygindex[] = {                         0,
  110,    0,    0,  -28,   18,  -63,    1,   15,   91,    0,
    0,    0,   45,    0,    0,   36,    5,    0,    0,   63,
  -29,   48,    0,    0,   24,   22,
};
final static int YYTABLESIZE=369;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         57,
   57,   57,   57,   57,   74,   57,   96,   63,   11,   31,
   97,   64,    1,   65,   22,   37,    9,   57,   57,   53,
   57,   53,   53,   53,   68,   76,   39,   50,   21,   69,
   53,   31,   40,   31,  105,   23,   19,   53,   53,   51,
   53,   51,   51,   51,   19,   56,   49,   58,   19,   49,
   27,   28,   41,   42,   64,   71,   65,   51,   51,   52,
   51,   52,   52,   52,   51,   64,   19,   65,   19,   52,
   73,   19,   54,  107,   60,   61,   76,   52,   52,   55,
   52,  111,   92,   38,   93,   71,   21,   82,   83,   85,
   86,   57,   30,   22,   67,   66,   43,   72,  103,   77,
   78,   38,   80,   79,   81,   87,   95,   98,   99,   61,
  102,  104,   20,  106,    2,  110,   48,   36,   45,   50,
   35,   17,   38,  109,   59,  101,  100,    0,    0,   84,
    0,   11,    0,    0,    0,    0,    0,   19,    0,    0,
    0,    0,    0,    0,   11,   19,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   11,    0,   15,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   29,    4,    0,    0,    5,
    0,    0,    6,    7,   57,   57,    0,   10,   57,   30,
   57,   57,   57,   57,   57,   57,   57,   57,    0,   57,
   57,   57,   57,   30,   53,   53,   20,    0,   53,    0,
   53,   53,   53,   53,   53,   53,   53,   53,    0,   53,
   53,   53,   53,    0,   51,   51,    0,    0,   51,    0,
   51,   51,   51,   51,   51,   51,   51,   51,    0,   51,
   51,   51,   51,    0,   52,   52,    0,    0,   52,    0,
   52,   52,   52,   52,   52,   52,   52,   52,    0,   52,
   52,   52,   52,   88,   89,   90,   91,    0,   38,   38,
    0,    0,   38,    0,   38,   38,   38,   38,   38,   38,
   38,   38,   41,   42,   36,   36,    0,    0,   36,    0,
    0,   36,   36,   36,   36,   36,   36,   36,    3,    4,
    0,    0,    5,    0,    0,    6,    7,    8,    9,    0,
   10,    3,    4,    0,    0,    5,    0,    0,    6,    7,
    0,    9,    0,   10,   29,    4,   15,   15,    5,    0,
   15,    6,    7,   15,   15,    0,   10,    0,   15,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         41,
   42,   43,   44,   45,   41,   47,  256,   36,   59,    9,
   74,   43,  266,   45,   40,  267,  266,   59,   60,   41,
   62,   43,   44,   45,   42,   55,   59,   23,   59,   47,
   26,   31,   59,   33,   98,   61,    1,   59,   60,   41,
   62,   43,   44,   45,    9,   31,   41,   33,   13,   44,
  256,  257,  257,  258,   43,   51,   45,   59,   60,   41,
   62,   43,   44,   45,   40,   43,   31,   45,   33,   40,
   59,   36,   41,  102,  256,  257,  106,   59,   60,   40,
   62,  110,   60,   41,   62,   81,   59,   64,   65,   68,
   69,  267,  270,   40,   44,   41,   41,  259,   94,  267,
   59,   59,   59,   44,  269,   41,   41,   41,  257,  257,
  261,   59,  267,   44,  267,  262,   41,   59,   41,   59,
   59,  267,   13,  106,   34,   81,   79,   -1,   -1,   67,
   -1,   59,   -1,   -1,   -1,   -1,   -1,  102,   -1,   -1,
   -1,   -1,   -1,   -1,   59,  110,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   59,   -1,   59,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,  256,  257,   -1,   -1,  260,
   -1,   -1,  263,  264,  256,  257,   -1,  268,  260,  270,
  262,  263,  264,  265,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,  270,  256,  257,  267,   -1,  260,   -1,
  262,  263,  264,  265,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,   -1,  256,  257,   -1,   -1,  260,   -1,
  262,  263,  264,  265,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,   -1,  256,  257,   -1,   -1,  260,   -1,
  262,  263,  264,  265,  266,  267,  268,  269,   -1,  271,
  272,  273,  274,  271,  272,  273,  274,   -1,  256,  257,
   -1,   -1,  260,   -1,  262,  263,  264,  265,  266,  267,
  268,  269,  257,  258,  256,  257,   -1,   -1,  260,   -1,
   -1,  263,  264,  265,  266,  267,  268,  269,  256,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,  265,  266,   -1,
  268,  256,  257,   -1,   -1,  260,   -1,   -1,  263,  264,
   -1,  266,   -1,  268,  256,  257,  256,  257,  260,   -1,
  260,  263,  264,  263,  264,   -1,  268,   -1,  268,
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
"lista_sent : sentencia_ejec lista_sent",
"lista_sent : sentencia_ejec",
"sentencia_ejec : asignacion ';'",
"$$1 :",
"sentencia_ejec : IF $$1 '(' cond ')' THEN bloque_IF",
"$$2 :",
"sentencia_ejec : PRINT $$2 '(' cadena ')' ';'",
"sentencia_ejec : llamada_funcion ';'",
"$$3 :",
"sentencia_ejec : RETURN $$3 expresion ';'",
"$$4 :",
"sentencia_ejec : LOOP $$4 bloque_sent UNTIL cond",
"sentencia_ejec : ';'",
"sentencia_ejec : error ';'",
"llamada_funcion : ID '(' lista_parametros ')'",
"llamada_funcion : ID '(' ')'",
"sentencia_declarativa_tipo : tipo lista_var ';'",
"sentencia_declarativa_tipo : tipo error ';'",
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

//#line 118 "Gramatica.nueva.y"

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
//#line 435 "Parser.java"
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
case 19:
//#line 45 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia 'if'");}
break;
case 21:
//#line 46 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia 'print'");}
break;
case 23:
//#line 47 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
break;
case 24:
//#line 48 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia 'return'");}
break;
case 26:
//#line 49 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia de iteracion");}
break;
case 32:
//#line 58 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");}
break;
case 50:
//#line 97 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia de asignacion");}
break;
//#line 616 "Parser.java"
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
