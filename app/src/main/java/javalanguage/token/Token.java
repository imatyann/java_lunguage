package javalanguage.token;
public final class Token {
    public final TokenType type;
    public final int pos;
    public final int value;
    public final String name;



    // INTトークンのコンストラクタ
    public Token(TokenType type, int pos, int value){
        this.type = type;
        this.pos = pos;
        this.value = value;
        this.name = "";
    }

    // VALトークンのコンストラクタ
    public Token(TokenType type, int pos, String name){
        this.type = type;
        this.pos = pos;
        this.value = 0;
        this.name = "";
    }

    // それ以外でのトークンのコンストラクタ
    public Token(TokenType type, int pos){
        this.type = type;
        this.pos = pos;
        this.value = 0;
        this.name = "";
    }
}
