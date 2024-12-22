package com.fresco.games.elite;

import javax.swing.*;

public class EliteGame extends JFrame {

	private static final long serialVersionUID = 1L;
	private SpacePanel spacePanel;

	public EliteGame() {
		setTitle("Elite Game");
		setSize(1000, 800);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);

		spacePanel = new SpacePanel();
		add(spacePanel);

		setVisible(true);
	}

	public static void main(String[] args) {
		SwingUtilities.invokeLater(() -> new EliteGame());
	}
}
