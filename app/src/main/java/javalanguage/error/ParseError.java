package javalanguage.error;

public class ParseError extends LangError{
    
    public ParseError(int pos, String message){
        super(pos, message);
    }
    
}
