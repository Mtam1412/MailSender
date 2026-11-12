package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.function.Consumer;

public class HomePanel {
    private JPanel root;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JLabel lblStatus;
    private JPanel pnlSent;
    private JLabel lblSentNum;
    private JLabel lblSentText;
    private JPanel pnlSuccess;
    private JLabel lblSuccessNum;
    private JLabel lblSuccessText;
    private JPanel pnlFail;
    private JLabel lblFailNum;
    private JLabel lblFailText;
    private JPanel pnlPending;
    private JLabel lblPendingNum;
    private JLabel lblPendingText;
    private JPanel pnlRecent;
    private JLabel lblRecentTitle;
    private JButton btnViewAll;
    private JTable tblRecent;
    private JPanel pnlPromo;
    private JLabel lblPromoTitle;
    private JLabel lblPromoSub;

    public HomePanel(Consumer<String> navigate) {
        lblStatus.setBorder(new EmptyBorder(5, 12, 5, 12));
        Theme.card(pnlRecent);

        Object[][] rows = {};
        tblRecent.setModel(Theme.model(new String[]{"Người nhận", "Tiêu đề", "Thời gian", "Trạng thái"}, rows));
        Theme.table(tblRecent);
        tblRecent.getColumnModel().getColumn(3).setCellRenderer(new Theme.StatusRenderer());

        btnViewAll.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        btnViewAll.addActionListener(e -> navigate.accept("history"));
    }

    /** Cập nhật 4 con số thống kê (người C hoặc A gọi sau khi đọc log). */
    public void setStats(int sent, int success, int fail, int pending) {
        lblSentNum.setText(String.valueOf(sent));
        lblSuccessNum.setText(String.valueOf(success));
        lblFailNum.setText(String.valueOf(fail));
        lblPendingNum.setText(String.valueOf(pending));
    }

    public JPanel getRoot() {
        return root;
    }
}
