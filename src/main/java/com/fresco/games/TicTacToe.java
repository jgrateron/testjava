package com.fresco.games;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

public class TicTacToe extends JFrame implements ActionListener, KeyListener {
	private static final long serialVersionUID = 1L;
	private JButton[] buttons = new JButton[9];
	private JLabel statusLabel;
	private boolean xTurn = true;
	private boolean gameWon = false;

	public TicTacToe() {
		setTitle("Tic-Tac-Toe");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new BorderLayout());
		setMinimumSize(new Dimension(300, 300));

		JPanel boardPanel = new JPanel(new GridLayout(3, 3));
		for (int i = 0; i < 9; i++) {
			buttons[i] = new JButton("");
			buttons[i].setFont(new Font("SansSerif", Font.BOLD, 40));
			buttons[i].addActionListener(this);
			buttons[i].addKeyListener(this);
			boardPanel.add(buttons[i]);
		}

		statusLabel = new JLabel("X's turn");
		statusLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
		statusLabel.setHorizontalAlignment(JLabel.CENTER);

		add(boardPanel, BorderLayout.CENTER);
		add(statusLabel, BorderLayout.SOUTH);

		pack();
		setLocationRelativeTo(null);
		setVisible(true);
		addKeyListener(this);
		setFocusable(true);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (gameWon)
			return; // Evita clicks después de ganar

		JButton button = (JButton) e.getSource();

		if (button.getText().equals("")) {
			button.setText(xTurn ? "X" : "O");
			xTurn = !xTurn;
			statusLabel.setText(xTurn ? "X's turn" : "O's turn");

			if (checkWin()) {
				statusLabel.setText((xTurn ? "O" : "X") + " wins!");
				gameWon = true;
			} else if (isBoardFull()) {
				statusLabel.setText("It's a draw!");
				gameWon = true;
			}
		}
	}

	private boolean checkWin() {
		int[][] winningCombinations = {
				{ 0, 1, 2 }, { 3, 4, 5 }, { 6, 7, 8 }, // Rows
				{ 0, 3, 6 }, { 1, 4, 7 }, { 2, 5, 8 }, // Columns
				{ 0, 4, 8 }, { 2, 4, 6 } // Diagonals
		};

		for (int[] combination : winningCombinations) {
			String b1 = buttons[combination[0]].getText();
			String b2 = buttons[combination[1]].getText();
			String b3 = buttons[combination[2]].getText();

			if (!b1.equals("") && b1.equals(b2) && b2.equals(b3)) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void keyPressed(KeyEvent e) {
		if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
			int option = JOptionPane.showConfirmDialog(this, "Do you want to start a new game?", "New Game",
					JOptionPane.YES_NO_OPTION);
			if (option == JOptionPane.YES_OPTION) {
				resetGame();
			} else {
				this.dispose();
			}
		}
	}

	@Override
	public void keyTyped(KeyEvent e) {
	}

	@Override
	public void keyReleased(KeyEvent e) {
	}

	private void resetGame() {
		for (JButton button : buttons) {
			button.setText("");
		}
		xTurn = true;
		gameWon = false;
		statusLabel.setText("X's turn");
	}

	private boolean isBoardFull() {
		for (JButton button : buttons) {
			if (button.getText().equals("")) {
				return false;
			}
		}
		return true;
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new TicTacToe());
	}
}
