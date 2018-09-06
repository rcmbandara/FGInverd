package general;

import java.awt.Color;
import java.awt.Component;

import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;

public class ColorColumnRenderer extends DefaultTableCellRenderer {
	Color bkgndColor, fgndColor;
	     
	   public ColorColumnRenderer(Color bkgnd, Color foregnd) {
	      super(); 
	      bkgndColor = bkgnd;
	      fgndColor = foregnd;
	   }
	     
	   public Component getTableCellRendererComponent
	        (JTable table, Object value, boolean isSelected,
	         boolean hasFocus, int row, int column) 
	   {
	      Component cell = super.getTableCellRendererComponent
	         (table, value, isSelected, hasFocus, row, column);
	  
	      cell.setBackground( bkgndColor );
	      cell.setForeground( fgndColor );
	      
	      return cell;
	   }
}
