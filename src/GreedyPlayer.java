
public class GreedyPlayer implements Player{

	int id;
	int cols;
	
	/**
     * Return the name of this player.
     * 
     * @return A name for this player
     */
    public String name() {
    	return "Greedy Mike";
    }
  
    /**
     * Initialize the player. The game calls this method once,
     * before any calls to calcMove().
     * 
     * @param id integer identifier for the player (can get opponent's id via 3-id);
     * @param msecPerMove time allowed for each move
     * @param rows the number of rows in the board
     * @param cols the number of columns in the board
     */
    public void init(int id, int msecPerMove, int rows, int cols) {
    	
    	this.id = id; //id is your players id
    	this.cols = cols; //column number
    }

    
    /**
     * Called by driver program to calculate the next move.
     *  
     * @param board current connect 4 board
     * @param oppMoveCol column of opponent's most recent move; -1 if this is the first move 
     * 		  of the game; note that the board may not be empty on the first move of the game!
     * @param arb handles communication between game and player
     * @throws TimeUpException If the game determines the player has run out of time
     */
    public void calcMove(Connect4Board board, int oppMoveCol, Arbitrator arb) 
    
     throws TimeUpException {
    	//make sure there is room to make a move
    	
    	if(board.isFull()) {
    		throw new Error ("Complaint: the board is full");
    	}
    	
    	int maxValue = -1000; //stores highest value
       
    	int [] scoreKeep = new int[7]; //array that stores temporary scores to eventually see which score is the highest
        
        int colHighestScore = 0;
        
        
        // Find maximum score for all possible moves 
        
        for (int i = 0 ; i < cols ; i++) { //Runs through each column and finds the highest score if there is a possible move

   
        	if (board.isValidMove(i)) { //make sure move is valid
        		
	        	board.move(i, id); 
	     
	        	scoreKeep[i] = calcScore (board, id);
	            board.unmove(i, id);
            
            if (scoreKeep[i] > maxValue) {
            	
	            maxValue = scoreKeep[i];
	            colHighestScore = i; //storing column location in i
            
            }
            
          }
        	
        }
        
        arb.setMove(colHighestScore); 
    	
    }
    

    
 // Return the number of connect-4s that player #id has.
    public int calcScore(Connect4Board board, int id)
	{
		final int rows = board.numRows();
		final int cols = board.numCols();
		int score = 0;
		// Look for horizontal connect-4s.
		for (int r = 0; r < rows; r++) {
			for (int c = 0; c <= cols - 4; c++) {
				if (board.get(r, c + 0) != id) continue;
				if (board.get(r, c + 1) != id) continue;
				if (board.get(r, c + 2) != id) continue;
				if (board.get(r, c + 3) != id) continue;
				score++;
			}
		}
		// Look for vertical connect-4s.
		for (int c = 0; c < cols; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c) != id) continue;
				if (board.get(r + 1, c) != id) continue;
				if (board.get(r + 2, c) != id) continue;
				if (board.get(r + 3, c) != id) continue;
				score++;
			}
		}
		// Look for diagonal connect-4s.
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = 0; r <= rows - 4; r++) {
				if (board.get(r + 0, c + 0) != id) continue;
				if (board.get(r + 1, c + 1) != id) continue;
				if (board.get(r + 2, c + 2) != id) continue;
				if (board.get(r + 3, c + 3) != id) continue;
				score++;
			}
		}
		for (int c = 0; c <= cols - 4; c++) {
			for (int r = rows - 1; r >= 4 - 1; r--) {
				if (board.get(r - 0, c + 0) != id) continue;
				if (board.get(r - 1, c + 1) != id) continue;
				if (board.get(r - 2, c + 2) != id) continue;
				if (board.get(r - 3, c + 3) != id) continue;
				score++;
			}
		}
		return score;
	}

    
    
}
