package com.fresco;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

public class SolarSystemSimulation extends JPanel {

    private static final int WIDTH = 1200; // Ancho de la ventana
    private static final int HEIGHT = 1200; // Alto de la ventana
    private static final int SUN_RADIUS = 40; // Radio del Sol
    private BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    
    private List<Planet> planets = new ArrayList<>();
    private double scaleFactor = 0.7; // Factor de escala para distancias y radios
    private double timeScale = 0.9;   // Factor de escala para la velocidad (ajustable)

    public SolarSystemSimulation() {
        // Inicializar los planetas con distancias, radios, velocidades y colores realistas
        planets.add(new Planet(100, 6, 0.02, new Color(105, 105, 105)));   // Mercurio
        planets.add(new Planet(150, 7, 0.015, new Color(219, 184, 113))); // Venus
        planets.add(new Planet(200, 8, 0.01, new Color(63, 136, 197),     // Tierra
                List.of(new Moon(15, 0.03)))); // Luna
        planets.add(new Planet(250, 6, 0.008, new Color(193, 68, 14),     // Marte
                List.of(new Moon(12, 0.025), new Moon(18, 0.02)))); // Fobos, Deimos
        planets.add(new Planet(350, 20, 0.004, new Color(218, 178, 122),  // Júpiter
                List.of(
                        new Moon(25, 0.015), new Moon(30, 0.012), new Moon(35, 0.01),
                        new Moon(40, 0.008), new Moon(45, 0.006), new Moon(50, 0.005),
                        new Moon(55, 0.004), new Moon(60, 0.003), new Moon(65, 0.002),
                        new Moon(70, 0.001))));
        planets.add(new Planet(450, 13, 0.002, new Color(227, 190, 103), true, // Saturno
                List.of(
                        new Moon(30, 0.015), new Moon(35, 0.012), new Moon(40, 0.01),
                        new Moon(45, 0.008), new Moon(50, 0.006), new Moon(55, 0.005),
                        new Moon(60, 0.004), new Moon(65, 0.003), new Moon(70, 0.002),
                        new Moon(75, 0.001))));
        planets.add(new Planet(550, 10, 0.0015, new Color(135, 206, 250), // Urano
                List.of(
                        new Moon(20, 0.012), new Moon(25, 0.01), new Moon(30, 0.008),
                        new Moon(35, 0.006), new Moon(40, 0.005), new Moon(45, 0.004),
                        new Moon(50, 0.003), new Moon(55, 0.002), new Moon(60, 0.0015),
                        new Moon(65, 0.001))));
        planets.add(new Planet(650, 9, 0.001, new Color(65, 105, 225),    // Neptuno
                List.of(
                        new Moon(25, 0.01), new Moon(30, 0.008), new Moon(35, 0.006),
                        new Moon(40, 0.005), new Moon(45, 0.004), new Moon(50, 0.003),
                        new Moon(55, 0.002), new Moon(60, 0.0015), new Moon(65, 0.001),
                        new Moon(70, 0.0008))));

        // Configurar un temporizador para animar el movimiento
        Timer timer = new Timer(20, e -> {
            for (Planet planet : planets) {
                planet.updateAngle(timeScale);
                for (Moon moon : planet.getMoons()) {
                    moon.updateAngle(timeScale);
                }
            }
            repaint(); // Repintar el panel
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        // Fondo negro
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Dibujar el Sol en el centro
        draw3DSphere(g2d, getWidth() / 2, getHeight() / 2, SUN_RADIUS, Color.YELLOW);

        // Dibujar los planetas con el factor de escala aplicado
        for (Planet planet : planets) {
            int scaledDistance = (int) (planet.getDistance() * scaleFactor);
            int scaledRadius = (int) (planet.getRadius() * scaleFactor);

            int x = (int) (getWidth() / 2 + scaledDistance * Math.cos(planet.getAngle()));
            int y = (int) (getHeight() / 2 + scaledDistance * Math.sin(planet.getAngle()));
            draw3DSphere(g2d, x, y, scaledRadius, planet.getColor());

            // Dibujar los anillos de Saturno si es necesario
            if (planet.hasRings()) {
                drawRings(g2d, x, y, scaledRadius);
            }

            // Dibujar las lunas
            for (Moon moon : planet.getMoons()) {
                int moonScaledDistance = (int) (moon.getDistance() * scaleFactor);
                int moonScaledRadius = (int) (moon.getRadius() * scaleFactor);

                int moonX = (int) (x + moonScaledDistance * Math.cos(moon.getAngle()));
                int moonY = (int) (y + moonScaledDistance * Math.sin(moon.getAngle()));
                draw3DSphere(g2d, moonX, moonY, moonScaledRadius, Color.LIGHT_GRAY);
            }
        }
		Graphics2D g2do = (Graphics2D) g;
		g2do.drawImage(bufferedImage, 0, 0, null);
    }

    // Método para dibujar una esfera con efecto 3D usando gradientes
    private void draw3DSphere(Graphics2D g2d, int centerX, int centerY, int radius, Color baseColor) {
        GradientPaint gradient = new GradientPaint(
                centerX - radius, centerY - radius, brighten(baseColor, 0.7f),
                centerX + radius, centerY + radius, darken(baseColor, 0.7f)
        );

        g2d.setPaint(gradient);
        g2d.fillOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
        g2d.setColor(Color.BLACK);
        g2d.drawOval(centerX - radius, centerY - radius, 2 * radius, 2 * radius);
    }

    // Método para dibujar los anillos de Saturno
    private void drawRings(Graphics2D g2d, int centerX, int centerY, int planetRadius) {
        int ringOuterRadius = planetRadius * 3;
        int ringInnerRadius = planetRadius * 2;

        GradientPaint ringGradient = new GradientPaint(
                centerX - ringOuterRadius, centerY, new Color(200, 180, 100, 150),
                centerX + ringOuterRadius, centerY, new Color(100, 80, 40, 150)
        );

        g2d.setPaint(ringGradient);
        g2d.setStroke(new BasicStroke(4));
        g2d.draw(new Ellipse2D.Double(
                centerX - ringOuterRadius,
                centerY - ringOuterRadius / 2,
                2 * ringOuterRadius,
                ringOuterRadius
        ));
    }

    // Métodos para aclarar/oscurecer colores
    private Color brighten(Color color, float factor) {
        int r = Math.min(255, (int) (color.getRed() * factor));
        int g = Math.min(255, (int) (color.getGreen() * factor));
        int b = Math.min(255, (int) (color.getBlue() * factor));
        return new Color(r, g, b);
    }

    private Color darken(Color color, float factor) {
        int r = Math.max(0, (int) (color.getRed() * factor));
        int g = Math.max(0, (int) (color.getGreen() * factor));
        int b = Math.max(0, (int) (color.getBlue() * factor));
        return new Color(r, g, b);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Simulación 3D del Sistema Solar");
        SolarSystemSimulation simulation = new SolarSystemSimulation();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(WIDTH, HEIGHT);
        frame.add(simulation);
        frame.setVisible(true);
    }
}

// Clase que representa un planeta
class Planet {
    private int distance; // Distancia al Sol
    private int radius;   // Radio del planeta
    private double angle; // Ángulo actual en radianes
    private double speed; // Velocidad angular
    private Color color;
    private boolean hasRings;
    private List<Moon> moons;

    public Planet(int distance, int radius, double speed, Color color) {
        this(distance, radius, speed, color, false, new ArrayList<>());
    }

    public Planet(int distance, int radius, double speed, Color color, boolean hasRings) {
        this(distance, radius, speed, color, hasRings, new ArrayList<>());
    }

    public Planet(int distance, int radius, double speed, Color color, List<Moon> moons) {
        this(distance, radius, speed, color, false, moons);
    }

    public Planet(int distance, int radius, double speed, Color color, boolean hasRings, List<Moon> moons) {
        this.distance = distance;
        this.radius = radius;
        this.angle = 0;
        this.speed = speed;
        this.color = color;
        this.hasRings = hasRings;
        this.moons = moons;
    }

    public void updateAngle(double timeScale) {
        angle += speed * timeScale;
        if (angle > 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
    }

    public int getDistance() {
        return distance;
    }

    public int getRadius() {
        return radius;
    }

    public double getAngle() {
        return angle;
    }

    public Color getColor() {
        return color;
    }

    public boolean hasRings() {
        return hasRings;
    }

    public List<Moon> getMoons() {
        return moons;
    }
}

// Clase que representa una luna
class Moon {
    private int distance; // Distancia al planeta
    private int radius;   // Radio de la luna
    private double angle; // Ángulo actual en radianes
    private double speed; // Velocidad angular

    public Moon(int distance, double speed) {
        this.distance = distance;
        this.radius = 2; // Radio predeterminado para las lunas
        this.angle = 0;
        this.speed = speed;
    }

    public void updateAngle(double timeScale) {
        angle += speed * timeScale;
        if (angle > 2 * Math.PI) {
            angle -= 2 * Math.PI;
        }
    }

    public int getDistance() {
        return distance;
    }

    public int getRadius() {
        return radius;
    }

    public double getAngle() {
        return angle;
    }
}
