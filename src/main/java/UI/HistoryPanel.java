package UI;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

public class HistoryPanel {
    private JPanel root;
    private JLabel lblTitle;
    private JLabel lblSubtitle;
    private JPanel cardFilter;
    private JTextField txtSearch;
    private JComboBox cboStatusFilter;
    private JButton btnRefresh;
    private JPanel cardHistoryTable;
    private JTable tblHistory;
    private JButton btnDetail;

    private final DefaultTableModel model;
    private final TableRowSorter<DefaultTableModel> sorter;

    public HistoryPanel() {
        Theme.card(cardFilter);
        Theme.card(cardHistoryTable);
        Theme.placeholder(txtSearch, "Tìm kiếm theo người nhận, tiêu đề...");

        model = Theme.model(new String[]{"Thời gian", "Người nhận", "Tiêu đề", "Trạng thái"}, new Object[][]{});
        tblHistory.setModel(model);
        Theme.table(tblHistory);
        sorter = new TableRowSorter<>(model);
        tblHistory.setRowSorter(sorter);
        tblHistory.getColumnModel().getColumn(3).setCellRenderer(new Theme.StatusRenderer());

        Theme.secondary(btnRefresh);
        Theme.secondary(btnDetail);

        txtSearch.getDocument().addDocumentListener(new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { applyFilter(); }
            public void removeUpdate(DocumentEvent e) { applyFilter(); }
            public void changedUpdate(DocumentEvent e) { applyFilter(); }
        });
        cboStatusFilter.addActionListener(e -> applyFilter());
        btnRefresh.addActionListener(e -> {
            txtSearch.setText("");
            cboStatusFilter.setSelectedIndex(0);
            applyFilter();
        });
        btnDetail.addActionListener(e -> showDetail());
    }

    private void applyFilter() {
        List<RowFilter<DefaultTableModel, Integer>> filters = new ArrayList<>();
        String q = txtSearch.getText().trim();
        if (!q.isEmpty()) {
            filters.add(RowFilter.regexFilter("(?i)" + Pattern.quote(q), 1, 2)); // cột Người nhận, Tiêu đề
        }
        if (cboStatusFilter.getSelectedIndex() > 0) {
            String status = (String) cboStatusFilter.getSelectedItem();
            filters.add(RowFilter.regexFilter("^" + Pattern.quote(status) + "$", 3)); // cột Trạng thái
        }
        sorter.setRowFilter(filters.isEmpty() ? null : RowFilter.andFilter(filters));
    }

    private void showDetail() {
        int viewRow = tblHistory.getSelectedRow();
        if (viewRow < 0) {
            JOptionPane.showMessageDialog(root, "Hãy chọn một dòng trong bảng trước.");
            return;
        }
        int r = tblHistory.convertRowIndexToModel(viewRow);
        String msg = "Thời gian: " + model.getValueAt(r, 0)
                + "\nNgười nhận: " + model.getValueAt(r, 1)
                + "\nTiêu đề: " + model.getValueAt(r, 2)
                + "\nTrạng thái: " + model.getValueAt(r, 3);
        JOptionPane.showMessageDialog(root, msg, "Chi tiết", JOptionPane.INFORMATION_MESSAGE);
    }

    /** Người C gọi hàm này mỗi khi gửi xong một email để thêm vào bảng. */
    public void addRecord(String time, String to, String subject, String status) {
        model.insertRow(0, new Object[]{time, to, subject, status});
    }

    public JPanel getRoot() {
        return root;
    }
}
