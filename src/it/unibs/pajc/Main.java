package it.unibs.pajc;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;


public class Main {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Ball Simulation");
        GamePanel panel = new GamePanel();
        JButton reset = new JButton("Reset");

        reset.addActionListener(e -> panel.reset());

        frame.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        frame.add(reset, BorderLayout.SOUTH);

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(panel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        panel.startGame();
    }
}

class GamePanel extends JPanel {
    private static final int PANEL_WIDTH = 1000;
    private static final int PANEL_HEIGHT = 500;
    private Ball ball;
    private Giocatore giocatore1, giocatore2;
    private final int FPS = 60;

    public GamePanel() {
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBackground(Color.BLACK);

        ball = new Ball(PANEL_WIDTH / 2, PANEL_HEIGHT / 4);
        giocatore1 = new Giocatore(100, 400, 20, 50); // Giocatore 1
        giocatore2 = new Giocatore(800, 400, 20, 50); // Giocatore 2

        this.setFocusable(true);
        this.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyPressed(java.awt.event.KeyEvent e) {
                int key = e.getKeyCode();
                // Controlli per il Giocatore 1 (WASD)
                if (key == KeyEvent.VK_A) giocatore1.moveLeft();
                if (key == KeyEvent.VK_D) giocatore1.moveRight();
                if (key == KeyEvent.VK_W) giocatore1.jump();

                // Controlli per il Giocatore 2 (Frecce)
                if (key == KeyEvent.VK_LEFT) giocatore2.moveLeft();
                if (key == KeyEvent.VK_RIGHT) giocatore2.moveRight();
                if (key == KeyEvent.VK_UP) giocatore2.jump();
            }
        });
    }

    public void startGame() {
        Timer timer = new Timer(1000/FPS , e -> {
            ball.move();
            ball.checkBounds();

            ball.checkCollision(giocatore1);
            ball.checkCollision(giocatore2);

            giocatore1.update(); // Aggiorna stato del Giocatore 1
            giocatore2.update(); // Aggiorna stato del Giocatore 2

            repaint();
        });
        timer.start();
    }

    public void reset() {
        ball.resetBall(PANEL_WIDTH / 2, PANEL_HEIGHT / 4, 2);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        ball.draw(g);
        giocatore1.draw(g);
        giocatore2.draw(g);
    }
}
