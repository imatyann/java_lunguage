package test.java.javalanguage.lexer;

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
    }    

}
