package net.dreiucker.decdesclanguage.tracebility.views;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.ColumnHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;

public class ColumnHeaderLayerStack extends AbstractLayerTransform {

	public ColumnHeaderLayerStack(IDataProvider dataProvider, BodyLayerStack body) {
		DataLayer dataLayer = new DataLayer(dataProvider);
		ColumnHeaderLayer colHeaderLayer = new ColumnHeaderLayer(dataLayer, body,
				body.getSelectionLayer());
		setUnderlyingLayer(colHeaderLayer);
	}
}
