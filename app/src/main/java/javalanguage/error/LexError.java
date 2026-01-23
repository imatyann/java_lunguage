package javalanguage.error;
// 字句解析エラー 未知の文字などが含まれる
public class LexError extends LangError{
    public LexError(int pos, String message){
        super(pos, message);
    }
}
