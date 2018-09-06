package general;

import java.awt.Color;

public class MyCellRenderer extends javax.swing.table.DefaultTableCellRenderer {

	public java.awt.Component getTableCellRendererComponent(javax.swing.JTable table, java.lang.Object value,
			boolean isSelected, boolean hasFocus, int row, int column) {
		final java.awt.Component cellComponent = super.getTableCellRendererComponent(table, value, isSelected, hasFocus,
				row, column);

		Object val = table.getValueAt(row, 2);

		String sval = val.toString();
		sval = sval.replaceAll(":", "");
		int ival = Integer.parseInt(sval);
		if (ival == 0) {
			cellComponent.setForeground(Color.black);
			cellComponent.setBackground(Color.red);

		} else {
			cellComponent.setBackground(Color.white);
			cellComponent.setForeground(Color.black);
		}
		if (isSelected) {
			cellComponent.setForeground(table.getSelectionForeground());
			cellComponent.setBackground(table.getSelectionBackground());
		}

		return cellComponent;

	}

}
