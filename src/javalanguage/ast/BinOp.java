package javalanguage.ast;
// AST式中の二項式
import javalanguage.token.TokenType;

public final class BinOp implements Expr{    
    public final TokenType op;
    public final Expr left;
    public final Expr right;

    // コンストラクタ
    public BinOp(TokenType op, Expr left, Expr right){
        this.op  = op;
        this.left = left;
        this.right = right;
    }



}
