package bg.sofia.uni.fmi.mjt.markdown.converter;

public abstract class AbstractFormattingConverter extends AbstractConverter {

    private final String formattingSymbol;

    protected AbstractFormattingConverter(String formattingSymbol, String targetHtmlTag) {
        super(targetHtmlTag);

        this.formattingSymbol = formattingSymbol;
    }

    public String getFormattingSymbol() {
        return formattingSymbol;
    }

    @Override
    public String apply(String line) {
        String formattedContent = getFormattedContent(line);
        String oldContent = formattingSymbol + formattedContent + formattingSymbol;
        String newContent = String.format(getTargetHtmlTag(), formattedContent);

        return line.replace(oldContent, newContent);
    }

    @Override
    public boolean isApplicable(String line) {
        return line.contains(formattingSymbol);
    }

    private String getFormattedContent(String line) {
        int firstOcc = line.indexOf(formattingSymbol);
        int lastOcc = line.lastIndexOf(formattingSymbol);

        return line.substring(firstOcc, lastOcc);
    }
}
