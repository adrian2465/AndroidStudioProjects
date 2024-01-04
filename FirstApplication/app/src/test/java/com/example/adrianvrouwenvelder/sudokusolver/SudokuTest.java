package com.example.adrianvrouwenvelder.sudokusolver;

/**
 * Test cases for SudokuSolver.
 * @author: Adrian
 */

import static org.junit.Assert.fail;

import org.junit.Before;
import org.junit.Test;

// Test different sudoku puzzles.  The purpose of these
// tests is to determine whether or not the algorithm can solve the
// puzzles, and not to zoom in on a particular solution (if multiple solutions exist).
public class SudokuTest {
	private SudokuSolver ss=null;

	@Before
	public void init() {
		ss = new SudokuSolver();
	}

	@Test
	public void emptyTest() {
		// ==========================================================================
		assert(ss.init(new int[][] {
			// EMPTY
			{0,0,0,0,0,0,0,0,0}, // Row 1   
			{0,0,0,0,0,0,0,0,0}, // row 2  
			{0,0,0,0,0,0,0,0,0}, // row 3  
			{0,0,0,0,0,0,0,0,0}, // row 4  
			{0,0,0,0,0,0,0,0,0}, // row 5  
			{0,0,0,0,0,0,0,0,0}, // row 6  
			{0,0,0,0,0,0,0,0,0}, // row 7  
			{0,0,0,0,0,0,0,0,0}, // row 8  
			{0,0,0,0,0,0,0,0,0}, // row 9  
		}));

		ss.printGrid("\n\nPuzzle 0 - empty");
		if (!summarize()) fail("Could not solve puzzle.");
	}

	@Test
	public void invalidTest() {
		// ==========================================================================
		assert(!ss.init(new int[][] {
				// EMPTY
				{1,0,0,0,0,0,0,0,0}, // Row 1
				{0,0,0,0,0,0,0,0,0}, // row 2
				{0,0,1,0,0,0,0,0,0}, // row 3
				{0,0,0,0,0,0,0,0,0}, // row 4
				{0,0,0,0,0,0,0,0,0}, // row 5
				{0,0,0,0,0,0,0,0,0}, // row 6
				{1,0,0,0,0,0,0,0,0}, // row 7
				{0,0,0,0,0,0,0,0,0}, // row 8
				{0,0,0,0,0,0,0,0,0}, // row 9
		}));

		ss.printGrid("\n\nPuzzle 0 - Invalid (no solution attempted)");
	}

	@Test
	public void alaskaBeyondMagEasyTest() {
		//
		// ==========================================================================
		assert(ss.init(new int[][] {
			// Source for puzzle: Alaska Beyond Magazine May 2017
			// Minigrid centers: 1,1; 1,4; 1,7
			//                   4,1; 4,4; 4,7
			//                   7,1; 7,4; 7,7
			// EASY
			{0,4,7,5,0,2,9,0,0}, // Row 1   
			{5,1,0,6,0,0,0,2,0}, // row 2  
			{0,0,9,7,8,0,0,0,1}, // Row 3
			{0,0,0,4,9,0,0,6,0}, // row 4  3,3->4,4 5,5=>4,4    x/3*3+1
			{0,7,6,0,0,0,1,4,0}, // row 5
			{0,2,0,0,6,5,0,0,0}, // row 6
			{8,0,0,0,3,6,7,0,0}, // row 7
			{0,3,0,0,0,4,0,1,8}, // row 8
			{0,0,4,8,0,1,5,3,0}  // row 9
		}));
		ss.printGrid("\n\nPuzzle 1 = easy");
		if (!summarize()) fail("Could not solve puzzle.");
	}

	@Test
	public void alaskaBeyondMagMediumTest() {	
		// ==========================================================================
		assert(ss.init(new int[][] {
			// Source for puzzles: Alaska Beyond Magazine May 2017
			// MEDIUM
			{2,0,0,3,0,0,1,0,4}, // Row 1   
			{4,0,0,0,0,0,0,3,0}, // row 2  
			{0,7,0,4,1,0,2,0,0}, // Row 3
			{9,0,0,0,0,3,8,1,0}, // row 4  3,3->4,4 5,5=>4,4    x/3*3+1
			{0,4,0,9,0,6,0,2,0}, // row 5
			{0,6,8,2,0,0,0,0,9}, // row 6
			{0,0,2,0,5,4,0,7,0}, // row 7
			{0,8,0,0,0,0,0,0,1}, // row 8
			{1,0,5,0,0,8,0,0,2}  // row 9
		}));

		ss.printGrid("\n\nPuzzle 2 - medium");
		if (!summarize()) fail("Could not solve puzzle.");
	}

