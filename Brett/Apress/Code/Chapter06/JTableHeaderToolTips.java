import java.awt.event.MouseEvent;
import javax.swing.table.*;

public class JTableHeaderToolTips extends JTableHeader {

	protected String[] toolTips;

	public JTableHeaderToolTips(TableColumnModel tcm) {
		super(tcm);
	}

	public void setToolTips(String[] tips) {
		toolTips = tips;
	}

	public String getToolTipText(MouseEvent event) {
		String tip = super.getToolTipText(event);
		int column = columnAtPoint(event.getPoint());
		if ((toolTips != null) && (column < toolTips.length) &&
				(toolTips[column] != null)) {
			tip = toolTips[column];
		}
		return tip;
	}

}
