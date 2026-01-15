package javalanguage.error;

public class RuntimeError extends LangError {
    public RuntimeError(int pos, String message){
        super(pos, message);
    }    
}
