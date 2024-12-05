package com.fresco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class MatrixEffect extends JPanel implements ActionListener, Runnable {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int FONT_SIZE = 12;
	private static final char[] CHARACTERS = " 01ABCDEFGHIJKLMNOPQRSTUVWXYZ$#@!%^&*()+/=".toCharArray();
	private Random random = new Random();
	private int[][] matrix;
	private Timer timer;

	public MatrixEffect() {
		matrix = new int[WIDTH / FONT_SIZE][HEIGHT / FONT_SIZE];
		timer = new Timer(100, this); // Aumenta la velocidad de refresco (más frames por segundo)
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		timer.start();
		new Thread(this).start();
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2d.setColor(Color.GREEN);
		Font font = new Font("Monospaced", Font.BOLD, FONT_SIZE);
		g2d.setFont(font);

		for (int i = 0; i < matrix.length; i++) {
			for (int j = 0; j < matrix[i].length; j++) {
				if (matrix[i][j] > 0) {
					char c = CHARACTERS[random.nextInt(CHARACTERS.length)];
					int x = i * FONT_SIZE;
					int y = j * FONT_SIZE + matrix[i][j] * FONT_SIZE / 2; // Reduce la velocidad de caída
					g2d.drawString(String.valueOf(c), x, y);
				}
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		repaint();
	}

	@Override
	public void run() {
		while (true) {
			try {
				int col = random.nextInt(matrix.length);
				for (int row = 0; row < matrix[col].length; row++) {
					matrix[col][row] = 1;
				}
				Thread.sleep(100 + random.nextInt(100)); // Disminuye el tiempo entre apariciones de columnas
			} catch (InterruptedException ex) {
				Thread.currentThread().interrupt();
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Matrix Effect");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new MatrixEffect());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}