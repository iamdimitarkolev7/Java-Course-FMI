public class DataCenter {
    public static int getCommunicatingServersCount(int[][] map) {
        if (map.length == 0) {
            return 0;
        }

        int numRows = map.length;
        int numCols = map[0].length;
        int[] rowCount = new int[numRows];
        int[] colCount = new int[numCols];
        int totalServers = 0;

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (map[row][col] == 1) {
                    rowCount[row]++;
                    colCount[col]++;
                    totalServers++;
                }
            }
        }

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                if (map[row][col] == 1) {
                    if (rowCount[row] == 1 && colCount[col] == 1) {
                        totalServers--;
                    }
                }
            }
        }

        return totalServers;
    }
}
