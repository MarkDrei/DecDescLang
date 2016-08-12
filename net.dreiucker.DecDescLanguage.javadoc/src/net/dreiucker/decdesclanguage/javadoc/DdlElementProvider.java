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

import net.dreiucker.decDescLanguage.Decision;
import net.dreiucker.decDescLanguage.Definition;
import net.dreiucker.decDescLanguage.Model;
import net.dreiucker.decDescLanguage.impl.ModelImpl;
import net.dreiucker.emfVisitor.AEmfElementHandler;
import net.dreiucker.emfVisitor.EmfVisitor;
import net.dreiucker.javadocextender.extensionpoint.IElementChangeListener;
import net.dreiucker.javadocextender.extensionpoint.IElementProvider;
import net.dreiucker.ui.CustomDdlActivator;

public class DdlElementProvider implements IElementProvider, IXtextBuilderParticipant {
	
	/**
	 * Holds additional data about about an decision
	 */
	private final class DecisionData {
		
		public DecisionData(URI fileLocation, String decisionDescription) {
			this.fileLocation = fileLocation;
			this.decisionDescription = decisionDescription;
		}
		
		URI fileLocation;
		String decisionDescription;
	}

	private final static boolean DEBUG = false;
	
	private final static String DDL_FILE_EXTENSION = ".ddl";
	
	private final static String VALID_TAG = "decision";
	
	private ArrayList<IElementChangeListener> changeListeners;
	
	// Buffers the relation from "ddl decision" to the file path it is contained in
	Map<String, DecisionData> decisionsToFiles = new HashMap<>();
	
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
		
		visitor.visitAllEmfResources(new AEmfElementHandler() {
			
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
							Decision decision = (Decision) def;
							String decisionName = decision.getName();
							result.add(decisionName);
							// create a URI string in the format
							//   platform:/resource/TestDSL/src/example1/people.ddl#//@definitions.1
							String definitionUri = uriString + "#//@definitions." + definitionIndex;
							String decisionDescription = "Reference the decision <b>" + decisionName
									+ "</b> on the issue <em>" + decision.getIssue() + "</em>";
							
							decisionsToFiles.put(decisionName,
									new DecisionData(URI.createURI(definitionUri), decisionDescription));
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
		DecisionData decisionData = decisionsToFiles.get(decisionName);
		if (decisionData != null) {
			URI uri = decisionData.fileLocation;
			CustomDdlActivator.getInstance().openDdlEditor(uri);
		}
	}
	
	@Override
	public String getElementDescription(String decisionName) {
		DecisionData decisionData = decisionsToFiles.get(decisionName);
		if (decisionData != null) {
			return decisionData.decisionDescription;
		}
		return null;
	}

	
}
