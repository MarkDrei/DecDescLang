package net.dreiucker.decdesclanguage.javadoc;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.inject.Inject;

public class BuilderParticipant implements IXtextBuilderParticipant {

	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;
	
	@Override
	public void build(IBuildContext context, IProgressMonitor monitor) throws CoreException {

		// TODO remove
		System.out.println("MDD BuilderParticipant.build called, ResourceDescriptionsProvider is " + resourceDescriptionsProvider);
		
		
	}

}
