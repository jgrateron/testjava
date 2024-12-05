package com.fresco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.time.Duration;
import java.util.Random;

public class MatrixEffect2 extends JPanel implements ActionListener, Runnable {

	private static final long serialVersionUID = 1L;
	private static final int WIDTH = 1200;
	private static final int HEIGHT = 800;
	private static final int FONT_SIZE = 18;

	private Random random = new Random();
	private Column[] matrix;
	private Timer timer;
	private static boolean running = true;

	public MatrixEffect2() {
		matrix = new Column[WIDTH / FONT_SIZE];
		for (int i = 0; i < matrix.length; i++) {
			matrix[i] = new Column(HEIGHT / FONT_SIZE);
		}
		timer = new Timer(50, this);
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
			var array = matrix[i].getArray();
			for (int j = 0; j < array.length; j++) {
				char c = array[j];
				int x = i * FONT_SIZE;
				int y = j * FONT_SIZE;
				g2d.drawString(String.valueOf(c), x, y);
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
				Thread.sleep(Duration.ofMillis(50));
				if (running) {
					int z = random.nextInt(3);
					int r = random.nextInt(1, 5);
					for (int i = z; i < matrix.length; i = i + r) {
						matrix[i].scrollDown();
					}
				}
			} catch (InterruptedException e) {
			}
		}
	}

	class Column {
		private static final char[] CHARACTERS = {
				' ', '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
				'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e',
				'f', 'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w', 'x', 'y', 'z',
				'~', '!', '@', '#', '$', '%', '^', '&', '*', '(', ')', '-', '=', '[', ']', '{', '}', '/', '\\', '|'
		};
		private char[] array;
		private static Random random = new Random();

		public Column(int height) {
			array = new char[height];
			initialize();
		}

		public void initialize() {
			for (int i = 0; i < array.length; i++) {
				array[i] = CHARACTERS[0];
			}
			int ini = 0;
			int fin = array.length;
			for (;;) {
				ini = random.nextInt(array.length);
				fin = random.nextInt(array.length);
				if (fin - ini > 10) {
					break;
				}
			}
			for (int j = ini; j < fin; j++) {
				array[j] = CHARACTERS[random.nextInt(CHARACTERS.length)];
			}
		}

		public char[] getArray() {
			return array;
		}

		public void scrollDown() {
			char last = array[array.length - 1];
			for (int j = array.length - 1; j > 0; j--) {
				array[j] = array[j - 1];
			}
			array[0] = last;
			int w = random.nextInt(0, 2);
			if (w == 1) {
				int k = random.nextInt(array.length);
				if (array[k] != CHARACTERS[0]) {
					array[k] = CHARACTERS[random.nextInt(CHARACTERS.length)];
				}
			}
		}
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Matrix Effect");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.addKeyListener(new KeyAdapter() {
			@Override
			public void keyPressed(KeyEvent e) {
				running = !running;
			}
		});
		frame.add(new MatrixEffect2());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}