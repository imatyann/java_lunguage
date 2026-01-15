package src.javalanguage.token;
public final class Token {
    public final TokenType type;
    public final int pos;
    public final int value;

    // INT以外でのトークンのコンストラクタ
    public Token(TokenType type, int pos){
        this.type = type;
        this.pos = pos;
        this.value = 0;
    }

    // INTトークンのコンストラクタ
    public Token(TokenType type, int pos, int value){
        this.type = type;
        this.pos = pos;
        this.value = value;
    }

}
