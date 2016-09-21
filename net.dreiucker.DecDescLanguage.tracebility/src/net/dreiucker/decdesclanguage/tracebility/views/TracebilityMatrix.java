package net.dreiucker.decdesclanguage.tracebility.views;

import java.util.HashSet;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.config.ConfigRegistry;
import org.eclipse.nebula.widgets.nattable.config.DefaultNatTableStyleConfiguration;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.layer.ILayer;
import org.eclipse.nebula.widgets.nattable.painter.IOverlayPainter;
import org.eclipse.nebula.widgets.nattable.resize.command.InitializeAutoResizeColumnsCommand;
import org.eclipse.nebula.widgets.nattable.resize.command.InitializeAutoResizeRowsCommand;
import org.eclipse.nebula.widgets.nattable.sort.config.SingleClickSortConfiguration;
import org.eclipse.nebula.widgets.nattable.ui.menu.HeaderMenuConfiguration;
import org.eclipse.nebula.widgets.nattable.util.GCFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.IWorkbenchActionConstants;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import net.dreiucker.decdesclanguage.tracebility.Activator;
import net.dreiucker.decdesclanguage.tracebility.data.BodyDataProvider;
import net.dreiucker.decdesclanguage.tracebility.data.DataCollector;
import net.dreiucker.decdesclanguage.tracebility.data.RowHeaderDataProvider;

public class TracebilityMatrix extends ViewPart {

	/**
	 * The ID of the view as specified by the extension.
	 */
	public static final String ID = "net.dreiucker.decdesclanguage.tracebility.views.TracebilityMatrix";

	private Composite viewComposite;
	private NatTable natTable = null;
	
	private MatrixEditorOpener editorOpener;
	
	private Action action1;

