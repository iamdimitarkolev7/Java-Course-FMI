public class PrefixExtractor {
    public static String getLongestCommonPrefix(String[] words) {
        if (words.length == 0) {
            return "";
        }

        StringBuilder resultStr = new StringBuilder("");

        int shortestWordLen = getLengthOfShortestWord(words);

        for (int i = 0; i < shortestWordLen; i++) {
            char letter = words[0].charAt(i);

            for (String word : words) {
                if (word.charAt(i) != letter) {
                    return resultStr.toString();
                }
            }

            resultStr.append(letter);
        }

        return resultStr.toString();
    }

    private static int getLengthOfShortestWord(String[] words) {
        int resultLen = Integer.MAX_VALUE;

        for (String word : words) {
            if (word.length() < resultLen) {
                resultLen = word.length();
            }
        }

        return resultLen;
    }
}
