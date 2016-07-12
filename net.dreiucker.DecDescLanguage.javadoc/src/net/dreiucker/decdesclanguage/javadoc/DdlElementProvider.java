package net.dreiucker.decdesclanguage.javadoc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.builder.IXtextBuilderParticipant;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;

import net.dreiucker.decDescLanguage.Decision;
import net.dreiucker.decDescLanguage.Definition;
import net.dreiucker.decDescLanguage.Model;
import net.dreiucker.decDescLanguage.impl.ModelImpl;
import net.dreiucker.emfVisitor.EmfElementHandler;
import net.dreiucker.emfVisitor.EmfVisitor;
import net.dreiucker.javadocextender.extensionpoint.IElementChangeListener;
import net.dreiucker.javadocextender.extensionpoint.IElementProvider;
import net.dreiucker.ui.CustomDdlActivator;

public class DdlElementProvider implements IElementProvider, IXtextBuilderParticipant {

	private final static boolean DEBUG = false;
	
	private final static String DDL_FILE_EXTENSION = ".ddl";
	
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
		
		EmfVisitor visitor = new EmfVisitor(DDL_FILE_EXTENSION);
		
		final Set<String> result = new HashSet<>();
		
		visitor.visitAllEmfResources(new EmfElementHandler() {
			
			private int definitionIndex = -1;
			
			@Override
			public void handleResource(IResource iRes) {
				definitionIndex = -1;
			}
			
			@Override
			public void handleEmfElement(IResource iRes, EObject content, String uriString) {
				
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
		});
		
		return result;
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
	}
	

	
}
