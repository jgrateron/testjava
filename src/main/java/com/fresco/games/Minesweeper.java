package com.fresco.games;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.Stack;

public class Minesweeper extends JFrame {

	private static final long serialVersionUID = 1L;
	private final int rows = 10;
	private final int cols = 10;
	private final int minas = (rows * cols) / 13;
	private JButton[][] botones;
	private int[][] tablero; // 0: empty, -1: mine, >0: number of adjacent mines
	private boolean debug = true;

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new Minesweeper());
	}

	public Minesweeper() {
		setTitle("Minesweeper %dx%d".formatted(rows, cols));
		setMinimumSize(new Dimension(rows * 45, cols * 45));
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new GridLayout(rows, cols));

		botones = new JButton[rows][cols];
		tablero = generarTablero();

		Action escapeAction = new AbstractAction() {
			private static final long serialVersionUID = 1L;

			@Override
			public void actionPerformed(ActionEvent e) {
				resetGame();
			}
		};

		getRootPane().getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(
				KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "Escape");
		getRootPane().getActionMap().put("Escape", escapeAction);
		crearComponentes();

		setLocationRelativeTo(null);
		setVisible(true);
	}

	private void crearComponentes() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				JButton boton = new JButton("");
				boton.setFont(new Font("SansSerif", Font.BOLD, 14));
				boton.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {
						int fila = getFila(e.getSource());
						int columna = getColumna(e.getSource());
						revelarCasilla(fila, columna);
					}
				});
				boton.addMouseListener(new MouseAdapter() {
					@Override
					public void mouseClicked(MouseEvent e) {
						if (SwingUtilities.isRightMouseButton(e)) {
							int fila = getFila(e.getSource());
							int columna = getColumna(e.getSource());
							if (botones[fila][columna].isEnabled()) {
								if (botones[fila][columna].getText().equals("?")) {
									botones[fila][columna].setText("");
								} else {
									botones[fila][columna].setText("?");
								}
							}
						}
					}
				});
				botones[i][j] = boton;
				add(boton);
			}
		}
	}

	private int[][] generarTablero() {
		int[][] tablero = new int[rows][cols];
		Random random = new Random();
		int minasColocadas = 0;
		while (minasColocadas < minas) {
			int fila = random.nextInt(rows);
			int columna = random.nextInt(cols);
			if (tablero[fila][columna] == 0) {
				tablero[fila][columna] = -1;
				minasColocadas++;
			}
		}
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (tablero[i][j] != -1) {
					tablero[i][j] = contarMinasAdyacentes(i, j, tablero);
				}
			}
		}
		if (debug) {
			mostrarTableroText(tablero);
		}
		return tablero;
	}

	public void mostrarTableroText(int[][] tablero) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				System.out.print("%2d".formatted(tablero[i][j]));
			}
			System.out.println();
		}
	}

	private int contarMinasAdyacentes(int fila, int columna, int[][] tablero) {
		int contador = 0;
		for (int i = Math.max(0, fila - 1); i <= Math.min(rows - 1, fila + 1); i++) {
			for (int j = Math.max(0, columna - 1); j <= Math.min(cols - 1, columna + 1); j++) {
				if (tablero[i][j] == -1) {
					contador++;
				}
			}
		}
		return contador;
	}

	private void revelarCasilla(int fila, int columna) {
		if (tablero[fila][columna] == -1) {
			botones[fila][columna].setText("X");
			revelarTodoElTablero("Lost!");
			return;
		}
		if (tablero[fila][columna] == 0) {
			revelarVacias(fila, columna);
		} else {
			botones[fila][columna].setText(String.valueOf(tablero[fila][columna]));
		}
		if (juegoTerminado()) {
			revelarTodoElTablero("Win!");
		}
	}

	private boolean juegoTerminado() {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (botones[i][j].isEnabled()) {
					if (botones[i][j].getText().equals("")) {
						return false;
					}
					if (botones[i][j].getText().equals("?") && tablero[i][j] != -1) {
						return false;
					}
				}
			}
		}
		return true;
	}

	private void revelarTodoElTablero(String message) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				botones[i][j].setEnabled(false);
				if (tablero[i][j] == -1) {
					botones[i][j].setText("X");
				} else if (tablero[i][j] == 0) {
					botones[i][j].setText("");
				} else {
					botones[i][j].setText(String.valueOf(tablero[i][j]));
				}
			}
		}
		setTitle("Minesweeper %dx%d [%s]".formatted(rows, cols, message));
	}

	private void revelarVacias(int fila, int columna) {
		Stack<Coordenadas> pila = new Stack<>();
		Set<Coordenadas> visitados = new HashSet<>();
		pila.push(new Coordenadas(fila, columna));
		visitados.add(new Coordenadas(fila, columna));
		while (!pila.isEmpty()) {
			Coordenadas coordenadas = pila.pop();
			int f = coordenadas.fila;
			int c = coordenadas.columna;

			if (f < 0 || f >= rows || c < 0 || c >= cols || !botones[f][c].getText().equals("")
					|| tablero[f][c] != 0) {
				continue;
			}
			botones[f][c].setText("");
			botones[f][c].setEnabled(false);
			for (int i = Math.max(0, f - 1); i <= Math.min(rows - 1, f + 1); i++) {
				for (int j = Math.max(0, c - 1); j <= Math.min(cols - 1, c + 1); j++) {
					Coordenadas coordenada = new Coordenadas(i, j);
					if (!visitados.contains(coordenada)) {
						pila.push(coordenada);
						visitados.add(coordenada);
					}
				}
			}
		}
	}

	private int getFila(Object source) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (botones[i][j] == source) {
					return i;
				}
			}
		}
		return -1;
	}

	private int getColumna(Object source) {
		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < cols; j++) {
				if (botones[i][j] == source) {
					return j;
				}
			}
		}
		return -1;
	}

	private void resetGame() {
		int option = JOptionPane.showConfirmDialog(this, "Do you want to start a new game?", "New Game",
				JOptionPane.YES_NO_OPTION);
		if (option == JOptionPane.YES_OPTION) {
			tablero = generarTablero();
			for (int i = 0; i < rows; i++) {
				for (int j = 0; j < cols; j++) {
					botones[i][j].setText("");
					botones[i][j].setEnabled(true);
				}
			}
			setTitle("Minesweeper %dx%d".formatted(rows, cols));
		} else {
			this.dispose();
		}
	}

	class Coordenadas {
		int fila;
		int columna;

		public Coordenadas(int fila, int columna) {
			this.fila = fila;
			this.columna = columna;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null || getClass() != obj.getClass())
				return false;
			Coordenadas other = (Coordenadas) obj;
			return fila == other.fila && columna == other.columna;
		}

		@Override
		public int hashCode() {
			return Integer.hashCode(fila * 1000 + columna);
		}
	}
}