package net.dreiucker.decdesclanguage.tracebility.views;

import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.layer.cell.ILayerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import net.dreiucker.decdesclanguage.tracebility.data.BodyDataProvider;

public class EditorOpener implements MouseListener {

	private BodyDataProvider dataProvider;
	private NatTable table;
	
	int mouseDownX = -1;
	int mouseDownY = -1;

	public EditorOpener(NatTable natTable, BodyDataProvider dataProvider) {
		this.table = natTable;
		this.dataProvider = dataProvider;
		natTable.addMouseListener(this);
	}

	@Override
	public void mouseDoubleClick(MouseEvent e) {
	}

	@Override
	public void mouseDown(MouseEvent e) {
		mouseDownX = e.x;
		mouseDownY = e.y;
	}

	@Override
	public void mouseUp(MouseEvent e) {
		// only handle if mouse did not move
		if ( (e.stateMask & SWT.CONTROL) != 0 && e.x == mouseDownX && e.y == mouseDownY) {
			int row = table.getRowPositionByY(e.y);
			int column = table.getColumnPositionByX(e.x);
			
			if (row == 0 && column > 0) {
				Object requirement = table.getCellByPosition(column, row).getDataValue().toString();
				System.out.println("Navigage to requirement " + requirement);
			}
			else if (row > 0 && column == 0) {
				Object decision = table.getCellByPosition(column, row).getDataValue().toString();
				System.out.println("Navigage to decision " + decision);
			}
		}
		
	}
}