	/**
	 * The constructor.
	 */
	public TracebilityMatrix() {
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	public void createPartControl(final Composite parent) {
		viewComposite = parent;
		updatePartControl();

		// Create the help context id for the viewer's control
		PlatformUI.getWorkbench().getHelpSystem().setHelp(parent, "net.dreiucker.DecDescLanguage.tracebility.viewer");
		hookContextMenu();
		makeActions();
		contributeToActionBars();
	}

	/**
	 * Triggers the creation of a new matrix
	 * @param parent
	 */
	private void updatePartControl() {
		if (natTable != null) {
			if (editorOpener != null) {
				natTable.removeMouseListener(editorOpener);
			}
			natTable.dispose();
		}
		
		final Label label = new Label(viewComposite, SWT.NONE);
		label.setText("Please wait while data is being populated...");

		final BodyDataProvider dataProvider = new BodyDataProvider();
		new DataCollector(dataProvider, viewComposite.getDisplay(), new Runnable() {

			@Override
			public void run() {
				// delete the old content
				label.dispose();
				// generate the new content
				createTableWidget(viewComposite, dataProvider);
				viewComposite.layout();
			}
		}).schedule();
	}

	private void createTableWidget(final Composite parent, final BodyDataProvider dataProvider) {
		BodyLayerStack body = new BodyLayerStack(dataProvider);
		DefaultColumnHeaderDataProvider columnHeaderDataProvider = new DefaultColumnHeaderDataProvider(
				dataProvider.getColumnHeaders());
		ColumnHeaderLayerStack columnHeaderLayer = new ColumnHeaderLayerStack(columnHeaderDataProvider, body);

		RowHeaderDataProvider rowHeaderDataProvider = new RowHeaderDataProvider(dataProvider);
		RowHeaderLayerStack rowHeaderLayer = new RowHeaderLayerStack(rowHeaderDataProvider, body);

		DefaultCornerDataProvider cornerDataProvider = new DefaultCornerDataProvider(columnHeaderDataProvider,
				rowHeaderDataProvider);

		CornerLayer cornerLayer = new CornerLayer(new DataLayer(cornerDataProvider, 100, 100), rowHeaderLayer, columnHeaderLayer);

		GridLayer gridLayer = new GridLayer(body, columnHeaderLayer, rowHeaderLayer, cornerLayer);

		natTable = new NatTable(parent, gridLayer, false);
		editorOpener = new MatrixEditorOpener(natTable, dataProvider);
		
		ConfigRegistry configRegistry = new ConfigRegistry();
		natTable.setConfigRegistry(configRegistry);
		
		natTable.addConfiguration(new DefaultNatTableStyleConfiguration());
        natTable.addConfiguration(new HeaderMenuConfiguration(natTable));
        natTable.addConfiguration(new SingleClickSortConfiguration());

        
		// resize the row after the paint event (credits to the NatTable FAQ)
//		natTable.addListener(SWT.Paint, new Listener() {
//
//			@Override
//			public void handleEvent(Event arg0) {
//				for (int i = 0; i < natTable.getColumnCount(); i++) {
//					InitializeAutoResizeColumnsCommand columnCommand = new InitializeAutoResizeColumnsCommand(natTable,
//							i, natTable.getConfigRegistry(), new GCFactory(natTable));
//					boolean result = natTable.doCommand(columnCommand);
//					System.out.println("Initial column resize for column " + i + " finished with " + result);
//				}
//
//				for (int i = 0; i < natTable.getRowCount(); i++) {
//					InitializeAutoResizeRowsCommand rowCommand = new InitializeAutoResizeRowsCommand(natTable, i,
//							natTable.getConfigRegistry(), new GCFactory(natTable));
//					natTable.doCommand(rowCommand);
//				}
//						
//				
////				natTable.removeListener(SWT.Paint, this);
//			}
//		});
		
		natTable.addOverlayPainter(new IOverlayPainter() {
			
			private HashSet rowset = new HashSet();
			private HashSet colset = new HashSet();
					
			@Override
			public void paintOverlay(GC gc, ILayer layer) { 
				int count = natTable.getColumnCount();
				for (int i=0; i < count; i++) {
					if (natTable.isColumnPositionResizable(i) == false) {
						continue;
					}
			
					int pos = natTable.getColumnIndexByPosition(i);
					if (colset.contains(pos)) {
						continue;
					}
			
					colset.add(pos);
			
					InitializeAutoResizeColumnsCommand columnCommand = 
						new InitializeAutoResizeColumnsCommand(natTable, i, 
								natTable.getConfigRegistry(), 
								new GCFactory(natTable));
					System.out.println(natTable.doCommand(columnCommand));
//					natTable.doCommand(columnCommand);
				}
				
				count = natTable.getRowCount();
				for (int i=0; i < count; i++) {
					if (natTable.isRowPositionResizable(i) == false){
						continue;
					}
			
					int pos = natTable.getRowIndexByPosition(i);
					if (rowset.contains(pos)) {
						continue;
					}
					
					rowset.add(pos);
					
					InitializeAutoResizeRowsCommand rowCommand = 
						new InitializeAutoResizeRowsCommand(natTable, i, 
							natTable.getConfigRegistry(), 
							new GCFactory(natTable));
					natTable.doCommand(rowCommand);
				}	
			} 
		});
		
		natTable.configure();
	}

	private void hookContextMenu() {
		MenuManager menuMgr = new MenuManager("#PopupMenu");
		menuMgr.setRemoveAllWhenShown(true);
		menuMgr.addMenuListener(new IMenuListener() {
			public void menuAboutToShow(IMenuManager manager) {
				TracebilityMatrix.this.fillContextMenu(manager);
			}
		});
		Menu menu = menuMgr.createContextMenu(viewComposite);
		viewComposite.setMenu(menu);
	}

	private void contributeToActionBars() {
		IActionBars bars = getViewSite().getActionBars();
		fillLocalPullDown(bars.getMenuManager());
		fillLocalToolBar(bars.getToolBarManager());
	}

	private void fillLocalPullDown(IMenuManager manager) {
		manager.add(action1);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
	}

	private void makeActions() {
		action1 = new Action() {
			public void run() {
				updatePartControl();
			}
		};
		action1.setText("Refresh");
		action1.setToolTipText("Refresh the tracebility matrix");

		action1.setImageDescriptor(Activator.getImageDescriptor("icons/refresh.gif"));
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		// viewer.getControl().setFocus();
	}
}
