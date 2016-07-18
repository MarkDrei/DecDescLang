package net.dreiucker.decdesclanguage.tracebility.views;

import org.eclipse.emf.common.util.URI;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

import net.dreiucker.decdesclanguage.reqif.ui.ReqifUiHelper;
import net.dreiucker.decdesclanguage.tracebility.data.BodyDataProvider;
import net.dreiucker.ui.CustomDdlActivator;

public class MatrixEditorOpener implements MouseListener {

	private static final boolean DEBUG = false;
	private BodyDataProvider dataProvider;
	private NatTable table;
	
	int mouseDownX = -1;
	int mouseDownY = -1;

	public MatrixEditorOpener(NatTable natTable, BodyDataProvider dataProvider) {
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
				String requirement = table.getCellByPosition(column, row).getDataValue().toString();
				
				if (DEBUG) {
					System.out.println("Navigage to requirement " + requirement);
				}
				
				java.net.URI uri = dataProvider.getRequirementsUri(requirement);
				if (uri != null) {
					new ReqifUiHelper().openReqifEditor(uri, requirement);
				}
			}
			else if (row > 0 && column == 0) {
				// a decision reference
				String decision = table.getCellByPosition(column, row).getDataValue().toString();
				
				if (DEBUG) {
					System.out.println("Navigage to decision " + decision);
				}
				
				URI uri = dataProvider.getDecisionUri(decision);
				if (uri != null) {
					CustomDdlActivator.getInstance().openDdlEditor(uri);
				}
			}
			else if (row > 0 && column >= 0) {
				row = table.getRowIndexByPosition(row);
				column = table.getColumnIndexByPosition(column);
				// reference to a "reference of a requirement"
				URI uri = dataProvider.getURI(column, row);
				
				if (uri != null) {
					CustomDdlActivator.getInstance().openDdlEditor(uri);
				}
			}
		}
		
	}
}
