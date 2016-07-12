package net.dreiucker.decdesclanguage.tracebility.data;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;

public class BodyDataProvider implements IDataProvider {
	
	List<String> columnHeaders = new ArrayList<>();
	List<String> rowHeaders = new ArrayList<>();
	
	public BodyDataProvider() {
	}
	
	@Override
	public int getColumnCount() {
		return columnHeaders.size();
	}
	
	@Override
	public int getRowCount() {
		return rowHeaders.size();
	}
	

	@Override
	public Object getDataValue(int columnIndex, int rowIndex) {
		return columnHeaders.get(columnIndex) + "  /  " + rowHeaders.get(rowIndex);
	}

	@Override
	public void setDataValue(int columnIndex, int rowIndex, Object newValue) {
		// not supported
	}

	public String[] getColumnHeaders() {
		return columnHeaders.toArray(new String[columnHeaders.size()]);
	}
	
	public String[] getRowHeaders() {
		return rowHeaders.toArray(new String[rowHeaders.size()]);
	}
	
	
	
}
