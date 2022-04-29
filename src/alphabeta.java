
public class alphabeta implements Player {

	int id;
	int cols;
	int opponent_id;
	int alpha, beta;
	
	/**
     * Return the name of this player.
     * 
     * @return A name for this player
     */
   public String name() {
	   
    	return "Alfalfa";
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
    	this.opponent_id= 3-id;
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
    public void calcMove(Connect4Board board, int oppMoveCol, Arbitrator arb) 
    
    
        throws TimeUpException {
    	//make sure there is room to make a move
    	
    	if(board.isFull()) {
    		throw new Error ("Complaint: the board is full");
    	}
    	
    	
    	int move = 0; //0-6 the column number
    	int depth = 1; 
    	
    	

    	//while theres time remaining and search depth is <= the number of moves remaining
    	while(!arb.isTimeUp() && depth <= board.numEmptyCells()) {
    		
    		
    		//iterate through 7 cols like greedy and keep track of the best score from minimax and set move to best score
    		
        	int bestScore = -1000; //stores highest value
            
        	int [] scoreKeep = new int[7]; //array that stores temporary scores to eventually see which score is the highest
            
            int colHighestScore = 0;
            
            
            // Find maximum score for all possible moves 
            
            for (int i = 0 ; i < cols ; i++) { //Runs through each column and finds the highest score if there is a possible move

       
            	if (board.isValidMove(i)) { //make sure move is valid
            		
    	        	board.move(i, id); 
    	     
    	        	scoreKeep[i] = alphabeta(board, depth - 1, alpha, beta, false, arb);
    	            board.unmove(i, id);
                
                if (scoreKeep[i] > bestScore) {
                	
                	bestScore = scoreKeep[i];
    	            colHighestScore = i;  //storing column location in i
                
                }
                
              }
            	
            }
            
            arb.setMove(colHighestScore); 
            depth++;
        	
    	}

    	
    }
    
    public int alphabeta(Connect4Board board, int depth, int alpha, int beta, boolean isMaximizing, Arbitrator arb) {
  	
    	int bestScore; //stores highest value
    	
    	if(depth == 0 || board.isFull() || arb.isTimeUp()) {
    		return calcScore(board, id) - calcScore(board, opponent_id);
    	}
    	
    	if(isMaximizing) {
    		bestScore = -1000;
    		for (int i = 0 ; i < cols ; i++) {
    			
    			if (board.isValidMove(i)) {
    			
	    			board.move(i, id);
	    			bestScore = Math.max(bestScore, alphabeta(board, depth - 1, alpha, beta, false, arb));
	    			alpha = Math.max(alpha, bestScore);
    			
	    			if (alpha >= beta) {
	    				break;	
	    			}
	    			
	    			board.unmove(i, id);
	    			
    			}
    		}
    		
    		 return bestScore;
    	}
    		
    	else {
    		
    		bestScore = 1000;
    		
    		for (int i = 0 ; i < cols ; i++) {
    			
    			if (board.isValidMove(i)) {
    				
	    			board.move(i, opponent_id);
	    			bestScore = Math.min(bestScore, alphabeta(board, depth - 1, alpha, beta, true, arb));
	    			beta = Math.min(beta, bestScore);
	    			
	    			if (alpha >= beta) {
	    				break;
	    			}
	    			
	    			board.unmove(i, opponent_id);
    			
    			}
    		}
    			
    			return bestScore;
    			
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
    
    
//    function alphabeta(node, depth, α, β, maximizingPlayer):
//  
//    if depth = 0 or node is a terminal node then
//    		return the heuristic value of node
//    	if maximizingPlayer then
//    		value = −∞
	//    	for each child of node do
		//    	value = max(value, alphabeta(child, depth − 1, α, β, FALSE))
		//    	α = max(α, value)
		//    	if α ≥ β then
			//    	break /* β cut-off */
	//    	return value
//    	else
	//    	value = +∞
	//    	for each child of node do
	//    		value = min(value, alphabeta(child, depth − 1, α, β, TRUE))
	//    		β = min(β, value)
	//   	 	if α ≥ β then
	//   		 	break /* α cut-off */
	//    	return value


    
    
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
