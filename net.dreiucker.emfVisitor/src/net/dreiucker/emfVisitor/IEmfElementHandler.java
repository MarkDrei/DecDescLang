package net.dreiucker.emfVisitor;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

/**
 * Handler which knows what to do with a EMF element
 * 
 * @author Mark
 *
 */
public interface IEmfElementHandler {

	/**
	 * Check whether the search should continue
	 * 
	 * @return <code>true</code> to continue the visiting, <code>false</code> to
	 *         abort it
	 */
	boolean shallContinue();

	/**
	 * A new resource is about to be visited
	 * 
	 * @param iRes
	 *            the resource in question
	 */
	void handleResource(IResource iRes);

	/**
	 * A new EMF object was found
	 * 
	 * @param iRes
	 *            The resource in which the EMF object resides
	 * @param content
	 *            The EMF object that was found
	 * @param uriString
	 *            the URI of the resource
	 */
	void handleEmfElement(IResource iRes, EObject content, String uriString);

}
