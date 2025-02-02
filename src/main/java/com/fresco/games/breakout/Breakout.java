package com.fresco.games.breakout;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;
import java.util.Random;

public class Breakout extends JFrame implements ActionListener, KeyListener {

    // Dimensiones originales
    private static final int ORIGINAL_WIDTH = 600;
    private static final int ORIGINAL_HEIGHT = 400;
    private static final int ORIGINAL_PADDLE_WIDTH = 80;
    private static final int ORIGINAL_PADDLE_HEIGHT = 10;
    private static final int ORIGINAL_BALL_SIZE = 10;
    private static final int ORIGINAL_BRICK_WIDTH = 50;
    private static final int ORIGINAL_BRICK_HEIGHT = 20;

    // Dimensiones escaladas (doble de tamaño)
    private static final int WIDTH = ORIGINAL_WIDTH * 2;
    private static final int HEIGHT = ORIGINAL_HEIGHT * 2;
    private static final int PADDLE_WIDTH = ORIGINAL_PADDLE_WIDTH * 2;
    private static final int PADDLE_HEIGHT = ORIGINAL_PADDLE_HEIGHT * 2;
    private static final int BALL_SIZE = ORIGINAL_BALL_SIZE * 2;
    private static final int BRICK_WIDTH = ORIGINAL_BRICK_WIDTH * 2;
    private static final int BRICK_HEIGHT = ORIGINAL_BRICK_HEIGHT * 2;

    private int paddleX;
    private int ballX, ballY;

    // Ajustar la velocidad de la pelota
    private int ballXDir = 2;
    private int ballYDir = -2;

    private int score = 0;
    private Timer timer;
    private boolean[][] bricks;
    private boolean gameOver = false;
    private boolean gameStarted = false;
    private BufferedImage buffer;
    private Graphics bufferGraphics;
    private Color[][] brickColors;
    private Random random;

    public Breakout() {
        setTitle("Breakout");
        setSize(WIDTH, HEIGHT);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setResizable(false);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);

        random = new Random();
        initGame();
        timer = new Timer(10, this);
        timer.start();
        setVisible(true);

        buffer = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        bufferGraphics = buffer.getGraphics();
    }

     private void initGame() {
        paddleX = WIDTH / 2 - PADDLE_WIDTH / 2;
        ballX = WIDTH / 2;
        ballY = HEIGHT / 2;
        ballXDir = 2; // Reiniciar la velocidad en X
        ballYDir = -2; // Reiniciar la velocidad en Y
        score = 0;
        gameOver = false;
        gameStarted = false;
        bricks = new boolean[5][10];
        brickColors = new Color[5][10];

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                bricks[i][j] = true;
                brickColors[i][j] = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        drawScene(bufferGraphics);
        g.drawImage(buffer, 0, 0, this);
    }

    private void drawScene(Graphics g) {
    	Graphics2D g2d = (Graphics2D) g;
    	g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        g.setColor(Color.WHITE);
        g.fillRect(paddleX, HEIGHT - PADDLE_HEIGHT - 20, PADDLE_WIDTH, PADDLE_HEIGHT);

        g.setColor(Color.RED);
        g.fillOval(ballX, ballY, BALL_SIZE, BALL_SIZE);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (bricks[i][j]) {
                    g.setColor(brickColors[i][j]);
                    g.fillRect(j * BRICK_WIDTH + 100, i * BRICK_HEIGHT + 100, BRICK_WIDTH, BRICK_HEIGHT);
                }
            }
        }

        g.setColor(Color.WHITE);
        g.setFont(new Font("Arial", Font.BOLD, 40));
        g.drawString("Score: " + score, 20, 70);
        if (!gameStarted) {
             g.setFont(new Font("Arial", Font.BOLD, 80));
            g.drawString("Press Enter to Start", WIDTH / 2 - 400, HEIGHT / 2);
        }
         if (gameOver) {
            g.setColor(Color.WHITE);
            g.setFont(new Font("Arial", Font.BOLD, 80));
            g.drawString("Game Over!", WIDTH / 2 - 200, HEIGHT / 2);
            g.drawString("Press Enter to Restart", WIDTH / 2 - 400, HEIGHT / 2 + 100);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (gameOver || !gameStarted) return;

        ballX += ballXDir;
        ballY += ballYDir;

        if (ballX <= 0 || ballX >= WIDTH - BALL_SIZE) {
            ballXDir *= -1;
        }
        if (ballY <= 0) {
            ballYDir *= -1;
        }

        if (ballY >= HEIGHT - PADDLE_HEIGHT - 20 - BALL_SIZE &&
                ballX >= paddleX && ballX <= paddleX + PADDLE_WIDTH) {
            ballYDir *= -1;
        }

        checkBrickCollisions();

        if (ballY > HEIGHT) {
            gameOver = true;
            timer.stop();
        }

        repaint();
    }

    private void checkBrickCollisions() {
         int ballRight = ballX + BALL_SIZE;
        int ballBottom = ballY + BALL_SIZE;
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                if (bricks[i][j]) {
                    int brickX = j * BRICK_WIDTH + 100;
                    int brickY = i * BRICK_HEIGHT + 100;
                    int brickRight = brickX + BRICK_WIDTH;
                    int brickBottom = brickY + BRICK_HEIGHT;

                   if (ballX < brickRight && ballRight > brickX && ballY < brickBottom && ballBottom > brickY) {
                        bricks[i][j] = false;
                        score += 10;
                        ballYDir *= -1;
                    }
                }
            }
        }
       if (score == 500) {
           gameOver = true;
           timer.stop();
       }
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            paddleX -= 40;
            if (paddleX < 0) paddleX = 0;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            paddleX += 40;
            if (paddleX > WIDTH - PADDLE_WIDTH) paddleX = WIDTH - PADDLE_WIDTH;
        } else if (e.getKeyCode() == KeyEvent.VK_Q) {
            System.exit(0);
        } else if (e.getKeyCode() == KeyEvent.VK_ENTER) {
            if (gameOver) {
                initGame();
                timer.start();
            } else if (!gameStarted) {
                gameStarted = true;
            }
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(Breakout::new);
    }
}
