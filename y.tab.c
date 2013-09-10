#ifndef lint
static char yysccsid[] = "@(#)yaccpar	1.8 (Berkeley) 01/20/90";
#endif
#define YYBYACC 1
#define ID 257
#define CTE 258
#define cadena 259
#define if 260
#define then 261
#define else 262
#define print 263
#define return 264
#define function 265
#define begin 266
#define end 267
#define loop 268
#define until 269
#define uint 270
#define YYERRCODE 256
short yylhs[] = {                                        -1,
    0,    0,    3,    3,    1,    1,    4,    4,    4,    4,
    4,    4,    5,   12,   12,    8,    8,    7,   13,   13,
   13,   13,   13,   13,   14,   15,   15,    9,    9,   16,
    2,    6,   10,   10,   10,   17,   17,   17,   18,   18,
   18,   11,
};
short yylen[] = {                                         2,
    1,    1,    2,    1,    1,    1,    1,    6,    5,    5,
    3,    4,    2,    2,    2,    1,    3,    3,    1,    1,
    2,    2,    2,    2,    6,    2,    3,    2,    1,    1,
    3,    4,    3,    3,    1,    3,    3,    1,    1,    1,
    3,    1,
};
short yydefred[] = {                                      0,
    0,    0,    0,    0,    0,    0,   42,    0,    1,    2,
    5,    6,    7,    0,    0,    0,    0,    0,   39,   40,
    0,    0,    0,   38,    0,    0,    0,    0,   13,   30,
    0,    0,    0,    0,    0,    0,    0,   11,    0,    0,
    0,    0,    3,   31,    0,   15,   14,    0,   28,   32,
    0,    0,    0,    0,    0,    0,    0,   41,    0,    0,
   36,   37,   12,   10,    0,   21,   22,   23,   24,    0,
    9,    0,    8,    0,   17,
};
short yydgoto[] = {                                       8,
    9,   10,   26,   11,   12,   13,   34,   73,   31,   35,
   14,   29,   56,    0,    0,   32,   23,   24,
};
short yysindex[] = {                                   -149,
  -35,  -23,   -7,  -40, -137, -149,    0,    0,    0,    0,
    0,    0,    0, -222, -216,  -40,  -40, -212,    0,    0,
  -40,  -31,  -32,    0, -137, -224, -221,  -56,    0,    0,
    8, -216,  -27,   10,  -24,   11,  -21,    0,  -40,  -40,
  -40,  -40,    0,    0,  -40,    0,    0,   -9,    0,    0,
 -208,   -6,   -4,   -3,   13,  -40,   -5,    0,  -32,  -32,
    0,    0,    0,    0, -149,    0,    0,    0,    0,  -16,
    0, -193,    0, -149,    0,
};
short yyrindex[] = {                                      0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    1,    0, -197,    0,    0,    0,    0,    0,
    0,   30,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,  -38,  -36,    0,    0,    0,    0,    0,   23,   45,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   59,
    0,   73,    0,    0,    0,
};
short yygindex[] = {                                      2,
    6,    0,   47,    0,    0,    0,   32,    0,   43,    9,
    0,   51,    0,    0,    0,    0,  -33,   -2,
};
#define YYTABLESIZE 343
short yytable[] = {                                      21,
   35,   19,   46,   20,   15,   59,   60,   27,   55,   41,
   25,   39,   22,   40,   42,   39,   17,   40,   39,   58,
   40,   39,   33,   40,   33,   16,   39,   38,   40,   37,
   25,   50,   18,   35,   28,   52,   54,   53,   61,   62,
   30,   35,   44,   35,   34,   35,   36,   45,   48,   64,
   51,   57,   65,   71,   66,   33,   67,   68,   18,   35,
   35,   35,   35,   33,   70,   33,   72,   33,   74,    4,
   29,   43,   16,   69,   49,   75,   63,   34,   47,    0,
    0,   33,   33,   33,   33,   34,    0,   34,    0,   34,
    0,    0,    0,    0,    0,    0,    0,    0,    0,   18,
    0,    0,    0,   34,   34,   34,   34,    1,    0,    0,
    2,    0,    0,    3,    4,    0,    5,    0,    6,    1,
    7,    0,    2,    0,    0,    3,    4,    0,    0,    0,
    6,    0,    7,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
   28,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,   19,   20,   19,   19,
   20,   20,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,    0,    0,    0,
    0,    0,    0,    0,    0,    0,    0,   35,    0,    0,
   35,    0,   35,   35,   35,    0,    0,   35,   35,   35,
   35,    0,    0,    0,    0,    0,    0,    0,    0,   33,
    0,    0,   33,    0,   33,   33,   33,    0,    0,   33,
   33,   33,   33,    0,    0,    0,    0,    0,    0,    0,
    0,   34,    0,    0,   34,    0,   34,   34,   34,    0,
    0,   34,   34,   34,   34,   18,    0,    0,   18,    0,
   18,   18,   18,    0,    0,   18,   18,   18,   18,   16,
    0,    0,   16,    0,    0,   16,   16,    0,    0,   16,
   16,   16,   16,
};
short yycheck[] = {                                      40,
    0,   40,   59,   40,   40,   39,   40,    6,   33,   42,
    5,   43,    4,   45,   47,   43,   40,   45,   43,   41,
   45,   43,    0,   45,   16,   61,   43,   59,   45,   21,
   25,   59,   40,   33,  257,   60,   61,   62,   41,   42,
  257,   41,  267,   43,    0,   45,  259,  269,   41,   59,
   41,   41,  261,   59,   61,   33,   61,   61,    0,   59,
   60,   61,   62,   41,   56,   43,   65,   45,  262,  267,
   41,   25,    0,   61,   32,   74,   45,   33,   28,   -1,
   -1,   59,   60,   61,   62,   41,   -1,   43,   -1,   45,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   41,
   -1,   -1,   -1,   59,   60,   61,   62,  257,   -1,   -1,
  260,   -1,   -1,  263,  264,   -1,  266,   -1,  268,  257,
  270,   -1,  260,   -1,   -1,  263,  264,   -1,   -1,   -1,
  268,   -1,  270,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,   -1,
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
#define YYFINAL 8
#ifndef YYDEBUG
#define YYDEBUG 0
#endif
#define YYMAXTOKEN 270
#if YYDEBUG
char *yyname[] = {
"end-of-file",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
"'!'",0,0,0,0,0,0,"'('","')'","'*'","'+'",0,"'-'",0,"'/'",0,0,0,0,0,0,0,0,0,0,0,
"';'","'<'","'='","'>'",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,
0,0,0,0,0,0,"ID","CTE","cadena","if","then","else","print","return","function",
"begin","end","loop","until","uint",
};
char *yyrule[] = {
"$accept : bloque_sent",
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
#endif
#ifndef YYSTYPE
typedef int YYSTYPE;
#endif
#define yyclearin (yychar=(-1))
#define yyerrok (yyerrflag=0)
#ifdef YYSTACKSIZE
#ifndef YYMAXDEPTH
#define YYMAXDEPTH YYSTACKSIZE
#endif
#else
#ifdef YYMAXDEPTH
#define YYSTACKSIZE YYMAXDEPTH
#else
#define YYSTACKSIZE 500
#define YYMAXDEPTH 500
#endif
#endif
int yydebug;
int yynerrs;
int yyerrflag;
int yychar;
short *yyssp;
YYSTYPE *yyvsp;
YYSTYPE yyval;
YYSTYPE yylval;
short yyss[YYSTACKSIZE];
YYSTYPE yyvs[YYSTACKSIZE];
#define yystacksize YYSTACKSIZE
#define YYABORT goto yyabort
#define YYACCEPT goto yyaccept
#define YYERROR goto yyerrlab
int
yyparse()
{
    register int yym, yyn, yystate;
#if YYDEBUG
    register char *yys;
    extern char *getenv();

    if (yys = getenv("YYDEBUG"))
    {
        yyn = *yys;
        if (yyn >= '0' && yyn <= '9')
            yydebug = yyn - '0';
    }
#endif

    yynerrs = 0;
    yyerrflag = 0;
    yychar = (-1);

    yyssp = yyss;
    yyvsp = yyvs;
    *yyssp = yystate = 0;

yyloop:
    if (yyn = yydefred[yystate]) goto yyreduce;
    if (yychar < 0)
    {
        if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, reading %d (%s)\n", yystate,
                    yychar, yys);
        }
#endif
    }
    if ((yyn = yysindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: state %d, shifting to state %d (%s)\n",
                    yystate, yytable[yyn],yyrule[yyn]);
#endif
        if (yyssp >= yyss + yystacksize - 1)
        {
            goto yyoverflow;
        }
        *++yyssp = yystate = yytable[yyn];
        *++yyvsp = yylval;
        yychar = (-1);
        if (yyerrflag > 0)  --yyerrflag;
        goto yyloop;
    }
    if ((yyn = yyrindex[yystate]) && (yyn += yychar) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yychar)
    {
        yyn = yytable[yyn];
        goto yyreduce;
    }
    if (yyerrflag) goto yyinrecovery;
#ifdef lint
    goto yynewerror;
#endif
yynewerror:
    yyerror("syntax error");
#ifdef lint
    goto yyerrlab;
#endif
yyerrlab:
    ++yynerrs;
yyinrecovery:
    if (yyerrflag < 3)
    {
        yyerrflag = 3;
        for (;;)
        {
            if ((yyn = yysindex[*yyssp]) && (yyn += YYERRCODE) >= 0 &&
                    yyn <= YYTABLESIZE && yycheck[yyn] == YYERRCODE)
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: state %d, error recovery shifting\
 to state %d\n", *yyssp, yytable[yyn]);
#endif
                if (yyssp >= yyss + yystacksize - 1)
                {
                    goto yyoverflow;
                }
                *++yyssp = yystate = yytable[yyn];
                *++yyvsp = yylval;
                goto yyloop;
            }
            else
            {
#if YYDEBUG
                if (yydebug)
                    printf("yydebug: error recovery discarding state %d\n",
                            *yyssp);
#endif
                if (yyssp <= yyss) goto yyabort;
                --yyssp;
                --yyvsp;
            }
        }
    }
    else
    {
        if (yychar == 0) goto yyabort;
#if YYDEBUG
        if (yydebug)
        {
            yys = 0;
            if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
            if (!yys) yys = "illegal-symbol";
            printf("yydebug: state %d, error recovery discards token %d (%s)\n",
                    yystate, yychar, yys);
        }
#endif
        yychar = (-1);
        goto yyloop;
    }
