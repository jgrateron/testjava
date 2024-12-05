package com.fresco.games.tetris;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class TetrisBoard extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	public static final int BOARD_WIDTH = 12;
	public static final int BOARD_HEIGHT = 20;
	private static final int SQUARE_SIZE = 35;
	public static int[][] board = new int[BOARD_HEIGHT][BOARD_WIDTH];
	private Timer timer;
	private Tetromino currentPiece;
	private int score = 0;
	private JFrame frame;

	public TetrisBoard(JFrame frame) {
		this.frame = frame;
		setPreferredSize(new Dimension(BOARD_WIDTH * SQUARE_SIZE, BOARD_HEIGHT * SQUARE_SIZE));
		setFocusable(true);
		timer = new Timer(500, this); // Velocidad de caída
		timer.start();
		newPiece();
		addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				switch (e.getKeyCode()) {
				case KeyEvent.VK_LEFT:
					currentPiece.move(-1, 0);
					break;
				case KeyEvent.VK_RIGHT:
					currentPiece.move(1, 0);
					break;
				case KeyEvent.VK_DOWN:
					currentPiece.move(0, 1);
					break;
				case KeyEvent.VK_UP:
					currentPiece.rotate();
					break;
				}
				repaint();
			}
		});
	}

	private void newPiece() {
		currentPiece = new Tetromino();
	}

	private void placePiece() {
		for (int row = 0; row < currentPiece.shape.length; row++) {
			for (int col = 0; col < currentPiece.shape[0].length; col++) {
				if (currentPiece.shape[row][col] != 0) {
					int boardX = currentPiece.x + col;
					int boardY = currentPiece.y + row;
					if (boardY >= 0) { // Solo si está dentro del tablero
						board[boardY][boardX] = 1;
					}
				}
			}
		}
		checkLines();
	}

	private void checkLines() {
		int completedLines = 0;
		for (int row = BOARD_HEIGHT - 1; row >= 0; row--) {
			boolean lineComplete = true;
			for (int col = 1; col < BOARD_WIDTH - 1; col++) {
				if (board[row][col] == 0) {
					lineComplete = false;
					break;
				}
			}
			if (lineComplete) {
				completedLines++;
				removeLine(row);
			}
		}
		if (completedLines > 0) {
			switch (completedLines) {
			case 1:
				score += 100;
				break;
			case 2:
				score += 300;
				break;
			case 3:
				score += 500;
				break;
			case 4:
				score += 800;
				break; // Tetris!
			default:
				break;
			}
			frame.setTitle("Tetris [%d]".formatted(score));
		}
	}

	private void removeLine(int row) {
		for (int i = row; i > 0; i--) {
			System.arraycopy(board[i - 1], 0, board[i], 0, BOARD_WIDTH - 2);
		}
		Arrays.fill(board[0], 1, BOARD_WIDTH - 1, 0);
		repaint(); // Redibuja el tablero para mostrar el cambio
	}

	public void stop() {
		timer.stop();
	}

	public void restart() {
		restartGame();
		timer.restart();
	}

	public void restartGame() {
		for (int row = 0; row < BOARD_HEIGHT; row++) {
			Arrays.fill(board[row], 0); // Llena con ceros, excluyendo las columnas fijas
		}
		score = 0;
		newPiece();
		frame.setTitle("Tetris [0]");
		repaint();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());

		g.setColor(Color.GRAY); // Color de las paredes
		g.fillRect(0, 0, SQUARE_SIZE, BOARD_HEIGHT * SQUARE_SIZE); // Columna izquierda
		g.fillRect((BOARD_WIDTH - 1) * SQUARE_SIZE, 0, SQUARE_SIZE, BOARD_HEIGHT * SQUARE_SIZE); // Columna derecha

		for (int row = 0; row < BOARD_HEIGHT; row++) {
			for (int col = 1; col < BOARD_WIDTH - 1; col++) {
				if (board[row][col] != 0) {
					drawSquare(g, col, row, Color.WHITE); // Color de las piezas colocadas
				}
			}
		}
		if (currentPiece != null) {
			drawPiece(g, currentPiece);
		}
	}

	private void drawSquare(Graphics g, int x, int y, Color color) {
		g.setColor(color);
		g.fillRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
		g.setColor(Color.DARK_GRAY);
		g.drawRect(x * SQUARE_SIZE, y * SQUARE_SIZE, SQUARE_SIZE, SQUARE_SIZE);
	}

	private void drawPiece(Graphics g, Tetromino piece) {
		for (int row = 0; row < piece.shape.length; row++) {
			for (int col = 0; col < piece.shape[0].length; col++) {
				if (piece.shape[row][col] != 0) {
					drawSquare(g, piece.x + col, piece.y + row, piece.color);
				}
			}
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (!currentPiece.move(0, 1)) {
			placePiece();
			newPiece();
		}
		repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Tetris [0]");
		var tetris = new TetrisBoard(frame);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		Action escapeAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				tetris.stop();
				int option = JOptionPane.showConfirmDialog(frame, "Do you want to start a new game?", "New Game",
						JOptionPane.YES_NO_OPTION);
				if (option == JOptionPane.YES_OPTION) {
					tetris.restart();
				} else {
					frame.dispose();
				}
			}
		};

		frame.getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
		frame.getRootPane().getActionMap().put("Escape", escapeAction);

		frame.add(tetris);
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}