package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.LinkedHashMap;
import java.util.Map;

public class TemplatePanel {
    private JPanel root;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JPanel cardTplList;
    private JList lstTemplates;
    private JButton btnNewTemplate;
    private JPanel cardTplEdit;
    private JTextField txtTplSubject;
    private JTextArea txtTplBody;
    private JButton btnTplSave;
    private JButton btnTplDelete;
    private JButton btnTplPreview;

    private final Map<String, String[]> templates = new LinkedHashMap<>();
    private final DefaultListModel<String> listModel = new DefaultListModel<>();
    private String current;

    public TemplatePanel() {
        Theme.card(cardTplList);
        Theme.card(cardTplEdit);

        // {ten_nguoi_nhan} sẽ được thay bằng tên người nhận khi gửi
        templates.put("Chào mừng", new String[]{"Chào mừng bạn đến với MailSender!",
                "Xin chào {ten_nguoi_nhan}!\n\nCảm ơn bạn đã sử dụng MailSender."});
        templates.put("Khuyến mãi", new String[]{"Khuyến mãi đặc biệt dành cho bạn",
                "Chào {ten_nguoi_nhan},\n\nTháng này chúng tôi có nhiều ưu đãi hấp dẫn."});
        templates.put("Thông báo hệ thống", new String[]{"Thông báo hệ thống",
                "Kính gửi {ten_nguoi_nhan},\n\nHệ thống sẽ bảo trì vào cuối tuần này."});
        templates.put("Cập nhật sản phẩm", new String[]{"Cập nhật sản phẩm mới",
                "Xin chào {ten_nguoi_nhan},\n\nChúng tôi vừa phát hành phiên bản mới."});
        for (String name : templates.keySet()) {
            listModel.addElement(name);
        }
        lstTemplates.setModel(listModel);
        lstTemplates.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        lstTemplates.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                loadSelected();
            }
        });

        Theme.primary(btnTplSave);
        Theme.danger(btnTplDelete);
        Theme.secondary(btnTplPreview);
        Theme.secondary(btnNewTemplate);

        btnNewTemplate.addActionListener(e -> createTemplate());
        btnTplSave.addActionListener(e -> saveCurrent());
        btnTplDelete.addActionListener(e -> deleteCurrent());
        btnTplPreview.addActionListener(e -> preview());

        lstTemplates.setSelectedIndex(0);
    }

    private void loadSelected() {
        String name = (String) lstTemplates.getSelectedValue();
        current = name;
        if (name == null) {
            txtTplSubject.setText("");
            txtTplBody.setText("");
            return;
        }
        String[] t = templates.get(name);
        txtTplSubject.setText(t[0]);
        txtTplBody.setText(t[1]);
        txtTplBody.setCaretPosition(0);
    }

    private void createTemplate() {
        String name = JOptionPane.showInputDialog(root, "Tên mẫu mới:");
        if (name == null || name.trim().isEmpty()) {
            return;
        }
        name = name.trim();
        if (templates.containsKey(name)) {
            JOptionPane.showMessageDialog(root, "Tên mẫu đã tồn tại.");
            return;
        }
        templates.put(name, new String[]{"", "Xin chào {ten_nguoi_nhan},\n\n"});
        listModel.addElement(name);
        lstTemplates.setSelectedValue(name, true);
    }

    private void saveCurrent() {
        if (current == null) {
            return;
        }
        templates.put(current, new String[]{txtTplSubject.getText(), txtTplBody.getText()});
        JOptionPane.showMessageDialog(root, "Đã lưu mẫu \"" + current + "\".");
    }

    private void deleteCurrent() {
        if (current == null) {
            return;
        }
        int ok = JOptionPane.showConfirmDialog(root, "Xóa mẫu \"" + current + "\"?", "Xác nhận",
                JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            templates.remove(current);
            listModel.removeElement(current);
            if (!listModel.isEmpty()) {
                lstTemplates.setSelectedIndex(0);
            } else {
                loadSelected();
            }
        }
    }

    private void preview() {
        String body = applyPlaceholders(txtTplBody.getText(), "Nguyễn Văn A");
        JTextArea area = new JTextArea(body, 10, 40);
        area.setEditable(false);
        area.setLineWrap(true);
        area.setWrapStyleWord(true);
        JOptionPane.showMessageDialog(root, new JScrollPane(area), txtTplSubject.getText(),
                JOptionPane.PLAIN_MESSAGE);
    }

    /** Thay {ten_nguoi_nhan} bằng tên thật. Dùng khi gửi mail theo mẫu. */
    public static String applyPlaceholders(String text, String recipientName) {
        return text.replace("{ten_nguoi_nhan}", recipientName);
    }

    public String[] getTemplate(String name) {
        return templates.get(name);
    }

    public JPanel getRoot() {
        return root;
    }
}
