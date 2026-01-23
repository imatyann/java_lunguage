package javalanguage.ast;
// AST式中の整数値
public final class IntLit implements Expr{
    public final int value;
    public final int pos;

    // コンストラクタ
    public IntLit(int value, int pos){
        this.value = value;
        this.pos = pos;
    }

    @Override
    public int getPos(){
        return this.pos;
    }
}
