package javalanguage.parser;
// Token<List>を解析し、解析木Exprに翻訳する
import java.util.List;

import org.jspecify.annotations.NonNull;

import javalanguage.ast.Expr;
import javalanguage.ast.IntLit;
import javalanguage.ast.BinOp;
import javalanguage.error.ParseError;
import javalanguage.token.Token;
import javalanguage.token.TokenType;

public class Parser {
    private List<Token> tokens;
    private int pos;
    //Token<List>をExprに変換する関数
    public Expr parse(List<Token> tokens) throws ParseError {
        this.tokens = tokens;
        this.pos = 0;

        Expr e = parseExpr();
        consume(TokenType.EOF);
        return e;
    }

    // 現在読んでいるTokenを翻訳し、Factorを返す関数
    private Expr parseFactor() throws ParseError{
        Token t = current();
        if (t.type == TokenType.INT){
            consume(TokenType.INT);
            return new IntLit(t.value, t.pos);
        }

        if (t.type == TokenType.LPAREN){
            consume(TokenType.LPAREN);
            Expr e = parseExpr();
            consume(TokenType.RPAREN);
            return e;
        }

        throw new ParseError(t.pos, "Expected INT but found " + t.type);
    }

    // 現在読んでいる箇所から始まる加減算式を翻訳し、BinOp式を出力する関数
    private Expr parseExpr() throws ParseError {
        Expr e = parseTerm();

        while(true){

            TokenType ty = current().type;
            if (ty == TokenType.PLUS || ty == TokenType.MINUS){
                TokenType op = ty;
                int opPos = current().pos;
                consume(ty);
                Expr rhs = parseTerm();
                e = new BinOp(op, e, rhs, opPos);
                continue;
            }
            break;
        }
        return e;
    }

    // 現在読んでいる箇所から始まる乗除算式を翻訳し、BinOp式を出力する関数
    private Expr parseTerm() throws ParseError{
        Expr t = parseFactor();

        while(true){

            TokenType ty = current().type;
            if (ty == TokenType.MUL || ty == TokenType.DIV){
                TokenType op = ty;
                int opPos = current().pos;
                consume(ty);
                Expr rhs = parseFactor();
                t = new BinOp(op, t, rhs, opPos);
                continue;
            }
            break;
        }
        return t;
    }

    // Parserが現在読んでいるTokenを出力する関数
    private Token current() {
        return tokens.get(pos);
    }

    // 現在読んでいるTokenの型がexpectedであるかどうか確かめ、次を読む関数
    private Token consume(TokenType expected) throws ParseError{
        Token t = current();
        if (t.type != expected) {
            throw new ParseError(t.pos, "Expected " + expected + " but found " + t.type);
        }
        pos ++ ;
        return t;
    }

}
