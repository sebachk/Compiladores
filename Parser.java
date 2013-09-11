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
public final static short if=260;
public final static short then=261;
public final static short else=262;
public final static short print=263;
public final static short return=264;
public final static short function=265;
public final static short begin=266;
public final static short end=267;
public final static short loop=268;
public final static short until=269;
public final static short uint=270;
public final static short YYERRCODE=256;
final static short yylhs[] = {                           -1,
    0,    0,    2,    2,    5,    5,    3,    3,    3,    3,
    3,    3,    9,   12,   14,   14,    8,    8,    7,   15,
   15,   15,   15,   15,   15,    1,   16,   16,   11,   11,
   17,    4,    4,   18,   18,    6,   10,   10,   10,   19,
   19,   19,   20,   20,   20,   13,
};
final static short yylen[] = {                            2,
    1,    1,    1,    1,    2,    1,    2,    6,    5,    2,
    3,    4,    4,    3,    2,    1,    1,    3,    3,    1,
    1,    2,    2,    2,    2,    6,    2,    3,    2,    1,
    1,    3,    4,    2,    1,    3,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    2,
    3,    4,    0,    0,    0,    0,    0,    0,    0,   44,
   45,    0,    0,   42,    0,   46,    0,    0,    0,    0,
    0,    0,    7,   10,   31,    0,    0,    0,    0,    0,
    0,   11,    0,    0,    0,    0,    0,    5,   32,   34,
    0,    0,    0,    0,   13,   29,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   40,   41,    0,    0,   15,
   14,   33,   12,    0,   22,   23,   24,   25,    0,    9,
    0,    0,    0,    8,   28,   26,    0,   18,
};
final static short yydgoto[] = {                          8,
    9,   10,   11,   12,   28,   13,   39,   84,   21,   40,
   36,   29,   30,   52,   62,   69,   37,   31,   23,   24,
};
final static short yysindex[] = {                      -129,
  -38,  -20,  -18, -231, -239, -138, -119,    0,    0,    0,
    0,    0,  -27,  -24, -218, -231, -231, -214,   12,    0,
    0,  -35,  -31,    0,   13,    0, -109, -213, -212, -201,
 -109, -203,    0,    0,    0,   26, -218,  -28,   27,   56,
   30,    0, -231, -231, -231, -231, -212,    0,    0,    0,
 -201,   25, -195, -231,    0,    0, -176,   39,   41,   42,
   43, -231,   28,  -31,  -31,    0,    0, -218,   45,    0,
    0,    0,    0, -119,    0,    0,    0,    0,  -28,    0,
 -212, -178, -171,    0,    0,    0, -119,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    0,
    0,    0,   14,    0,    0,    0, -161,    0, -107,    0,
    0,    0,    0,    0,    0,    0,   66,   53,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   54,    0,    0,    0,    0,    0,    0, -229, -227,    0,
    0,    0,    0,   36,   49,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   64,    0,
   73,    0,   78,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   -4,   -6,   33,  -22,    0,   67,    0,    6,    3,
   83,    0,  -43,   72,    0,   46,   61,   95,   -3,    5,
};
final static int YYTABLESIZE=347;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         27,
   43,   15,   32,   68,   48,   14,   22,   43,   53,   44,
   45,   14,   14,   39,   43,   46,   44,   25,   38,   17,
   27,   18,   16,   42,   27,   19,   20,   20,   20,   21,
   21,   33,   14,   43,   34,   37,   14,   68,   35,   64,
   65,   43,   43,   43,   41,   43,   39,   43,   38,   66,
   67,   15,   47,   49,   39,   51,   39,   26,   39,   43,
   43,   43,   43,   19,   79,   54,   55,   57,   37,   83,
   63,   72,   39,   39,   39,   39,   37,   17,   37,   14,
   37,   38,   88,   71,   74,   82,   80,    6,   61,   38,
   87,   38,   14,   38,   37,   37,   37,   37,   43,   75,
   44,   76,   77,   78,   19,    6,   30,   38,   38,   38,
   38,   36,   16,   27,   86,   58,   60,   59,    1,   56,
   73,    2,   70,   50,    3,    4,   85,    1,   81,    7,
    2,   26,    0,    3,    4,    5,    6,    1,    7,    0,
    2,    0,    0,    3,    4,    0,    6,    1,    7,   35,
    2,    0,   35,    3,    4,   35,   35,    0,    7,    0,
   35,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   43,    0,    0,
   43,    0,   43,   43,   43,    0,    0,   43,   43,   43,
   39,    0,    0,   39,    0,   39,   39,   39,    0,    0,
   39,   39,   39,    0,    0,    0,    0,    0,    0,    0,
    0,    0,   37,    0,    0,   37,    0,   37,   37,   37,
    0,    0,   37,   37,   37,   38,    0,    0,   38,    0,
   38,   38,   38,    0,    0,   38,   38,   38,    0,    0,
   19,    0,    0,   19,    0,   19,   19,   19,    0,    0,
   19,   19,   19,    0,   17,    0,    0,   17,    0,    0,
   17,   17,    0,    0,   17,   17,   17,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                          6,
    0,   40,    7,   47,   27,    0,    4,   43,   31,   45,
   42,    6,    7,    0,   43,   47,   45,  257,   16,   40,
   27,   40,   61,   59,   31,  257,  258,  257,  258,  257,
  258,   59,   27,   33,   59,    0,   31,   81,  257,   43,
   44,   41,   42,   43,  259,   45,   33,   47,    0,   45,
   46,   40,   40,  267,   41,  257,   43,  270,   45,   59,
   60,   61,   62,    0,   62,  269,   41,   41,   33,   74,
   41,  267,   59,   60,   61,   62,   41,    0,   43,   74,
   45,   33,   87,   59,  261,   41,   59,  266,   33,   41,
  262,   43,   87,   45,   59,   60,   61,   62,   43,   61,
   45,   61,   61,   61,   41,  267,   41,   59,   60,   61,
   62,   59,   59,   41,   82,   60,   61,   62,  257,   37,
   54,  260,   51,   29,  263,  264,   81,  257,   68,  268,
  260,  270,   -1,  263,  264,  265,  266,  257,  268,   -1,
  260,   -1,   -1,  263,  264,   -1,  266,  257,  268,  257,
  260,   -1,  260,  263,  264,  263,  264,   -1,  268,   -1,
  268,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,
  260,   -1,  262,  263,  264,   -1,   -1,  267,  268,  269,
  257,   -1,   -1,  260,   -1,  262,  263,  264,   -1,   -1,
  267,  268,  269,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,  257,   -1,   -1,  260,   -1,  262,  263,  264,
   -1,   -1,  267,  268,  269,  257,   -1,   -1,  260,   -1,
  262,  263,  264,   -1,   -1,  267,  268,  269,   -1,   -1,
  257,   -1,   -1,  260,   -1,  262,  263,  264,   -1,   -1,
  267,  268,  269,   -1,  257,   -1,   -1,  260,   -1,   -1,
  263,  264,   -1,   -1,  267,  268,  269,
};
}
final static short YYFINAL=8;
final static short YYMAXTOKEN=270;
final static String yyname[] = {
"end-of-file",null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,null,
null,null,null,"'!'",null,null,null,null,null,null,"'('","')'","'*'","'+'",null,
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
null,null,null,null,null,null,"ID","CTE","cadena","if","then","else","print",
"return","function","begin","end","loop","until","uint",
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
"sentencia_ejec : if '(' cond ')' then bloque_if",
"sentencia_ejec : print '(' cadena ')' ';'",
"sentencia_ejec : llamada_funcion ';'",
"sentencia_ejec : return expresion ';'",
"sentencia_ejec : loop bloque_sent until cond",
"llamada_funcion : ID '(' lista_parametros ')'",
"sentencia_declarativa_tipo : tipo lista_var ';'",
"lista_var : ID lista_var",
"lista_var : ID",
"bloque_if : bloque_sent",
"bloque_if : bloque_sent else bloque_sent",
"cond : expresion comparador expresion",
"comparador : '<'",
"comparador : '>'",
"comparador : '<' '='",
"comparador : '>' '='",
"comparador : '=' '='",
"comparador : '!' '='",
"sentencia_declar_funcion : function ID '(' parametros ')' bloque_funcion",
"parametros : tipo parametro",
"parametros : tipo parametro parametros",
"lista_parametros : parametro lista_parametros",
"lista_parametros : parametro",
"parametro : ID",
"bloque_funcion : begin lista_sent end",
"bloque_funcion : begin lista_sent_declar lista_sent end",
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

//#line 93 "Docs/Gramatica.nueva.y"


String prueba_Codigo_De_gramatica
//#line 314 "Parser.java"
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
/**
 * A default run method, used for operating this parser
 * object in the background.  It is intended for extending Thread
 * or implementing Runnable.  Turn off with -Jnorun .
 */
public void run()
{
  yyparse();
}
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
