package net.dreiucker.decdesclanguage.tracebility.data;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;

public class DataProvider implements IDataProvider {
	
	List<String> headers = new ArrayList<>();
	
	public DataProvider() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
	public int getColumnCount() {
		return headers.size();
	}
	
	@Override
	public int getRowCount() {
		return 4;
	}
	

	@Override
	public Object getDataValue(int columnIndex, int rowIndex) {
		return headers.get(columnIndex) + "  /  " + rowIndex;
	}

	@Override
	public void setDataValue(int columnIndex, int rowIndex, Object newValue) {
		// not supported
	}
	
}
