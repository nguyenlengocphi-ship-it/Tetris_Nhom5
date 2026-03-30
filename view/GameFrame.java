package view;

import controller.DBConnection;
import controller.GameController;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class GameFrame extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;

    public GameFrame() {
        this.setTitle("Game xếp hình - Nhóm 5");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false); 

        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        MenuView menuView = new MenuView(name -> startGame(name));
        mainPanel.add(menuView, "MENU");

        this.add(mainPanel);
        this.pack(); 
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

    private void startGame(String playerName) {
        GameController controller = new GameController();
        GamePanel gamePanel = new GamePanel(controller, playerName);

        mainPanel.add(gamePanel, "GAME");
        cardLayout.show(mainPanel, "GAME");

        gamePanel.setFocusable(true);
        gamePanel.requestFocusInWindow();

        this.pack(); 
        this.setLocationRelativeTo(null);

        gamePanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (controller.isGameOver()) {
                    showLeaderboard(playerName, controller.getScore());
                    return;
                }

                if (e.getKeyCode() == KeyEvent.VK_P) {
                    controller.togglePause();
                    gamePanel.repaint();
                    return;
                }

                if (!controller.isPaused()) {
                    switch (e.getKeyCode()) {
                        case KeyEvent.VK_LEFT -> controller.moveLeft();
                        case KeyEvent.VK_RIGHT -> controller.moveRight();
                        case KeyEvent.VK_DOWN -> controller.update();
                        case KeyEvent.VK_UP -> controller.rotate();
                        case KeyEvent.VK_SPACE -> controller.hardDrop();
                    }
                }
                gamePanel.repaint();
            }
        });
    }

    private void showLeaderboard(String name, int score) {
        new Thread(() -> {
            DBConnection.saveScore(name, score);
            try { Thread.sleep(500); } catch (Exception e) {}
            SwingUtilities.invokeLater(() -> {
                LeaderboardView lbView = new LeaderboardView(() -> cardLayout.show(mainPanel, "MENU"));
                mainPanel.add(lbView, "RANK");
                cardLayout.show(mainPanel, "RANK");
                this.pack();
            });
        }).start();
    }
}