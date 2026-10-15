    public static List<Location> possibleMoves(char[][] forest, Location current){
        int[][] moves = new int[][] {
            {-1, 0}, //up
            {1, 0},  //down
            {0, -1}, //left
            {0, 1}   //right
        };

        List<Location> validMoves = new ArrayList<>();

        for(int[] move : moves){
            Location possibleMove = new Location(current.row() + move[0], current.column() + move[1]);
            if(possibleMove.row() >= 0 && possibleMove.row() < forest.length &&
               possibleMove.column() >= 0 && possibleMove.column() < forest[0].length &&
               forest[possibleMove.row()][possibleMove.column()] != '.'){
                validMoves.add(possibleMove);
            }
        }

        return validMoves;
    }

    