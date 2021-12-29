import java.util.ArrayList;
public class NQueens {
    //Board Size
    private int boardSize = 0;

    //Number of Queens placed on the board
    private int queenCount = 0;

    //Number of valid placements
    private int solutionCount = 0;

    //To validate if row is already occupied
    private boolean[] rowOccupied;

    //To validate if column is already occupied
    private boolean[] colOccupied;

    //To validate if left diagonal is occupied
    private boolean[] lDiagonalOccupied;

    //To validate if right diagonal is occupied
    private boolean[] rDiagonalOccupied;

    //To place queens
    private boolean[][] board;

    //Result
    static ArrayList<ArrayList<String>> arrResult = new ArrayList<>();

    //To determine the left diagonal index based on row index and column index
    public int leftDiagonalIndex(int rowIndex, int colIndex){
        return rowIndex + colIndex;
    }

    //To determine the right diagonal index based on row index and column index
    public int rightDiagonalIndex(int rowIndex, int colIndex){
        int difference = Math.abs(rowIndex - colIndex);
        int rDiagonalIndex = boardSize - 1;
        if(rowIndex >= colIndex){
            rDiagonalIndex = rDiagonalIndex + difference;
        }
        else{
            rDiagonalIndex = rDiagonalIndex - difference;
        }
        return rDiagonalIndex;
    }

    //To determine if Queen can be placed at rowIndex and colIndex
    public boolean isValid(int rowIndex, int colIndex){
        if(!rowOccupied[rowIndex]){
            if(!colOccupied[colIndex]){
                if(!lDiagonalOccupied[leftDiagonalIndex(rowIndex, colIndex)]){
                    if(!rDiagonalOccupied[rightDiagonalIndex(rowIndex, colIndex)]){
                        return true;
                    }
                }
            }
        }
        return false;
    }

    //To copy the valid N queens placement to result
    public void copyBoard(){
        arrResult.add(new ArrayList<String>());
        for(int rindex = 0; rindex < boardSize; rindex++){
            StringBuilder sb = new StringBuilder();
            for(int cindex = 0; cindex < boardSize; cindex++){
                if(board[rindex][cindex]){
                    sb.append("Q");
                }
                else{
                    sb.append(".");
                }
            }
            arrResult.get(solutionCount).add(sb.toString());
        }
        solutionCount++;
    }

    //To recursively determine if Queen can be placed row by row
    public void queens(){
        //If N queens have been placed on board
        if(queenCount == boardSize){
            copyBoard();
            return;
        }

        /* Place a Queen
        @row determined by the number of queens already placed on board - queenCount
        @col validate for each column from 0 to n-1 - colIndex
        Determine if Queen can be placed at rowIndex and colIndex
            If yes, recursively call queens() to determine the next Queen's position
            else, try placing current Queen at the next column and repeat
         */
        for(int colIndex = 0; colIndex < boardSize; colIndex++){
            int rowIndex = queenCount;
            if(isValid(rowIndex, colIndex)){
                rowOccupied[rowIndex] = true;
                colOccupied[colIndex] = true;
                lDiagonalOccupied[leftDiagonalIndex(rowIndex, colIndex)] = true;
                rDiagonalOccupied[rightDiagonalIndex(rowIndex, colIndex)] = true;
                board[rowIndex][colIndex] = true;
                queenCount++;

                queens();

                queenCount--;
                rowOccupied[rowIndex] = false;
                colOccupied[colIndex] = false;
                lDiagonalOccupied[leftDiagonalIndex(rowIndex, colIndex)] = false;
                rDiagonalOccupied[rightDiagonalIndex(rowIndex, colIndex)] = false;
                board[rowIndex][colIndex] = false;
            }
        }
    }


    public void solve(int nQueens){
        boardSize = nQueens;
        rowOccupied = new boolean[boardSize];
        colOccupied = new boolean[boardSize];
        board = new boolean[boardSize][boardSize];
        lDiagonalOccupied = new boolean[2 * boardSize - 1];
        rDiagonalOccupied = new boolean[2 * boardSize - 1];
        queens();
        System.out.println(arrResult);
    }
}
