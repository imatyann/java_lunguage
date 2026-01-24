package javalanguage.parser;
// Token<List>を解析し、解析木Exprに翻訳する
import java.util.List;

import org.jspecify.annotations.NonNull;

import javalanguage.ast.Expr;
import javalanguage.ast.IntLit;
import javalanguage.ast.UnaryOp;
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

    // 現在読んでいる箇所から始まる加減算式を翻訳し、BinOp式を出力する関数
    private Expr parseExpr() throws ParseError {
        Expr e = parseTerm();

        while(true){
            Token t = current();
            TokenType ty = t.type;
            if (ty == TokenType.PLUS || ty == TokenType.MINUS){
                TokenType op = ty;
                consume(ty);
                Expr rhs = parseTerm();
                e = new BinOp(op, e, rhs, t.pos);
                continue;
            }
            break;
        }
        return e;
    }

    // 現在読んでいる箇所から始まる乗除算式を翻訳し、BinOp式を出力する関数
    private Expr parseTerm() throws ParseError{
        Expr e = parseUnary();

        while(true){
            Token t = current();
            TokenType ty = t.type;
            if (ty == TokenType.MUL || ty == TokenType.DIV){
                TokenType op = ty;
                consume(ty);
                Expr rhs = parseUnary();
                e = new BinOp(op, e, rhs, t.pos);
                continue;
            }
            break;
        }
        return e;
    }

    private Expr parseUnary() throws ParseError {
        Token t = current();
        if (t.type == TokenType.MINUS){
            consume(TokenType.MINUS);
            Expr rhs = parseUnary();
            return new UnaryOp(t.type, rhs, t.pos);
        }
        return parseFactor();
    }

    // 現在読んでいるTokenを翻訳し、Factorを返す関数
    private Expr parseFactor() throws ParseError{
        Token t = current();
        if (t.type == TokenType.INT){
            consume(TokenType.INT);
            return new IntLit(t.value, t.pos);

        } else if (t.type == TokenType.LPAREN){
            consume(TokenType.LPAREN);
            Expr e = parseExpr();
            consume(TokenType.RPAREN);
            return e;
        
        }

        throw new ParseError(t.pos, "Expected INT but found " + t.type);
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
