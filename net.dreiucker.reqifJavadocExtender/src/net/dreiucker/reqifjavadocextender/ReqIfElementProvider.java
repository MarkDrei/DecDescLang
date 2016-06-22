package net.dreiucker.reqifjavadocextender;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.resources.IResourceProxy;
import org.eclipse.core.resources.IResourceProxyVisitor;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.ecore.resource.ResourceSet;
import org.eclipse.emf.ecore.resource.impl.ResourceSetImpl;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIFContent;
import org.eclipse.rmf.reqif10.SpecObject;

import net.dreiucker.decdesclanguage.reqif.ReqifModelHelper;
import net.dreiucker.javadocextender.extensionpoint.IElementChangeListener;
import net.dreiucker.javadocextender.extensionpoint.IElementProvider;

public class ReqIfElementProvider implements IElementProvider {

	private final String VALID_TAG = "requirement";

	private List<IElementChangeListener> listeners = new ArrayList<>();

	@Override
	public String getTag() {
		return VALID_TAG;
	}

	@Override
	public Set<String> getKnownElements() {
		ReqifResourceCollector resourceCollector = new ReqifResourceCollector();

		IWorkspaceRoot workspaceRoot = ResourcesPlugin.getWorkspace().getRoot();
		try {
			workspaceRoot.accept(resourceCollector, 0);
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
				if (content instanceof ReqIF) {
					ReqIFContent coreContent = ((ReqIF) content).getCoreContent();
					String idForNameAttribute = ReqifModelHelper.findIdForNameAttribute(coreContent);
					for (SpecObject o : coreContent.getSpecObjects()) {
						extractID(result, idForNameAttribute, o);
					}
				}
			}
		}

		return result;
	}

	/**
	 * Extracts the ID of the spec object
	 * 
	 * @param result
	 *            List to which ID gets added if it is found
	 * @param idForNameAttribute
	 *            The UUID String that identifies the attribute which contains
	 *            the ID field
	 * @param specObject
	 *            The {@link SpecObject} whose ID is required
	 */
	private void extractID(Set<String> result, String idForNameAttribute, SpecObject specObject) {
		EList<AttributeValue> values = specObject.getValues();
		for (AttributeValue value : values) {
			if (value instanceof AttributeValueString) {
				String attributeID = ((AttributeValueString) value).getDefinition().getIdentifier();
				if (attributeID.equals(idForNameAttribute)) {
					result.add(((AttributeValueString) value).getTheValue());
					break;
				}
			}
		}
	}

	@Override
	public boolean unknownElementsAllowed() {
		return false;
	}

	@Override
	public void addElementsChangedListener(IElementChangeListener listener) {
		listeners.add(listener);
	}

	@Override
	public void removeElementsChangedListener(IElementChangeListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public void openEditor(String text) {
		// TODO Auto-generated method stub
		
	}
	
	/**
	 * Collects all {@link IResource} elements which are DDL elements
	 * 
	 * @author Mark
	 *
	 */
	private class ReqifResourceCollector implements IResourceProxyVisitor {

		private final static String DDL_FILE_EXTENSION = ".reqif";
		private final static String FOLDER_TO_IGNOER = "bin";

		public ArrayList<IResource> resources = new ArrayList<>();

		@Override
		public boolean visit(IResourceProxy proxy) throws CoreException {
			if (proxy.getType() == IResource.FOLDER && proxy.getName().equals(FOLDER_TO_IGNOER)) {
				return false;
			}
			if (proxy.getType() == IResource.FILE) {
				if (proxy.getName().endsWith(DDL_FILE_EXTENSION)) {
					// System.out.println("MDD: Found a DDL file: " +
					// proxy.requestFullPath().toString());
					resources.add(proxy.requestResource());
				}
				return false;
			}
			return true;
		}
	}
}
