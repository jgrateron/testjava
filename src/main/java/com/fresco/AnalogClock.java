package com.fresco;

import javax.swing.*;
import java.awt.*;
import java.util.Calendar;

public class AnalogClock extends JPanel {

	private static final long serialVersionUID = 1L;
	private int hour;
	private int minute;
	private int second;
	private Timer timer;

	public AnalogClock() {
		// Inicializar el temporizador para actualizar cada segundo
		timer = new Timer(250, (e) -> {
			repaint();
		});
		timer.start();
		this.setPreferredSize(new Dimension(600, 600)); // Tamaño del reloj
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) g;
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// Obtener la hora actual
		Calendar calendar = Calendar.getInstance();
		hour = calendar.get(Calendar.HOUR);
		minute = calendar.get(Calendar.MINUTE);
		second = calendar.get(Calendar.SECOND);

		int centerX = getWidth() / 2;
		int centerY = getHeight() / 2;
		int radius = Math.min(centerX, centerY) - 10;

		// Dibujar el círculo del reloj
		g2d.setColor(Color.BLACK);
		g2d.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);

		// Dibujar los numeros (opcional)
		for (int i = 1; i <= 12; i++) {
			double angle = Math.toRadians(i * 30 - 90);
			int x = centerX + (int) (radius * 0.9 * Math.cos(angle));
			int y = centerY + (int) (radius * 0.9 * Math.sin(angle));
			g2d.drawString(String.valueOf(i), x - 5, y + 5);
		}
		// Dibujar las manecillas
		g2d.setColor(Color.BLACK);
		drawHand(g2d, centerX, centerY, radius * 0.6, (hour % 12 + minute / 60.0) * 30, 3, -30); // Hora
		g2d.setColor(Color.BLUE);
		drawHand(g2d, centerX, centerY, radius * 0.8, minute * 6, 3, -40); // Minuto
		g2d.setColor(Color.RED);
		drawHand(g2d, centerX, centerY, radius * 0.9, second * 6, 1, -50); // Segundo

		g2d.setColor(Color.GRAY);
		for (int i = 0; i < 60; i++) {
			int len = (i % 5 == 0) ? 10 : 5; // Líneas más largas cada 5 segundos
			double angle = Math.toRadians(i * 6 - 90);
			int x1 = centerX + (int) ((radius - 5) * Math.cos(angle)); // Ajustar el radio para el comienzo de la marca
			int y1 = centerY + (int) ((radius - 5) * Math.sin(angle));
			int x2 = centerX + (int) ((radius - 5 - len) * Math.cos(angle));
			int y2 = centerY + (int) ((radius - 5 - len) * Math.sin(angle));
			g2d.drawLine(x1, y1, x2, y2);
		}
	}

	private void drawHand(Graphics2D g2d, int centerX, int centerY, double length, double angle, int strokeWidth,
			int offset) { // Añadido
		// strokeWidth
		angle = Math.toRadians(angle - 90);
		int x = centerX + (int) (length * Math.cos(angle));
		int y = centerY + (int) (length * Math.sin(angle));

		// Ajustar el punto de inicio de la manecilla
		int startX = centerX + (int) (offset * Math.cos(angle));
		int startY = centerY + (int) (offset * Math.sin(angle));

		// Establecer el grosor de la línea
		g2d.setStroke(new BasicStroke(strokeWidth));

		g2d.drawLine(startX, startY, x, y); // Dibujar desde startX, startY

		// Dibujar dos líneas pequeñas a 45 grados apuntando hacia afuera
		int smallLineLength = 10;
		double angle1 = angle + Math.toRadians(45);
		double angle2 = angle - Math.toRadians(45);

		int x1 = x - (int) (smallLineLength * Math.cos(angle1));
		int y1 = y - (int) (smallLineLength * Math.sin(angle1));
		int x2 = x - (int) (smallLineLength * Math.cos(angle2));
		int y2 = y - (int) (smallLineLength * Math.sin(angle2));

		g2d.drawLine(x, y, x1, y1);
		g2d.drawLine(x, y, x2, y2);
	}

	public static void main(String[] args) {
		JFrame frame = new JFrame("Reloj Analógico");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(new AnalogClock());
		frame.pack();
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
	}
}