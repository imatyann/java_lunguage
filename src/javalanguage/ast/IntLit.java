package javalanguage.ast;
// AST式中の整数値
public final class IntLit implements Expr{
    public final int value;

    // コンストラクタ
    public IntLit(int value){
        this.value = value;
    }
}
