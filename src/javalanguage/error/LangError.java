package javalanguage.error;

public abstract class LangError extends Exception{
    public final int pos;
    public final String message;

    protected LangError(int pos, String message) {
        super(message);
        this.pos = pos;
        this.message = message;
    }
}
