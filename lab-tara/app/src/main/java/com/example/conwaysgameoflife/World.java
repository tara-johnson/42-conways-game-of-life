package com.example.conwaysgameoflife;

import com.example.conwaysgameoflife.models.Cell;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    public static final Random RANDOM = new Random();
    public int width;
    public int height;
    private Cell[][] board;

    public World(int width, int height) {
        this.width = width;
        this.height = height;
        board = new Cell[width][height];
        init();
    }

    private void init() {
        for(int row = 0; row < width; row++) {
            for(int col = 0; col < height; col++) {
                board[row][col] = new Cell(row, col, RANDOM.nextBoolean());
            }
        }
    }

    public Cell get(int row, int col) {
        return board[row][col];
    }

    public int numNeighbors(int row, int col) {
        int num = 0;

        for(int k = row - 1; k <= row + 1; k++) {
            for (int l = col - 1; l <= col + 1; l++) {
                if((k != row || l != col) && k >= 0 && k < width && l >= 0 && l < height) {
                    Cell cell = board[k][l];

                    if (cell.alive) {
                        num++;
                    }
                }

            }
        }
        return num;
    }

    public void nextGeneration() {
        List<Cell> liveCells = new ArrayList<Cell>();
        List<Cell> deadCells = new ArrayList<Cell>();

        for(int row = 0; row < width; row++) {
            for(int col = 0; col < height; col++) {
                Cell cell = board[row][col];

                int numNeighbors = numNeighbors(cell.x, cell.y);

                // Any live cell with fewer than two live neighbors OR
                // Any live cell with greater than three live neighbors
                // Dies
                if (cell.alive &&
                        (numNeighbors < 2 || numNeighbors > 3)) {
                    deadCells.add(cell);
                }

                // Any live cell with exactly 2 or exactly 3 neighbors stays alive
                // Any dead cell with exactly 3 neighbors becomes a live cell
                if ((cell.alive &&
                        (numNeighbors == 3 || numNeighbors == 2))
                        ||
                        (!cell.alive && numNeighbors == 3)) {
                    liveCells.add(cell);
                }
            }
        }

        // Update future live cells
        for (Cell cell : liveCells) {
            cell.reborn();
        }

        // Update future dead cells
        for (Cell cell: deadCells) {
            cell.die();
        }
    }
}
