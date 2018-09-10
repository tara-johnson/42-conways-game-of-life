package com.example.conwaysgameoflife.models;

public class Cell {
    public int x;
    public int y;
    public boolean alive;

    public Cell(int x, int y, boolean alive) {
        this.x = x;
        this.y = y;
        this.alive = alive;
    }

    // If a cell dies, switch it's display to false so it doesn't show in the grid
    public void die() {
        alive = false;
    }

    // If a cell is reborn, switch it's display to true so it shows up in the grid
    public void reborn() {
        alive = true;
    }

    public void invert() {
        alive = !alive;
    }
}
