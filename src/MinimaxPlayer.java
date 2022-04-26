
public class MinimaxPlayer {


	int id;
	int cols;
	int opponent_id;
	
	/**
     * Return the name of this player.
     * 
     * @return A name for this player
     */
    String name() {
    	return "Minnie";
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
    void init(int id, int msecPerMove, int rows, int cols) {
    	
    	this.id = id; //id is your players id
    	this.cols = cols;
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
    void calcMove(Connect4Board board, int oppMoveCol, Arbitrator arb) 
    
        throws TimeUpException {
    	//make sure there is room to make a move
    	
    	if(board.isFull()) {
    		throw new Error ("Complaint: the board is full");
    	}
    	
    	
    	int move = 0; //0-6 the column number
    	int maxDepth = 1; 
    	//while theres time remaining and search depth is <= the number of moves remaining
    	while(!arb.isTimeUp() && maxDepth <= board.numEmptyCells()) {
    		
//    		bestScore = -1000;
//    		
//    		for (int i cols) {
//    			board.move(i,  id);
//    			score = minimax(board, depth, isMaximizing, arb);
    			
    		

        	//run first level of minimax serach
        	//set move to be the column corresponding to the best score
    		maxDepth++;
        	arb.setMove(move);
    	}

    	
    }
    
    /**
	    nested for loops that goes through every column and row 
	    make a function to pass array parameter into
	    parameter = game board -- if statement 
	    	if spot is full don't touch
	    	if not full then look around it
	    		see if you have any of your spots that are filled
	    		
	    	
	    
    
    
    */
 
    
    //extra credit -> make another method that calculates a heuristic score
    

    public int minimax(Connect4Board board, int depth, boolean isMaximizing, Arbitrator arb) {
    	
    	int bestScore;
    	
    	if(depth == 0 || board.isFull() || arb.isTimeUp()) {
    		return calcScore(board, id) - calcScore(board, opponent_id);
    	}
    	
    	if(isMaximizing) {
    		bestScore = -1000;
    		for (int i = 0 ; i < cols ; i++) {
    			board.move(i, id);
    			bestScore = Math.max(bestScore, minimax(board, depth - 1, false, arb));
    			board.unmove(i, id);
    		}
    	}
    		
    	else {
    		bestScore = 1000;
    		for (int i = 0 ; i < cols ; i++) {
    			board.move(i, opponent_id);
    			bestScore = Math.min(bestScore, minimax(board, depth - 1, true, arb));
    			board.unmove(i, opponent_id);
    			}
    			
    			return bestScore;
    			
    		}
    	
    	return bestScore;
		
    	}
    	
    //best score at that particular point 
    	
//   if maximizingPlayer then
//    	bestScore := −1000 --> really small number so we're able to update
//    	for each possible node --> go through the board and try all the diff moves you can make
	//    	board.move(...) for your player
	//    	bestScore := Math.max(value, minimax(board, depth − 1, FALSE)) 
	//    	board.unmove(...)
//    	return bestScore
    
   // else /* minimizing player */ 
//    	bestScore = 1000;
//    	for each possible move do
	//    	board.move(...) for your opponents player
	//    	bestScore = Math.min(bestScore, minimax(board, depth -1, TRUE, arb)
	//    return bestScore
    	
    
    

    
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


