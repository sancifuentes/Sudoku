/**
 * 
 */
package scifuentes_G20_A02;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.NoSuchElementException;
import java.util.Scanner;

/**
 * Title: SudokuGame class
 * 
 * Programming II, Assignment 2 Course: 420-G20
 * 
 * Description: Sudoku game logic
 * 
 * @author Santiago Cifuentes
 *
 */

public class SudokuGame {
	public String board[][] = new String[9][9];
	private int[] lastMove = new int[2];
	private FileWriter gameWriter;
	private Scanner fileReader;
	private String gameBoard;
	private File gameFile;

	SudokuInterface sudokuInterface = new SudokuInterface();
	SudokuFrame sudokuFrame = new SudokuFrame();

	public SudokuGame() {
		// TODO Auto-generated constructor stub

	} // SudokuGame()

	public boolean loadGame(String filename) throws FileNotFoundException {
		try {
			if (filename.isEmpty())
				gameFile = new File("sudoku.txt");

			else {
				gameFile = new File(filename);
				if (validateFile() == false)
					return false;
			}

			fileReader = new Scanner(gameFile);

			fileReader.useDelimiter("~");

			for (int row = 0; row < board.length; row++) {
				for (int col = 0; col < board[row].length; col++) {
					gameBoard = fileReader.next();
					board[row][col] = gameBoard;
					sudokuInterface.displayBoard(gameBoard);
				} // for inner
			} // for outer

			return true;

		} // try
		catch (FileNotFoundException e) {
			return false;

		} // catch

		catch (NoSuchElementException e) {
			return false;
		} // catch
	} // loadGame()

	private boolean validateFile() throws FileNotFoundException {
		Scanner fileValidate = new Scanner(gameFile);

		int rows = 0;

		while (fileValidate.hasNextLine()) {
			rows++;
			fileValidate.nextLine();
		}

		if (rows == 9)
			return true;
		else
			return false;

	} // validateFile()

	public boolean validateRow(String value, int row) {
		int count = 0;

		while (count < board[row].length) {
			if (value.equals(board[row][count])) // checks to see if the value is already present in the row
			{
				return false;
			}
			count++;
		}

		return true;
	} // validateRow(String, int)

	public boolean validateCol(String value, int col) {
		int count = 0;

		while (count < board[col].length) {
			if (value.equals(board[count][col])) // checks to see if the value is already present in the column
			{
				return false;
			}
			count++;
		}

		return true;
	} // validateCol(String, int)

	public boolean checkRow() {
		int total = 0;
		int correctRows = 0;

		for (int row = 0; row < board.length; row++) {
			for (int col = 0; col < board[row].length; col++)
				total += Integer.parseInt(board[row][col].replaceAll("\\s+", "")); // adds up all the values from the
																					// rows.

			if (total == 45) // if the sum of the row is 45, then that row is correct and does not contain
								// any duplicate values.
				correctRows++;
			total = 0;
		}

		if (correctRows == 9) // if there is 9 correct rows, then all rows are correct.
			return true;
		else
			return false;
	} // checkRow()

	public boolean checkColumn() {
		int total = 0;
		int correctCols = 0;

		for (int col = 0; col < board.length; col++) {
			for (int row = 0; row < board[col].length; row++)
				total += Integer.parseInt(board[row][col].replaceAll("\\s+", ""));

			if (total == 45)
				correctCols++;
			total = 0;
		}

		if (correctCols == 9)
			return true;
		else
			return false;
	} // validateColumn()

	public boolean checkEmpty() {
		boolean valid = false;
		int count = 0;

		for (int r = 0; r < board.length; r++) {
			for (int c = 0; c < board[r].length; c++) {
				if (board[r][c].equals("*")) {
					count++; // counts the number of empty squares
					valid = false;
				}
			} // for inner
		} // for outer

		if (count == 0) // if no more empty squares are counted, then return true
			valid = true;

		return valid;
	}

	public void lastRow(int row) {
		lastMove[0] = row;
	} // lastRow(int)

	public void lastCol(int col) // saves the last row and col value into the array lastMove
	{
		lastMove[1] = col;
	} // lastCol(int)

	public void undoMove() {
		board[lastMove[0]][lastMove[1]] = "*"; // undoes the last value to *
	} // undoMove()

	public void saveGame(String filename) {
		String gameFilename = filename;
		File gameSave = new File(gameFilename);

		try {
			gameWriter = new FileWriter(gameSave);

			for (int row = 0; row < board.length; row++) {
				for (int col = 0; col < board[row].length; col++) {
					gameWriter.write(board[row][col] + "~"); // saves the current board into the file with the tilde
																// delimiter
				} // for inner
			} // for outer
			gameWriter.close();
		} // try
		catch (IOException e) {

		} // catch

	} // saveGame(String)

	public void quitGame() {
		System.exit(0);
	} // quitGame()

} // SudokuGame
