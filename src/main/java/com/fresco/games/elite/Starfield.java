package com.fresco.games.elite;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

class Starfield {
	private List<Star> stars = new ArrayList<>();
	private List<FixedStar> fixedStars = new ArrayList<>(); // Lista de estrellas fijas
	private int width, height;
	private Random random = new Random();

	public Starfield(int width, int height, int starCount, int fixedStarCount) {
		this.width = width;
		this.height = height;
		for (int i = 0; i < starCount; i++) {
			stars.add(generateStar());
		}
		for (int i = 0; i < fixedStarCount; i++) {
			fixedStars.add(generateFixedStar());
		}

	}

	public void update() {
		for (Star star : stars) {
			star.update();
			if (star.y - star.size > this.height) {
				star.y = -star.size;
				star.x = random.nextInt(this.width);
			}
		}
	}

	public void draw(Graphics2D g2d) {
		// Dibujar primero las estrellas fijas para que queden al fondo
		for (FixedStar star : fixedStars) {
			star.draw(g2d);
		}
		for (Star star : stars) {
			star.draw(g2d);
		}
	}

	private Star generateStar() {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int speed = random.nextInt(2) + 1; // velocidad entre 1 y 3
		int size = random.nextInt(2) + 1; // tamaño entre 1 y 3
		Color color = Color.WHITE;
		float brightness = random.nextFloat() * 0.6f + 0.4f; // Brillo entre 0.4 y 1.0
		color = new Color(brightness, brightness, brightness);
		return new Star(x, y, speed, size, color);
	}

	private FixedStar generateFixedStar() {
		int x = random.nextInt(width);
		int y = random.nextInt(height);
		int size = random.nextInt(2) + 1; // tamaño entre 1 y 3
		Color color = Color.WHITE;
		float brightness = random.nextFloat() * 0.4f + 0.2f; // Brillo entre 0.2 y 0.6, para que sean más tenues
		color = new Color(brightness, brightness, brightness);
		return new FixedStar(x, y, size, color);
	}
}
