package net.dreiucker.emfVisitor;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;

/**
 * Class which helps to visit all EMF root elements which can be found inside
 * the workspace
 * 
 * @author Mark
 *
 */
public class EmfVisitor {

	private ResourceCollector resourceCollector;

	/**
	 * 
	 * @param relevantFileExtension
	 *            Only consider files with this file extension (speeds up the
	 *            search)
	 */
	public EmfVisitor(String relevantFileExtension) {
		resourceCollector = new ResourceCollector(relevantFileExtension);
	}

	/**
	 * Searches and visits all top level EMF resources
	 * 
	 * @param elementHandler
	 *            The handler who gets to decide what to do with these resources
	 */
	public void visitAllEmfResources(IEmfElementHandler elementHandler) {

		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		try {
			workspaceRoot.accept(resourceCollector, 0);
		} catch (CoreException e) {
			System.err.println(
					"Failed to collect the workspace elements with extension " + resourceCollector.getFileExtension());
			e.printStackTrace();
		}

		ResourceSet resSet = new ResourceSetImpl();
		for (IResource iRes : resourceCollector.getResources()) {
			String uriString = "platform:/resource" + iRes.getFullPath().toString();
			Resource res = resSet.getResource(URI.createURI(uriString), true);
			for (EObject content : res.getContents()) {
				elementHandler.handleEmfElement(iRes, content, uriString);
			}
		}
	}
}
