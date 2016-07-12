package net.dreiucker.emfVisitor;

import java.util.ArrayList;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;

/**
 * Collects all {@link IResource} elements with a certain file
 * extension inside the workspace
 * 
 * @author Mark
 *
 */
public class ResourceCollector implements IResourceProxyVisitor {
	
	private final static String FOLDER_TO_IGNOR = "bin";
	private String extension;
	
	private ArrayList<IResource> resources = new ArrayList<>();

	public ResourceCollector(String relevantFileExtension) {
		this.extension = relevantFileExtension;
	}
	
	@Override
	public boolean visit(IResourceProxy proxy) {
		if (proxy.getType() == IResource.FOLDER && proxy.getName().equals(FOLDER_TO_IGNOR)) {
			return false;
		}
		if (proxy.getType() == IResource.FILE) {
			if (proxy.getName().endsWith(extension)) {
				// System.out.println("MDD: Found a relevant file: " +
				// proxy.requestFullPath().toString());
				resources.add(proxy.requestResource());
			}
			return false;
		}
		return true;
	}
	
	public ArrayList<IResource> getResources() {
		return resources;
	}

	public String getFileExtension() {
		return extension;
	}

}
