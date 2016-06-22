package net.dreiucker.decdesclanguage.javadoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;

import net.dreiucker.decDescLanguage.Decision;
import net.dreiucker.decDescLanguage.Definition;
import net.dreiucker.decDescLanguage.Model;
import net.dreiucker.decDescLanguage.impl.ModelImpl;
import net.dreiucker.javadocextender.extensionpoint.IElementChangeListener;
import net.dreiucker.javadocextender.extensionpoint.IElementProvider;
import net.dreiucker.ui.CustomDdlActivator;

public class DdlElementProvider implements IElementProvider, IXtextBuilderParticipant {

	private final static boolean DEBUG = false;
	
	private final static String VALID_TAG = "decision";
	
	private ArrayList<IElementChangeListener> changeListeners;
	
	// Buffers the relation from "ddl decision" to the file path it is contained in
	Map<String, URI> decisionsToFiles = new HashMap<>();
	
	public DdlElementProvider() {
		changeListeners = new ArrayList<>();
	}

	@Override
	public String getTag() {
		return VALID_TAG;
	}

	@Override
	public Set<String> getKnownElements() {
		DdlResourceCollector resourceCollector = new DdlResourceCollector();
		try {
			getWorkspaceRoot().accept(resourceCollector, 0);
		} catch (CoreException e) {
			System.err.println("Failed to calculate known javadoc elements for tag " + VALID_TAG);
			e.printStackTrace();
		}
		
		ResourceSet resSet = new ResourceSetImpl();
		Set<String> result = new HashSet<>();
		for (IResource iRes : resourceCollector.resources) {
			int definitionIndex = -1;
			String uriString = "platform:/resource" + iRes.getFullPath().toString();
			Resource res = resSet.getResource(URI.createURI(uriString), true);
			for (EObject content : res.getContents()) {
				if (content instanceof Model) {
					EList<Definition> definitions = ((ModelImpl) content).getDefinitions();
					for (Definition def : definitions) {
						definitionIndex++;
						if (def instanceof Decision) {
							String decisionName = ((Decision) def).getName();
							result.add(decisionName);
							// create a URI string in the format
							//   platform:/resource/TestDSL/src/example1/people.ddl#//@definitions.1
							String definitionUri = uriString + "#//@definitions." + definitionIndex;
							decisionsToFiles.put(decisionName, URI.createURI(definitionUri));
							if (DEBUG) {
								System.out.println("  MDD found a decision: " + decisionName);
							}
						}
					}
				}
			}
		}
		
		return result;
	}
	
	private IWorkspaceRoot getWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
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
	
	@Override
	public void openEditor(String decisionName) {
		// get the correct IFile
		URI uri = decisionsToFiles.get(decisionName);
		LanguageSpecificURIEditorOpener editorOpener = CustomDdlActivator.getInstance().getEditorOpener();
		editorOpener.open(uri, true);
		
		// This is based on java.net.URI from an  iResource.getLocationURI()
		
//		if (uri != null) {
//			System.out.println(" MDD: Opening file at " + uri.getPath());
//			IFile[] files = getWorkspaceRoot().findFilesForLocationURI(uri);
//			if (files.length > 0) {
//				IFile file = files[0];
//				IEditorDescriptor editor = PlatformUI.getWorkbench().getEditorRegistry().getDefaultEditor(file.getName());
//				IWorkbenchPage activePage = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getActivePage();
//				try {
//					IEditorPart openEditor = activePage.openEditor(new FileEditorInput(file), editor.getId());
//					if (openEditor != null && openEditor instanceof XtextEditor) {
//						XtextEditor xEditor = (XtextEditor) openEditor;
//						xEditor.getDocument().
//					}
//				} catch (PartInitException e) {
//					System.err.println("Failed to open editor \"" + editor.getId()+ "\" for " + uri.getPath());
//					e.printStackTrace();
//				}
//			}
//		}
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
