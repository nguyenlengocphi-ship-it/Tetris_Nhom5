package view;

import controller.GameController;
import java.awt.*;
import java.util.Vector;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import model.Shape;
import model.DatabaseManager; 
import utils.SoundManager; 

public class GamePanel extends JPanel {
    private final int TILE = 30;
    private GameController controller;
    private Timer timer;
    private String playerName;
    private JButton btnPause; 
    private JLabel lblScore;
    
    private boolean isGameOverProcessed = false;
    private SoundManager sound = new SoundManager();

    public GamePanel(GameController controller, String playerName) {
        this.controller = controller;
        this.playerName = playerName;
        sound.playBGM("nhactrochoi.wav");

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(33, 37, 41));
        this.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.setOpaque(false);
        infoPanel.setPreferredSize(new Dimension(300, 70));

        JLabel lblName = new JLabel("Player: " + playerName);
        lblName.setForeground(Color.WHITE);
        lblName.setFont(new Font("Segoe UI", Font.BOLD, 16));
        
        lblScore = new JLabel("Score: 0");
        lblScore.setForeground(new Color(255, 215, 0));
        lblScore.setFont(new Font("Segoe UI", Font.BOLD, 22));

        infoPanel.add(lblName);
        infoPanel.add(lblScore);
        this.add(infoPanel, BorderLayout.NORTH);
        JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.setOpaque(false);
        JPanel canvas = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                renderBoard(g);
            }
            @Override
            public Dimension getPreferredSize() { return new Dimension(300, 600); }
        };
        canvas.setBackground(new Color(45, 52, 54));
        canvas.setBorder(BorderFactory.createLineBorder(new Color(100, 100, 100), 2));
        centerWrapper.add(canvas);
        this.add(centerWrapper, BorderLayout.CENTER);
        JPanel controlPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 0, 10));
        controlPanel.setOpaque(false);
        btnPause = new JButton("Tạm dừng");
        styleButton(btnPause, new Color(241, 196, 15));
        btnPause.setPreferredSize(new Dimension(150, 40)); 
        btnPause.addActionListener(e -> {
            controller.togglePause();
            if (controller.isPaused()) {
                sound.stopBGM();
            } else {
                sound.resumeBGM();
            }
            
            btnPause.setText(controller.isPaused() ? "Tiếp tục" : "Tạm dừng");
            this.requestFocusInWindow();
        });
        controlPanel.add(btnPause);
        this.add(controlPanel, BorderLayout.SOUTH);
        timer = new Timer(500, e -> {
            if (controller.isGameOver() && !isGameOverProcessed) {
                timer.stop(); 
                handleGameOver(); 
            } else if (!controller.isPaused() && !controller.isGameOver()) {
                int scoreBefore = controller.getScore();
                controller.update();
                int scoreAfter = controller.getScore();
                if (scoreAfter > scoreBefore) {
                    SoundManager.playOneShot("nhacdone.wav");
                }
                lblScore.setText("Score: " + scoreAfter);
            }
            repaint();
        });
        timer.start();
        
        SwingUtilities.invokeLater(() -> this.requestFocusInWindow());
    }

    private void handleGameOver() {
        isGameOverProcessed = true;
        sound.stopBGM(); 

        SwingUtilities.invokeLater(() -> {
            JOptionPane.showMessageDialog(this, "Ván game đã kết thúc!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            
            new Thread(() -> {
                try {
                    DatabaseManager.saveScore(playerName, controller.getScore());
                    Vector<Vector<Object>> data = DatabaseManager.getTopScores();
                    SwingUtilities.invokeLater(() -> showLeaderboard(data));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }).start();
        });
    }

    private void showLeaderboard(Vector<Vector<Object>> data) {
        Vector<String> columns = new Vector<>();
        columns.add("Hạng"); columns.add("Người chơi"); columns.add("Điểm");

        JTable table = new JTable(data, columns);
        table.setRowHeight(30);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }

        JScrollPane scroll = new JScrollPane(table);
        scroll.setPreferredSize(new Dimension(380, 250));

        JPanel panel = new JPanel(new BorderLayout(5, 15));
        JLabel title = new JLabel("BẢNG XẾP HẠNG HỆ THỐNG", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 20));
        title.setForeground(new Color(46, 204, 113));
        
        panel.add(title, BorderLayout.NORTH);
        panel.add(scroll, BorderLayout.CENTER);

        JOptionPane.showMessageDialog(this, panel, "Kết quả Nhóm 5", JOptionPane.PLAIN_MESSAGE);
    }

    private void renderBoard(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setColor(new Color(255, 255, 255, 15));
        for (int i = 0; i <= 300; i += TILE) g2.drawLine(i, 0, i, 600);
        for (int i = 0; i <= 600; i += TILE) g2.drawLine(0, i, 300, i);
        
        Color[][] board = controller.getBoard();
        if (board != null) {
            for (int r = 0; r < 20; r++) {
                for (int c = 0; c < 10; c++) {
                    if (board[r][c] != null) drawSq(g2, c, r, board[r][c]);
                }
            }
        }
        
        Shape s = controller.getCurrentShape();
        if (s != null) {
            int[][] co = s.getCoords();
            for (int r = 0; r < co.length; r++) {
                for (int c = 0; c < co[r].length; c++) {
                    if (co[r][c] != 0) drawSq(g2, controller.getCurX() + c, controller.getCurY() + r, s.getColor());
                }
            }
        }
        
        if (controller.isPaused()) {
            g2.setColor(new Color(0, 0, 0, 180));
            g2.fillRect(0, 0, 300, 600);
            g2.setColor(Color.WHITE);
            g2.setFont(new Font("Segoe UI", Font.BOLD, 35));
            FontMetrics fm = g2.getFontMetrics();
            int x = (300 - fm.stringWidth("PAUSED")) / 2;
            g2.drawString("PAUSED", x, 300);
        }
    }

    private void drawSq(Graphics g, int x, int y, Color c) {
        g.setColor(c);
        g.fill3DRect(x * TILE, y * TILE, TILE, TILE, true);
        g.setColor(c.darker());
        g.drawRect(x * TILE, y * TILE, TILE, TILE);
    }

    private void styleButton(JButton btn, Color bg) {
        btn.setBackground(bg);
        btn.setForeground(Color.BLACK);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setBorder(BorderFactory.createLineBorder(bg.darker(), 1));
    }
}