	@Test
	public void extremeSudokuEvilTest() {
		//
		// ==========================================================================
		assert(ss.init(new int[][] {
			// EVIL (src www.extemesudoku.info/sudoku.html)
			{0,0,3, 1,0,8, 9,0,0}, // Row 1
			{0,0,0, 0,0,0, 0,0,0}, // row 2
			{8,0,2, 0,0,0, 5,0,4}, // Row 3

			{9,0,0, 0,4,0, 0,0,1}, // row 4  3,3->4,4 5,5=>4,4    x/3*3+1
			{0,0,0, 3,0,7, 0,0,0}, // row 5
			{6,0,0, 0,9,0, 0,0,5}, // row 6

			{1,0,4, 0,0,0, 2,0,3}, // row 7
			{0,0,0, 0,0,0, 0,0,0}, // row 8
			{0,0,7, 4,0,9, 6,0,0}  // row 9
		}));

		ss.printGrid("\n\nPuzzle 3 - evil (extremesudoku)");
		if (!summarize()) fail("Could not solve puzzle.");
	}

	@Test
	public void extremeSudokuExtremeTest() {
		// ==========================================================================
		assert(ss.init(new int[][] {
			// EXTREME (src www.extemesudoku.info/sudoku.html)
			{0,1,0,0,8,0,0,6,0}, // Row 1   
			{2,0,0,4,0,1,0,0,8}, // row 2  
			{0,0,3,0,0,0,1,0,0}, // row 3  
			{0,5,0,1,0,6,0,3,0}, // row 4  
			{4,0,0,0,0,0,0,0,9}, // row 5  
			{0,3,0,8,0,9,0,2,0}, // row 6  
			{0,0,1,0,0,0,5,0,0}, // row 7  
			{9,0,0,2,0,4,0,0,1}, // row 8  
			{0,7,0,0,1,0,0,9,0}, // row 9  
		}));

		ss.printGrid("\n\nPuzzle 4 - extreme (extremeSudoku)");
		if (!summarize()) fail("Could not solve puzzle.");
	}
	
	@Test
	public void impossibleTest() {
		assert(ss.init(new int[][] {
			// IMPOSSIBLE src: me.
			{0,1,0, 0,8,0, 0,6,0}, // Row 1
			{2,0,0, 5,0,1, 0,0,8}, // row 2
			{0,0,3, 0,0,0, 1,0,0}, // row 3

			{0,5,0, 0,0,6, 0,3,0}, // row 4
			{4,0,0, 0,0,0, 0,0,9}, // row 5
			{0,3,0, 8,0,9, 0,2,0}, // row 6

			{0,0,1, 0,0,0, 5,0,0}, // row 7
			{9,0,0, 2,0,4, 0,0,1}, // row 8
			{0,7,0, 0,1,0, 0,9,0}, // row 9
		}));

		ss.printGrid("\n\nPuzzle 5 - impossible (selfmade)");

		if (summarize()) fail("Able to solve impossible puzzle!");
	}


	@Test
	public void bruteForceBreaker() { // Takes 88,178,562 iterations of solve to complete!  
		assert(ss.init(new int[][] {
			// HARD for Backtracking Algorithm: src: https://en.wikipedia.org/wiki/Sudoku_solving_algorithms.
			{0,0,0,0,0,0,0,0,0}, // Row 1   
			{0,0,0,0,0,3,0,8,5}, // row 2  
			{0,0,1,0,2,0,0,0,0}, // row 3  
			{0,0,0,5,0,7,0,0,0}, // row 4  
			{0,0,4,0,0,0,1,0,0}, // row 5  
			{0,9,0,0,0,0,0,0,0}, // row 6  
			{5,0,0,0,0,0,0,7,3}, // row 7  
			{0,0,2,0,1,0,0,0,0}, // row 8  
			{0,0,0,0,4,0,0,0,9}, // row 9  
		}));

		ss.printGrid("\n\nPuzzle 6 - backtracker breaker (Wikipedia)");

		if (!summarize()) fail("Unable to solve puzzle!");
	}
	/**
	 * Helper method to factor out common solve/printGrid code.
	 * @return true if solvable, false otherwise.
	 */
	private boolean summarize() {
		if (ss.solve(0,0)) {
			ss.printGrid("Initially filled with "+ss.getNumConstants()+" digits. Completed in "+ss.getCount()+" solve calls");
			return true;
		} else { 
			System.out.println("No solution in "+ss.getCount()+" solve calls");
			return false;
		}
	}
}
