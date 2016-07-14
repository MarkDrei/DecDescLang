package net.dreiucker.decdesclanguage.reqif;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.AttributeValueString;
import org.eclipse.rmf.reqif10.ReqIFContent;
import org.eclipse.rmf.reqif10.SpecHierarchy;
import org.eclipse.rmf.reqif10.SpecObject;

public class ReqifModelHelper2 {

	/**
	 * 
	 * 
	 * @param specObjectUUID the unique ID of the spec object 
	 * @param hierarchies
	 *            the hierarchies to be searched
	 * @return the {@link SpecHierarchy} which contains the element or
	 *         <code>null</code> if it was not found
	 * @return
	 */
	public static SpecHierarchy findSpecHierarchyByUUID(String specObjectUUID, EList<SpecHierarchy> hierarchies) {
		for (SpecHierarchy hierarchy : hierarchies) {
			SpecObject specObject = hierarchy.getObject();
			if (specObjectUUID.equals(specObject.getIdentifier())) {
				return hierarchy;
			}
			SpecHierarchy result = findSpecHierarchyByUUID(specObjectUUID, hierarchy.getChildren());
			if (result != null)
				return result;
		}
		return null;
	}

	/**
	 * 
	 * @param specID
	 *            The ID (aka name) of the spec object
	 * @param specNameUUID
	 *            The UUID which identifies the property which stores the name
	 * @param hierarchies
	 *            the hierarchies to be searched
	 * @return the {@link SpecHierarchy} which contains the element or
	 *         <code>null</code> if it was not found
	 */
	public static SpecHierarchy findSpecHierarchyByNameID(String specID, String specNameUUID,
			EList<SpecHierarchy> hierarchies) {
		for (SpecHierarchy hierarchy : hierarchies) {
			SpecObject specObject = hierarchy.getObject();
			EList<AttributeValue> values = specObject.getValues();
			for (AttributeValue val : values) {
				if (val instanceof AttributeValueString) {
					String definitionID = ((AttributeValueString) val).getDefinition().getIdentifier();
					if (definitionID.equals(specNameUUID)) {
						if (specID.equals(((AttributeValueString) val).getTheValue())) {
							return hierarchy;
						}
					}
				}
			}
			SpecHierarchy result = findSpecHierarchyByNameID(specID, specNameUUID,hierarchy.getChildren());
			if (result != null)
				return result;
		}
		return null;
	}
	
	/**
	 * Extracts the ID of the spec object
	 * 
	 * @param idForNameAttribute
	 *            The UUID String that identifies the attribute which contains
	 *            the ID field
	 * @param specObject
	 *            The {@link SpecObject} whose ID is required
	 *            
	 * @return The extracted ID or <code>null</code>, if none was found
	 */
	public static String extractID(String idForNameAttribute, SpecObject specObject) {
		EList<AttributeValue> values = specObject.getValues();
		for (AttributeValue value : values) {
			if (value instanceof AttributeValueString) {
				String attributeID = ((AttributeValueString) value).getDefinition().getIdentifier();
				if (attributeID.equals(idForNameAttribute)) {
					return ((AttributeValueString) value).getTheValue();
				}
			}
		}
		return null;
	}
	
	/**
	 * Extract the ID (aka name) of the specObject.
	 * 
	 * Note: Due to the nature of the ReqIF structure this is a somewhat complex search
	 * and not as fast as one would might expect.
	 * 
	 * @param specObject The {@link SpecObject} whose name is required 
	 * @return The name of <code>null</code> of it was not found (either due to not having a name
	 * or not being inside a valid hierarchy)
	 */
	public static String extractID(SpecObject specObject) {
		String idForNameAttribute = findIdForNameAttribute(specObject);
		return extractID(idForNameAttribute, specObject);
	}
	
	/**
	 * Starting with a SpecObject, this method searches in the hierarchy for the UUID of 
	 * the attribute which identifies the ID (aka "name") of the spec attribute)
	 * 
	 * @param specObject Some specObject which must be in a Specification hierarchy
	 * @return The UUID of the ID attribute or <code>null</code> if it could not be found 
	 */
	public static String findIdForNameAttribute(SpecObject specObject) {
		
		// search upwards in the hierarchy
		EObject hierarchyElement = specObject;
		while (hierarchyElement != null && !(hierarchyElement instanceof ReqIFContent)) {
			hierarchyElement = hierarchyElement.eContainer();
		}
		
		if (hierarchyElement != null) {
			return ReqifModelHelper.findIdForNameAttribute((ReqIFContent) hierarchyElement);
		}
		
		return null;
	}
}
