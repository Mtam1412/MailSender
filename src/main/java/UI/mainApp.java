package UI;

import javax.swing.*;
import java.awt.*;

public class mainApp {
    public static void main(String[] args) {
        // Dùng FlatLaf nếu đã thêm vào pom.xml (đẹp hơn nhiều). Không có thì dùng giao diện mặc định.
        UIManager.put("defaultFont", new Font("Segoe UI", Font.PLAIN, 14));
        try {
            Class.forName("com.formdev.flatlaf.FlatLightLaf").getMethod("setup").invoke(null);
            UIManager.put("Button.arc", 12);
            UIManager.put("Component.arc", 10);
            UIManager.put("TextComponent.arc", 10);
        } catch (Exception ex) {
            System.out.println("Chưa thêm FlatLaf vào pom.xml -> dùng giao diện Swing mặc định.");
        }

        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("MailSender - Ứng dụng gửi email tự động");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setContentPane(new mainForm().getRoot());
            frame.setSize(1180, 760);
            frame.setMinimumSize(new Dimension(1000, 680));
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
