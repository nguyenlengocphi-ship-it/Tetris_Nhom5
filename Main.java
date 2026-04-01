import javax.swing.SwingUtilities;
import view.GameFrame;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                GameFrame frame = new GameFrame();

                frame.setVisible(true);
                
                System.out.println(">>> Game Xếp gạch đã sẵn sàng. Chơi thôi!");
            } catch (Exception e) {
                System.err.println("Lỗi khởi động Game: " + e.getMessage());
                e.printStackTrace();
            }
        });
    }
}

