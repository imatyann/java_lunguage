package javalanguage.parser;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import javalanguage.ast.Expr;
import javalanguage.ast.IntLit;
import javalanguage.error.ParseError;
import javalanguage.parser.Parser;
import javalanguage.token.Token;
import javalanguage.token.TokenType;


public class ParseTest {
    // 数値のみのListが通るかどうかのテスト
    @Test
    void parse_int_only() throws ParseError{
        Parser p = new Parser();
        List<Token> ts = List.of(
            new Token(TokenType.INT, 0, 123),
            new Token(TokenType.EOF, 3)
        );

        Expr e = p.parse(ts);
        assertEquals(123, ((IntLit) e).value);
    }

    @Test
    void parse_mul() throws ParseError {
        Parser p = new Parser();
        List<Token> ts = List.of(
                new Token(TokenType.INT, 0, 2),
                new Token(TokenType.MUL, 1),
                new Token(TokenType.INT, 2, 3),
                new Token(TokenType.EOF, 3)
        );

        Expr e = p.parse(ts);
        assertTrue(e instanceof javalanguage.ast.BinOp);

        javalanguage.ast.BinOp top = (javalanguage.ast.BinOp) e;
        assertEquals(TokenType.MUL, top.op);
        assertTrue(top.left instanceof javalanguage.ast.IntLit);
    }

    @Test
    void parse_plus() throws ParseError{
        Parser p = new Parser();
        List<Token> ts = List.of(
            new Token(TokenType.INT, 0, 1),
            new Token(TokenType.PLUS, 1),
            new Token(TokenType.INT, 2, 2),
            new Token(TokenType.EOF, 3)            
        );

        Expr e = p.parse(ts);
        assertTrue(e instanceof javalanguage.ast.BinOp);
    }

    @Test
    void parse_precedence() throws ParseError {
        Parser p = new Parser();
        List<Token> ts = List.of(
                new Token(TokenType.INT, 0, 1),
                new Token(TokenType.PLUS, 1),
                new Token(TokenType.INT, 2, 2),
                new Token(TokenType.MUL, 3),
                new Token(TokenType.INT, 4, 3),
                new Token(TokenType.EOF, 5)
        );

        Expr e = p.parse(ts);
        assertTrue(e instanceof javalanguage.ast.BinOp);

        javalanguage.ast.BinOp top = (javalanguage.ast.BinOp) e;
        assertEquals(TokenType.PLUS, top.op);
        assertTrue(top.right instanceof javalanguage.ast.BinOp);  // 右が * の木
    }

    @Test
    void parse_minus_left_assoc() throws ParseError {
        Parser p = new Parser();
        // 1 - 2 - 3
        List<Token> ts = List.of(
                new Token(TokenType.INT, 0, 1),
                new Token(TokenType.MINUS, 1),
                new Token(TokenType.INT, 2, 2),
                new Token(TokenType.MINUS, 3),
                new Token(TokenType.INT, 4, 3),
                new Token(TokenType.EOF, 5)
        );

        Expr e = p.parse(ts);
        assertTrue(e instanceof javalanguage.ast.BinOp);

        javalanguage.ast.BinOp top = (javalanguage.ast.BinOp) e;
        assertEquals(TokenType.MINUS, top.op);
        assertTrue(top.left instanceof javalanguage.ast.BinOp);
    }

    @Test
    void parse_unary() throws ParseError {
        Parser p = new Parser();
        // 1 + 2 * -3
        List<Token> ts = List.of(
                new Token(TokenType.INT, 0, 1),
                new Token(TokenType.PLUS, 1),
                new Token(TokenType.INT, 2, 2),
                new Token(TokenType.MUL, 3),
                new Token(TokenType.MINUS, 4),
                new Token(TokenType.INT, 5, 3),
                new Token(TokenType.EOF, 6)
        );

        Expr e = p.parse(ts);
        assertTrue(e instanceof javalanguage.ast.BinOp);

        javalanguage.ast.BinOp top = (javalanguage.ast.BinOp) e;

        assertTrue(top.left instanceof javalanguage.ast.IntLit);
        assertEquals(1, ((javalanguage.ast.IntLit) top.left).value);

        assertTrue(top.right instanceof javalanguage.ast.BinOp);
        javalanguage.ast.BinOp right = (javalanguage.ast.BinOp) top.right;
        assertEquals(TokenType.MUL, right.op);

        assertTrue(right.left instanceof javalanguage.ast.IntLit);
        assertEquals(2, ((javalanguage.ast.IntLit) right.left).value);

        assertTrue(right.right instanceof javalanguage.ast.UnaryOp);
        assertEquals(TokenType.MINUS, ((javalanguage.ast.UnaryOp) right.right).op);

    }




}