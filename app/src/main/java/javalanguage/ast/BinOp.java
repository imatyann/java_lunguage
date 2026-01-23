package javalanguage.ast;
// AST式中の二項式
import javalanguage.token.TokenType;

public final class BinOp implements Expr{    
    public final TokenType op;
    public final Expr left;
    public final Expr right;
    public final int pos;

    // コンストラクタ
    public BinOp(TokenType op, Expr left, Expr right, int pos){
        this.op  = op;
        this.left = left;
        this.right = right;
        this.pos = pos;
    }

    @Override
    public int pos() {
        return pos;
    }
    



}
