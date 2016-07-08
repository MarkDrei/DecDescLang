package net.dreiucker.decdesclanguage.tracebility.data;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.swt.widgets.Display;

import net.dreiucker.reqifjavadocextender.ReqIfElementProvider;

public class DataCollector extends Job {

	private Display display;
	private Runnable runnable;
	private DataProvider dataProvider;

	/**
	 * Creates a data collector.
	 * 
	 * The process of collecting data can take a while and should be done in its
	 * own thread / job in the backround, the given display / runnable can be
	 * used to schedule a UI thread once the collector has finished
	 * 
	 * @param dataProvider
	 *            The data provider who stores the data
	 * @param display
	 *            The display on whose UI thread the finish jobs gets scheduled
	 * @param runnable
	 *            The job to schedule once the work is done
	 */
	public DataCollector(DataProvider dataProvider, Display display, Runnable runnable) {
		super("TracebilityMatrix Data Collector");
		this.dataProvider = dataProvider;

		this.display = display;
		this.runnable = runnable;

	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		try {
			startCollectoting();
			// TODO remove
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		display.asyncExec(runnable);

		return Status.OK_STATUS;
	}
	
	private void startCollectoting() {
		ReqIfElementProvider elementProvider = new ReqIfElementProvider();
		dataProvider.headers.addAll(elementProvider.getKnownElements());
		
	}

}
