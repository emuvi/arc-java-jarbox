package pin.jarbox.bin;

public class Token {

    public static enum Type {
        UNKNOWN, KEYWORD, VARIABLE, STRING, NUMBER, BLOCK
    }

    public final Type type;
    public final String source;
    public final int start;

    public Token(Type type, String source, int start) {
        this.type = type;
        this.source = source;
        this.start = start;
    }

}
