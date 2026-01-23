package javalanguage.ast;
// 式(AST)の基底型で、IntLitとBinOpの共通の親
public interface Expr {
    int getPos();
}
