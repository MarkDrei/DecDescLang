package net.dreiucker.decdesclanguage.tracebility.views;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.selection.SelectionLayer;
import org.eclipse.nebula.widgets.nattable.viewport.ViewportLayer;

public class BodyLayerStack extends AbstractLayerTransform {

	private SelectionLayer selectionLayer;

	public BodyLayerStack(IDataProvider dataProvider) {
		DataLayer bodyDataLayer = new DataLayer(dataProvider);
		this.selectionLayer = new SelectionLayer(bodyDataLayer);
		ViewportLayer viewportLayer = new ViewportLayer(this.selectionLayer);
		setUnderlyingLayer(viewportLayer);
	}

	public SelectionLayer getSelectionLayer() {
		return this.selectionLayer;
	}
}
