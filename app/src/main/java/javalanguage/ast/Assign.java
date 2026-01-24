package javalanguage.ast;

public class Assign implements Expr{
    public final Var target;
    public final Expr value;
    public final int pos;

    // コンストラクタ
    public Assign(Var target, Expr value, int pos){
        this.target  = target;
        this.value = value;
        this.pos = pos;
    }

    @Override
    public int getPos(){
        return this.pos;
    } 
}
