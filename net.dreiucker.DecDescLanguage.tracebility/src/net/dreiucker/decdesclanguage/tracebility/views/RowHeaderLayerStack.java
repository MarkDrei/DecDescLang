package net.dreiucker.decdesclanguage.tracebility.views;

import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.RowHeaderLayer;
import org.eclipse.nebula.widgets.nattable.layer.AbstractLayerTransform;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayerListener;
import org.eclipse.nebula.widgets.nattable.layer.event.ILayerEvent;

public class RowHeaderLayerStack extends AbstractLayerTransform {

	public RowHeaderLayerStack(IDataProvider dataProvider, BodyLayerStack body) {
		DataLayer dataLayer = new DataLayer(dataProvider, 150, 20);
		RowHeaderLayer rowHeaderLayer = new RowHeaderLayer(dataLayer, body,
				body.getSelectionLayer());
		setUnderlyingLayer(rowHeaderLayer);
		
		rowHeaderLayer.addLayerListener(new ILayerListener() {
			
			@Override
			public void handleLayerEvent(ILayerEvent event) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
}
