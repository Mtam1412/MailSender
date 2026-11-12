package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.swing.table.DefaultTableModel;

public class SchedulePanel {
    private JPanel root;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JPanel cardScheduleForm;
    private JComboBox cboEmail;
    private JButton btnSchedule;
    private JSpinner spnDate;
    private JSpinner spnTime;
    private JComboBox cboPriority;
    private JPanel cardScheduleTable;
    private JTable tblSchedule;
    private JButton btnCancelSchedule;

    private final DefaultTableModel model;

    public SchedulePanel() {
        Theme.card(cardScheduleForm);
        Theme.card(cardScheduleTable);

        spnDate.setModel(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.DAY_OF_MONTH));
        spnDate.setEditor(new JSpinner.DateEditor(spnDate, "dd/MM/yyyy"));
        spnTime.setModel(new SpinnerDateModel(new Date(), null, null, java.util.Calendar.MINUTE));
        spnTime.setEditor(new JSpinner.DateEditor(spnTime, "HH:mm"));

        model = Theme.model(new String[]{"Thời gian", "Tiêu đề", "Người nhận", "Trạng thái"}, new Object[][]{
                {"28/11/2025 15:30", "Khuyến mãi tháng 11", "nguyenvana@gmail.com", "Đang chờ"}
        });
        tblSchedule.setModel(model);
        Theme.table(tblSchedule);
        tblSchedule.getColumnModel().getColumn(3).setCellRenderer(new Theme.StatusRenderer());

        Theme.primary(btnSchedule);
        Theme.danger(btnCancelSchedule);

        btnSchedule.addActionListener(e -> addSchedule());
        btnCancelSchedule.addActionListener(e -> cancelSelected());
    }

    private void addSchedule() {
        String when = new SimpleDateFormat("dd/MM/yyyy").format((Date) spnDate.getValue())
                + " " + new SimpleDateFormat("HH:mm").format((Date) spnTime.getValue());
        // TODO: đăng ký tác vụ gửi thật (java.util.Timer / ScheduledExecutorService) tại đây
        model.addRow(new Object[]{when, String.valueOf(cboEmail.getSelectedItem()), "(theo email đã chọn)", "Đang chờ"});
    }

    private void cancelSelected() {
        int row = tblSchedule.getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(root, "Hãy chọn một lịch trong bảng trước.");
            return;
        }
        int ok = JOptionPane.showConfirmDialog(root, "Hủy lịch gửi này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (ok == JOptionPane.YES_OPTION) {
            model.removeRow(tblSchedule.convertRowIndexToModel(row));
        }
    }

    public JPanel getRoot() {
        return root;
    }
}
