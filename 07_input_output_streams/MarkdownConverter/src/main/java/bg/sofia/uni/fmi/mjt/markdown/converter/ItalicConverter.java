package bg.sofia.uni.fmi.mjt.markdown.converter;

public class ItalicConverter extends AbstractFormattingConverter {

    private static final String ITALIC_TAG = "<em>%s</em>";
    private static final String ITALIC_SYMBOL = "*";

    public ItalicConverter() {
        super(ITALIC_SYMBOL, ITALIC_TAG);
    }
}
