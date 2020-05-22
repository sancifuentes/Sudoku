package scifuentes_G20_A02;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EtchedBorder;
import java.awt.TextArea;
import javax.swing.border.LineBorder;
import java.awt.Color;
import javax.swing.JTextArea;
import java.awt.Font;
import javax.swing.JTextPane;

/**
 * Title: SudokuFrame_AboutPanel class
 * 
 * Programming II, Assignment 2 Course: 420-G20
 * 
 * Description: Code for the instructions panel in the frame
 * 
 * @author Santiago Cifuentes
 *
 */

public class SudokuFrame_InstructionsPanel extends JPanel{

	public SudokuFrame_InstructionsPanel() {
		
		JTextPane InstructionsTxt = new JTextPane();
		InstructionsTxt.setEditable(false);
		InstructionsTxt.setFont(new Font("Arial", Font.PLAIN, 19));
		InstructionsTxt.setText("Sudoku is a logic based, number placement puzzle. The board is a 9 x 9 grid with 81 squares. \r\n\r\nRules:\r\n\t1. You must place the numbers 1 - 9 in each row without repeating a number\r\n\t2. You must place the numbers 1 - 9 in each column without repating a number\r\n\t3. You must place the numbers 1 - 9 in each of the marked 3 x 3 boxes without repeating a number\r\n\r\nHow to play:\r\n\t1. Select a compatible Sudoku file. File > Open Sudoku file...\r\n\t   1.1 If no filename is known, then select the default Sudoku file. File > Use default file\r\n\t2. Click in an empty square (*)\r\n\t3. Select number from the keypad\r\n\t4. Play until all empty squares (*) are filled.\r\n\r\n\r\nUndo - Undoes the last move done\r\nSave - Overwrites the current file being used with the new values\r\nQuit - Exits the game");
		add(InstructionsTxt);
	}

}
