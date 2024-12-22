package com.fresco.games.elite;

import java.awt.*;

class Missile {
	int x, y, speed;
	final int SIZE = 6; // Tamaño del misil
	final int offset = SIZE / 2;

	public Missile(int x, int y, int speed) {
		this.x = x;
		this.y = y;
		this.speed = speed;
	}

	public void update() {
		y -= speed; // Mover hacia arriba
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(Color.YELLOW);
		g2d.fillRect(x - offset, y - offset, SIZE, SIZE);
	}

	public Rectangle getBounds() {
		return new Rectangle(x, y, SIZE, SIZE);
	}
}