yyreduce:
#if YYDEBUG
    if (yydebug)
        printf("yydebug: state %d, reducing by rule %d (%s)\n",
                yystate, yyn, yyrule[yyn]);
#endif
    yym = yylen[yyn];
    yyval = yyvsp[1-yym];
    switch (yyn)
    {
    }
    yyssp -= yym;
    yystate = *yyssp;
    yyvsp -= yym;
    yym = yylhs[yyn];
    if (yystate == 0 && yym == 0)
    {
#if YYDEBUG
        if (yydebug)
            printf("yydebug: after reduction, shifting from state 0 to\
 state %d\n", YYFINAL);
#endif
        yystate = YYFINAL;
        *++yyssp = YYFINAL;
        *++yyvsp = yyval;
        if (yychar < 0)
        {
            if ((yychar = yylex()) < 0) yychar = 0;
#if YYDEBUG
            if (yydebug)
            {
                yys = 0;
                if (yychar <= YYMAXTOKEN) yys = yyname[yychar];
                if (!yys) yys = "illegal-symbol";
                printf("yydebug: state %d, reading %d (%s)\n",
                        YYFINAL, yychar, yys);
            }
#endif
        }
        if (yychar == 0) goto yyaccept;
        goto yyloop;
    }
    if ((yyn = yygindex[yym]) && (yyn += yystate) >= 0 &&
            yyn <= YYTABLESIZE && yycheck[yyn] == yystate)
        yystate = yytable[yyn];
    else
        yystate = yydgoto[yym];
#if YYDEBUG
    if (yydebug)
        printf("yydebug: after reduction, shifting from state %d \
to state %d\n", *yyssp, yystate);
#endif
    if (yyssp >= yyss + yystacksize - 1)
    {
        goto yyoverflow;
    }
    *++yyssp = yystate;
    *++yyvsp = yyval;
    goto yyloop;
yyoverflow:
    yyerror("yacc stack overflow");
yyabort:
    return (1);
yyaccept:
    return (0);
}
