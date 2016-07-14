package net.dreiucker.decdesclanguage.tracebility.data;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.core.runtime.IStatus;
import org.eclipse.core.runtime.Status;
import org.eclipse.core.runtime.jobs.Job;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIFContent;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.swt.widgets.Display;
import org.eclipse.xtext.util.Tuples;

import net.dreiucker.decDescLanguage.AbstractRequirement;
import net.dreiucker.decDescLanguage.AbstractRequirements;
import net.dreiucker.decDescLanguage.Decision;
import net.dreiucker.decDescLanguage.Definition;
import net.dreiucker.decDescLanguage.Model;
import net.dreiucker.decdesclanguage.reqif.ReqifModelHelper;
import net.dreiucker.decdesclanguage.reqif.ReqifModelHelper2;
import net.dreiucker.emfVisitor.AEmfElementHandler;
import net.dreiucker.emfVisitor.EmfVisitor;

public class DataCollector extends Job {

	protected static final boolean DEBUG = true;
	
	private static final String DDL_FILE_EXTENSION = ".ddl";
	private final static String REQIF_FILE_EXTENSION = ".reqif";

	private Display display;
	private Runnable runnable;
	private BodyDataProvider dataProvider;

	/**
	 * Creates a data collector.
	 * 
	 * The process of collecting data can take a while and should be done in its
	 * own thread / job in the background. The given display / runnable can be
	 * used to schedule a UI thread once the collector has finished
	 * 
	 * @param dataProvider
	 *            The data provider who stores the data
	 * @param display
	 *            The display on whose UI thread the finish jobs gets scheduled
	 * @param runnable
	 *            The job to schedule once the work is done
	 */
	public DataCollector(BodyDataProvider dataProvider, Display display, Runnable runnable) {
		super("TracebilityMatrix Data Collector");
		this.dataProvider = dataProvider;

		this.display = display;
		this.runnable = runnable;

	}

	@Override
	protected IStatus run(IProgressMonitor monitor) {

		collect(monitor);

		// collecting done
		display.asyncExec(runnable);

		return Status.OK_STATUS;
	}
	
	private void collect(IProgressMonitor monitor) {
		collectRequirements(monitor);
		collectDecisions(monitor);
		
	}



	private void collectRequirements(final IProgressMonitor monitor) {
		
		EmfVisitor visitor = new EmfVisitor(REQIF_FILE_EXTENSION);
		
		final Set<String> result = new HashSet<>();
		
		visitor.visitAllEmfResources(new AEmfElementHandler() {
			
			@Override
			public boolean shallContinue() {
				return !monitor.isCanceled();
			}
			
			@Override
			public void handleEmfElement(IResource iRes, EObject content, String uriString) {
				if (content instanceof ReqIF) {
					ReqIFContent coreContent = ((ReqIF) content).getCoreContent();
					String idForNameAttribute = ReqifModelHelper.findIdForNameAttribute(coreContent);
					for (SpecObject o : coreContent.getSpecObjects()) {
						String id = ReqifModelHelper2.extractID(idForNameAttribute, o);
						if (id != null) {
							if (DEBUG) {
								System.out.println("MDD Found requirement: " + id);
							}
							result.add(id);
						}
					}
				}
			}
		});
		
		dataProvider.requirementColumnHeaders.addAll(result);
	}
	
	/**
	 * Collect the decisions and the mappings to the Requirements.
	 * Note that requirements must have been collected first or else the references will not be
	 * collectible
	 * 
	 * @param monitor
	 */
	private void collectDecisions(final IProgressMonitor monitor) {
		EmfVisitor emfVisitor = new EmfVisitor(DDL_FILE_EXTENSION);
		
		final Set<String> result = new HashSet<>();
		
		emfVisitor.visitAllEmfResources(new AEmfElementHandler() {
			
			@Override
			public boolean shallContinue() {
				return !monitor.isCanceled();
			}

			@Override
			public void handleEmfElement(IResource iRes, EObject content, String uriString) {
				if (content instanceof Model) {
					EList<Definition> definitions = ((Model) content).getDefinitions();
					for (Definition definition : definitions) {
						if (definition instanceof Decision) {
							Decision decision = (Decision) definition;
							if (DEBUG) {
								System.out.println("MDD Found definition: " + decision.getName());
							}
							dataProvider.decisionRowHeaders.add(((Decision) definition).getName());
							
							AbstractRequirements requirements = decision.getRequirement();
							if(requirements != null) {
								EList<AbstractRequirement> requirements2 = requirements.getRequirements();
								if (requirements2 != null) {
									for (AbstractRequirement requirement : requirements2) {
										SpecObject ref = requirement.getRequirement().getRef();
										if (ref != null) {
											String id = ReqifModelHelper2.extractID(ref);
											enterReferenceToMatrix(id);
										}
									}
								}
							}
						}
					}
				}
			}

			/**
			 * 
			 * @param id The ID / name of the reference
			 */
			private void enterReferenceToMatrix(String id) {
				if (id != null) {
					// Found a concrete reference, now add it to the data provider
					int index = dataProvider.requirementColumnHeaders.indexOf(id);
					
					if (index < 0) {
						System.err.println("Unable to find requirement \"" + id + "\" in the Tracebility Matrix");
					} else {
						if (DEBUG) {
							System.out.println("Entering reference in cell " + dataProvider.decisionRowHeaders.size() + "/" + index);
						}
						dataProvider.decisionRefersToReq.put(
								Tuples.create(
										Integer.valueOf(dataProvider.decisionRowHeaders.size()),
										Integer.valueOf(index)),
								Boolean.TRUE);
					}
				}
			}
		});
		
		dataProvider.decisionRowHeaders.addAll(result);
		
	}

}
