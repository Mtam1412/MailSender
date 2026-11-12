package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class mainForm {
    private JPanel root;
    private JPanel sidebarPanel;
    private JLabel lblLogo;
    private JLabel lblTagline;
    private JButton btnHome;
    private JButton btnCompose;
    private JButton btnSmtp;
    private JButton btnSchedule;
    private JButton btnTemplate;
    private JButton btnHistory;
    private JButton btnSettings;
    private JButton btnAbout;
    private JPanel contentPanel;

    private final CardLayout cards;
    private final JButton[] navButtons;
    private final String[] navCards = {"home", "compose", "smtp", "schedule", "template", "history"};

    public mainForm() {
        cards = (CardLayout) contentPanel.getLayout();
        navButtons = new JButton[]{btnHome, btnCompose, btnSmtp, btnSchedule, btnTemplate, btnHistory};

        // Thêm 6 trang vào contentPanel. Tên thẻ (card name) phải trùng với navCards ở trên.
        contentPanel.add(new HomePanel(this::navigateTo).getRoot(), "home");
        contentPanel.add(new ComposePanel().getRoot(), "compose");
        contentPanel.add(new SmtpPanel().getRoot(), "smtp");
        contentPanel.add(new SchedulePanel().getRoot(), "schedule");
        contentPanel.add(new TemplatePanel().getRoot(), "template");
        contentPanel.add(new HistoryPanel().getRoot(), "history");

        for (int i = 0; i < navButtons.length; i++) {
            final String card = navCards[i];
            styleNav(navButtons[i]);
            navButtons[i].addActionListener(e -> navigateTo(card));
        }
        styleNav(btnSettings);
        styleNav(btnAbout);
        btnSettings.addActionListener(e -> JOptionPane.showMessageDialog(root,
                "Trang Cài đặt sẽ được bổ sung sau.", "Cài đặt", JOptionPane.INFORMATION_MESSAGE));
        btnAbout.addActionListener(e -> JOptionPane.showMessageDialog(root,
                "MailSender - Ứng dụng gửi email tự động\nĐồ án Lập trình mạng (Java Swing + JavaMail)",
                "Giới thiệu", JOptionPane.INFORMATION_MESSAGE));

        navigateTo("home");
    }

    private void styleNav(JButton b) {
        b.setBorder(new EmptyBorder(10, 16, 10, 16));
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
    }

    /** Chuyển sang trang có tên thẻ `card` và tô sáng nút tương ứng trên sidebar. */
    public void navigateTo(String card) {
        cards.show(contentPanel, card);
        for (int i = 0; i < navButtons.length; i++) {
            boolean active = navCards[i].equals(card);
            navButtons[i].setContentAreaFilled(active);
            navButtons[i].setOpaque(active);
            navButtons[i].setBackground(Theme.PRIMARY);
        }
        sidebarPanel.repaint();
    }

    public JPanel getRoot() {
        return root;
    }
}
