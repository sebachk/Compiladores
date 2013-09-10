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
    1,    1,    1,    1,    2,    1,    1,    6,    5,    2,
    3,    4,    4,    2,    2,    2,    1,    3,    3,    1,
    1,    2,    2,    2,    2,    6,    2,    3,    2,    1,
    1,    3,    4,    2,    1,    4,    3,    3,    1,    3,
    3,    1,    1,    1,    1,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    2,
    3,    4,    7,    0,    0,    0,    0,    0,    0,   44,
   45,    0,    0,   42,    0,   46,    0,    0,    0,    0,
    0,    0,   10,   31,    0,    0,    0,    0,    0,    0,
   11,    0,    0,    0,    0,    0,    5,   32,   34,    0,
   14,    0,    0,   13,   29,   36,    0,    0,    0,    0,
    0,    0,    0,    0,    0,   40,   41,    0,    0,   16,
   15,   33,   12,    0,   22,   23,   24,   25,    0,    9,
    0,    0,    0,    8,   28,   26,    0,   18,
};
final static short yydgoto[] = {                          8,
    9,   10,   11,   12,   28,   13,   38,   84,   21,   39,
   35,   29,   30,   51,   62,   69,   36,   31,   23,   24,
};
final static short yysindex[] = {                      -129,
  -38,  -24,  -22, -226, -235, -138, -119,    0,    0,    0,
    0,    0,    0,  -31, -222, -226, -226, -221,    5,    0,
    0,  -35,  -27,    0,   16,    0, -109, -213, -212, -189,
 -109, -198,    0,    0,   31, -222,  -34,   43,   56,   44,
    0, -226, -226, -226, -226, -212,    0,    0,    0,  -59,
    0, -181, -226,    0,    0,    0, -174,   27,   30,   39,
   41, -226,   45,  -27,  -27,    0,    0, -222,   62,    0,
    0,    0,    0, -119,    0,    0,    0,    0,  -16,    0,
 -212, -160, -155,    0,    0,    0, -119,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    1,    0,
    0,    0,   14,    0,    0,    0, -154,    0, -107,    0,
    0,    0,    0,    0,    0,   71,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0, -217, -207,    0,
    0,    0,    0,   36,   49,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,   64,    0,
   73,    0,   78,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,   -4,   -1,   33,  -10,    0,   67,    0,    6,    3,
   85,    0,  -42,   74,    0,   42,   59,  100,   10,   22,
};
final static int YYTABLESIZE=347;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         70,
   43,   15,   32,   68,   27,   14,   22,   42,   42,   43,
   43,   14,   14,   39,   44,   17,   47,   18,   37,   45,
   52,   25,   16,   41,   56,   27,   42,   33,   43,   27,
   19,   20,   14,   43,   34,   37,   14,   40,   68,   20,
   20,   43,   43,   43,   15,   43,   39,   43,   38,   21,
   21,   64,   65,   48,   39,   46,   39,   26,   39,   43,
   43,   43,   43,   19,   79,   66,   67,   50,   37,   83,
   53,   54,   39,   39,   39,   39,   37,   17,   37,   14,
   37,   38,   88,   57,   63,   72,   74,   75,   61,   38,
   76,   38,   14,   38,   37,   37,   37,   37,   42,   77,
   43,   78,   82,   80,   19,    6,   87,   38,   38,   38,
   38,   30,    6,   27,   86,   58,   60,   59,    1,   73,
   55,    2,   85,   71,    3,    4,   81,    1,   49,    7,
    2,   26,    0,    3,    4,    5,    6,    1,    7,    0,
    2,    0,    0,    3,    4,    0,    6,    1,    7,   35,
    2,    0,   35,    3,    4,   35,   35,    0,    7,    0,
   35,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   50,    0,    0,
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
yycheck = new short[] {                         59,
    0,   40,    7,   46,    6,    0,    4,   43,   43,   45,
   45,    6,    7,    0,   42,   40,   27,   40,   16,   47,
   31,  257,   61,   59,   59,   27,   43,   59,   45,   31,
  257,  258,   27,   33,  257,    0,   31,  259,   81,  257,
  258,   41,   42,   43,   40,   45,   33,   47,    0,  257,
  258,   42,   43,  267,   41,   40,   43,  270,   45,   59,
   60,   61,   62,    0,   62,   44,   45,  257,   33,   74,
  269,   41,   59,   60,   61,   62,   41,    0,   43,   74,
   45,   33,   87,   41,   41,  267,  261,   61,   33,   41,
   61,   43,   87,   45,   59,   60,   61,   62,   43,   61,
   45,   61,   41,   59,   41,  266,  262,   59,   60,   61,
   62,   41,  267,   41,   82,   60,   61,   62,  257,   53,
   36,  260,   81,   50,  263,  264,   68,  257,   29,  268,
  260,  270,   -1,  263,  264,  265,  266,  257,  268,   -1,
  260,   -1,   -1,  263,  264,   -1,  266,  257,  268,  257,
  260,   -1,  260,  263,  264,  263,  264,   -1,  268,   -1,
  268,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,
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
"sentencia_ejec : asignacion",
"sentencia_ejec : if '(' cond ')' then bloque_if",
"sentencia_ejec : print '(' cadena ')' ';'",
"sentencia_ejec : llamada_funcion ';'",
"sentencia_ejec : return expresion ';'",
"sentencia_ejec : loop bloque_sent until cond",
"llamada_funcion : ID '(' lista_parametros ')'",
"sentencia_declarativa_tipo : tipo lista_var",
"lista_var : ID lista_var",
"lista_var : ID ';'",
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
"asignacion : ID '=' expresion ';'",
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
