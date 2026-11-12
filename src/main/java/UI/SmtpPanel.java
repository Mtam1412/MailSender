package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class SmtpPanel {
    private JPanel root;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JPanel cardSmtp;
    private JTextField txtHost;
    private JSpinner spnPort;
    private JTextField txtUser;
    private JPasswordField txtPass;
    private JCheckBox chkSsl;
    private JButton btnTest;
    private JButton btnSaveConfig;
    private JPanel cardResult;
    private JLabel lblResultIcon;
    private JLabel lblResultTitle;
    private JLabel lblResultText;

    public SmtpPanel() {
        Theme.card(cardSmtp);
        spnPort.setModel(new SpinnerNumberModel(587, 1, 65535, 1));
        spnPort.setEditor(new JSpinner.NumberEditor(spnPort, "#")); // hiện 587 chứ không phải 587,0
        Theme.placeholder(txtUser, "your-email@gmail.com");

        Theme.primary(btnTest);
        Theme.primary(btnSaveConfig);

        btnTest.addActionListener(e -> testConnection());
        btnSaveConfig.addActionListener(e -> JOptionPane.showMessageDialog(root,
                "Đã lưu cấu hình (demo).\nTODO: ghi vào file config.properties."));
        setResult(0, "Chưa kiểm tra", "Bấm \"Kiểm tra kết nối\" để thử.");
    }

    private void testConnection() {
        // TODO: thay đoạn demo này bằng code kiểm tra kết nối SMTP thật của người B
        boolean ok = !txtHost.getText().trim().isEmpty()
                && !txtUser.getText().trim().isEmpty()
                && txtPass.getPassword().length > 0;
        if (ok) {
            setResult(1, "Kết nối thành công!", "Máy chủ SMTP đã sẵn sàng hoạt động.");
        } else {
            setResult(2, "Kết nối thất bại", "Vui lòng nhập đủ máy chủ, tài khoản và mật khẩu.");
        }
    }

    /** state: 0 = chưa kiểm tra, 1 = thành công, 2 = thất bại */
    private void setResult(int state, String title, String text) {
        Color fg = state == 1 ? Theme.OK : state == 2 ? Theme.ERR : Theme.MUTED;
        Color bg = state == 1 ? new Color(232, 248, 238) : state == 2 ? new Color(253, 232, 232) : Theme.BG;
        cardResult.setBackground(bg);
        lblResultIcon.setText(state == 1 ? "✓" : state == 2 ? "✕" : "…");
        lblResultIcon.setForeground(fg);
        lblResultTitle.setText(title);
        lblResultTitle.setForeground(fg);
        lblResultText.setText(text);
    }

    // ---- Các hàm để người B lấy cấu hình ----
    public String getHost() { return txtHost.getText().trim(); }
    public int getPort() { return (Integer) spnPort.getValue(); }
    public String getUser() { return txtUser.getText().trim(); }
    public char[] getPassword() { return txtPass.getPassword(); }
    public boolean isSsl() { return chkSsl.isSelected(); }

    public JPanel getRoot() {
        return root;
    }
}
