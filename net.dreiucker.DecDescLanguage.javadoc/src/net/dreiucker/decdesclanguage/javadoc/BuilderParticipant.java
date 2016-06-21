package net.dreiucker.decdesclanguage.javadoc;

import java.util.ArrayList;

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

public class BuilderParticipant implements IXtextBuilderParticipant {

	
	@Override
	public void build(IBuildContext context, IProgressMonitor monitor) throws CoreException {
		
		IWorkspaceRoot myWorkspaceRoot = ResourcesPlugin.getWorkspace().getRoot();

		ResourceCollector resourceCollector = new ResourceCollector();
		myWorkspaceRoot.accept(resourceCollector, 0);
		
		ResourceSet resSet = new ResourceSetImpl(); 
		for (IResource iRes : resourceCollector.resources) {
			String uriString = "platform:/resource" + iRes.getFullPath().toString();
			Resource res = resSet.getResource(URI.createURI(uriString), true);
			for (EObject content : res.getContents()) {
				if (content instanceof Model) {
					EList<Definition> definitions = ((ModelImpl) content).getDefinitions();
					for (Definition def : definitions) {
						if (def instanceof Decision) {
							String decisionName = ((Decision) def).getName();
							System.out.println("  MDD found a decision: " + decisionName);
						}
					}
				}
			}
		}
	}

	private class ResourceCollector implements IResourceProxyVisitor {
		
		public ArrayList<IResource> resources = new ArrayList<>();
		
		@Override
		public boolean visit(IResourceProxy proxy) throws CoreException {
			if (proxy.getType() == IResource.FOLDER && proxy.getName().equals("bin")) {
				return false;
			}
			if (proxy.getType() == IResource.FILE) {
				if (proxy.getName().endsWith(".ddl")) {
//					System.out.println("MDD: Found a DDL file: " + proxy.requestFullPath().toString());
					resources.add(proxy.requestResource());
				}
				return false;
			}
			return true;
		}
	}
}
