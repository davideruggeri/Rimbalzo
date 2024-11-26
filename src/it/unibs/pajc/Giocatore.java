package it.unibs.pajc;

import java.awt.*;

//ciao
public class Giocatore extends Rectangle {
    private static final int BASE_HEIGHT = 400; // Altezza della base del pannello
    private static final int MOVEMENT_SPEED = 10; // Velocità di movimento orizzontale
    private static final double GRAVITY = 0.5; // Gravità per il salto
    private static final int JUMP_STRENGTH = -8; // Forza del salto

    private double yVelocity = 0; // Velocità verticale
    private boolean isJumping = false; // Stato del salto

    public Giocatore(int startX, int startY, int larghezza, int altezza) {
        super(startX, startY, larghezza, altezza);
    }

    public void moveLeft() {
        x -= MOVEMENT_SPEED;
    }

    public void moveRight() {
        x += MOVEMENT_SPEED;
    }

    public void jump() {
        if (!isJumping) {
            yVelocity = JUMP_STRENGTH;
            isJumping = true;
        }
    }

    public void update() {
        if (isJumping) {
            yVelocity += GRAVITY; // Aggiungi gravità alla velocità verticale
            y += yVelocity;

            if (y >= BASE_HEIGHT) {
                y = BASE_HEIGHT; // Rimetti il giocatore sulla base
                yVelocity = 0; // Ferma il movimento verticale
                isJumping = false; // Termina il salto
            }
        }
    }

    public void draw(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(x, y, width, height);
    }
}
