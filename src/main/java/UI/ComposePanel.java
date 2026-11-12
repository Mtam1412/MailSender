package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.text.StyledEditorKit;
import java.io.File;

public class ComposePanel {
    private JPanel root;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JPanel cardCompose;
    private JTextField txtTo;
    private JButton btnContacts;
    private JTextField txtCc;
    private JTextField txtBcc;
    private JTextField txtSubject;
    private JButton btnBold;
    private JButton btnItalic;
    private JButton btnUnderline;
    private JButton btnLink;
    private JButton btnImage;
    private JTextPane txtBody;
    private JPanel cardOptions;
    private JCheckBox chkMultiple;
    private JCheckBox chkSaveHistory;
    private JCheckBox chkUseTemplate;
    private JComboBox cboTemplate;
    private JPanel cardAttach;
    private JButton btnAttach;
    private JLabel lblAttachInfo;
    private JButton btnClear;
    private JButton btnSend;

    private final java.util.List<File> attachments = new java.util.ArrayList<>();

    public ComposePanel() {
        Theme.card(cardCompose);
        Theme.card(cardOptions);
        Theme.card(cardAttach);

        Theme.placeholder(txtTo, "Nhập địa chỉ email, cách nhau bằng dấu chấm phẩy (;)");
        Theme.placeholder(txtCc, "Nhập địa chỉ CC (tùy chọn)");
        Theme.placeholder(txtBcc, "Nhập địa chỉ BCC (tùy chọn)");
        Theme.placeholder(txtSubject, "Nhập tiêu đề email");

        Theme.primary(btnSend);
        Theme.secondary(btnClear);
        Theme.secondary(btnContacts);
        Theme.secondary(btnAttach);
        for (AbstractButton b : new AbstractButton[]{btnBold, btnItalic, btnUnderline, btnLink, btnImage}) {
            Theme.tool(b);
            b.setFocusable(false); // giữ con trỏ trong khung soạn thảo khi bấm nút
        }

        // Định dạng chữ có sẵn của Swing (đậm / nghiêng / gạch chân)
        btnBold.addActionListener(new StyledEditorKit.BoldAction());
        btnItalic.addActionListener(new StyledEditorKit.ItalicAction());
        btnUnderline.addActionListener(new StyledEditorKit.UnderlineAction());
        btnLink.addActionListener(e -> todo("Chèn liên kết"));
        btnImage.addActionListener(e -> todo("Chèn ảnh"));
        btnContacts.addActionListener(e -> todo("Chọn từ danh bạ / nhập file CSV, TXT (người B)"));

        cboTemplate.setEnabled(false);
        chkUseTemplate.addActionListener(e -> cboTemplate.setEnabled(chkUseTemplate.isSelected()));

        btnAttach.addActionListener(e -> chooseFiles());
        btnClear.addActionListener(e -> clearAll());
        btnSend.addActionListener(e -> send());
    }

    private void todo(String what) {
        JOptionPane.showMessageDialog(root, "Chức năng \"" + what + "\" sẽ được bổ sung.");
    }

    private void chooseFiles() {
        JFileChooser fc = new JFileChooser();
        fc.setMultiSelectionEnabled(true);
        if (fc.showOpenDialog(root) != JFileChooser.APPROVE_OPTION) {
            return;
        }
        long total = 0;
        for (File f : fc.getSelectedFiles()) {
            total += f.length();
        }
        if (total > 10L * 1024 * 1024) {
            JOptionPane.showMessageDialog(root, "Tổng dung lượng vượt quá 10MB.", "Tệp quá lớn",
                    JOptionPane.WARNING_MESSAGE);
            return;
        }
        attachments.clear();
        attachments.addAll(java.util.Arrays.asList(fc.getSelectedFiles()));
        lblAttachInfo.setText(attachments.size() + " tệp đã chọn (" + (total / 1024) + " KB)");
    }

    private void clearAll() {
        txtTo.setText("");
        txtCc.setText("");
        txtBcc.setText("");
        txtSubject.setText("");
        txtBody.setText("");
        attachments.clear();
        lblAttachInfo.setText("Chưa có tệp nào (tối đa 10MB)");
    }

    private void send() {
        String to = txtTo.getText().trim();
        if (to.isEmpty()) {
            warn("Vui lòng nhập ít nhất một người nhận.");
            txtTo.requestFocus();
            return;
        }
        for (String addr : to.split("[;,]")) {
            if (!addr.trim().matches("[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+")) {
                warn("Địa chỉ email không hợp lệ: " + addr.trim());
                txtTo.requestFocus();
                return;
            }
        }
        if (txtSubject.getText().trim().isEmpty()) {
            warn("Vui lòng nhập tiêu đề.");
            txtSubject.requestFocus();
            return;
        }
        // TODO: gọi code gửi mail SMTP của người B tại đây (dùng getTo(), getSubject(), getBody()...)
        JOptionPane.showMessageDialog(root, "Giao diện demo: đã kiểm tra hợp lệ.\nTại đây sẽ gọi code gửi mail của người B.");
    }

    private void warn(String msg) {
        JOptionPane.showMessageDialog(root, msg, "Thiếu thông tin", JOptionPane.WARNING_MESSAGE);
    }

    // ---- Các hàm để người B, C lấy dữ liệu từ form ----
    public String getTo() { return txtTo.getText().trim(); }
    public String getCc() { return txtCc.getText().trim(); }
    public String getBcc() { return txtBcc.getText().trim(); }
    public String getSubject() { return txtSubject.getText().trim(); }
    public String getBody() { return txtBody.getText(); }
    public java.util.List<File> getAttachments() { return attachments; }
    public boolean isSaveHistory() { return chkSaveHistory.isSelected(); }
    public JButton getSendButton() { return btnSend; }

    public JPanel getRoot() {
        return root;
    }
}
