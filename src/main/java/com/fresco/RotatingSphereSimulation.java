package com.fresco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RotatingSphereSimulation extends JPanel implements ActionListener {

    private static final int WIDTH = 800; // Ancho de la ventana
    private static final int HEIGHT = 600; // Alto de la ventana
    private static final int SPHERE_RADIUS = 200; // Radio de la esfera
    private static final int POINT_COUNT = 500; // Número de puntos en la esfera
    private static final double ROTATION_SPEED = 0.01; // Velocidad de rotación (más lenta)

    private final List<Point3D> points = new ArrayList<>(); // Lista de puntos 3D
    private final Random random = new Random(); // Generador de números aleatorios
    private double angleX = 0; // Ángulo de rotación en el eje X
    private double angleY = 0; // Ángulo de rotación en el eje Y

    public RotatingSphereSimulation() {
        // Generar puntos aleatorios en la superficie de la esfera
        for (int i = 0; i < POINT_COUNT; i++) {
            double theta = Math.random() * 2 * Math.PI; // Ángulo aleatorio en [0, 2π]
            double phi = Math.acos(2 * Math.random() - 1); // Ángulo aleatorio en [0, π]
            double x = SPHERE_RADIUS * Math.sin(phi) * Math.cos(theta);
            double y = SPHERE_RADIUS * Math.sin(phi) * Math.sin(theta);
            double z = SPHERE_RADIUS * Math.cos(phi);
            points.add(new Point3D(x, y, z, random.nextInt(10))); // Asignar un número aleatorio (0-9)
        }

        // Configurar un temporizador para la animación
        Timer timer = new Timer(16, this); // ~60 FPS
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Limpiar el fondo
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        g2d.setColor(Color.WHITE);
        // Proyectar y dibujar cada punto
        for (Point3D point : points) {
            // Rotar el punto
            Point3D rotated = rotatePoint(point, angleX, angleY);

            // Proyectar a 2D
            int x = (int) (rotated.x * 200 / (rotated.z + 300) + WIDTH / 2);
            int y = (int) (rotated.y * 200 / (rotated.z + 300) + HEIGHT / 2);

            // Determinar el color basado en la coordenada Z
            //int brightness = (int) (255 * (1 - Math.min(1, (rotated.z + 200) / 400.0)));
            //g2d.setColor(new Color(brightness, brightness, brightness));

            // Dibujar el número ASCII correspondiente al punto
            g2d.drawString(String.valueOf(point.number), x, y);
        }
    }

    private Point3D rotatePoint(Point3D point, double angleX, double angleY) {
        // Rotar alrededor del eje X
        double y = point.y * Math.cos(angleX) - point.z * Math.sin(angleX);
        double z = point.y * Math.sin(angleX) + point.z * Math.cos(angleX);

        // Rotar alrededor del eje Y
        double x = point.x * Math.cos(angleY) + z * Math.sin(angleY);
        z = -point.x * Math.sin(angleY) + z * Math.cos(angleY);

        return new Point3D(x, y, z, point.number);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // Actualizar los ángulos de rotación
        angleX += ROTATION_SPEED;
        angleY += ROTATION_SPEED;

        // Redibujar el panel
        repaint();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Rotating Sphere Simulation");
        RotatingSphereSimulation panel = new RotatingSphereSimulation();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(panel);
        frame.setVisible(true);
    }

    // Clase simple para representar un punto 3D con un número asociado
    private static class Point3D {
        double x, y, z;
        int number; // Número asociado al punto

        Point3D(double x, double y, double z, int number) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.number = number;
        }
    }
}