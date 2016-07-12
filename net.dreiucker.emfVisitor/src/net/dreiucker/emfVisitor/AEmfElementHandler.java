package net.dreiucker.emfVisitor;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;

/**
 * Default implementations for all methods of the {@link IEmfElementHandler}.
 * Intended to be sub-classed by clients who want to implement only certain methods.
 * 
 * @author Mark
 *
 */
public class AEmfElementHandler implements IEmfElementHandler {

	@Override
	public boolean shallContinue() {
		// default is to always continue
		return true;
	}

	@Override
	public void handleResource(IResource iRes) {
		// default is no operation
	}

	@Override
	public void handleEmfElement(IResource iRes, EObject content, String uriString) {
		// default is no operation
	}

}
