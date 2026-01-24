package javalanguage.lexer;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import javalanguage.error.LexError;
import javalanguage.lexer.Lexer;
import javalanguage.token.Token;
import javalanguage.token.TokenType;

public class LexerTest {

    @Test
    void tokenize_int() throws LexError{
        Lexer lexer = new Lexer();
        List<Token> ts = lexer.tokenize("123");

        assertEquals(2, ts.size());
        assertEquals(TokenType.INT, ts.get(0).type);
        assertEquals(0, ts.get(0).pos);

        assertEquals(123, ts.get(0).value);

        assertEquals(TokenType.EOF, ts.get(1).type);
        assertEquals(3, ts.get(1).pos);
    }   
    
    @Test
    void tokenize_whitespace() throws LexError {
        Lexer lexer = new Lexer();
        List<Token> ts = lexer.tokenize("12 \n 3");

        assertEquals(3, ts.size());
        assertEquals(TokenType.INT, ts.get(0).type);
        // System.out.println(ts.get(0).value);
        assertEquals(12, ts.get(0).value);
        assertEquals(TokenType.INT, ts.get(1).type);
        assertEquals(3, ts.get(1).value);
        assertEquals(TokenType.EOF, ts.get(2).type);
        
    }

    @Test
    void tokenize_operators_and_parens() throws LexError {
        Lexer lexer = new Lexer();
        List<Token> ts = lexer.tokenize("1+(2*3)");

        assertEquals(TokenType.INT, ts.get(0).type);
        assertEquals(TokenType.PLUS, ts.get(1).type);
        assertEquals(TokenType.LPAREN, ts.get(2).type);
        assertEquals(TokenType.INT, ts.get(3).type);
        assertEquals(TokenType.MUL, ts.get(4).type);
        assertEquals(TokenType.INT, ts.get(5).type);
        assertEquals(TokenType.RPAREN, ts.get(6).type);
        assertEquals(TokenType.EOF, ts.get(7).type);
           
    }

    @Test
    void tokenize_negative() throws LexError {
        Lexer lexer = new Lexer();
        List<Token> ts = lexer.tokenize("1+(2*-3)");

        assertEquals(TokenType.INT, ts.get(0).type);
        assertEquals(TokenType.PLUS, ts.get(1).type);
        assertEquals(TokenType.LPAREN, ts.get(2).type);
        assertEquals(TokenType.INT, ts.get(3).type);
        assertEquals(TokenType.MUL, ts.get(4).type);
        assertEquals(TokenType.MINUS, ts.get(5).type);
        assertEquals(TokenType.INT, ts.get(6).type);
        assertEquals(TokenType.RPAREN, ts.get(7).type);
        assertEquals(TokenType.EOF, ts.get(8).type);
           
    }

    @Test
    void tokenize_var() throws LexError {
        Lexer lexer = new Lexer();
        List<Token> ts = lexer.tokenize("x = 3");

        assertEquals(TokenType.VAR, ts.get(0).type);
        assertEquals(TokenType.EQUAL, ts.get(1).type);
        assertEquals(TokenType.INT, ts.get(2).type);  
        assertEquals(TokenType.EOF, ts.get(3).type);  
                 
    }

    @Test
    void tokenize_unknown_character_throws() {
        Lexer lexer = new Lexer();

        LexError e = assertThrows(LexError.class, () -> lexer.tokenize("1a"));
        assertEquals(1, e.pos);
    }

    

}
