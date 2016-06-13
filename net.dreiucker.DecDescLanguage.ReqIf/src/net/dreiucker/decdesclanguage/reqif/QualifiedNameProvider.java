package net.dreiucker.decdesclanguage.reqif;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.rmf.reqif10.AttributeDefinition;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.ReqIFContent;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.rmf.reqif10.SpecType;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;

public class QualifiedNameProvider  extends DefaultDeclarativeQualifiedNameProvider {

	private final static String SPEC_OBJECT_NAME_ID = "ID";
	private final static String REQUIREMENT_TYPE_LONG_NAME = "Requirement Type";
	
	private IQualifiedNameConverter nameConverter;

	@Inject
	public QualifiedNameProvider(IQualifiedNameConverter nameConverter) {
		this.nameConverter = nameConverter;
	}
	
	/**
	 * Find the valid fully qualified names
	 */
	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		QualifiedName result;
		
		if (obj instanceof SpecObject) {
			result = identifySpecObjectNames((SpecObject) obj);
		} else {
			result = super.getFullyQualifiedName(obj); 
		}
		
		return result;
	}

	/**
	 * Identifies the user given name/ID that is associated with a spec
	 * object.
	 * 
	 * @param specObject the specObject whose name is searched
	 * @return The qualified name, or <code>null</code> if no user given name
	 * was associated with that specObject
	 */
	private QualifiedName identifySpecObjectNames(SpecObject specObject) {
		QualifiedName result = null;
		String identifier = findIdForNameAttribute(specObject);
		if (identifier != null) {
			EList<AttributeValue> values = specObject.getValues();
			for (AttributeValue value : values) { 
				if (value instanceof AttributeValueString) {
					AttributeValueString stringValue = (AttributeValueString) value;
					String valueIdentifier = stringValue.getDefinition().getIdentifier();
					if (identifier.equals(valueIdentifier)) {
						result = nameConverter.toQualifiedName(stringValue.getTheValue());
						break;
					}
				}
			}
		}
		
		//TODO remove
		if (result != null) {
			System.out.println("MDD generated a new qualified name: " + result.toString());
		}
		return result;
	}

	/**
	 * Tries to find the identifier which is used as attribute definition that
	 * marks the name/id of the specObject
	 * 
	 * @param specObject entry into the ecore hierarchy
	 * 
	 * @return the requested UUID or <code>null</code> in case it could not be found 
	 */
	private String findIdForNameAttribute(SpecObject specObject) {
		EObject container = specObject.eContainer();
		// search in the hierarchy for the ReqIFContent
		while (container != null && !(container instanceof ReqIFContent)) {
			container = container.eContainer();
		}
		if (container != null && (container instanceof ReqIFContent)) {
			return ReqifModelHelper.findIdForNameAttribute((ReqIFContent) container);
		}
		return null;
	}

}
