package net.dreiucker.reqifjavadocextender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IFile;
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
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIFContent;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.pror.editor.presentation.Reqif10Editor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.ide.IDE;
import org.eclipse.ui.part.FileEditorInput;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import net.dreiucker.decdesclanguage.reqif.ReqifModelHelper;
import net.dreiucker.decdesclanguage.reqif.ReqifModelHelper2;
import net.dreiucker.javadocextender.extensionpoint.IElementChangeListener;
import net.dreiucker.javadocextender.extensionpoint.IElementProvider;

public class ReqIfElementProvider implements IElementProvider {

	private final String VALID_TAG = "requirement";
	
	private final static String EDITOR_ID = "org.eclipse.rmf.reqif10.presentation.Reqif10EditorID";

	private List<IElementChangeListener> listeners = new ArrayList<>();
	
	private Map<String, java.net.URI> requirementToFile = new HashMap<>();

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
						String id = extractID(idForNameAttribute, o);
						if (id != null) {
							result.add(id);
							requirementToFile.put(id, iRes.getLocationURI());
						}
					}
				}
			}
		}

		return result;
	}

	/**
	 * Extracts the ID of the spec object
	 * 
	 * @param idForNameAttribute
	 *            The UUID String that identifies the attribute which contains
	 *            the ID field
	 * @param specObject
	 *            The {@link SpecObject} whose ID is required
	 *            
	 * @return The extracted ID or null, if none was found
	 */
	private String extractID(String idForNameAttribute, SpecObject specObject) {
		EList<AttributeValue> values = specObject.getValues();
		for (AttributeValue value : values) {
			if (value instanceof AttributeValueString) {
				String attributeID = ((AttributeValueString) value).getDefinition().getIdentifier();
				if (attributeID.equals(idForNameAttribute)) {
					return ((AttributeValueString) value).getTheValue();
				}
			}
		}
		return null;
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
	public void openEditor(String reqId) {
		java.net.URI uri = requirementToFile.get(reqId);
		
		// identify the editor
		IEditorPart editor = null;
		IFile[] files = getWorkspaceRoot().findFilesForLocationURI(uri);
		if (files != null && files.length > 0) {
			IEditorInput editorInput = new FileEditorInput(files[0]);
			IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
			try {
				editor = IDE.openEditor(activePage, editorInput, EDITOR_ID);
			} catch (PartInitException e) {
				System.err.println("Failed to open editor " + EDITOR_ID + " on file " + files[0].getFullPath());
				e.printStackTrace();
			}
		}
		
		// now move to the correct selection
		if (editor != null) {
			Reqif10Editor reqifEditor = (Reqif10Editor) editor.getAdapter(Reqif10Editor.class);
			if (reqifEditor != null) {
				Pair<Specification, SpecHierarchy> spec = findSpecification(reqId, reqifEditor.getReqif());
				reqifEditor.setSelection(new StructuredSelection(spec.getSecond()));
				reqifEditor.openSpecEditor(spec.getFirst());
			}
		}
	}
	
	private Pair<Specification, SpecHierarchy> findSpecification(String reqId, ReqIF reqif) {
		String idForNameAttribute = ReqifModelHelper.findIdForNameAttribute(reqif.getCoreContent());
		EList<Specification> specifications = reqif.getCoreContent().getSpecifications();
		for (Specification spec : specifications) {
			SpecHierarchy hierarchy = ReqifModelHelper2.findSpecHierarchyByNameID(
					reqId, idForNameAttribute, spec.getChildren());
			if (hierarchy != null) {
				return Tuples.create(spec, hierarchy);
			}
		}
		
		return null;
	}

	private IWorkspaceRoot getWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
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
