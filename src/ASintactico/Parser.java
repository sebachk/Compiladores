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
    0,    0,    2,    2,    5,    5,    3,    7,    3,   10,
    3,    3,   13,    3,   14,    3,   11,   11,   16,   18,
   18,    9,    9,    8,   19,   19,   19,   19,   19,   19,
    1,    1,   20,   20,   15,   15,   21,    4,    4,   22,
   22,    6,   12,   12,   12,   23,   23,   23,   24,   24,
   24,   17,
};
final static short yylen[] = {                            2,
    1,    1,    1,    1,    2,    1,    2,    0,    7,    0,
    6,    2,    0,    4,    0,    5,    4,    3,    3,    2,
    1,    1,    3,    3,    1,    1,    1,    1,    1,    1,
    6,    5,    2,    3,    2,    1,    1,    3,    4,    2,
    1,    3,    3,    3,    1,    3,    3,    1,    1,    1,
    1,    1,
};
final static short yydefred[] = {                         0,
    0,    8,   10,   13,    0,    0,   15,    0,    1,    2,
    3,    4,    0,    0,    0,    0,    0,    0,    0,    0,
   52,    0,    0,    0,    0,    0,    0,    7,   12,   37,
   18,    0,    0,    0,   50,   51,    0,    0,   48,    0,
    0,    0,    0,    5,   38,   40,    0,    0,    0,    0,
   17,   35,    0,    0,    0,    0,    0,    0,    0,   14,
    0,    0,    0,   20,   19,   39,    0,    0,    0,   46,
   47,    0,   28,   27,   30,   29,   25,   26,    0,    0,
   32,    0,    0,   16,    0,    0,   11,   34,   31,    0,
    9,    0,   23,
};
final static short yydgoto[] = {                          8,
    9,   10,   11,   12,   23,   13,   17,   57,   91,   18,
   36,   58,   19,   27,   32,   24,   25,   48,   79,   63,
   33,   26,   38,   39,
};
final static short yysindex[] = {                      -129,
  -28,    0,    0,    0, -222, -148,    0,    0,    0,    0,
    0,    0,  -14,  -10,  -37, -232,   -2,   10, -232,   11,
    0, -169, -215, -217, -203, -169, -119,    0,    0,    0,
    0,   14, -201,   17,    0,    0,  -21,  -24,    0, -232,
 -200,  -30,  -38,    0,    0,    0, -203,   -1, -199, -198,
    0,    0, -232, -232, -232, -232,   28,  -43,   31,    0,
 -193, -201,   34,    0,    0,    0, -232,  -24,  -24,    0,
    0, -185,    0,    0,    0,    0,    0,    0, -232,   18,
    0, -217, -193,    0, -119,  -21,    0,    0,    0, -184,
    0, -119,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0, -188,    0, -109,    0,    0,    0,    0,    0,    0,
    0,    0,   46,    1,    0,    0,   30,   21,    0,    0,
    0,    0,    0,    0,    0,    0,   33,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   41,   65,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,   52,    0,    0,    0,   85,    0,    0,    0,   98,
    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,  -18,    8,  -55,   -6,    0,    0,   29,    0,    0,
    5,   -9,    0,    0,   69,    0,  -35,   57,    0,   23,
   45,   87,  -17,  -16,
};
final static int YYTABLESIZE=367;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         53,
   49,   54,   61,   31,   14,   81,   37,   62,   50,   42,
   14,   15,   53,   22,   54,   44,   77,   55,   78,   49,
   45,   53,   56,   54,   34,   35,   14,   89,   60,   22,
   14,   14,   16,   22,   20,   68,   69,   40,   70,   71,
   43,   49,   49,   49,   28,   49,   62,   49,   29,   41,
   43,   45,   21,   47,   51,   30,   15,   65,   59,   49,
   49,   45,   49,   45,   44,   45,   90,   66,   72,   86,
   67,   80,    6,   93,   83,   85,   87,   92,    6,   45,
   45,   43,   45,   43,   24,   43,   36,    1,   42,   14,
    2,   21,   33,    3,    4,   84,   14,   22,    7,   43,
   43,   52,   43,   64,   88,   44,   82,   44,    1,   44,
   46,    2,    0,    0,    3,    4,    0,    0,    0,    7,
    0,   21,    0,   44,   44,   24,   44,    1,    0,    0,
    2,    0,    0,    3,    4,    5,    6,    1,    7,    0,
    2,    0,    0,    3,    4,    0,    6,   41,    7,    0,
   41,    0,    0,   41,   41,    0,    0,    0,   41,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   30,
    0,    0,    0,    0,    0,    0,    0,   73,   74,   75,
   76,   21,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   49,    0,    0,
   49,    0,   49,   49,   49,    0,    0,   49,   49,   49,
    0,   49,   49,   49,   49,    0,    0,   45,    0,    0,
   45,    0,   45,   45,   45,    0,    0,   45,   45,   45,
    0,   45,   45,   45,   45,    0,    0,   43,    0,    0,
   43,    0,   43,   43,   43,    0,    0,   43,   43,   43,
    0,   43,   43,   43,   43,    0,    0,    0,    0,    0,
    0,   44,    0,    0,   44,    0,   44,   44,   44,    0,
    0,   44,   44,   44,    0,   44,   44,   44,   44,    0,
    0,   24,    0,    0,   24,    0,   24,   24,   24,    0,
    0,   24,   24,   24,   22,    0,    0,   22,    0,    0,
   22,   22,    0,    0,   22,   22,   22,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         43,
    0,   45,   41,   41,    0,   61,   16,   43,   27,   19,
    6,   40,   43,    6,   45,   22,   60,   42,   62,   26,
    0,   43,   47,   45,  257,  258,   22,   83,   59,   22,
   26,   27,   61,   26,  257,   53,   54,   40,   55,   56,
    0,   41,   42,   43,   59,   45,   82,   47,   59,   40,
   40,  267,  270,  257,   41,  257,   40,   59,  259,   59,
   60,   41,   62,   43,    0,   45,   85,  267,   41,   79,
  269,   41,  266,   92,   41,  261,   59,  262,  267,   59,
   60,   41,   62,   43,    0,   45,   41,  257,   59,   85,
  260,   59,   41,  263,  264,   67,   92,    0,  268,   59,
   60,   33,   62,   47,   82,   41,   62,   43,  257,   45,
   24,  260,   -1,   -1,  263,  264,   -1,   -1,   -1,  268,
   -1,  270,   -1,   59,   60,   41,   62,  257,   -1,   -1,
  260,   -1,   -1,  263,  264,  265,  266,  257,  268,   -1,
  260,   -1,   -1,  263,  264,   -1,  266,  257,  268,   -1,
  260,   -1,   -1,  263,  264,   -1,   -1,   -1,  268,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  271,  272,  273,
  274,  270,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,
  260,   -1,  262,  263,  264,   -1,   -1,  267,  268,  269,
   -1,  271,  272,  273,  274,   -1,   -1,  257,   -1,   -1,
  260,   -1,  262,  263,  264,   -1,   -1,  267,  268,  269,
   -1,  271,  272,  273,  274,   -1,   -1,  257,   -1,   -1,
  260,   -1,  262,  263,  264,   -1,   -1,  267,  268,  269,
   -1,  271,  272,  273,  274,   -1,   -1,   -1,   -1,   -1,
   -1,  257,   -1,   -1,  260,   -1,  262,  263,  264,   -1,
   -1,  267,  268,  269,   -1,  271,  272,  273,  274,   -1,
   -1,  257,   -1,   -1,  260,   -1,  262,  263,  264,   -1,
   -1,  267,  268,  269,  257,   -1,   -1,  260,   -1,   -1,
  263,  264,   -1,   -1,  267,  268,  269,
};
}
final static short YYFINAL=8;
final static short YYMAXTOKEN=274;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,"'('","')'","'*'","'+'",null,
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
"$accept : declaracion",
"declaracion : sentencia_declar_funcion",
"declaracion : bloque_sent",
"bloque_sent : sentencia_ejec",
"bloque_sent : bloque_funcion",
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
"llamada_funcion : ID '(' lista_parametros ')'",
"llamada_funcion : ID '(' ')'",
"sentencia_declarativa_tipo : tipo lista_var ';'",
"lista_var : ID lista_var",
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
"sentencia_declar_funcion : FUNCTION ID '(' parametros ')' bloque_funcion",
"sentencia_declar_funcion : FUNCTION ID '(' ')' bloque_funcion",
"parametros : tipo parametro",
"parametros : tipo parametro parametros",
"lista_parametros : parametro lista_parametros",
"lista_parametros : parametro",
"parametro : ID",
"bloque_funcion : BEGIN lista_sent END",
"bloque_funcion : BEGIN lista_sent_declar lista_sent END",
"lista_sent_declar : sentencia_declarativa_tipo lista_sent_declar",
"lista_sent_declar : sentencia_declarativa_tipo",
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

