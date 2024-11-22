package it.unibs.pajc;

import java.awt.*;

public class Ball extends Rectangle {
    public double xVelocity, yVelocity;
    private double xPosition, yPosition;
    private static final double GRAVITY = 0.981;
    private static final int INITIAL_SPEED = 5;
    private static final int BALL_SIZE = 20;
    private static final int PANEL_HEIGHT = 500;
    private static final int PANEL_WIDTH = 1000;


    public Ball(int startX, int startY) {
        super(startX, startY, BALL_SIZE, BALL_SIZE);
        this.xPosition = startX;
        this.yPosition = startY;

        xVelocity = 0;
        yVelocity = 0;
    }

    public void move() {
        yVelocity += GRAVITY;

        xPosition += xVelocity;
        yPosition += yVelocity;

        this.x = (int) xPosition;
        this.y = (int) yPosition;
    }

    public void resetBall(int x, int y, int lato) { //lato = 1 dx, lato = 2 sx
        this.xPosition = x;
        this.yPosition = y;
        double angle = 0;
        if (lato == 1) {
            angle = Math.toRadians(45);
        } else if (lato == 2) {
            angle = Math.toRadians(135);
        }
        xVelocity = INITIAL_SPEED*Math.cos(angle);
        yVelocity = INITIAL_SPEED*Math.sin(angle);
    }
    public void checkBounds() {
        if (x <= 0 || x + width >= PANEL_WIDTH) {
            xVelocity = -xVelocity;
        }

        if (y + height >= PANEL_HEIGHT) {
            yVelocity = -yVelocity * GRAVITY;
            y = PANEL_HEIGHT -height;
        }
    }

    public void checkCollision(Giocatore giocatore) {
        if (this.intersects(giocatore)) {
            // Determina se la collisione avviene sopra
            if (this.y + this.height <= giocatore.y + 5) {
                // Collisione sulla parte superiore
                this.y = giocatore.y - this.height; // Riposiziona sopra il giocatore
                yVelocity = -Math.abs(yVelocity); // Inverti velocità verticale

                // Modifica l'angolo in base alla posizione del contatto
                double giocatoreCentro = giocatore.x + giocatore.width / 2.0;
                double pallaCentro = this.x + this.width / 2.0;
                double distanza = pallaCentro - giocatoreCentro;
                double maxDistanza = giocatore.width / 2.0;
                xVelocity = INITIAL_SPEED * (distanza / maxDistanza); // Nuova velocità orizzontale
            }
            // Collisione sul lato sinistro
            else if (this.x + this.width <= giocatore.x + 5) {
                this.x = giocatore.x - this.width; // Riposiziona a sinistra del giocatore
                xVelocity = -Math.abs(xVelocity); // Inverti velocità orizzontale
            }
            // Collisione sul lato destro
            else if (this.x >= giocatore.x + giocatore.width - 5) {
                this.x = giocatore.x + giocatore.width; // Riposiziona a destra del giocatore
                xVelocity = Math.abs(xVelocity); // Inverti velocità orizzontale
            }
        }
    }




    public void draw(Graphics g) {
        g.setColor(Color.RED);
        g.fillOval(this.x, this.y, BALL_SIZE, BALL_SIZE);
    }
}
