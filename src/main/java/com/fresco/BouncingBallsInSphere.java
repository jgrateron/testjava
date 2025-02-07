package com.fresco;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

// Clase principal que extiende JPanel y maneja la animación de bolas rebotando dentro de una esfera
public class BouncingBallsInSphere extends JPanel implements ActionListener {

    // Constantes para definir el tamaño del panel, radio de la esfera y número de bolas
    private static final int WIDTH = 1000; // Ancho del panel (ajustado para una esfera más grande)
    private static final int HEIGHT = 800; // Alto del panel (ajustado para una esfera más grande)
    private static final int SPHERE_RADIUS = 600; // Radio de la esfera (más grande)
    private static final int BALL_RADIUS = 10; // Radio de cada bola
    private static final int NUM_BALLS = 20; // Número de bolas reducido

    // Lista que almacena las bolas en movimiento
    private List<Ball> balls = new ArrayList<>();
    
    // Ángulo de rotación de la esfera para dar efecto de movimiento
    private double rotationAngle = 0;
    
    // Timer para controlar la frecuencia de actualización de la animación (~30 FPS)
    private Timer timer;
    private BufferedImage bufferedImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_ARGB);
    
    // Constructor de la clase. Inicializa el panel, crea las bolas y comienza el temporizador.
    public BouncingBallsInSphere() {
        setPreferredSize(new Dimension(WIDTH, HEIGHT)); // Establece el tamaño preferido del panel
        setBackground(Color.BLACK); // Fondo negro para un mejor contraste visual
        
        Random random = new Random(); // Generador de números aleatorios para posiciones y velocidades iniciales
        
        // Crear las bolas con posiciones y velocidades aleatorias
        for (int i = 0; i < NUM_BALLS; i++) {
            // Generar coordenadas x, y, z dentro del rango de la esfera
            double x = random.nextDouble() * SPHERE_RADIUS * 2 - SPHERE_RADIUS;
            double y = random.nextDouble() * SPHERE_RADIUS * 2 - SPHERE_RADIUS;
            double z = random.nextDouble() * SPHERE_RADIUS * 2 - SPHERE_RADIUS;
            
            // Asegurarse de que la bola esté dentro de la esfera
            double distance = Math.sqrt(x * x + y * y + z * z);
            if (distance > SPHERE_RADIUS) {
                double scale = SPHERE_RADIUS / distance;
                x *= scale;
                y *= scale;
                z *= scale;
            }
            
            // Velocidades aleatorias en cada eje
            double vx = random.nextDouble() * 2 - 1;
            double vy = random.nextDouble() * 2 - 1;
            double vz = random.nextDouble() * 2 - 1;
            
            // Asignar un color aleatorio a cada bola
            Color ballColor = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            
            // Agregar la bola a la lista
            balls.add(new Ball(x, y, z, vx, vy, vz, ballColor));
        }

        // Iniciar el temporizador para actualizar la animación cada ~33 ms (~30 FPS)
        timer = new Timer(33, this);
        timer.start();
    }

    // Método sobrescrito para dibujar los componentes gráficos del panel
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g); // Llama al método de la clase padre para limpiar el panel
        Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
		g2d.setColor(Color.BLACK);
		g2d.fillRect(0, 0, getWidth(), getHeight());
        // Habilitar antialiasing para mejorar la calidad visual de las líneas y círculos
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar la malla de la esfera
        drawSphereMesh(g2d);
        
        // Dibujar cada bola en su posición actual con su color correspondiente
        for (Ball ball : balls) {
            g2d.setColor(ball.color); // Establecer el color de la bola
            
            // Proyectar las coordenadas 3D a 2D considerando la rotación de la esfera
            double[] projected = project3DTo2D(ball.x, ball.y, ball.z, rotationAngle);
            int px = (int) (WIDTH / 2 + projected[0]); // Coordenada X en pantalla
            int py = (int) (HEIGHT / 2 + projected[1]); // Coordenada Y en pantalla
            
            // Dibujar la bola como un círculo lleno
            g2d.fillOval(px - BALL_RADIUS, py - BALL_RADIUS, BALL_RADIUS * 2, BALL_RADIUS * 2);
        }
		Graphics2D g2do = (Graphics2D) g;
		g2do.drawImage(bufferedImage, 0, 0, null);
    }

    // Método para dibujar la malla de la esfera
    private void drawSphereMesh(Graphics2D g2d) {
        g2d.setColor(Color.WHITE); // Color blanco para la malla
        
        int numMeridians = 20; // Número de meridianos (líneas verticales)
        int numParallels = 10; // Número de paralelos (líneas horizontales)

        // Dibujar los meridianos
        for (int i = 0; i <= numMeridians; i++) {
            double angle = Math.toRadians(360.0 * i / numMeridians); // Ángulo para cada meridiano
            drawMeridian(g2d, angle);
        }

        // Dibujar los paralelos
        for (int i = 0; i <= numParallels; i++) {
            double latitude = Math.toRadians(-90 + 180.0 * i / numParallels); // Latitud para cada paralelo
            drawParallel(g2d, latitude);
        }
    }

    // Método para dibujar un meridiano dado un ángulo
    private void drawMeridian(Graphics2D g2d, double angle) {
        for (double latitude = -Math.PI / 2; latitude <= Math.PI / 2; latitude += Math.PI / 36) {
            // Calcular las coordenadas 3D de dos puntos consecutivos en el meridiano
            double x1 = SPHERE_RADIUS * Math.cos(latitude) * Math.cos(angle);
            double y1 = SPHERE_RADIUS * Math.sin(latitude);
            double z1 = SPHERE_RADIUS * Math.cos(latitude) * Math.sin(angle);
            
            double x2 = SPHERE_RADIUS * Math.cos(latitude + Math.PI / 36) * Math.cos(angle);
            double y2 = SPHERE_RADIUS * Math.sin(latitude + Math.PI / 36);
            double z2 = SPHERE_RADIUS * Math.cos(latitude + Math.PI / 36) * Math.sin(angle);
            
            // Proyectar ambos puntos a 2D y dibujar la línea entre ellos
            double[] p1 = project3DTo2D(x1, y1, z1, rotationAngle);
            double[] p2 = project3DTo2D(x2, y2, z2, rotationAngle);
            int xScreen1 = (int) (WIDTH / 2 + p1[0]);
            int yScreen1 = (int) (HEIGHT / 2 + p1[1]);
            int xScreen2 = (int) (WIDTH / 2 + p2[0]);
            int yScreen2 = (int) (HEIGHT / 2 + p2[1]);
            g2d.drawLine(xScreen1, yScreen1, xScreen2, yScreen2);
        }
    }

    // Método para dibujar un paralelo dada una latitud
    private void drawParallel(Graphics2D g2d, double latitude) {
        for (double longitude = 0; longitude <= 2 * Math.PI; longitude += Math.PI / 36) {
            // Calcular las coordenadas 3D de dos puntos consecutivos en el paralelo
            double x1 = SPHERE_RADIUS * Math.cos(latitude) * Math.cos(longitude);
            double y1 = SPHERE_RADIUS * Math.sin(latitude);
            double z1 = SPHERE_RADIUS * Math.cos(latitude) * Math.sin(longitude);
            
            double x2 = SPHERE_RADIUS * Math.cos(latitude) * Math.cos(longitude + Math.PI / 36);
            double y2 = SPHERE_RADIUS * Math.sin(latitude);
            double z2 = SPHERE_RADIUS * Math.cos(latitude) * Math.sin(longitude + Math.PI / 36);
            
            // Proyectar ambos puntos a 2D y dibujar la línea entre ellos
            double[] p1 = project3DTo2D(x1, y1, z1, rotationAngle);
            double[] p2 = project3DTo2D(x2, y2, z2, rotationAngle);
            int xScreen1 = (int) (WIDTH / 2 + p1[0]);
            int yScreen1 = (int) (HEIGHT / 2 + p1[1]);
            int xScreen2 = (int) (WIDTH / 2 + p2[0]);
            int yScreen2 = (int) (HEIGHT / 2 + p2[1]);
            g2d.drawLine(xScreen1, yScreen1, xScreen2, yScreen2);
        }
    }

    // Método para proyectar un punto 3D a 2D considerando la rotación de la esfera
    private double[] project3DTo2D(double x, double y, double z, double angle) {
        // Rotar alrededor del eje Y
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);
        double rotatedX = x * cos - z * sin;
        double rotatedZ = x * sin + z * cos;

        // Proyección perspectiva simple basada en la profundidad
        double depth = SPHERE_RADIUS - rotatedZ;
        double scale = SPHERE_RADIUS / (SPHERE_RADIUS + depth);
        return new double[]{rotatedX * scale, y * scale};
    }

    // Método llamado por el temporizador para actualizar la posición de las bolas y redibujar
    @Override
    public void actionPerformed(ActionEvent e) {
        updateBalls(); // Actualizar las posiciones de las bolas
        repaint(); // Solicitar repintado del panel
    }

    // Método para actualizar la posición de las bolas y manejar colisiones con la esfera
    private void updateBalls() {
        rotationAngle += 0.02; // Incrementar el ángulo de rotación para dar efecto de giro

        for (Ball ball : balls) {
            // Actualizar la posición de la bola según su velocidad
            ball.x += ball.vx;
            ball.y += ball.vy;
            ball.z += ball.vz;

            // Calcular la distancia desde el centro de la esfera
            double distance = Math.sqrt(ball.x * ball.x + ball.y * ball.y + ball.z * ball.z);

            // Si la bola sale de la esfera, reflejar su velocidad y ajustar su posición
            if (distance > SPHERE_RADIUS) {
                double normalX = ball.x / distance;
                double normalY = ball.y / distance;
                double normalZ = ball.z / distance;
                
                // Calcular el producto escalar para reflejar la velocidad
                double dotProduct = ball.vx * normalX + ball.vy * normalY + ball.vz * normalZ;
                ball.vx -= 2 * dotProduct * normalX;
                ball.vy -= 2 * dotProduct * normalY;
                ball.vz -= 2 * dotProduct * normalZ;

                // Ajustar la posición para asegurar que la bola permanezca dentro de la esfera
                double scaleFactor = (SPHERE_RADIUS - 1) / distance;
                ball.x *= scaleFactor;
                ball.y *= scaleFactor;
                ball.z *= scaleFactor;
            }
        }
    }

    // Clase interna que representa una bola con posición, velocidad y color
    private static class Ball {
        double x, y, z; // Posición en el espacio 3D
        double vx, vy, vz; // Velocidad en cada eje
        Color color; // Color de la bola

        // Constructor para inicializar una bola con posición, velocidad y color
        Ball(double x, double y, double z, double vx, double vy, double vz, Color color) {
            this.x = x;
            this.y = y;
            this.z = z;
            this.vx = vx;
            this.vy = vy;
            this.vz = vz;
            this.color = color;
        }
    }

    // Método principal para iniciar la aplicación
    public static void main(String[] args) {
        JFrame frame = new JFrame("Bouncing Balls in Sphere Java");
        BouncingBallsInSphere panel = new BouncingBallsInSphere();
        
        // Configuración básica del JFrame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null); // Centrar la ventana
        frame.setVisible(true); // Hacer visible la ventana
    }
}