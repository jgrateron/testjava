package com.fresco.games.elite;

import java.awt.*;

class Star {
    int x, y, speed, size;
    Color color;

    public Star(int x, int y, int speed, int size, Color color) {
        this.x = x;
        this.y = y;
        this.speed = speed;
        this.size = size;
        this.color = color;
    }

    public void update() {
    	this.y += speed;
    }

    public void draw(Graphics2D g2d) {
        g2d.setColor(color);
        g2d.fillRect(x, y, size, size);
    }
}