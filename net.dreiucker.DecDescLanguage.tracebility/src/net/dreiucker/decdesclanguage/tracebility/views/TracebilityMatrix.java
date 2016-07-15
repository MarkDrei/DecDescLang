package net.dreiucker.decdesclanguage.tracebility.views;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.action.IMenuListener;
import org.eclipse.jface.action.IMenuManager;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.jface.action.MenuManager;
import org.eclipse.jface.action.Separator;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.nebula.widgets.nattable.NatTable;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultColumnHeaderDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.data.DefaultCornerDataProvider;
import org.eclipse.nebula.widgets.nattable.grid.layer.CornerLayer;
import org.eclipse.nebula.widgets.nattable.grid.layer.GridLayer;
import org.eclipse.nebula.widgets.nattable.layer.DataLayer;
import org.eclipse.nebula.widgets.nattable.resize.command.InitializeAutoResizeColumnsCommand;
import org.eclipse.nebula.widgets.nattable.util.GCFactory;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.ui.IActionBars;
import org.eclipse.ui.ISharedImages;
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
	
	private EditorOpener editorOpener;
	
	private Action action1;
	private Action action2;

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

		natTable = new NatTable(parent, gridLayer);
		editorOpener = new EditorOpener(natTable, dataProvider);

		// resize the row after the paint event (credits to the NatTable FAQ)
		natTable.addListener(SWT.Paint, new Listener() {

			@Override
			public void handleEvent(Event arg0) {
				for (int i = 0; i < natTable.getColumnCount(); i++) {
					InitializeAutoResizeColumnsCommand columnCommand = new InitializeAutoResizeColumnsCommand(natTable,
							i, natTable.getConfigRegistry(), new GCFactory(natTable));
					natTable.doCommand(columnCommand);
				}
				
				natTable.removeListener(SWT.Paint, this);
			}
		});
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
		manager.add(new Separator());
		manager.add(action2);
	}

	private void fillContextMenu(IMenuManager manager) {
		manager.add(action1);
		manager.add(action2);
		// Other plug-ins can contribute there actions here
		manager.add(new Separator(IWorkbenchActionConstants.MB_ADDITIONS));
	}

	private void fillLocalToolBar(IToolBarManager manager) {
		manager.add(action1);
		manager.add(action2);
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

		action2 = new Action() {
			public void run() {
				showMessage("Action 2 executed");
			}
		};
		action2.setText("Action 2");
		action2.setToolTipText("Action 2 tooltip");
		action2.setImageDescriptor(
				PlatformUI.getWorkbench().getSharedImages().getImageDescriptor(ISharedImages.IMG_OBJS_INFO_TSK));
	}

	private void showMessage(String message) {
		MessageDialog.openInformation(viewComposite.getShell(), "Tracebility Matrix", message);
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	public void setFocus() {
		// viewer.getControl().setFocus();
	}
}
