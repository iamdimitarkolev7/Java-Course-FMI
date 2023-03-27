package bg.sofia.uni.fmi.mjt.markdown.converter;

import java.util.regex.Pattern;

public class HeaderConverter extends AbstractConverter {

    private static final String HEADER_TAG = "<h%s>%s</h%s>";
    private static final char HEADER_SYMBOL = '#';

    public HeaderConverter() {
        super(HEADER_TAG);
    }

    @Override
    public String apply(String line) {
        int counter = 0;

        while (line.charAt(counter) == HEADER_SYMBOL) {
            counter++;
        }

        String headerContent = line.substring(counter + 1);

        return String.format(HEADER_TAG, counter, headerContent, counter);
    }

    @Override
    public boolean isApplicable(String line) {
        return Pattern.compile("^#{1,6}\s.+$").matcher(line).find();
    }
}
