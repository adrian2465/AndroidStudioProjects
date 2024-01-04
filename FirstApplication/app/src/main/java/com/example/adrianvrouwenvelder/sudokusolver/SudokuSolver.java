package com.example.adrianvrouwenvelder.sudokusolver;

/**
 * Solve any solvable SUDOKU puzzle.
 * This solution uses dynamic programming with some heuristics to arrive
 * at a solution.
 * @author Adrian
 */
public class SudokuSolver {
	
	private int count=0,numConstants=0;
	private int[][] grid=null;

	// 'possibles' contains 'true' for each of the possible valid values (when matrix contains only constants) 
	// for each position.  This serves as a starting point for permutating through
	// all possible valid combinations.  possibles[i][j][8] means that 8 is a possibility for position i,j.
	private boolean[][][] possibles;

	/**
	 * Solve the puzzle.
	 * 
	 * Solve sets a position to a possible value, checks its validity, and if that's still valid, 
	 * moves to the next space and carries on this way until it has filled the board; if it has filled the board, 
	 * then that's a viable solution.
	 *
	 * This code uses recursion (backtracking algorithm) so it can store a particular cell location on the stack.  
	 * So, each level of recursion
	 * is responsible for a cell in the grid, and can increment just that cell to the next possible value.
	 *
	 * As briefly mentioned, each possible is checked for validity before recursing deeper; this 
	 * prunes the search space quite rapidly most of the time.  This is much more efficient than filling
	 * the entire grid randomly before checking whether it's a valid solution.
	 *
	 * Complexity: solve can go 81 stack levels deep (m) , and for each of those levels, it may try on the order of 
	 * 9 combinations (n) -- probably much less. 
	 * Ostensibly, the complexity is O(n^m) (i.e. 9^81); however, in practice, it is much less since a good number
	 * of the guesses are immediately (within 30 tests -- the 9 cell minigrid, the 9 on the row, and the 9 on the column)
	 * eliminated.  
	 * 
	 * @param i
	 * @param j
	 * @return true if a solution exists for this placement, false otherwise.
	 */
	public boolean solve(int i, int j) {
		count++;
		// Figure out where we're going next.
		int newJ=j+1;
		int newI=i;
		if (newJ>8) {
			newJ=0;
			newI=i+1;
		}
		// Find next valid value for this item.  Place it in the grid, and recurse.
		// increment is the next position to increment to guarantee hitting all 
		// permutations. (Dynamic Programming trick)
		for(int increment=1;increment<10;increment++) {
			// Find a valid value.
			if (!possibles[i][j][increment]&&grid[i][j]>=0) {
				continue; // continue if this is not an option.
			}
			if (grid[i][j]>=0) { // Only enter if if this is not yet known valid.
				if (!isValid(i,j,increment)) continue; // Not an option.
				grid[i][j]=increment;
			} else {
				// Cause loop to exit because there's only one value.
				increment=10;
			}
			// If we placed a valid item at the end, we're done.
			if (j==8&&i==8) return true;

			// We got here because there are options left.
			// If we found a solution down deep, return true
			if (solve(newI,newJ)) return true;

			// No solution yet.
			// If no more possibilities exist for this position,
			// return false and let the parent in recursion iterate
			if (grid[i][j]>0) grid[i][j]=0; // Revert this hypothesis.
		}
		// If we get down here and didn't already find something, there is no solution.
		return false;
	}

	/**
	 * @return the number of constants provided.
	 */
	public int getNumConstants() {
		return numConstants;
	}

	/**
	 * @return the number of solve iterations it took to get to a solution.
	 */
	public int getCount() {
		return count;
	}

	/**
	 * Check whether the "minigrid" or 9x9 has the proposed value.
	 * If val is in the minigrid, return true (which indicates invalid)
	 * @param row
	 * @param col
	 * @param val
	 * @return true if val is already in this minigrid.
	 */
	private boolean miniGridHasValue(int row, int col, int val) {
		// Figure out the minigrid for this row/col
		int miniGridRow=(row/3)*3+1; // Calculate center of minigrid
		int miniGridCol=(col/3)*3+1; // Calculate center of minigrid
		// Check the entire minigrid.
		for (int i=miniGridRow-1;i<=miniGridRow+1;i++) 
			for (int j=miniGridCol-1;j<=miniGridCol+1;j++) 
				if (grid[i][j]==val||grid[i][j]==-val) {
					return true;
				}
		return false;
	}
	
	/**
	 * Check whether row or column has val, return true if it does (indicating invalid)
	 * @param row
	 * @param col
	 * @param val
	 * @return true if val is already in this row or column.
	 */
	private boolean rowOrColHasValue(int row, int col, int val) {
		for (int i=0;i<grid.length;i++) if (grid[i][col]==val||grid[i][col]==-val) {
			return true;
		}
		for (int j=0;j<grid.length;j++) if (grid[row][j]==val||grid[row][j]==-val) {
			return true;
		}
		return false;
	}

	/**
	 * Combine the validity checks to see whether this proposed value is valid for this position
	 * @param row
	 * @param col
	 * @param val
	 * @return true if val in this spot is valid, false if not.
	 */
	private boolean isValid(int row, int col, int val) {
		if (grid[row][col]!=0) return false;
		if (miniGridHasValue(row,col,val)||rowOrColHasValue(row,col,val)) return false;
		return true;
	}

	/* Check whether initial board is valid or not. Invoke after initials have been negated. */
	private boolean isValid() {
		for (int row=0;row<grid.length;row++) {
			for (int col=0;col<grid.length;col++) {
				int val = grid[row][col];
				if (val==0) continue;
				grid[row][col]=0;
				boolean isValid = !(miniGridHasValue(row,col,val)||rowOrColHasValue(row,col,val));
				grid[row][col]=val;
				if (!isValid) return false;
			}
		}
		return true;
	}
	/**
	 * Initialize the grid by setting the constants to be negative (so we know they're original, 
	 * and shouldn't be turned into guesses).
	 * Initialize the "possibles" as well, based on what's valid after filling in
	 * the constants.
	 * @param tryGrid
	 */
	public boolean init(int[][] tryGrid) {
		numConstants=0;
		grid=tryGrid;
		possibles=new boolean[grid.length][grid[0].length][10];
		// Place all the constants.
		for (int i=0;i<grid.length;i++) for (int j=0;j<grid[i].length;j++) {
			if (grid[i][j]==0) continue;
			grid[i][j]=-grid[i][j];
			numConstants++;
		}
		if (!isValid()) return false;
		// Initialize the possibles matrix.
		for (int i=0;i<grid.length;i++) 
			for (int j=0;j<grid[i].length;j++) 
				if (grid[i][j]<0) 
					possibles[i][j][-grid[i][j]]=true; // All constants are assumed to be possibles.  
				else 
					for (int k=1;k<=9;k++) 
						possibles[i][j][k]=isValid(i,j,k);
		return true;
	}

	/**
	 * Simply pretty-print the grid.
	 * @param msg Message to produce along with grid.
	 */
	public void printGrid(String msg) {
		System.out.println(msg);
		for (int i=0;i<grid.length;i++)  {
			for (int j=0;j<grid[i].length;j++) {
				int val=Math.abs(grid[i][j]);
				System.out.print(String.format("%s%s",
							val==0?" ":(""+val),
							(j+1)%3==0?"|":" "));
			}
			if ((i+1)%3==0) System.out.println("\n------------------");
			else System.out.println();
		}
	}
	public int valueAt(int row, int col) {return grid[row][col];}
}
