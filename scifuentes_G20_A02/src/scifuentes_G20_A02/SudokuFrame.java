package scifuentes_G20_A02;

import javax.swing.JFrame;
import javax.swing.JMenuBar;
import javax.swing.JPanel;
import javax.swing.JMenu;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JButton;
import javax.swing.border.LineBorder;
import java.awt.Color;
import java.awt.FileDialog;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.border.BevelBorder;
import javax.swing.border.EtchedBorder;
import javax.swing.JTextPane;
import javax.swing.ImageIcon;
import java.awt.SystemColor;

/**
 * Title: SudokuFrame class
 * 
 * Programming II, Assignment 2 Course: 420-G20
 * 
 * Description: Frame code for SudokuGame
 * 
 * @author Santiago Cifuentes
 *
 */

public class SudokuFrame extends JFrame implements ActionListener {
	private static SudokuGame sudokuGame = new SudokuGame();

	private JPanel sudokuBoard = new JPanel();
	private JPanel keypad = new JPanel();
	private JMenuBar menuBar = new JMenuBar();
	private JMenu fileMenu = new JMenu("File");
	private JMenuItem openFileMenuItem = new JMenuItem("Open Sudoku file...");
	private JMenuItem useDefaultFileMenuItem = new JMenuItem("Use default file");
	private JMenuItem exitMenuItem = new JMenuItem("Exit");
	private JMenu menuEdit = new JMenu("Edit");
	private JMenuItem saveMenuItem = new JMenuItem("Save");
	private JMenuItem undoMenuItem = new JMenuItem("Undo");
	private JMenu helpMenu = new JMenu("Help");
	private JMenuItem instructionsMenuItem = new JMenuItem("Instructions");
	private JMenu aboutMenu = new JMenu("About");
	private JMenuItem programInfoMenuItem = new JMenuItem("Program info");
	private JButton btnUndo = new JButton("Undo");
	private JButton btnSave = new JButton("Save");
	private JButton btnQuit = new JButton("Quit");
	private boolean isSelected = false;
	private boolean isDefaultUsed = false;
	private int selectedRow;
	private int selectedCol;
	private JButton selectedNum;

	public String filename;

	private String[] keyLabel = { "7", "8", "9", "4", "5", "6", "1", "2", "3" };

	private JButton btnKey[] = new JButton[keyLabel.length];
	private JButton btnSquare[][] = new JButton[9][9];
	private final JTextPane gameInfoTxtPane = new JTextPane();
	private JTextPane filenameTxtPane = new JTextPane();

	public SudokuFrame() {
		getContentPane().setBackground(new Color(0, 0, 0));
		setBounds(100, 100, 1320, 932);
		setTitle("Heritage Sudoku");
		setJMenuBar(menuBar);
		keypad.setLayout(new GridLayout(3, 3));
		sudokuBoard.setBorder(null);
		sudokuBoard.setBackground(new Color(0, 0, 0));
		sudokuBoard.setLayout(new GridLayout(9, 9));

		menuBar.add(fileMenu);
		openFileMenuItem.setActionCommand("Open Sudoku file...");
		fileMenu.add(openFileMenuItem);
		fileMenu.add(useDefaultFileMenuItem);
		fileMenu.add(exitMenuItem);

		menuBar.add(menuEdit);
		menuEdit.add(saveMenuItem);
		menuEdit.add(undoMenuItem);
		menuBar.add(helpMenu);
		helpMenu.add(instructionsMenuItem);
		menuBar.add(aboutMenu);

		aboutMenu.add(programInfoMenuItem);
		getContentPane().setLayout(null);

		sudokuBoard.setBounds(15, 36, 731, 789);
		getContentPane().add(sudokuBoard);

		keypad.setBorder(new LineBorder(new Color(0, 0, 0), 2));
		keypad.setBounds(778, 365, 505, 460);
		getContentPane().add(keypad);
		btnUndo.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnUndo.setToolTipText("Undo last move");
		btnUndo.setBounds(778, 320, 145, 29);
		btnUndo.setBackground(new Color(153, 153, 51));
		btnUndo.setForeground(Color.WHITE);
		getContentPane().add(btnUndo);
		btnSave.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnSave.setForeground(Color.WHITE);
		btnSave.setBackground(new Color(153, 153, 51));
		btnSave.setToolTipText("Save current game to selected file");
		btnSave.setBounds(960, 320, 145, 29);
		getContentPane().add(btnSave);
		btnQuit.setFont(new Font("Segoe UI Symbol", Font.BOLD, 18));
		btnQuit.setForeground(Color.WHITE);
		btnQuit.setBackground(new Color(153, 153, 51));
		btnQuit.setToolTipText("Quit game");
		btnQuit.setBounds(1138, 320, 145, 29);
		getContentPane().add(btnQuit);
		gameInfoTxtPane.setForeground(new Color(255, 255, 255));
		gameInfoTxtPane.setBackground(new Color(0, 0, 0));
		gameInfoTxtPane.setEditable(false);
		gameInfoTxtPane.setBounds(778, 36, 505, 130);
		gameInfoTxtPane.setFont(new Font("Segoe UI Symbol", Font.BOLD, 50));

		getContentPane().add(gameInfoTxtPane);
		filenameTxtPane.setForeground(Color.WHITE);

		filenameTxtPane.setFont(new Font("Segoe UI Symbol", Font.BOLD, 14));
		filenameTxtPane.setBackground(SystemColor.desktop);
		filenameTxtPane.setEditable(false);
		filenameTxtPane.setBounds(15, 0, 380, 29);
		getContentPane().add(filenameTxtPane);

		openFileMenuItem.addActionListener(this);
		useDefaultFileMenuItem.addActionListener(this);
		exitMenuItem.addActionListener(this);
		saveMenuItem.addActionListener(this);
		undoMenuItem.addActionListener(this);
		instructionsMenuItem.addActionListener(this);
		programInfoMenuItem.addActionListener(this);

		btnUndo.addActionListener(this);
		btnSave.addActionListener(this);
		btnQuit.addActionListener(this);

		// TODO Auto-generated constructor stub
	} // sudokuFrame()


	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String[][] theBoard = getBoard();

