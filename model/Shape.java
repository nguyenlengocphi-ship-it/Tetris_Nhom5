package model;

import java.awt.Color;
import java.util.Random;

public class Shape {
    private static final int[][][] SHAPES = {
        {{0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}, {0, 1, 0, 0}}, 
        {{0, 1, 0}, {0, 1, 0}, {1, 1, 0}}, 
        {{1, 0, 0}, {1, 0, 0}, {1, 1, 0}}, 
        {{1, 1}, {1, 1}}, 
        {{0, 1, 1}, {1, 1, 0}, {0, 0, 0}}, 
        {{1, 1, 1}, {0, 1, 0}, {0, 0, 0}}, 
        {{1, 1, 0}, {0, 1, 1}, {0, 0, 0}}  
    };

    private static final Color[] COLORS = {
        Color.CYAN, Color.BLUE, Color.ORANGE, Color.YELLOW, 
        Color.GREEN, new Color(128, 0, 128), Color.RED, Color.PINK
    };

    private int[][] currentCoords;
    private Color currentColor;

    public Shape() {
        Random rand = new Random();
        currentCoords = copyMatrix(SHAPES[rand.nextInt(SHAPES.length)]);
        currentColor = COLORS[rand.nextInt(COLORS.length)];
    }

    public void rotate() {
        int size = currentCoords.length;
        int[][] rotated = new int[size][size];
        for (int r = 0; r < size; r++) {
            for (int c = 0; c < size; c++) {
                rotated[c][size - 1 - r] = currentCoords[r][c];
            }
        }
        currentCoords = rotated;
    }

    private int[][] copyMatrix(int[][] matrix) {
        int[][] copy = new int[matrix.length][];
        for (int i = 0; i < matrix.length; i++) copy[i] = matrix[i].clone();
        return copy;
    }

    public void setCoords(int[][] coords) { this.currentCoords = coords; }
    public int[][] getCoords() { return currentCoords; }
    public Color getColor() { return currentColor; }
    public int getSize() { return currentCoords.length; }
}