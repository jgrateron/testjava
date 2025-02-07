package com.fresco.games.elite;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class SpacePanel extends JPanel implements ActionListener, KeyListener {
	private int shipX = 500;
	private int shipY = 400;
	private int shipSpeedX = 0;
	private int shipSpeedY = 0;
	private final int ACCELERATION = 2;
	private final int MAX_SPEED = 10;
	private Image shipImage;
	private Image shipImageBoost; // Imagen con el efecto del cohete
	private Starfield starfield;
	private Timer timer;
	private List<Missile> missiles = new ArrayList<>(); // Lista de misiles
	private final int MISSILE_SPEED = 5;
	private boolean isShipMoving = false; // Indica si la nave se está moviendo
	private List<Rock> rocks = new ArrayList<>(); // Lista de rocas
	private Random random = new Random();
	private final int ROCK_SPAWN_RATE = 120; // Una roca cada 60 frames
	private BufferedImage bufferedImage = new BufferedImage(1000, 800, BufferedImage.TYPE_INT_ARGB);
	private int framesSinceLastRock = 0;

	public SpacePanel() {
		setFocusable(true);
		addKeyListener(this);
		setBackground(Color.BLACK);
		setDoubleBuffered(true);
		timer = new Timer(20, this);
		timer.start();

		starfield = new Starfield(1000, 800, 150, 50); // Inicializa el starfield con 150 estrellas en movimiento y 50
														// fijas
		try {
			shipImage = ImageIO.read(getClass().getResource("/nave.png")); // Carga la imagen
			shipImageBoost = ImageIO.read(getClass().getResource("/nave_boost.png"));// Carga la imagen del cohete
		} catch (IOException e) {
			System.err.println("Error al cargar la imagen: " + e);
			shipImage = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) shipImage.getGraphics();
			g.setColor(Color.WHITE);
			g.fillRect(0, 0, 50, 50);

			shipImageBoost = new BufferedImage(50, 50, BufferedImage.TYPE_INT_ARGB);
			g = (Graphics2D) shipImageBoost.getGraphics();
			g.setColor(Color.RED);
			g.fillRect(0, 0, 50, 50);

		}
	}

	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D) bufferedImage.getGraphics();
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, getWidth(), getHeight());
        
		// Dibujar el fondo de estrellas
		starfield.draw(g2d);

		// Dibujar la imagen de la nave dependiendo de si se está moviendo o no
		if (isShipMoving) {
			g2d.drawImage(shipImageBoost, shipX - shipImageBoost.getWidth(null) / 2,
					shipY - shipImageBoost.getHeight(null) / 2, null);
		} else {
			g2d.drawImage(shipImage, shipX - shipImage.getWidth(null) / 2, shipY - shipImage.getHeight(null) / 2, null);
		}

		// Dibujar los misiles
		for (Missile missile : missiles) {
			missile.draw(g2d);
		}
		// Dibujar las rocas
		for (Rock rock : rocks) {
			rock.draw(g2d);
		}
		Graphics2D g2do = (Graphics2D) g;
		g2do.drawImage(bufferedImage, 0, 0, null);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		shipX += shipSpeedX;
		shipY += shipSpeedY;

		// Mantener la nave dentro de la ventana
		if (shipX < 0)
			shipX = 0;
		if (shipX > getWidth())
			shipX = getWidth();
		if (shipY < 0)
			shipY = 0;
		if (shipY > getHeight())
			shipY = getHeight();

		// Actualizar el movimiento de las estrellas
		starfield.update();

		// Actualizar los misiles
		List<Missile> missilesToRemove = new ArrayList<>();
		for (Missile missile : missiles) {
			missile.update();
			// Eliminar misiles si salen de la pantalla
			if (missile.y + missile.SIZE < 0) {
				missilesToRemove.add(missile);
			}
		}
		missiles.removeAll(missilesToRemove);

		// Actualizar y eliminar las rocas
		List<Rock> rocksToRemove = new ArrayList<>();
		for (Rock rock : rocks) {
			rock.update();
			if (rock.x + rock.size < 0 || rock.x - rock.size > getWidth() || rock.y + rock.size < 0
					|| rock.y - rock.size > getHeight()) {
				rocksToRemove.add(rock);
			}
		}
		rocks.removeAll(rocksToRemove);

		// Crear nuevas rocas
		framesSinceLastRock++;
		if (framesSinceLastRock >= ROCK_SPAWN_RATE && rocks.size() < 3) {
			rocks.add(generateRock());
			framesSinceLastRock = 0; // Reinicia el contador
		}

		// Actualiza si la nave está en movimiento
		isShipMoving = shipSpeedX != 0 || shipSpeedY != 0;

		repaint();
	}

	private Rock generateRock() {
		int size = random.nextInt(20) + 60; // Tamaño entre 20 y 40
		int startX;
		int startY;
		double speedX, speedY;
		// Genera la posicion inicial de las rocas
		if (random.nextBoolean()) {
			// Roca desde arriba o abajo
			startX = random.nextInt(getWidth());
			if (random.nextBoolean()) {
				startY = -size;
				speedY = random.nextDouble() * 1.5 + 1;
			} else {
				startY = getHeight() + size;
				speedY = -(random.nextDouble() * 1.5 + 1);
			}
			speedX = random.nextDouble() * 0.5 - 0.25; // velocidad horizontal entre -0.25 y 0.25

		} else {
			// Roca desde la izquierda o derecha
			if (random.nextBoolean()) {
				startX = -size;
				speedX = random.nextDouble() * 1.5 + 1;
			} else {
				startX = getWidth() + size;
				speedX = -(random.nextDouble() * 1.5 + 1);
			}
			startY = random.nextInt(getHeight());
			speedY = random.nextDouble() * 0.5 - 0.25; // velocidad vertical entre -0.25 y 0.25
		}
		double rotationSpeed = random.nextDouble() * 0.1 - 0.05; // velocidad de rotación entre -0.05 y 0.05
		Color color = Color.GRAY;
		float brightness = random.nextFloat() * 0.4f + 0.2f; // Brillo entre 0.2 y 0.6
		color = new Color(brightness, brightness, brightness);

		int rockType = random.nextInt(3); // Genera un tipo de roca aleatorio entre 0 y 2

		return new Rock(startX, startY, size, speedX, speedY, rotationSpeed, color, rockType);
	}

	@Override
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_SPACE) {
			// Disparar un misil
			int missileStartX = shipX; // Misil inicia en la posición X de la nave
			int missileStartY = shipY - shipImage.getHeight(null) / 2; // Misil inicia en la parte superior de la nave

			missiles.add(new Missile(missileStartX, missileStartY, MISSILE_SPEED));
		} else if (key == KeyEvent.VK_UP) {
			shipSpeedY = Math.max(shipSpeedY - ACCELERATION, -MAX_SPEED);

		} else if (key == KeyEvent.VK_DOWN) {
			shipSpeedY = Math.min(shipSpeedY + ACCELERATION, MAX_SPEED);
		} else if (key == KeyEvent.VK_LEFT) {
			shipSpeedX = Math.max(shipSpeedX - ACCELERATION, -MAX_SPEED);
		} else if (key == KeyEvent.VK_RIGHT) {
			shipSpeedX = Math.min(shipSpeedX + ACCELERATION, MAX_SPEED);
		} else if (key == KeyEvent.VK_Q) {
			// Cierra la aplicación
			System.exit(0);
		}
	}

	@Override
	public void keyReleased(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == KeyEvent.VK_UP) {
			shipSpeedY = 0;
		} else if (key == KeyEvent.VK_DOWN) {
			shipSpeedY = 0;
		} else if (key == KeyEvent.VK_LEFT) {
			shipSpeedX = 0;
		} else if (key == KeyEvent.VK_RIGHT) {
			shipSpeedX = 0;
		}

	}

	@Override
	public void keyTyped(KeyEvent e) {
		// No usado en este ejemplo.
	}
}
