# java_lunguage

### 設計
## 仕様
言語処理系を作成する(MVP版では単純な計算機)
コマンドライン引数、もしくはファイルで入力されたファイルを実行する

いかなる入力も受け付けるが、構文に従わない入力はエラー文を出力する
エラーでない場合、そのままターミナルに入力文を実行した結果を出力する
出力は値のみを行う

## 言語仕様
# 値
10進の非負整数

# トークン
整数リテラル：0または1..9で始まる文字列
四則演算：+-*/
括弧：()

# 優先順位
括弧の中身を最優先に処理する
*/の計算を次に処理する
優先度が同じ場合、左結合で処理を行う
複数行の入力は、一つの式として扱う

# 評価
+-*/で四則演算を行う
ただし、/は整数除算の切り捨て

# エラー条件
字句エラー：未知の文字がある
構文エラー：括弧不整合、演算子の位置、入力の途中終了など
実行時エラー：0除算

#　細かい仕様
負の数は不可
空入力は構文エラー

## 内部処理
# トークン列
INT(value): 整数リテラル 0または1..9で始まる数字列
PLUS: +
MINUS: -
MUL: *
DIV: /
LPAREN: (
RPAREN: )
EOF: 入力終了の内部トークン

# 構文木
構文解析の結果をAST式として表現する
ASTは以下の二種で表現される
・IntLit(value): 整数リテラル
・BinOp(op, left, right): 二式を四則演算でつないだ式
opは{PLUS,MINUS,MUL,DIV}のいずれか

入力を受け取ったら、文法を精査した後、構文木の形に落とす
構文木で表せる式をExpr(AST)と呼ぶ

# エラー型
LexError: 字句解析で発見 未知の文字など
ParseError: 構文解析で発見 括弧不整合など
RuntimeError: 評価で発生 0除算など
これらのエラーは位置情報と簡潔なメッセージを持つ

# パイプライン処理
入力：文字列String source
字句解析機：souece -> List<Token>
構文解析機：List<token> -> Expr(AST)
評価：Expr -> int
出力：成功時は評価結果お標準出力へ出力する
失敗：エラーを生成し、エラー箇所とメッセージを出力して終了

## 構文解析の規則
前提として、入力は語尾にEOFを含むトークン列List<Token>となっている
1) 
入力全体は式Exprを一つ含む。
Exprを一つ読み取った後がEOFでないなら構文エラー
2) 
Exprを四段階に評価する
・Expr：加減算レベル 優先度が最低で、最後に処理をする
・Term：乗除算レベル 複数含まれる
・Unary：負の数の演算 最も優先度が高い
・Factor：最小単位 値や括弧の計算
3) Expr
Exprは、まずTermを一つ読む
次のトークンがPLUS,MINUSである限り繰り返す
　e = BinOp(PLUSもしくはMINUS, e, 次のTerm)
こうして、Exprは左結合の構文木になる
4) Term
Termは、まずUnaryを一つ読む
次のトークンがMUL,DIVである限り、繰り返す
　e = BinOp(MULもしくはDIV, t, 次のUnary)
こうして、Termは優先度の高い左結合になる
6) Unary
Unaryは、まずFactorを一つ読む
次のトークンがINTである限り、繰り返す
  e = UnaryOp(MINUS, e, 次のFactor)
こうして、Unaryは優先度の高い左結合となる
5) Factor
・次のトークンがINT(value)の場合
INTを消費し、IntLit(value)を返す
・次のトークンがLPARENの場合
LPARENを消費、Exprを読み、直後のRPARENを消費し、Exprを返す

## 各責務を行うファイル
# エントリ
・Main
責務：入力の取得→実行→結果/エラー出力
公開：public static void main(String[] args)

# 字句解析
・Lexer
責務：string sourceを走査し、List<Token>を作成する
公開：List<Token> tokenize(String source) throws LexError
保証：成功時、トークン列は未知文字を含まない

・Token
フィールド：TokenType type, int pos, int value

・TokenType
INT, PLUS, MINUS, MUL, DIV, LPAREN, RPAREN, EOF

# 構文解析
・Parser
責務：List<Token>からExprを作成
公開：Expr parse(List<Token> tokens) throw ParseError
内部：parseExpr(), parseTerm(), parseUnary(), parseFactor()の三段階
保証：成功時、ASTは構文的に正しい

# AST
・Expr
実装：IntLit, BinOp
・IntLit
フィールド：int value
フィールド：TokenType op, Expr left, Expr right

# エラー
・LangError
フィールド：int pos, String message
・LexError/ParseError/RuntimeError
LangErrorを継承

