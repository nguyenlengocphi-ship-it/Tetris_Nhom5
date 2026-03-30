package view;

import java.awt.*;
import java.util.function.Consumer;
import javax.swing.*;

public class MenuView extends JPanel {
    private JTextField nameField;

    public MenuView(Consumer<String> onStart) {
        this.setLayout(new GridBagLayout());
        this.setBackground(new Color(33, 37, 41)); 
        this.setPreferredSize(new Dimension(300, 600));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);
        gbc.gridx = 0;

        JLabel title = new JLabel();
        String colorfulTitle = "<html>" +
                "<font color='#FF1744'>T</font>" + 
                "<font color='#FFEA00'>E</font>" + 
                "<font color='#00E676'>T</font>" + 
                "<font color='#2979FF'>R</font>" + 
                "<font color='#D500F9'>I</font>" + 
                "<font color='#FF9100'>S</font>" + 
                " " + 
                "<font color='#00CED1'>G</font>" + 
                "<font color='#FF69B4'>A</font>" + 
                "<font color='#F0E68C'>M</font>" + 
                "<font color='#E6E6FA'>E</font>" + 
                "</html>";
        title.setText(colorfulTitle);
        title.setFont(new Font("Segoe UI", Font.BOLD, 40)); 
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 50, 0); 
        this.add(title, gbc);

        JLabel label = new JLabel("Nhập tên người chơi:");
        label.setForeground(new Color(223, 230, 233));
        label.setFont(new Font("Segoe UI", Font.BOLD, 15)); 
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        this.add(label, gbc);

        nameField = new JTextField(15);
        nameField.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        nameField.setHorizontalAlignment(JTextField.CENTER);
        nameField.setBackground(Color.WHITE);
        nameField.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(99, 110, 114), 1),
            BorderFactory.createEmptyBorder(8, 10, 8, 10)
        ));
        gbc.gridy = 2;
        this.add(nameField, gbc);

        JButton startButton = new JButton("BẮT ĐẦU CHƠI");
        startButton.setFont(new Font("Segoe UI", Font.BOLD, 16));
        startButton.setBackground(new Color(46, 204, 113)); 
        startButton.setForeground(Color.WHITE);
        startButton.setFocusPainted(false);
        startButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        startButton.setPreferredSize(new Dimension(200, 50)); 
        
        startButton.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(39, 174, 96));
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                startButton.setBackground(new Color(46, 204, 113));
            }
        });

        startButton.addActionListener(e -> {
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Vui lòng nhập tên!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            } else {
                onStart.accept(name);
            }
        });
        
        gbc.gridy = 3;
        gbc.insets = new Insets(35, 0, 10, 0);
        this.add(startButton, gbc);

        JLabel info = new JLabel("Nhóm 5 - Lập trình Java");
        info.setForeground(new Color(149, 165, 166));
        info.setFont(new Font("Segoe UI", Font.ITALIC, 14));
        gbc.gridy = 4;
        gbc.insets = new Insets(80, 0, 0, 0); 
        this.add(info, gbc);
    }
}