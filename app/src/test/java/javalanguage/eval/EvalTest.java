package javalanguage.eval;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.junit.jupiter.api.Test;

import javalanguage.ast.BinOp;
import javalanguage.ast.Expr;
import javalanguage.ast.IntLit;
import javalanguage.error.RuntimeError;
import javalanguage.token.TokenType;


public class EvalTest {

    @Test
    void eval_plus_to_div() throws RuntimeError{
        Evaluator evaluator = new Evaluator();
        Expr ex_plus = new BinOp(TokenType.PLUS, new IntLit(1), new IntLit(2));
        assertEquals(3, evaluator.eval(ex_plus));
        Expr ex_minus = new BinOp(TokenType.MINUS, new IntLit(1), new IntLit(2));
        assertEquals(-1, evaluator.eval(ex_minus));
        Expr ex_mul = new BinOp(TokenType.MUL, new IntLit(3), new IntLit(2));
        assertEquals(6, evaluator.eval(ex_mul));
        Expr ex_div = new BinOp(TokenType.DIV, new IntLit(6), new IntLit(3));
        assertEquals(2, evaluator.eval(ex_div));
        Expr ex_div_rem = new BinOp(TokenType.DIV, new IntLit(10), new IntLit(3));
        assertEquals(3, evaluator.eval(ex_div_rem));
    }   

    @Test
    void eval_long_expr() throws RuntimeError{
        Evaluator evaluator = new Evaluator();
        // 1 + 2 * 3 = 7
        Expr ex_long = new BinOp(TokenType.PLUS, new IntLit(1), new BinOp(TokenType.MUL, new IntLit(2), new IntLit(3)));
        assertEquals(7, evaluator.eval(ex_long));

        //( 12 + 4 ) / 5 = 3
        Expr ex_paren = new BinOp(TokenType.DIV, new BinOp(TokenType.PLUS, new IntLit(12), new IntLit(4)), new IntLit(5));
        assertEquals(3, evaluator.eval(ex_paren));
        
    }
}