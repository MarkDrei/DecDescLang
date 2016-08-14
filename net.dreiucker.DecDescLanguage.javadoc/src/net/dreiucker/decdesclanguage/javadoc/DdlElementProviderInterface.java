package net.dreiucker.decdesclanguage.javadoc;

import java.util.Set;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;

import net.dreiucker.javadocextender.extensionpoint.IElementChangeListener;
import net.dreiucker.javadocextender.extensionpoint.IElementProvider;

/**
 * This is just the interface to the external clients.
 * It's main purpose is to abstract from the lifecycle of the singleton implementation
 * instance.
 * 
 * @author Mark
 *
 */
public class DdlElementProviderInterface implements IElementProvider, IXtextBuilderParticipant {
	
	public DdlElementProviderInterface() {
		// no op, all handled by the implementation class
	}

	@Override
	public String getTag() {
		return DdlElementProviderImplementation.getInstance().getTag();
	}

	@Override
	public Set<String> getKnownElements() {
		return DdlElementProviderImplementation.getInstance().getKnownElements();
	}

	@Override
	public boolean unknownElementsAllowed() {
		return DdlElementProviderImplementation.getInstance().unknownElementsAllowed();
	}

	@Override
	public void addElementsChangedListener(IElementChangeListener listener) {
		DdlElementProviderImplementation.getInstance().addElementsChangedListener(listener);
	}

	@Override
	public void removeElementsChangedListener(IElementChangeListener listener) {
		DdlElementProviderImplementation.getInstance().removeElementsChangedListener(listener);
	}
	
	
	@Override
	public void build(IBuildContext context, IProgressMonitor monitor) throws CoreException {
		DdlElementProviderImplementation.getInstance().build(context, monitor);
	}
	
	@Override
	public void openEditor(String decisionName) {
		DdlElementProviderImplementation.getInstance().openEditor(decisionName);
	}
	
	@Override
	public String getElementDescription(String decisionName) {
		return DdlElementProviderImplementation.getInstance().getElementDescription(decisionName);
	}
	
}
