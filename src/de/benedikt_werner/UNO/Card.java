package de.benedikt_werner.UNO;


public class Card {
    public static final int SWAP_DIR = -1;
    public static final int TAKE_TWO = -2;
    public static final int JUMP = -3;
    public static final int WILD = 11;
    public static final int WILD_FOUR = 12;
    public static final String BLACK = "black";
    public static final String[] colors = {"red", "green", "blue", "yellow"};

    public int number; // -1: swap dir, -2: take two, -3: jump player
    public String color;

    public Card(int number, String color) {
        this.number = number;
        this.color = color;
    }

    public String toString() {
        String num;
        switch (number) {
            case SWAP_DIR: num = "SWAP DIRECTION"; break;
            case TAKE_TWO: num = "TAKE TWO"; break;
            case JUMP: num = "JUMP"; break;
            case WILD: num = "WILD"; break;
            case WILD_FOUR: num = "WILD TAKE FOUR"; break;
            default: num = number + ""; break;
        }
        return "(" + num + " " + color + ")";
    }

    public boolean isWild() {
        return color.equals(BLACK);
    }
}
