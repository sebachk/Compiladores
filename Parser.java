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
    0,    0,    2,    2,    5,    5,    3,    3,    6,    6,
    6,    6,    6,    6,    7,   14,   14,   10,   10,    9,
   15,   15,   15,   15,   15,   15,    1,   16,   16,   11,
   11,   17,    4,    8,   12,   12,   12,   18,   18,   18,
   19,   19,   19,   13,
};
final static short yylen[] = {                            2,
    1,    1,    1,    1,    2,    1,    1,    1,    1,    6,
    5,    5,    3,    4,    2,    2,    2,    1,    3,    3,
    1,    1,    2,    2,    2,    2,    6,    2,    3,    2,
    1,    1,    3,    4,    3,    3,    1,    3,    3,    1,
    1,    1,    3,    1,
};
final static short yydefred[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,   44,    0,    1,
    2,    3,    4,    7,    8,    9,    0,    0,    0,    0,
    0,   41,   42,    0,    0,    0,   40,    0,    0,    0,
    0,    0,   15,   32,    0,    0,    0,    0,    0,    0,
    0,   13,    0,    0,    0,    0,    0,    5,   33,    0,
   17,   16,    0,   30,   34,    0,    0,    0,    0,    0,
    0,    0,   43,    0,    0,   38,   39,    0,    0,   14,
   12,    0,   23,   24,   25,   26,    0,   11,    0,    0,
    0,   10,   29,   27,    0,   19,
};
final static short yydgoto[] = {                          9,
   10,   11,   12,   13,   30,   14,   15,   16,   38,   82,
   35,   39,   17,   33,   61,   69,   36,   26,   27,
};
final static short yysindex[] = {                      -149,
  -34,  -33,  -32,  -40, -246, -125, -137,    0,    0,    0,
    0,    0,    0,    0,    0,    0, -243, -237,  -40,  -40,
 -233,    0,    0,  -40,  -28,  -14,    0,  -11, -125, -232,
 -231,  -56,    0,    0,   11, -237,  -27,   16,  -21,   17,
    8,    0,  -40,  -40,  -40,  -40, -227,    0,    0,  -40,
    0,    0,   -9,    0,    0, -196,   10,   14,   15,   18,
  -40,   13,    0,  -14,  -14,    0,    0, -237,   28,    0,
    0, -137,    0,    0,    0,    0,  -24,    0, -227, -189,
 -192,    0,    0,    0, -137,    0,
};
final static short yyrindex[] = {                         0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    1,    0,    0, -187,    0,
    0,    0,    0,    0,    0,   40,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,  -38,  -36,    0,    0,
    0,    0,    0,   23,   45,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   59,    0,   48,    0,
   73,    0,    0,    0,    0,    0,
};
final static short yygindex[] = {                         0,
    0,    2,    7,   12,   62,    0,    0,    0,   43,    0,
   58,    6,  -42,   63,    0,   19,   29,    4,    9,
};
final static int YYTABLESIZE=343;
static short yytable[];
static { yytable();}
static void yytable(){
yytable = new short[]{                         24,
   37,   21,   51,   22,   68,   18,   20,   21,   31,   25,
   28,   60,   29,   32,   43,   43,   44,   44,   43,   34,
   44,   43,   35,   44,   37,   40,   19,   45,   47,   41,
   42,   55,   46,   37,   49,   29,   68,   50,   57,   59,
   58,   37,    8,   37,   36,   37,   64,   65,   63,   71,
   43,   53,   44,   66,   67,   35,   56,   62,   20,   37,
   37,   37,   37,   35,   72,   35,   77,   35,   80,   85,
   73,   78,   18,   81,   74,   75,    6,   36,   76,    6,
   31,   35,   35,   35,   35,   36,   86,   36,   28,   36,
   48,   84,   70,   54,   52,    0,   79,   83,    0,   20,
    0,    0,    0,   36,   36,   36,   36,    1,    0,    0,
    2,    0,    0,    3,    4,    5,    6,    0,    7,    1,
    8,    0,    2,    0,    0,    3,    4,    0,    6,    0,
    7,    1,    8,    0,    2,    0,    0,    3,    4,    0,
    0,    0,    7,    0,    8,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   32,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   22,   23,   21,   21,
   22,   22,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   37,    0,    0,
   37,    0,   37,   37,   37,    0,    0,   37,   37,   37,
   37,    0,    0,    0,    0,    0,    0,    0,    0,   35,
    0,    0,   35,    0,   35,   35,   35,    0,    0,   35,
   35,   35,   35,    0,    0,    0,    0,    0,    0,    0,
    0,   36,    0,    0,   36,    0,   36,   36,   36,    0,
    0,   36,   36,   36,   36,   20,    0,    0,   20,    0,
   20,   20,   20,    0,    0,   20,   20,   20,   20,   18,
    0,    0,   18,    0,    0,   18,   18,    0,    0,   18,
   18,   18,   18,
};
}
static short yycheck[];
static { yycheck(); }
static void yycheck() {
yycheck = new short[] {                         40,
    0,   40,   59,   40,   47,   40,   40,   40,    7,    4,
  257,   33,    6,  257,   43,   43,   45,   45,   43,  257,
   45,   43,    0,   45,   19,  259,   61,   42,   40,   24,
   59,   59,   47,   33,  267,   29,   79,  269,   60,   61,
   62,   41,  270,   43,    0,   45,   43,   44,   41,   59,
   43,   41,   45,   45,   46,   33,   41,   41,    0,   59,
   60,   61,   62,   41,  261,   43,   61,   45,   41,  262,
   61,   59,    0,   72,   61,   61,  266,   33,   61,  267,
   41,   59,   60,   61,   62,   41,   85,   43,   41,   45,
   29,   80,   50,   36,   32,   -1,   68,   79,   -1,   41,
   -1,   -1,   -1,   59,   60,   61,   62,  257,   -1,   -1,
  260,   -1,   -1,  263,  264,  265,  266,   -1,  268,  257,
  270,   -1,  260,   -1,   -1,  263,  264,   -1,  266,   -1,
  268,  257,  270,   -1,  260,   -1,   -1,  263,  264,   -1,
   -1,   -1,  268,   -1,  270,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
  257,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,  257,  258,  257,  258,
  257,  258,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,   -1,   -1,
  260,   -1,  262,  263,  264,   -1,   -1,  267,  268,  269,
  270,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,  257,
   -1,   -1,  260,   -1,  262,  263,  264,   -1,   -1,  267,
  268,  269,  270,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,  257,   -1,   -1,  260,   -1,  262,  263,  264,   -1,
   -1,  267,  268,  269,  270,  257,   -1,   -1,  260,   -1,
  262,  263,  264,   -1,   -1,  267,  268,  269,  270,  257,
   -1,   -1,  260,   -1,   -1,  263,  264,   -1,   -1,  267,
  268,  269,  270,
};
}
final static short YYFINAL=9;
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
"$accept : coso",
"coso : sentencia_declar_funcion",
"coso : bloque_sent",
"bloque_sent : sentencia",
"bloque_sent : bloque_funcion",
"lista_sent : sentencia lista_sent",
"lista_sent : sentencia",
"sentencia : sentencia_ejec",
"sentencia : sentencia_declarativa_tipo",
"sentencia_ejec : asignacion",
"sentencia_ejec : if '(' cond ')' then bloque_if",
"sentencia_ejec : print '(' cadena ')' ';'",
"sentencia_ejec : ID '(' lista_parametros ')' ';'",
"sentencia_ejec : return expresion ';'",
"sentencia_ejec : loop bloque_sent until cond",
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
"asignacion : ID '=' expresion ';'",
"expresion : expresion '+' termino",
"expresion : expresion '-' termino",
"expresion : termino",
"termino : termino '*' factor",
"termino : termino '/' factor",
"termino : factor",
"factor : ID",
"factor : CTE",
"factor : '(' expresion ')'",
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
