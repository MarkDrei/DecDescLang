package net.dreiucker.reqifjavadocextender;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.eclipse.core.resources.IResource;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.rmf.reqif10.ReqIF;
import org.eclipse.rmf.reqif10.ReqIFContent;
import org.eclipse.rmf.reqif10.SpecObject;

import net.dreiucker.decdesclanguage.reqif.ReqifModelHelper;
import net.dreiucker.decdesclanguage.reqif.ReqifModelHelper2;
import net.dreiucker.decdesclanguage.reqif.ui.ReqifUiHelper;
import net.dreiucker.emfVisitor.AEmfElementHandler;
import net.dreiucker.emfVisitor.EmfVisitor;
import net.dreiucker.javadocextender.extensionpoint.IElementChangeListener;
import net.dreiucker.javadocextender.extensionpoint.IElementProvider;

public class ReqIfElementProvider implements IElementProvider {

	private final String VALID_TAG = "requirement";
	
	private final static String REQIF_FILE_EXTENSION = ".reqif";
	
	private List<IElementChangeListener> listeners = new ArrayList<>();
	
	private Map<String, java.net.URI> requirementToFile = new HashMap<>();

	
	@Override
	public String getTag() {
		return VALID_TAG;
	}

	@Override
	public Set<String> getKnownElements() {
		
		EmfVisitor visitor = new EmfVisitor(REQIF_FILE_EXTENSION);
		
		final Set<String> result = new HashSet<>();
		
		visitor.visitAllEmfResources(new AEmfElementHandler() {
			
			@Override
			public void handleEmfElement(IResource iRes, EObject content, String uriString) {
				if (content instanceof ReqIF) {
					ReqIFContent coreContent = ((ReqIF) content).getCoreContent();
					String idForNameAttribute = ReqifModelHelper.findIdForNameAttribute(coreContent);
					for (SpecObject o : coreContent.getSpecObjects()) {
						String id = ReqifModelHelper2.extractID(idForNameAttribute, o);
						if (id != null) {
							result.add(id);
							requirementToFile.put(id, iRes.getLocationURI());
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
		listeners.add(listener);
	}

	@Override
	public void removeElementsChangedListener(IElementChangeListener listener) {
		listeners.remove(listener);
	}
	
	@Override
	public void openEditor(String reqId) {
		java.net.URI uri = requirementToFile.get(reqId);
		
		new ReqifUiHelper().openReqifEditor(uri, reqId);
	}
}