//#line 100 "Gramatica.nueva.y"

AnalizadorLexico Al = new ALexico.AnalizadorLexico(new File("Docs/codigo.txt"));



int yylex(){
	ALexico.Token t = Al.GetToken();

	if(t!=null){
	System.out.println("Token = "+ALexico.Estructuras.getStringToken(t.getIdentif_tt()));
	yylval.ival=t.getIndice_ts();
	return t.getIdentif_tt();
	}
	yylval.ival=0;
	return 0;
}
void yyerror(String e){
	yyerror(e,yystate,yychar);
}


public void yyerror (String descripcion, int yystate, int token) 
  {
     System.err.println ("Error en línea "+Integer.toString(Al.LineasContadas)+" : "+descripcion);
     System.err.println ("Token leído : "+yyname[token]);
     System.err.print("Token(s) que se esperaba(n) : ");

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

    System.err.println(nombresTokens);
  }


public void run()
{
  System.out.println(yyparse());
}
//#line 394 "Parser.java"
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
//#line 11 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia declarativa de funcion");}
break;
case 8:
//#line 24 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia 'if'");}
break;
case 10:
//#line 25 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia 'print'");}
break;
case 12:
//#line 26 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia de llamado de funcion");}
break;
case 13:
//#line 27 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia 'return'");}
break;
case 15:
//#line 28 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia de iteracion");}
break;
case 19:
//#line 34 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia declarativa de variables");}
break;
case 42:
//#line 79 "Gramatica.nueva.y"
{System.out.println("Linea "+Al.LineasContadas+": Sentencia de asignacion");}
break;
//#line 575 "Parser.java"
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
