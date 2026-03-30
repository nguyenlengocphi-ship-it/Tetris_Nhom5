package view;

import controller.DBConnection;
import java.awt.*;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class LeaderboardView extends JPanel {
    public LeaderboardView(Runnable onBack) {
        this.setLayout(new BorderLayout(20, 20));
        this.setBackground(new Color(33, 37, 41)); 
        this.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel title = new JLabel("BẢNG XẾP HẠNG TOP 10", SwingConstants.CENTER);
        title.setFont(new Font("Segoe UI", Font.BOLD, 28));
        title.setForeground(new Color(255, 215, 0)); 
        this.add(title, BorderLayout.NORTH);

        String[] columns = {"Hạng", "Người chơi", "Điểm số", "Thời gian"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; 
            }
        };

        List<Object[]> highScores = DBConnection.getHighScores();
        for (Object[] row : highScores) {
            model.addRow(row);
        }

        JTable table = new JTable(model);
        styleTable(table); 

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.getViewport().setBackground(new Color(45, 52, 54));
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 215, 0), 1));
        this.add(scrollPane, BorderLayout.CENTER);

        JButton btnBack = new JButton("QUAY LẠI MENU");
        btnBack.setFont(new Font("Segoe UI", Font.BOLD, 16));
        btnBack.setBackground(new Color(231, 76, 60)); 
        btnBack.setForeground(Color.WHITE);
        btnBack.setFocusPainted(false);
        btnBack.addActionListener(e -> onBack.run());
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(btnBack);
        this.add(buttonPanel, BorderLayout.SOUTH);
    }

    private void styleTable(JTable table) {
        table.setRowHeight(35);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 16));
        table.getTableHeader().setBackground(new Color(52, 73, 94));
        table.getTableHeader().setForeground(Color.WHITE);
        table.setSelectionBackground(new Color(46, 204, 113));
        table.setShowGrid(false);
        table.setIntercellSpacing(new Dimension(0, 0));

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}