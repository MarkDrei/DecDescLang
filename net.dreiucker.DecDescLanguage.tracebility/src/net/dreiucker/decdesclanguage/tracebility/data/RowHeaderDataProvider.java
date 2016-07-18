package net.dreiucker.decdesclanguage.tracebility.data;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;

public class RowHeaderDataProvider implements IDataProvider {
	
	private BodyDataProvider bodyDataProvider;

	public RowHeaderDataProvider(BodyDataProvider bodyDataProvider) {
		this.bodyDataProvider = bodyDataProvider;
	}

	@Override
	public Object getDataValue(int columnIndex, int rowIndex) {
		return bodyDataProvider.decisionRowHeaders.get(rowIndex);
	}

	@Override
	public void setDataValue(int columnIndex, int rowIndex, Object newValue) {
		// not allowed
	}

	@Override
	public int getColumnCount() {
		return 1;
	}

	@Override
	public int getRowCount() {
		return bodyDataProvider.getRowCount();
	}

	
}