		if (e.getSource() == openFileMenuItem) {
			if (isSelected == true) // if there is already a file being used and the user tries to open the file
									// browser
			{
				JOptionPane.showMessageDialog(this, "File " + filename + " already in use", "File in use",
						JOptionPane.WARNING_MESSAGE);
				int confirm = JOptionPane.showConfirmDialog(this,
						"Would you like to open another file? Current file's unsaved progress will be lost.",
						"Open another file?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (confirm == JOptionPane.YES_OPTION) {
					sudokuBoard.removeAll(); // if the user does select another file, remove all of the last file's buttons
					selectSudokuFile(); // lets the user select another file

					try {
						if (sudokuGame.loadGame(filename) == true) {
							isDefaultUsed = false;
							isSelected = true;
							JOptionPane.showMessageDialog(this, filename + " has been selected.");
							sudokuBoard.setBorder(new LineBorder(new Color(0, 0, 0), 5));
							displayBoard(theBoard);
							filenameTxtPane.setText("Filename: " + filename); // displays the current file being used above the board

						}

						else if (sudokuGame.loadGame(filename) == false)
							JOptionPane.showMessageDialog(this,
									"The file " + filename + " is incompatible with this program.", "Incompatible file",
									JOptionPane.ERROR_MESSAGE);

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			else {

				selectSudokuFile();

				try {
					if (sudokuGame.loadGame(filename) == true) {
						isSelected = true;
						JOptionPane.showMessageDialog(this, filename + " has been selected.");
						sudokuBoard.setBorder(new LineBorder(new Color(0, 0, 0), 5));
						displayBoard(theBoard);
						filenameTxtPane.setText("Filename: " + filename);
					}

					else if (sudokuGame.loadGame(filename) == false)
						JOptionPane.showMessageDialog(this,
								"The file " + filename + " is incompatible with this program.", "Incompatible file",
								JOptionPane.ERROR_MESSAGE);

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}

		} // Select Sudoku file

		if (e.getSource() == useDefaultFileMenuItem) {
			if (isDefaultUsed == true) // if the user tries to use the default file while using the default file
			{
				JOptionPane.showMessageDialog(this, "Default filename " + filename + " is already being used. ",
						"Default file already in use", JOptionPane.ERROR_MESSAGE);
			}

			else if (isSelected == true && isDefaultUsed == false) // if the user selects to use the default file while
																	// using another file
			{
				int confirm = JOptionPane.showConfirmDialog(this,
						"Would you like to use the default file? Current file's unsaved progress will be lost.",
						"Open default file?", JOptionPane.YES_NO_OPTION, JOptionPane.WARNING_MESSAGE);
				if (confirm == JOptionPane.YES_OPTION) {
					sudokuBoard.removeAll();
					filename = "sudoku.txt"; // sets filename to the default sudoku.txt name

					try {
						if (sudokuGame.loadGame(filename) == true) {
							isDefaultUsed = true;
							isSelected = true;
							JOptionPane.showMessageDialog(this, "Default filename " + filename + " has been selected",
									"Default file", JOptionPane.INFORMATION_MESSAGE);
							sudokuBoard.setBorder(new LineBorder(new Color(0, 0, 0), 5));
							displayBoard(theBoard);
							filenameTxtPane.setText("Filename: " + filename);
						}

						else if (sudokuGame.loadGame(filename) == false) {
							JOptionPane.showMessageDialog(this,
									"The file " + filename + " is incompatible with this program.", "Incompatible file",
									JOptionPane.ERROR_MESSAGE);
						}

					} catch (FileNotFoundException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			}

			else {

				filename = "sudoku.txt";

				try {
					if (sudokuGame.loadGame(filename) == true) {
						isDefaultUsed = true;
						isSelected = true;
						JOptionPane.showMessageDialog(this, "Default filename " + filename + " has been selected",
								"Default file", JOptionPane.INFORMATION_MESSAGE);
						sudokuBoard.setBorder(new LineBorder(new Color(0, 0, 0), 5));
						displayBoard(theBoard);
						filenameTxtPane.setText("Filename: " + filename);
					}

					else if (sudokuGame.loadGame(filename) == false) {
						JOptionPane.showMessageDialog(this,
								"The file " + filename + " is incompatible with this program.", "Incompatible file",
								JOptionPane.ERROR_MESSAGE);
					}

				} catch (FileNotFoundException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		} // Use default "sudoku.txt" file

		if (e.getSource() == btnUndo || e.getSource() == undoMenuItem) {
			if (filename == null)
				JOptionPane.showMessageDialog(this, "No file has been loaded. Please load a file and input a value to undo.",
						"No File Selected", JOptionPane.WARNING_MESSAGE);
			else {
				sudokuGame.undoMove();
				if (theBoard[selectedRow][selectedCol] == "*") 
					btnSquare[selectedRow][selectedCol].setText("*"); // if player undoes last move, set last button text to "*"
				else
					JOptionPane.showMessageDialog(this, "You can not undo an already present value",
							"Value can not be undone", JOptionPane.WARNING_MESSAGE);
			}

		} // Undo move

		if (e.getSource() == btnSave || e.getSource() == saveMenuItem) {
			if (filename == null)
				JOptionPane.showMessageDialog(this, "No file has been loaded. Please load a file to save game.",
						"No File Selected", JOptionPane.WARNING_MESSAGE);
			else {
				sudokuGame.saveGame(filename);
				JOptionPane.showMessageDialog(this, "Game has been saved!", "Save", JOptionPane.INFORMATION_MESSAGE);

			}

		} // Save game

		if (e.getSource() == btnQuit || e.getSource() == exitMenuItem) {
			int confirm = JOptionPane.showConfirmDialog(this,
					"Are you sure you want to quit? All unsaved progress will be lost.", "Quit game?",
					JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);
			if (confirm == JOptionPane.YES_OPTION) {
				JOptionPane.showMessageDialog(this, "Thank you for playing Heritage Sudoku!", "See you soon!",
						JOptionPane.INFORMATION_MESSAGE);
				sudokuGame.quitGame();
			}

		} // Quit game

		if (e.getSource() == programInfoMenuItem) {
			JOptionPane.showMessageDialog(this, new SudokuFrame_AboutPanel(), "About", JOptionPane.PLAIN_MESSAGE);

		} // Open about info

		if (e.getSource() == instructionsMenuItem) {
			JOptionPane.showMessageDialog(this, new SudokuFrame_InstructionsPanel(), "How To Play",
					JOptionPane.PLAIN_MESSAGE);
		} // Open instructions/rules

		for (int row = 0; row < theBoard.length; row++) {
			for (int col = 0; col < theBoard[row].length; col++) {
				if (e.getSource() == btnSquare[row][col]) {
					selectedRow = row;
					selectedCol = col;

					gameInfoTxtPane
							.setText("Square Position\nRow: " + (selectedRow + 1) + " Column: " + (selectedCol + 1)); // Displays the row and col value of the board button 
				}

			}
		} // Board buttons

		for (int i = 0; i < keyLabel.length; i++) {
			if (e.getSource() == btnKey[i]) {
				if (filename == null)
					JOptionPane.showMessageDialog(this, "No file has been loaded. Please load a file to input a value.", // if the user clicks on a keypad button before loading a file
							"No File Selected", JOptionPane.WARNING_MESSAGE);
				else {
					selectedNum = btnKey[i];

					if (updateSquare(selectedRow, selectedCol, selectedNum) == true) {
						if (btnSquare[selectedRow][selectedCol].getText().replaceAll("\\s+", "").equals("*")) { // the .replaceAll serves as a fix of the same bug as the one encountered in CLI
							btnSquare[selectedRow][selectedCol].setText(selectedNum.getText());
							theBoard[selectedRow][selectedCol] = selectedNum.getText(); // if updateSquare returns true, set the value to both the board button and the board array
							sudokuGame.lastRow(selectedRow);
							sudokuGame.lastCol(selectedCol); // store the last row/col used for the undo

							if (sudokuGame.checkEmpty() == true) {
								JOptionPane.showMessageDialog(this, "All of the empty btnSquares have been filled!",
										"Sudoku completed!", JOptionPane.INFORMATION_MESSAGE);

								if (sudokuGame.checkRow() == true && sudokuGame.checkColumn() == true)
									JOptionPane.showMessageDialog(this, "You have won! Thank you for playing!",
											"Sudoku Won!", JOptionPane.INFORMATION_MESSAGE);

								else
									JOptionPane.showMessageDialog(this, "You have lost! Better luck next time!",
											"Sudoku Lost!", JOptionPane.INFORMATION_MESSAGE);
							}

						}

						else
							JOptionPane.showMessageDialog(this,
									"You can not change the value of the squares that already have a value", // user can not click on a board square and then try to change its value
									"Illegal Move", JOptionPane.WARNING_MESSAGE);

					}
				}

			} // Keypad buttons
		}

	} // actionPerformed(ActionEvent)
	

	public void keypadButtons() {
		for (int i = 0; i < keyLabel.length; i++) // creates the keypad
		{
			btnKey[i] = new JButton(keyLabel[i]);
			keypad.add(btnKey[i]);
			btnKey[i].setFont(new Font("Arial", Font.PLAIN, 45));
			btnKey[i].setBackground(new Color(32, 32, 32));
			btnKey[i].setForeground(new Color(255, 255, 102));
			btnKey[i].addActionListener(this);
		}
	} // keypadButtons()

	private void selectSudokuFile() {
		FileDialog fileDialog = new FileDialog(this, "Select Sudoku File", FileDialog.LOAD);
		fileDialog.setVisible(true);

		String directoryName = fileDialog.getDirectory();
		filename = fileDialog.getFile();
		System.out.println(filename);
		System.out.println(directoryName);
		isSelected = true;

		if (filename == null) {
			JOptionPane.showMessageDialog(this, "No file has been selected.", "No File Selected",
					JOptionPane.WARNING_MESSAGE);
			int confirm = JOptionPane.showConfirmDialog(this, "Would you like to use the default Sudoku file?", // if the user clicks on cancel in the file browse menu, then prompt if they want to use the default file
					"Use Default File?", JOptionPane.YES_NO_OPTION, JOptionPane.INFORMATION_MESSAGE);

			if (confirm == JOptionPane.YES_OPTION) {
				filename = "sudoku.txt";
				isSelected = true;
			}

		}
	} // selectSudokuFile()

	public String[][] getBoard() {
		return sudokuGame.board;
	} // getBoard()

	public void displayBoard(String[][] theBoard) {

		for (int row = 0; row < theBoard.length; row++) {
			for (int col = 0; col < theBoard[row].length; col++) {
				btnSquare[row][col] = new JButton(theBoard[row][col]);
				sudokuBoard.add(btnSquare[row][col]);
				btnSquare[row][col].setFont(new Font("Arial", Font.PLAIN, 35));
				btnSquare[row][col].setBackground(new Color(32, 32, 32));
				btnSquare[row][col].setForeground(new Color(255, 255, 102));
				btnSquare[row][col].addActionListener(this);

				if (!(btnSquare[row][col].getText().replaceAll("\\s+", "").equals("*"))) { // for the board buttons that already have a value, make them light blue to differentiate them from the other buttons
					btnSquare[row][col].setForeground(new Color(51, 255, 255));
				}

			} // inner loop
		} // outer loop
	} // displayBoard(String[][])

	public boolean updateSquare(int selectedRow, int selectedCol, JButton selectedNum) {
		boolean valid = false;

		if (sudokuGame.validateRow(selectedNum.getText(), selectedRow) == true
				&& sudokuGame.validateCol(selectedNum.getText(), selectedCol) == true) {
			valid = true;
		}

		else if (sudokuGame.validateRow(selectedNum.getText(), selectedRow) == false) {
			JOptionPane.showMessageDialog(this,
					"The row already contains a " + selectedNum.getText() + ". " + "Please try again.",
					"Repeated number in row.", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}

		else if (sudokuGame.validateCol(selectedNum.getText(), selectedCol) == false) {
			JOptionPane.showMessageDialog(this,
					"The column already contains a " + selectedNum.getText() + ". " + "Please try again.",
					"Repeated number in column.", JOptionPane.ERROR_MESSAGE);
			valid = false;
		}

		return valid;
	} // updateSquare(int, int, JButton)

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		SudokuFrame frame = new SudokuFrame();
		frame.setVisible(true);
		frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
		frame.keypadButtons();

	} // main()
}
