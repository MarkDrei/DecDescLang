package net.dreiucker.decdesclanguage.javadoc;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;

import net.dreiucker.decDescLanguage.Decision;
import net.dreiucker.decDescLanguage.Definition;
import net.dreiucker.decDescLanguage.Model;
import net.dreiucker.decDescLanguage.impl.ModelImpl;
import net.dreiucker.javadocextender.extensionpoint.IElementChangeListener;
import net.dreiucker.javadocextender.extensionpoint.IElementProvider;

public class DdlElementProvider implements IElementProvider, IXtextBuilderParticipant {

	private final static String VALID_TAG = "decision";
	
	private ArrayList<IElementChangeListener> changeListeners;
	
	public DdlElementProvider() {
		changeListeners = new ArrayList<>();
	}

	@Override
	public String getTag() {
		return VALID_TAG;
	}

	@Override
	public Set<String> getKnownElements() {
		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

		DdlResourceCollector resourceCollector = new DdlResourceCollector();
		try {
			myWorkspaceRoot.accept(resourceCollector, 0);
		} catch (CoreException e) {
			System.err.println("Failed to calculate known javadoc elements for tag " + VALID_TAG);
			e.printStackTrace();
		}
		
		ResourceSet resSet = new ResourceSetImpl();
		Set<String> result = new HashSet<>();
		for (IResource iRes : resourceCollector.resources) {
			String uriString = "platform:/resource" + iRes.getFullPath().toString();
			Resource res = resSet.getResource(URI.createURI(uriString), true);
			for (EObject content : res.getContents()) {
				if (content instanceof Model) {
					EList<Definition> definitions = ((ModelImpl) content).getDefinitions();
					for (Definition def : definitions) {
						if (def instanceof Decision) {
							String decisionName = ((Decision) def).getName();
							result.add(decisionName);
							// TODO remove
							System.out.println("  MDD found a decision: " + decisionName);
						}
					}
				}
			}
		}
		
		return result;
	}

	@Override
	public boolean unknownElementsAllowed() {
		return false;
	}

	@Override
	public void addElementsChangedListener(IElementChangeListener listener) {
		changeListeners.add(listener);
	}

	@Override
	public void removeElementsChangedListener(IElementChangeListener listener) {
		changeListeners.remove(listener);
	}
	
	
	@Override
	public void build(IBuildContext context, IProgressMonitor monitor) throws CoreException {
		for (IElementChangeListener l : changeListeners) {
			l.knownElementsChanged();
		}
	}
	
	
	/**
	 * Collects all {@link IResource} elements which are DDL elements 
	 * 
	 * @author Mark
	 *
	 */
	private class DdlResourceCollector implements IResourceProxyVisitor {
		
		private final static String DDL_FILE_EXTENSION = ".ddl";
		private final static String FOLDER_TO_IGNOER = "bin";
		
		public ArrayList<IResource> resources = new ArrayList<>();
		
		@Override
		public boolean visit(IResourceProxy proxy) throws CoreException {
			if (proxy.getType() == IResource.FOLDER && proxy.getName().equals(FOLDER_TO_IGNOER)) {
				return false;
			}
			if (proxy.getType() == IResource.FILE) {
				if (proxy.getName().endsWith(DDL_FILE_EXTENSION)) {
//					System.out.println("MDD: Found a DDL file: " + proxy.requestFullPath().toString());
					resources.add(proxy.requestResource());
				}
				return false;
			}
			return true;
		}
	}
}
