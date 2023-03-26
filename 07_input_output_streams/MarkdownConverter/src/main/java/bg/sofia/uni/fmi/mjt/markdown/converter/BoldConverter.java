package bg.sofia.uni.fmi.mjt.markdown.converter;

public class BoldConverter extends AbstractFormattingConverter {

    private static final String BOLD_TAG = "<strong>%s</strong>";
    private static final String BOLD_SYMBOL = "**";

    public BoldConverter() {
        super(BOLD_SYMBOL, BOLD_TAG);
    }
}
