package net.dreiucker.decdesclanguage.toreference.ui;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;

public class EmfEditorOpener extends LanguageSpecificURIEditorOpener {

	@Override
	protected void selectAndReveal(IEditorPart openEditor, URI uri, EReference crossReference, int indexInList,
			boolean select) {
		
		// TODO Open the ecore editor here
		
		
//		UMLEditor umlEditor = (UMLEditor) openEditor.getAdapter(UMLEditor.class);
//		if (umlEditor != null) {
//			EObject eObject = umlEditor.getEditingDomain().getResourceSet().getEObject(uri, true);
//			umlEditor.setSelectionToViewer(Collections.singletonList(eObject));
//		}
	}

}
