package bg.sofia.uni.fmi.mjt.markdown.converter;

public class CodeConverter extends AbstractFormattingConverter {

    private static final String CODE_TAG = "<code>%s</code>";
    private static final String CODE_SYMBOL = "`";

    public CodeConverter() {
        super(CODE_SYMBOL, CODE_TAG);
    }
}
