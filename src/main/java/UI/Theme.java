package UI;

import javax.swing.*;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.text.JTextComponent;
import java.awt.*;

/**
 * Màu sắc + các hàm tạo kiểu dùng chung cho toàn bộ giao diện.
 * Muốn đổi màu cả ứng dụng thì chỉ cần sửa ở đây.
 */
public final class Theme {
    public static final Color NAVY = new Color(30, 58, 110);
    public static final Color BG = new Color(243, 246, 252);
    public static final Color CARD = Color.WHITE;
    public static final Color PRIMARY = new Color(37, 99, 235);
    public static final Color BORDER = new Color(213, 220, 235);
    public static final Color TEXT = new Color(30, 41, 59);
    public static final Color MUTED = new Color(100, 116, 139);
    public static final Color OK = new Color(22, 163, 74);
    public static final Color ERR = new Color(220, 38, 38);
    public static final Color WARN = new Color(217, 119, 6);

    private Theme() {
    }

    /** Thẻ trắng viền nhạt bo góc. */
    public static void card(JComponent c) {
        c.setOpaque(true);
        c.setBackground(CARD);
        c.setBorder(new LineBorder(BORDER, 1, true));
    }

    private static void base(AbstractButton b, Color bg, Color fg, Insets margin) {
        b.setBackground(bg);
        b.setForeground(fg);
        b.setFocusPainted(false);
        b.setMargin(margin);
        b.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        b.putClientProperty("JButton.buttonType", "roundRect");
    }

    /** Nút chính (xanh dương, chữ trắng). */
    public static void primary(AbstractButton b) {
        base(b, PRIMARY, Color.WHITE, new Insets(9, 18, 9, 18));
    }

    /** Nút phụ (nền trắng). */
    public static void secondary(AbstractButton b) {
        base(b, Color.WHITE, TEXT, new Insets(9, 16, 9, 16));
    }

    /** Nút nhỏ trên thanh định dạng. */
    public static void tool(AbstractButton b) {
        base(b, Color.WHITE, TEXT, new Insets(5, 10, 5, 10));
    }

    /** Nút nguy hiểm (Xóa, Hủy). */
    public static void danger(AbstractButton b) {
        base(b, new Color(254, 226, 226), ERR, new Insets(9, 16, 9, 16));
    }

    /** Chữ mờ gợi ý trong ô nhập (chỉ hiện khi dùng FlatLaf). */
    public static void placeholder(JTextComponent t, String text) {
        t.putClientProperty("JTextField.placeholderText", text);
    }

    /** Tạo bảng dữ liệu chỉ đọc. */
    public static DefaultTableModel model(String[] columns, Object[][] rows) {
        return new DefaultTableModel(rows, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
    }

    /** Làm đẹp JTable. Gọi SAU khi đã setModel. */
    public static void table(JTable t) {
        t.setRowHeight(32);
        t.setShowHorizontalLines(true);
        t.setShowVerticalLines(false);
        t.setGridColor(BORDER);
        t.setFillsViewportHeight(true);
        t.setSelectionBackground(new Color(219, 234, 254));
        t.setSelectionForeground(TEXT);
        JTableHeader h = t.getTableHeader();
        h.setReorderingAllowed(false);
        h.setFont(h.getFont().deriveFont(Font.BOLD));
        h.setBackground(BG);
        h.setForeground(TEXT);
        Container p = SwingUtilities.getAncestorOfClass(JScrollPane.class, t);
        if (p instanceof JScrollPane) {
            JScrollPane sp = (JScrollPane) p;
            sp.setBorder(BorderFactory.createEmptyBorder());
            sp.getViewport().setBackground(Color.WHITE);
        }
    }

    /** Tô màu cột "Trạng thái": xanh = thành công, đỏ = thất bại, cam = đang chờ. */
    public static class StatusRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean selected,
                                                       boolean focus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, selected, focus, row, column);
            String s = String.valueOf(value);
            if (s.contains("Thành công")) {
                c.setForeground(OK);
            } else if (s.contains("Thất bại")) {
                c.setForeground(ERR);
            } else if (s.contains("Đang chờ")) {
                c.setForeground(WARN);
            }
            c.setFont(c.getFont().deriveFont(Font.BOLD));
            return c;
        }
    }
}
