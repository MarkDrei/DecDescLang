package net.dreiucker.decdesclanguage.reqif.ui;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.Specification;
import org.eclipse.rmf.reqif10.pror.editor.presentation.Reqif10Editor;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorPart;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PartInitException;
import org.eclipse.ui.ide.IDE;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;
import org.eclipse.xtext.ui.editor.utils.EditorUtils;
import org.eclipse.xtext.ui.resource.IStorage2UriMapper;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

import com.google.inject.Inject;

public class ReqifEditorOpener extends LanguageSpecificURIEditorOpener  {


//	private final static String EDITOR_ID = "org.eclipse.rmf.reqif10.pror.SpecificationEditor";
	private final static String EDITOR_ID = "org.eclipse.rmf.reqif10.presentation.Reqif10EditorID";

	private static final String REQIF_FILE_EXTENSION = ".reqif";
	
	@Inject
	private IStorage2UriMapper mapper;

	@Inject
	private IWorkbench workbench;
	
	@Override
	public IEditorPart open(URI uri, EReference crossReference, int indexInList, boolean select) {
		
		// get the editor
		String editorId = null;
		if (uri.segmentCount() > 0) {
			String extension = uri.segments()[uri.segmentCount() - 1];
			extension = extension.toLowerCase();
			if (extension.endsWith(REQIF_FILE_EXTENSION)) {
				editorId = EDITOR_ID;
			}
		}
		// default editor
		if (editorId == null) {
			editorId = getEditorId();
		}
		
		// note: the following code is taken form the superclass
		Iterator<Pair<IStorage, IProject>> storages = mapper.getStorages(uri.trimFragment()).iterator();
		if (storages != null && storages.hasNext()) {
			try {
				IStorage storage = storages.next().getFirst();
				IEditorInput editorInput = EditorUtils.createEditorInput(storage);
				IWorkbenchPage activePage = workbench.getActiveWorkbenchWindow().getActivePage();
				final IEditorPart editor = IDE.openEditor(activePage, editorInput, editorId);
				selectAndReveal(editor, uri, crossReference, indexInList, select);
				return EditorUtils.getXtextEditor(editor);
			} catch (WrappedException e) {
				System.err.println("Error while opening editor part for EMF URI '" + uri + "' " + e.getCause());
			} catch (PartInitException partInitException) {
				System.err.println("Error while opening editor part for EMF URI '" + uri + "' " + partInitException);
			}
		}
		return null;
	}
	
	@Override
	protected void selectAndReveal(IEditorPart openEditor, URI uri, EReference crossReference, int indexInList,
			boolean select) {
		
		String specObjectUUID = uri.fragment();
		
		Reqif10Editor editor = (Reqif10Editor) openEditor.getAdapter(Reqif10Editor.class);

		if (editor != null) {
			Pair<Specification, SpecHierarchy> spec = findSpecification(specObjectUUID, editor.getReqif());
			
			if (spec != null && spec.getFirst() != null) {
				editor.setSelection(new StructuredSelection(spec.getSecond()));
				editor.openSpecEditor(spec.getFirst());
			}
		}
	}

	/**
	 * Finds the {@link Specification} and the {@link SpecObject} for the given
	 * SpecID. If the Spec is not contained in any Specification, then only the
	 * SpecObject is returned. If it is contained in more than one
	 * Specification, then the first one is returned.
	 * 
	 * @param specObjectUUID
	 *            The id of the spec object
	 * @param reqif
	 *            The root of the model which will be searched
	 * @return The searched specObject and the first Specification it is
	 *         contained in. <code>null</code> contents possible for one or both values
	 */
	private Pair<Specification, SpecHierarchy> findSpecification(String specObjectUUID, ReqIF reqif) {
		EList<Specification> specifications = reqif.getCoreContent().getSpecifications();
		for(Specification spec : specifications) {
			SpecHierarchy specHierarchy = findSpecHierarchy(specObjectUUID, spec.getChildren());
			if (specHierarchy != null) {
				return Tuples.create(spec, specHierarchy);
			}
		}
		// TODO none was found
		return null;
	}
	
	private SpecHierarchy findSpecHierarchy(String specObjectUUID, EList<SpecHierarchy> hierarchies) {
		for (SpecHierarchy hierarchy : hierarchies) {
			SpecObject specObject = hierarchy.getObject();
			if (specObjectUUID.equals(specObject.getIdentifier())) {
				return hierarchy;
			}
			SpecHierarchy result = findSpecHierarchy(specObjectUUID, hierarchy.getChildren());
			if (result != null) return result;
		}
		return null;
	}
}
