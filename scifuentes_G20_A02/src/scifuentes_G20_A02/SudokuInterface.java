package scifuentes_G20_A02;

import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.util.Scanner;

/**
 * Title: SudokuInterface class
 * 
 * Programming II, Assignment 2 Course: 420-G20
 * 
 * Description: CLI code for SudokuGame
 * 
 * @author Santiago Cifuentes
 *
 */

public class SudokuInterface {
	private static SudokuGame sudokuGame = new SudokuGame();
	private Scanner keyboard;
	public static String filename;
	private boolean resume = true;

	public SudokuInterface() {
		// TODO Auto-generated constructor stub
		keyboard = new Scanner(System.in);

	} // SudokuInterface()

	public void initialize() {
		System.out.println("Welcome to Heritage Sudoku.");
		System.out.print("Please enter the filename for your puzzle: ");

		do {
			filename = keyboard.nextLine();
			if (filename.isEmpty()) {
				System.err.println("No filename entered. sudoku.txt used as default file.");
			}

			try {
				if (sudokuGame.loadGame(filename) == true) {
					resume = true;
					System.out.println(
							"\n\nType Q at any time to exit the game, S to save the game or U to undo your last move.");

				}

				else if (sudokuGame.loadGame(filename) == false) {
					System.err.println(filename
							+ " does not exist or has an incompatible file format. Please enter a valid filename.");
					resume = false;

				}
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		} while (resume != true);
	} // initialize()

	public void inputPositionValues() {
		do {
			System.out.println();
			System.out.print("Enter square number (row, column) => ");
			String posValue = keyboard.next();

			if (posValue.equalsIgnoreCase("q")) {
				System.out.println("\nAre you sure you want to quit? All unsaved progress will be lost.");
				System.out.println("Type Y to confirm or any other key to cancel.");
				String confirm = keyboard.next();

				if (confirm.equalsIgnoreCase("y")) {
					System.out.println("\n\nThank you for playing Heritage Sudoku!");
					sudokuGame.quitGame();
				}

				else if (!confirm.equalsIgnoreCase("y"))
					resume = false;
			}

			else if (posValue.equalsIgnoreCase("s")) {
				System.out.println("\n\nSaving game...");
				sudokuGame.saveGame(filename);
				System.out.println(filename);
				resume = false;
			}

			else if (posValue.equalsIgnoreCase("u")) {
				System.out.println("\nLast move undone");
				sudokuGame.undoMove();
				resume = false;
			}

			else {
				String rowVal = posValue.substring(0, posValue.indexOf(",")); // ignores the comma
				int rowIn = Integer.parseInt(rowVal);

				String colVal = posValue.substring(posValue.indexOf(",") + 1, posValue.length());
				int colIn = Integer.parseInt(colVal);

				if (validatePositionValues(rowIn, colIn) == false) {
					System.err.println("\nInput invalid. It must be between 1-9. Please try again.");
					resume = false;
				}

				else if (validatePositionValues(rowIn, colIn) == true) {
					resume = true;
					boardInputValue(rowIn, colIn);
				}

			} // else

		} while (resume != true); // If a value is wrong, lets the user input the value again. Also let's the user
									// use one of the three commands any time.

	} // inputValues()

	public void boardInputValue(int rowIn, int colIn) {
		String[][] theBoard = getBoard();

		do {
			System.out.println();
			System.out.print("Enter value (1-9) : ");
			String value = keyboard.next();

			if (value.equalsIgnoreCase("q")) {
				System.out.println("\nAre you sure you want to quit? All unsaved progress will be lost.");
				System.out.println("Press Y to confirm or any other key to cancel.");
				String confirm = keyboard.next();

				if (confirm.equalsIgnoreCase("y")) {
					System.out.println("\n\nThank you for playing Heritage Sudoku!");
					sudokuGame.quitGame();
				}

				else if (!confirm.equalsIgnoreCase("y"))
					resume = false;
			}

			else if (value.equalsIgnoreCase("s")) {
				System.out.println("\n\nSaving game...");
				sudokuGame.saveGame(filename);
				resume = false;
			}

			else if (value.equalsIgnoreCase("u")) {
				System.out.println("\nLast move undone");
				sudokuGame.undoMove();
				updateBoard(theBoard);
				inputPositionValues();
				resume = false;
			}

			else {
				if (!(theBoard[rowIn - 1][colIn - 1].equals("*"))) {
					if (colIn == 1) {
						if (validateRow(value, rowIn - 1) == true && validateCol(value, colIn - 1) == true) {
							theBoard[rowIn - 1][colIn - 1] = "\n" + value; // fixes bug encountered with col 0 where all "*"
																				// in that column could not be recognized as
																				// empty.
							sudokuGame.lastRow(rowIn - 1);
							sudokuGame.lastCol(colIn - 1);					
							updateBoard(theBoard);
						}
					}

					else {
						System.err.println("Invalid move. That location already has a value. Please try again.");
						inputPositionValues();
					}

				} // if

				else if (theBoard[rowIn - 1][colIn - 1].equals("*")) // rowIn-1 & colIn-1 => zero indexing
				{

					if (validateValue(value) == false)
						boardInputValue(rowIn, colIn);

					else if (validateValue(value) == true) {
						if (validateRow(value, rowIn - 1) == true && validateCol(value, colIn - 1) == true) {
							theBoard[rowIn - 1][colIn - 1] = value;
							sudokuGame.lastRow(rowIn - 1);
							sudokuGame.lastCol(colIn - 1);
							updateBoard(theBoard);

							if (sudokuGame.checkEmpty() == true) {
								System.out.println("\nAll of the empty squares have been filled out!");

								if (sudokuGame.checkRow() == true && sudokuGame.checkColumn() == true) {
									System.out.println("You have won! Thank you for playing!");
									sudokuGame.quitGame();
								}

								else
									System.out.println("You have lost! Better luck next time!");
							}

						} // if

					}
				}

			} // else

		} while (resume != true);

		inputPositionValues(); // lets the user input values until board is filled.

	}

	public boolean validatePositionValues(int rowIn, int colIn) {
		boolean valid = false;

		if ((rowIn > 0 && rowIn < 10) && (colIn > 0 && colIn < 10)) // verifies that the position values are between 1-9
			valid = true;
		else
			valid = false;

		return valid;

	} // validateValues(int, int)

	public boolean validateValue(String value) {
		boolean valid = false;

		if (Integer.parseInt(value) >= 10 || Integer.parseInt(value) <= 0) {
			System.err.println("\nValue invalid. It must be between 1 and 9, please try again.");
			valid = false;
		}

		else
			valid = true;

		return valid;
	} // validateValue(int)

	public boolean validateRow(String value, int rowIn) {
		boolean valid = false;

		if (sudokuGame.validateRow(value, rowIn) == true)
			valid = true;

		else if (sudokuGame.validateRow(value, rowIn) == false) {
			System.err.println("Invalid move. There is already a " + value + " in that row. Please try again");
			valid = false;
		}

		return valid;
	} // validateRow(String, int)

	public boolean validateCol(String value, int colIn) {
		boolean valid = false;

		if (sudokuGame.validateCol(value, colIn) == true)
			valid = true;

		else if (sudokuGame.validateCol(value, colIn) == false) {
			System.err.println("Invalid move. There is already a " + value + " in that column. Please try again.");
			valid = false;
		}

		return valid;
	} // validateCol(String, int)

	public String[][] getBoard() {
		return sudokuGame.board;
	}

	public void displayBoard(String gameBoard) {
		System.out.print(gameBoard);
		System.out.print(" ");
	}

	public void updateBoard(String[][] theBoard) {
		for (int row = 0; row < theBoard.length; row++) {
			for (int col = 0; col < theBoard[row].length; col++) {
				System.out.print(theBoard[row][col]);
				System.out.print(" ");

			}
		} // displaying updated board
	} // updateBoard(String[][]) updates the board after each value is put.

	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		SudokuInterface sudokuInterface = new SudokuInterface();

		sudokuInterface.initialize();
		sudokuInterface.getBoard();
		sudokuInterface.inputPositionValues();

	} // main()

} // SudokuInteface
