package controller;

import java.awt.Color;
import model.Shape;

public class GameController {
    private Color[][] board;
    private final int ROWS = 20, COLS = 10;
    private Shape currentShape;
    private int curX, curY, score = 0;
    
    private boolean gameOver = false;
    private boolean isPaused = false; 

    public GameController() {
        board = new Color[ROWS][COLS];
        spawnNewShape();
    }

    public void spawnNewShape() {
        currentShape = new Shape();
        curY = 0;
        curX = COLS / 2 - currentShape.getSize() / 2;
        
        if (checkCollision(curX, curY, currentShape.getCoords())) {
            gameOver = true;
        }
    }

    public void update() {
        if (isPaused || gameOver) return;

        if (!checkCollision(curX, curY + 1, currentShape.getCoords())) {
            curY++;
        } else {
            fixShapeToBoard();
            checkLines();
            spawnNewShape();
        }
    }

    public void hardDrop() {
        if (gameOver || isPaused) return;

        while (!checkCollision(curX, curY + 1, currentShape.getCoords())) {
            curY++;
        }
        fixShapeToBoard();
        checkLines();
        spawnNewShape();
    }

    public void rotate() {
        if (gameOver || isPaused) return;
        
        int[][] old = currentShape.getCoords();
        currentShape.rotate();
        if (checkCollision(curX, curY, currentShape.getCoords())) {
            currentShape.setCoords(old); 
        }
    }

    public void moveLeft() { 
        if (!gameOver && !isPaused && !checkCollision(curX - 1, curY, currentShape.getCoords())) {
            curX--; 
        }
    }

    public void moveRight() { 
        if (!gameOver && !isPaused && !checkCollision(curX + 1, curY, currentShape.getCoords())) {
            curX++; 
        }
    }

    public void togglePause() {
        this.isPaused = !this.isPaused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public void setGameOver(boolean status) {
        this.gameOver = status;
    }

    private boolean checkCollision(int x, int y, int[][] matrix) {
        for (int r = 0; r < matrix.length; r++) {
            for (int c = 0; c < matrix[r].length; c++) {
                if (matrix[r][c] != 0) {
                    int bX = x + c, bY = y + r;
                    if (bX < 0 || bX >= COLS || bY >= ROWS) return true;
                    if (bY >= 0 && board[bY][bX] != null) return true;
                }
            }
        }
        return false;
    }

    private void fixShapeToBoard() {
        int[][] coords = currentShape.getCoords();
        for (int r = 0; r < coords.length; r++) {
            for (int c = 0; c < coords[r].length; c++) {
                if (coords[r][c] != 0) {
                    if (curY + r >= 0) {
                        board[curY + r][curX + c] = currentShape.getColor();
                    }
                }
            }
        }
    }

    public void checkLines() {
        for (int r = ROWS - 1; r >= 0; r--) {
            boolean full = true;
            for (int c = 0; c < COLS; c++) { 
                if (board[r][c] == null) { 
                    full = false; 
                    break; 
                } 
            }
            if (full) { 
                removeRow(r); 
                score += 100; 
                r++; 
            }
        }
    }

    private void removeRow(int row) {
        for (int r = row; r > 0; r--) {
            board[r] = board[r - 1].clone();
        }
        board[0] = new Color[COLS];
    }

    public Color[][] getBoard() { return board; }
    public Shape getCurrentShape() { return currentShape; }
    public int getCurX() { return curX; }
    public int getCurY() { return curY; }
    public int getScore() { return score; }
}