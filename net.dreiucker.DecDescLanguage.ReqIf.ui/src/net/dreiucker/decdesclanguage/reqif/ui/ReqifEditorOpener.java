package net.dreiucker.decdesclanguage.reqif.ui;

import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.ui.IEditorPart;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;

public class ReqifEditorOpener extends LanguageSpecificURIEditorOpener  {

	@Override
	protected void selectAndReveal(IEditorPart openEditor, URI uri, EReference crossReference, int indexInList,
			boolean select) {
		
		// TODO remove
		System.out.println("MDD ReqifEditorOpener.selectAndReveal()");
		// TODO Open the reqif editor here
		
		
//		UMLEditor umlEditor = (UMLEditor) openEditor.getAdapter(UMLEditor.class);
//		if (umlEditor != null) {
//			EObject eObject = umlEditor.getEditingDomain().getResourceSet().getEObject(uri, true);
//			umlEditor.setSelectionToViewer(Collections.singletonList(eObject));
//		}
	}
}
