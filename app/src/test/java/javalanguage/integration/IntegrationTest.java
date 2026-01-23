package javalanguage.integration;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import javalanguage.lexer.Lexer;
import javalanguage.parser.Parser;
import javalanguage.eval.Evaluator;

import javalanguage.ast.Expr;
import javalanguage.token.Token;
import javalanguage.error.LexError;
import javalanguage.error.ParseError;
import javalanguage.error.RuntimeError;

public class IntegrationTest {

    @Test
    void eval_full_pipeline_success() throws LexError, ParseError, RuntimeError {
        Lexer lexer = new Lexer();
        Parser parser = new Parser();
        Evaluator evaluator = new Evaluator();

        String source = "1 + 2 * 3";
        List<Token> tokens = lexer.tokenize(source);
        Expr expr = parser.parse(tokens);
        int result = evaluator.eval(expr);

        assertEquals(7, result);
    }

    @Test
    void eval_full_pipeline_div_zero() throws LexError, ParseError {
        Lexer lexer = new Lexer();
        Parser parser = new Parser();
        Evaluator evaluator = new Evaluator();

        String source = "1 / 0";
        List<Token> tokens = lexer.tokenize(source);
        Expr expr = parser.parse(tokens);

        assertThrows(RuntimeError.class, () -> evaluator.eval(expr));
    }

    @Test
    void eval_full_pipeline_parse_error() throws LexError {
        Lexer lexer = new Lexer();
        Parser parser = new Parser();

        String source = "1 +";
        List<Token> tokens = lexer.tokenize(source);

        assertThrows(ParseError.class, () -> parser.parse(tokens));
    }

    @Test
    void eval_full_pipeline_lex_error() {
        Lexer lexer = new Lexer();

        String source = "1a";

        assertThrows(LexError.class, () -> lexer.tokenize(source));
    }
}
