import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class Fire {
    /**
     * Returns how long it takes for all vulnerable trees to be set on fire if a
     * match is lit at a given location.
     * 
     * The forest is represented via a rectangular 2d char array where t represents a tree
     * and . represents an empty space.
     * 
     * At time 0, the tree at location [matchR, matchC] is set on fire. At every subsequent
     * time step, any tree that is adjacent (up/down/left/right) to a burning tree is also
     * set on fire. 
     * 
     * 
     * EXAMPLE 
     * forest:
     * 
     * t..tttt.t
     * ..tt....t
     * ..ttttttt
     * tttt.....
     * 
     * matchR: 2
     * matchC: 6
     * 
     * Result: 8
     * 
     * Explanation:
     * At time 0, the tree at (2, 6) is set on fire. At time 1, its adjacent trees also catch on fire
     * At time 2, the trees adjacent to those catch as well. At time 8, the last tree to catch is at
     * (0,6). In this example, there is one tree that never burns, so it is not included in the time calculation.
     * 
     * 
     * @param forest a 2d array where t represents a tree and . represents the ground
     * @param matchR The row the match is lit at
     * @param matchC The column the match is lit at
     * @return the time at which the final tree to be incinerated starts burning
     */
    public static int timeToBurn(char[][] forest, int matchR, int matchC) {
        // HINT: when adding to your BFS queue, you can include more information than
        // just a location. What other information might be useful?

        // Validates the location of the starting tree
        Location start = firstTreeLocation(forest, matchR, matchC);

        boolean[][] visited = new boolean[forest.length][forest[0].length];
        Queue<Location> queue = new LinkedList<>();

        queue.add(start);
        visited[start.row()][start.column()] = true;

        int finalTime = 0;

        while(!queue.isEmpty()){
            Location current = queue.remove();
            finalTime = Math.max(finalTime, current.time());
            List<Location> neighbors = possibleMoves(forest, current);

            for (Location neighbor : neighbors) {
                int row = neighbor.row();
                int column = neighbor.column();
                if (!visited[row][column]) {
                    visited[row][column] = true;
                    Location nextTree = new Location(row, column, current.time() + 1);
                    queue.add(nextTree);
                }
            }
        }

        return finalTime;
    }

    public static List<Location> possibleMoves(char[][] forest, Location current){
        int[][] moves = new int[][] {
            {-1, 0}, //up
            {1, 0},  //down
            {0, -1}, //left
            {0, 1}   //right
        };

        List<Location> validMoves = new ArrayList<>();

        for(int[] move : moves){
            int nextRow = current.row() + move[0];
            int nextColumn = current.column() + move[1];

            if (nextRow >= 0 && nextRow < forest.length &&
                nextColumn >= 0 && nextColumn < forest[nextRow].length) {
                if (forest[nextRow][nextColumn] == 't') {
                    validMoves.add(new Location(nextRow, nextColumn, 0));
                }
            }
        }

        return validMoves;
    }

    public static Location firstTreeLocation(char[][] forest, int matchR, int matchC) {
        if (forest == null || matchR < 0 || matchR >= forest.length ||
            forest[matchR] == null || matchC < 0 || matchC >= forest[matchR].length ||
            forest[matchR][matchC] != 't')  {
            throw new NoTreesException();
        }

        return new Location(matchR, matchC, 0);
    }
}