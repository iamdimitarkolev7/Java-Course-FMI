public class TourGuide {
    public static int getBestSightseeingPairScore(int[] places) {
        int crrMaxScore = 0;

        for (int i = 0; i < places.length; i++) {
            for (int j = 0; j < places.length; j++) {
                if (i < j) {
                    int score = places[i] + places[j] + i - j;

                    if (score > crrMaxScore) {
                        crrMaxScore = score;
                    }
                }
            }
        }

        return crrMaxScore;
    }
}
