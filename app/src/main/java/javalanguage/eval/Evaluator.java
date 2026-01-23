package javalanguage.eval;
// Expr式を評価してintを返す責務
import javalanguage.ast.Expr;
import javalanguage.ast.BinOp;
import javalanguage.ast.IntLit;
import javalanguage.error.RuntimeError;

import javalanguage.token.TokenType;

public final class Evaluator {
    // Expr式を評価してintを出力する関数
    public int eval(Expr e) throws RuntimeError {
        if (e instanceof IntLit) {
            return ((IntLit) e).value;
        }

        if (e instanceof BinOp){
            BinOp b = (BinOp) e;
            TokenType t = b.op;
            int l = eval(b.left);
            int r = eval(b.right);
            switch (t) {
                case PLUS:
                    return l + r;
                case MINUS:
                    return l - r;
                case MUL:
                    return l * r;
                case DIV:
                    if (r == 0) throw new RuntimeError(b.getPos(), "Division by zero");
                    return l / r;
                default:
                    break;
            }

        }

        throw new RuntimeError(e.getPos(), "Unsupported operator: " + e);
    }
}
