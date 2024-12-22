package com.fresco.games.elite;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

class Rock {
	int x, y, size;
	double speedX, speedY;
	double rotationAngle;
	double rotationSpeed;
	Color color;
	Image rockImage; // Imagen de la roca

	public Rock(int x, int y, int size, double speedX, double speedY, double rotationSpeed, Color color, int rockType) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.speedX = speedX;
		this.speedY = speedY;
		this.rotationSpeed = rotationSpeed;
		this.rotationAngle = 0;
		this.color = color;

		try {
			if (rockType == 0) {
				rockImage = ImageIO.read(getClass().getResource("/rock.png")); // Carga la imagen
			} else if (rockType == 1) {
				rockImage = ImageIO.read(getClass().getResource("/rock2.png")); // Carga la imagen
			} else {
				rockImage = ImageIO.read(getClass().getResource("/rock3.png")); // Carga la imagen
			}
		} catch (IOException e) {
			System.err.println("Error al cargar la imagen: " + e);
			// En caso de error, puedes crear una imagen predeterminada
			rockImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
			Graphics2D g = (Graphics2D) rockImage.getGraphics();
			g.setColor(color);
			g.fillRect(0, 0, size, size);
		}

	}

	public void update() {
		x += speedX;
		y += speedY;
		rotationAngle += rotationSpeed;
	}

	public void draw(Graphics2D g2d) {
		AffineTransform originalTransform = g2d.getTransform();
		g2d.rotate(rotationAngle, x + size / 2.0, y + size / 2.0);
		g2d.drawImage(rockImage, x, y, size, size, null);
		g2d.setTransform(originalTransform);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, size, size);
	}
}
