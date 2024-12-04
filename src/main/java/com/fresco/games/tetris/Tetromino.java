package com.fresco.games.tetris;

import java.awt.*;
import java.util.Random;

public class Tetromino {
	public static final int TETROMINO_SIZE = 4;
	public int[][] shape;
	public Color color;
	public int x, y;

	public Tetromino() {
		Random random = new Random();
		int type = random.nextInt(8); // 7 tipos de tetrominos
		shape = TetrominoShapes.SHAPES[type];
		color = TetrominoShapes.COLORS[type];
		x = 5; // Posición inicial en el centro
		y = -2; // Empieza fuera de la pantalla
	}

	public boolean move(int dx, int dy) {
		int newX = x + dx;
		int newY = y + dy;
		if (isValidPosition(newX, newY)) {
			x = newX;
			y = newY;
			return true;
		}
		return false;
	}

	public void rotate() {
		int[][] rotatedShape = new int[shape[0].length][shape.length];
		for (int i = 0; i < shape.length; i++) {
			for (int j = 0; j < shape[0].length; j++) {
				rotatedShape[j][shape.length - 1 - i] = shape[i][j];
			}
		}
		if (isValidPosition(x, y, rotatedShape)) {
			shape = rotatedShape;
		}
	}

	private boolean isValidPosition(int newX, int newY) {
		return isValidPosition(newX, newY, shape);
	}

	private boolean isValidPosition(int newX, int newY, int[][] shape) {
		for (int row = 0; row < shape.length; row++) {
			for (int col = 0; col < shape[0].length; col++) {
				if (shape[row][col] != 0) {
					int boardX = newX + col;
					int boardY = newY + row;
					// Comprobar límites del tablero, incluyendo las columnas fijas
					if (boardX < 1 || boardX >= TetrisBoard.BOARD_WIDTH - 1 || boardY >= TetrisBoard.BOARD_HEIGHT
							|| boardY >= 0 && TetrisBoard.board[boardY][boardX] != 0) {
						return false;
					}
				}
			}
		}
		return true;
	}
	// ... (Métodos para rotar, mover, etc., ver más abajo)
}

// Tipos de Tetrominos y sus colores
class TetrominoShapes {
	public static final int[][][] SHAPES = {
			{ { 1, 1, 1, 1 } }, // I
			{ { 1, 1, 1 }, { 0, 1, 0 } }, // T
			{ { 1, 1, 0 }, { 0, 1, 1 } }, // Z
			{ { 0, 1, 1 }, { 1, 1, 0 } }, // S
			{ { 1, 1 }, { 1, 1 } }, // O
			{ { 1, 1 } },
			{ { 1, 1, 1 }, { 1, 0, 0 } }, // L
			{ { 1, 1, 1 }, { 0, 0, 1 } } // J
	};
	public static final Color[] COLORS = {
			Color.CYAN, Color.MAGENTA, Color.RED, Color.GREEN, Color.YELLOW, Color.ORANGE, Color.BLUE, Color.PINK,
			Color.LIGHT_GRAY
	};
}