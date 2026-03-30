package controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class DBConnection {
    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=tetris_db;encrypt=true;trustServerCertificate=true;user=sa;password=123456;";

    public static Connection getConnection() throws SQLException {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return DriverManager.getConnection(URL);
    }

    public static void saveScore(String name, int score) {
        String sql = "INSERT INTO highscores (player_name, score, play_date) VALUES (?, ?, GETDATE())";
        try (Connection conn = getConnection(); 
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            
            pstmt.setString(1, name);
            pstmt.setInt(2, score);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Lỗi lưu điểm: " + e.getMessage());
        }
    }

    public static List<Object[]> getHighScores() {
        List<Object[]> data = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        String sql = "SELECT TOP 10 player_name, score, play_date FROM highscores ORDER BY score DESC, play_date DESC";
        
        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {
            
            int rank = 1;
            while (rs.next()) {
                String formattedDate = "";
                if (rs.getTimestamp("play_date") != null) {
                    formattedDate = sdf.format(rs.getTimestamp("play_date"));
                }
                
                data.add(new Object[]{
                    rank++, 
                    rs.getString("player_name"), 
                    rs.getInt("score"),
                    formattedDate 
                });
            }
        } catch (SQLException e) {
            System.err.println("Lỗi lấy bảng xếp hạng: " + e.getMessage());
        }
        return data;
    }
}