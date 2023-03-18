public class Main {
    public static void main(String[] args) {
        String[] words1 = {"flower", "flow", "flight"};
        String[] words2 = {"dog", "racecar", "car"};
        String[] words3 = {"cat"};

        System.out.println(PrefixExtractor.getLongestCommonPrefix(words1));
        System.out.println(PrefixExtractor.getLongestCommonPrefix(words2));
        System.out.println(PrefixExtractor.getLongestCommonPrefix(words3));

        System.out.println("==========");

        int[] places1 = {8, 1, 5, 2, 6};
        int[] places2 = {1, 2};

        System.out.println(TourGuide.getBestSightseeingPairScore(places1));
        System.out.println(TourGuide.getBestSightseeingPairScore(places2));

        System.out.println("==========");

        int[][] map1 = { {1, 0}, {0, 1} };
        int[][] map2 = { {1, 0}, {1, 1} };
        int[][] map3 = { {1, 1, 0, 0}, {0, 0, 1, 0}, {0, 0, 1, 0}, {0, 0, 0, 1} };

        System.out.println(DataCenter.getCommunicatingServersCount(map1));
        System.out.println(DataCenter.getCommunicatingServersCount(map2));
        System.out.println(DataCenter.getCommunicatingServersCount(map3));

    }
}