package net.dreiucker.decdesclanguage.reqif.ui;

import java.net.URI;

import org.eclipse.core.resources.IFile;
import org.eclipse.core.resources.IWorkspaceRoot;
import org.eclipse.core.resources.ResourcesPlugin;
import org.eclipse.emf.common.util.EList;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.SpecHierarchy;
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

/**
 * Functionality provided to other plug-ins related to ReqIf and the User
 * Interface
 * 
 * @author Mark
 *
 */
public class ReqifUiHelper {

	private final static String EDITOR_ID = "org.eclipse.rmf.reqif10.presentation.Reqif10EditorID";

	/**
	 * Open the ReqIF Editor and select the given element
	 * 
	 * @param fileUri
	 *            The URI of the file to open. If it is not found, then the
	 *            editor will not open
	 * @param requirementId
	 *            The ID (aka name) of the requirement to select and reveal. If
	 *            the requirement cannot be found in the hierarchy, then the
	 *            selection remains unchanged.
	 */
	public void openReqifEditor(URI fileUri, String requirementId) {
		// identify the editor
		IEditorPart editor = null;
		IFile[] files = getWorkspaceRoot().findFilesForLocationURI(fileUri);
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
				Pair<Specification, SpecHierarchy> spec = findSpecification(requirementId, reqifEditor.getReqif());
				reqifEditor.setSelection(new StructuredSelection(spec.getSecond()));
				reqifEditor.openSpecEditor(spec.getFirst());
			}
		}
	}

	private Pair<Specification, SpecHierarchy> findSpecification(String reqId, ReqIF reqif) {
		String idForNameAttribute = ReqifModelHelper.findIdForNameAttribute(reqif.getCoreContent());
		EList<Specification> specifications = reqif.getCoreContent().getSpecifications();
		for (Specification spec : specifications) {
			SpecHierarchy hierarchy = ReqifModelHelper2.findSpecHierarchyByNameID(reqId, idForNameAttribute,
					spec.getChildren());
			if (hierarchy != null) {
				return Tuples.create(spec, hierarchy);
			}
		}

		return null;
	}

	private IWorkspaceRoot getWorkspaceRoot() {
		return ResourcesPlugin.getWorkspace().getRoot();
	}

}
