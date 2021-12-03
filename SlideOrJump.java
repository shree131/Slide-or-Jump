/**
* This class represents the game "Slide or Jump." The player can either   
* slide to the adjacent cell or jump over the adjacent cell, and the cost  
* of sliding or jumping is added until the player reaches the last cell. 
* The program calculates the optimal strategy to ensure a win, both 
* recursively and dynamically.
*
* @author  Shreeyasha Pandey
*/

public class SlideOrJump {


// Instance variable to represent the board cells
private int[] boardArray;

// Array to store series of optimal moves
private String[] movesArray;




   /**
    * Constructor to initialize the board
    * @param board The array containing the cost of visiting each cells 
    */
    public SlideOrJump (int[] board) {
   
      boardArray = new int[board.length];
      movesArray = new String[board.length-1];
      
      
      // Set the first cell to  0 
      this.boardArray[0] = 0;
      
      
      // Store the values of each cell to an array
      for (int i = 1; i < board.length; i++) {
      
        this.boardArray[i] = board[i];
        
       }
   } 
   
   
   
   
   /**	
    * Determines the minimum cost to play the game by a method call to totalCost
    * @return totalCost The minimum total cost needed to win the game            
    */
    public long recSolution() {
    
      int totalCost = totalCost(0);

      return totalCost;
   }


   
   /**	
    * Computes the optimal total cost of playing the game recursively 
	 * @param n The cell to be evaluated
    * @return The optimal cost of the game             
    */
    public int totalCost(int n) { 
      
      // Base Cases
      
      // When n is the last cell
      if (n == boardArray.length - 1) {
      
         // Return the cost of the last cell
         return boardArray[n];
         
      }
      
      
      // When n is the second cell from last 
      else if (n == boardArray.length - 2) {
      
         // Return the sum of the current cell and the last cell 
         return boardArray[n] + boardArray[n+1];
         
      }
      
      
      // When n is the third cell from last
      else if (n == boardArray.length - 3) {
      
         // Return the sum of the current cell and the last cell
         return boardArray[n] + boardArray[n+2];
         
      }
      
      
      // The general case
      
      // When n is a cell other than first, second, and third cell from the end
      else {
      
         // Return the sum of the current cell and the smaller between the cost of sliding and jumping
         return boardArray[n] + Math.min(totalCost(n+1), totalCost(n+2)); 
         
      }
      
   } 
   
   
   
   
   /**	
    * Computes the optimal cost of playing the game using dynamic programming
    * @return totalCost The minimum total cost to play the game            
    */
    public long dpSolution() {
   
      
      int len = boardArray.length;
      int min;
      
      // An array to store partial optimal solution
      int[] costArray = new int[len];
 

      // Initialize the cost to visit the last cell
      costArray[len - 1] = boardArray[len - 1];
      
      
      // Initialize the cost to visit the second cell from the end
      costArray[len - 2] = boardArray[len - 2] + costArray[len - 1];
      
      // Store the optimal moves for this cell
      movesArray[len-2] = "S";
      
      
      
      for(int n = len-3; n >= 0; n--) {
      
         // If the player is in the third cell from last
         if (n == len - 3) {
         
            // Add the cost of the current cell and the last cell
            costArray[n] = boardArray[n] + costArray[n + 2];
            
            // Store the optimal moves for this cell
            movesArray[n] = "J";
            
         }
         
         
         else {
            
            
            // Add the current cell and the smallest between the cost of sliding and jumping
            costArray[n] = boardArray[n] + Math.min(costArray[n + 1], costArray[n + 2]);
            
            // Make a call to optimalMoves to track the optimal move for the cell 
            optimalMoves(n, costArray[n + 1], costArray[n + 2]);
            
         }   
         
      }
      
      return costArray[0];
      
   }
   
   
   
   
   /**	
    * Determines whether to slide or jump and tracks the optimal move for a cell
    * @param cost1 The cost of sliding to the adjacent cell
    * @param cost2 The cost of jumping over the adjacent cell
    * @param index The index of the current cell
    */
    public void optimalMoves(int index, int cost1, int cost2) {
  
      int minCost = Math.min(cost1, cost2);
      
      
      // Cost of sliding is less to that of jumping
      if (minCost == cost1) {  
      
         movesArray[index] = "S";
         
      }
      
      
      // Cost of jumping is less to that of sliding
      else {
      
         movesArray[index] = "J";
         
      }
      
          
   }
   
  
   
   
   /**	
    * Returns a sequence of moves required to win the game
    * @return moves The string containing the sequence of optimal moves
    */
    public String getMoves() {
    
    
      // Make call to dpSolution
      dpSolution();
      
      String moves = "";       
      int i=0;
      

      while(i < movesArray.length) {
      
         // Concatenate the string
          moves += movesArray[i];
          
          
          // Increment index by 1 if optimal move is to slide
          if (movesArray[i].equals("S")) {
            
            i = i + 1;
            
          }
          
          
          // Increment index by 2 if optimal moves is to jump
          else if (movesArray[i].equals("J")){
            
            i = i + 2;
            
          }
          
      }
      
      return moves;
   }
   
   
} 