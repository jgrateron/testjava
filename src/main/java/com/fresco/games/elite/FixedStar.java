package com.fresco.games.elite;

import java.awt.*;

class FixedStar {
	int x, y, size;
	Color color;

	public FixedStar(int x, int y, int size, Color color) {
		this.x = x;
		this.y = y;
		this.size = size;
		this.color = color;
	}

	public void draw(Graphics2D g2d) {
		g2d.setColor(color);
		g2d.fillRect(x, y, size, size);
	}
}
