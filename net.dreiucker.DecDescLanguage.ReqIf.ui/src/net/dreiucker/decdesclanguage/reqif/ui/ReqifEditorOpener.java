package net.dreiucker.decdesclanguage.reqif.ui;

import java.util.Iterator;

import org.eclipse.core.resources.IProject;
import org.eclipse.core.resources.IStorage;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.common.util.WrappedException;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.jface.viewers.StructuredSelection;
import org.eclipse.rmf.reqif10.AttributeDefinitionString;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.AttributeValueString;
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

import net.dreiucker.decdesclanguage.reqif.ReqifModelHelper;

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
		
		String specId = getSpecObjectID(); 
		
		final Reqif10Editor editor = (Reqif10Editor) openEditor.getAdapter(Reqif10Editor.class);

		if (editor != null) {
			final Pair<Specification, SpecObject> spec = findSpecification(specId, editor.getReqif());
			
			if (spec.getFirst() != null) {
				editor.setFocus();
				final StructuredSelection structuredSelection = new StructuredSelection(spec.getFirst().getChildren().get(1));
				editor.setSelection(structuredSelection);
				editor.openSpecEditor(spec.getFirst());
			}
		}
	}

	private String getSpecObjectID() {
		//TODO ask the xtext editor for its current selection so identify the hyperlink element target
		String specId = "Req02";
		return specId;
	}

	/**
	 * Finds the {@link Specification} and the {@link SpecObject} for the given
	 * SpecID. If the Spec is not contained in any Specification, then only the
	 * SpecObject is returned. If it is contained in more than one
	 * Specification, then the first one is returned.
	 * 
	 * @param specID
	 *            The id of the spec object that is searched
	 * @param reqif
	 *            The root of the model which will be searched
	 * @return The searched specObject and the first Specification it is
	 *         contained in. <code>null</code> contents possible for one or both values
	 */
	private Pair<Specification, SpecObject> findSpecification(String specID, ReqIF reqif) {
		String nameId = ReqifModelHelper.findIdForNameAttribute(reqif.getCoreContent());
		
		EList<Specification> specifications = reqif.getCoreContent().getSpecifications();
		for(Specification spec : specifications) {
			SpecObject specObject = findSpecObject(nameId, specID, spec.getChildren());
			if (specObject != null) {
				return Tuples.create(spec, specObject);
			}
		}
		// TODO search the spec objects directly, we might just find the specObject but it is
		//      not contained in any specification
		
		// TODO none was found
		return null;
	}
	
	private SpecObject findSpecObject(String nameId, String specID, EList<SpecHierarchy> hierarchies) {
		for (SpecHierarchy hierarchy : hierarchies) {
			SpecObject specObject = hierarchy.getObject();
			EList<AttributeValue> values = specObject.getValues();
			for(AttributeValue value : values) {
				if (value instanceof AttributeValueString) {
					String theValue = ((AttributeValueString) value).getTheValue();
					if (theValue.equals(specID)) {
						AttributeDefinitionString definition = ((AttributeValueString) value).getDefinition();
						if (nameId.equals(definition.getIdentifier())) {
							return specObject;
						}
					}
				}
			}
			SpecObject result = findSpecObject(nameId, specID, hierarchy.getChildren());
			if (result != null) return result;
		}
		return null;
	}
}
