package javalanguage.ast;

import javalanguage.token.TokenType;

public class UnaryOp implements Expr{
    public final TokenType op;
    public final Expr expr;
    public final int pos;

    // コンストラクタ
    public UnaryOp(TokenType op, Expr expr, int pos){
        this.op  = op;
        this.expr = expr;
        this.pos = pos;
    }

    @Override
    public int getPos(){
        return this.pos;
    }


}
