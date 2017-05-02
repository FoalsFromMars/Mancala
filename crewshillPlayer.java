/****************************************************************
 * studPlayer.java
 * Implements MiniMax search with A-B pruning and iterative deepening search (IDS). The static board
 * evaluator (SBE) function is simple: the # of stones in studPlayer's
 * mancala minue the # in opponent's mancala.
 * -----------------------------------------------------------------------------------------------------------------
 * Licensing Information: You are free to use or extend these projects for educational purposes provided that
 * (1) you do not distribute or publish solutions, (2) you retain the notice, and (3) you provide clear attribution to UW-Madison
 *
 * Attribute Information: The Mancala Game was developed at UW-Madison.
 *
 * The initial project was developed by Chuck Dyer(dyer@cs.wisc.edu) and his TAs.
 *
 * Current Version with GUI was developed by Fengan Li(fengan@cs.wisc.edu).
 * Some GUI componets are from Mancala Project in Google code.
 */




//################################################################
// studPlayer class
//################################################################

public class crewshillPlayer extends Player {
	

	/*Use IDS search to find the best move. The step starts from 1 and keeps incrementing by step 1 until
	 * interrupted by the time limit. The best move found in each step should be stored in the
	 * protected variable move of class Player.
	 */
	public void move(GameState state)
	{
		int depthCount = 1;
		while(true){
			maxAction(state, depthCount);//calling wrapper classes for MiniMax search
			depthCount++;//incrementing the maximum depth of IDS
			System.out.println("depth count = "+ depthCount);
		}
		//making random player - just randomize moves 0-5 with Java.random

	}

	// Return best move for max player. Note that this is a wrapper function created for ease to use.
	// In this function, you may do one step of search. Thus you can decide the best move by comparing the 
	// sbe values returned by maxSBE. This function should call minAction with 5 parameters.
	public int maxAction(GameState state, int maxDepth)
	{//always run from root of tree; helps with converting values percolating up tree to moves
		int v = Integer.MIN_VALUE; //negative infinity, essentially. -1 could be maximum of the minimums for this functions.
		int bestMove = -1;//can be negative 1, because array will never have index of -1
		
		//want to get max of the min actions here, by generating children
		//only one possible move per bin- moving all the marbles
		//exactly 6 children; in array, labeled 0-5 index
		for(int i=0; i<6; i++){
			//current state, child will be copy using copy constructor
			
			GameState childState = new GameState(state);//made child, now apply move 
			
			//only does it for initializing- need more generations in the
			
			
			
			//pass in index of bin # to move, method of childState
			//should check for legality of move here
			//illegalMove returns true if the move is legal
			System.out.println("generating move i " + i);
			if(childState.illegalMove(i)){//keep checking legality of moves throughout tree traversal (just making sure that you're searching worthwhile)
				
				
				System.out.println("applying move i " +i);
			//	childState.applyMove(i);//can only apply to child state, returns boolean true if player can move again (stone lands in their mancala)
				//NOW we can kick off min searching
				int u = minAction(state, 1, maxDepth, Integer.MIN_VALUE, Integer.MAX_VALUE); //no need to worry about overflow here
				v = Math.max(u, v); //u = local SBE best, v = best SBE value so far
				if(u >= v){
					bestMove = i;//this is the best possible INDEX- the value of the best index for player's move will be returned.
				}
			}
		}
		

		return bestMove;
	}

	//return sbe value related to the best move for max player
	//inputs current game state, best/highest Max score from root along path (alpha), best lowest min score (beta
	//outputs best score for Max available from current state
	public int maxAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta)
	{//aplha, beta recurse properly to get values
		//recurse again, max action calls min action, pass in currDepth + 1 each time, don't have to do inside function
		//still check if curr == max || gameover, can end on either min or max node
		//alpha negative infinity, beta positive
		int v = Integer.MAX_VALUE;
		
		if(currentDepth == maxDepth || state.gameOver()){
			return sbe(state);//if at terminal state or max depth, return that child state's sbe
		}else{
			GameState maxChildState = new GameState(state);//made child, now apply move
			System.out.println("making a new MAXchild for recursive call");
			v = Math.max(v, minAction(maxChildState, currentDepth++, maxDepth, alpha, beta));//
			if(v >= beta){
				return v;//prune remaining childs
			}
			alpha = Math.max(alpha, v);
		}
		return v;//return value of maximum of minimum of childs


	}
	//return sbe value related to the bset move for min player
	//outputs best score for min available from the current state
	public int minAction(GameState state, int currentDepth, int maxDepth, int alpha, int beta)
	{//aplha, beta recurse properly to get values
		//recurse again, max action calls min action, pass in currDepth + 1 each time, don't have to do inside function
		//still check if curr == max || gameover, can end on either min or max node
		//alpha negative infinity, beta positive
		
		//need to create game states here too
		
		int v = Integer.MIN_VALUE;
		
		if(currentDepth == maxDepth || state.gameOver()){
			return sbe(state);//if at terminal state or max depth, return that child state's sbe
		}else{
			GameState minChildState = new GameState(state);//made child, now store for recursion + grandkids
			System.out.println("making a new MINchild for recursive call");
			v = Math.min(v, minAction(minChildState, currentDepth++, maxDepth, alpha, beta));//minimum of the max nodes so far
			if(alpha >= v){
				return v;//prune remaining childs for mins
			}
			beta = Math.min(beta, v);
		}
		return v;//return value of maximum of minimum of childs

	}

	//the sbe function for game state. Note that in the game state, the bins for current player are always in the bottom row.
	private int sbe(GameState state)
	{
		//hard code- mancalas are located at indexes 6 and 13- this will be the simplest implementation of a board evaluation
		int currPlayerMancala = state.mancalaOf(6);
		
		int player2Mancala = state.mancalaOf(13);
		
		return currPlayerMancala - player2Mancala;//evaluation of current player's mancala count minus opponent mancala count
				
			//look ahead more, make it faster
		//should really only look at single board state
		
		//want to look deeper down the tree- max min max min etc, always evaluate how good it is
		
		//look ahead 3 moves 
		
		

	}
}

