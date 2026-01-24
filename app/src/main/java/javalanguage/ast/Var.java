package javalanguage.ast;

public class Var implements Expr{
    public final String name;
    public final int pos;

    // コンストラクタ
    public Var(String name, int pos){
        this.name  = name;
        this.pos = pos;
    }

    @Override
    public int getPos(){
        return this.pos;
    }
}
