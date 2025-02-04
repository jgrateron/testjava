package com.fresco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RotatingSphere extends JPanel implements ActionListener {
	private static final int WIDTH = 800;
	private static final int HEIGHT = 600;
	private static final int SPHERE_RADIUS = 250;
	private static final int POINT_COUNT = 500;
	private static final double ROTATION_SPEED = 0.01;
	private final Random random = new Random(); // Generador de números aleatorios
	private char[] LETTERS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9' };

	private List<Point3D> points = new ArrayList<>();

	private double angleY = 0; // Solo rotación en el eje Y

	public RotatingSphere() {
		// Generar puntos fijos en la superficie de la esfera
		for (int i = 0; i < POINT_COUNT; i++) {
			double theta = Math.random() * 2 * Math.PI;
			double phi = Math.acos(2 * Math.random() - 1);
			double x = SPHERE_RADIUS * Math.sin(phi) * Math.cos(theta);
			double y = SPHERE_RADIUS * Math.sin(phi) * Math.sin(theta);
			double z = SPHERE_RADIUS * Math.cos(phi);

			points.add(new Point3D(x, y, z, Character.toString(LETTERS[random.nextInt(LETTERS.length)])));
		}
		setDoubleBuffered(true);
		// Configurar el temporizador para la animación
		Timer timer = new Timer(16, this); // ~60 FPS
		timer.start();
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		// Establecer el fondo negro
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());

		// Configurar fuente y color para los números
		g2d.setFont(new Font("Monospaced", Font.BOLD, 14));
		List<Point2D> pointsBack = new ArrayList<>();
		List<Point2D> pointsFront = new ArrayList<>();
		// Proyectar y dibujar cada punto
		for (Point3D point : points) {
			// Rotar el punto alrededor del eje Y
			double rotatedX = point.x * Math.cos(angleY) - point.z * Math.sin(angleY);
			double rotatedZ = point.x * Math.sin(angleY) + point.z * Math.cos(angleY);
			double rotatedY = point.y; // No hay rotación en el eje X

			// Proyección en perspectiva
			double scale = 500 / (500 + rotatedZ);
			int projectedX = (int) (rotatedX * scale + WIDTH / 2);
			int projectedY = (int) (rotatedY * scale + HEIGHT / 2);
			if (rotatedZ >= 0) {
				pointsBack.add(new Point2D(projectedX, projectedY, point.t));
			} else {
				pointsFront.add(new Point2D(projectedX, projectedY, point.t));
			}
		}
		g2d.setColor(Color.GRAY);
		for (Point2D point : pointsBack) {
			g2d.drawString(point.t, point.x, point.y);
		}
		g2d.setColor(Color.WHITE);
		for (Point2D point : pointsFront) {
			g2d.drawString(point.t, point.x, point.y);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// Actualizar el ángulo de rotación en el eje Y
		angleY += ROTATION_SPEED;

		// Repintar el panel para reflejar los cambios
		repaint();
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Rotating Sphere Simulation Java");
		RotatingSphere sphere = new RotatingSphere();

		frame.add(sphere);
		frame.setSize(WIDTH, HEIGHT + 50);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setVisible(true);
	}

	private static class Point2D {
		int x, y;
		String t;

		public Point2D(int x, int y, String t) {
			this.x = x;
			this.y = y;
			this.t = t;
		}
	}

	// Clase auxiliar para representar un punto 3D con un número fijo
	private static class Point3D {
		double x, y, z;
		String t; // Número fijo asociado al punto

		public Point3D(double x, double y, double z, String t) {
			this.x = x;
			this.y = y;
			this.z = z;
			this.t = t;
		}
	}
}