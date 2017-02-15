package de.benedikt_werner.UNO;


public abstract class PlayerController {
    Player player;

    public PlayerController(Player player) {
        this.player = player;
    }
    public abstract Card requestCard();
    public abstract Card requestTakeCards(int count);
    public abstract String requestWildColor();
}
