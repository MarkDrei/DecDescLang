package net.dreiucker.decdesclanguage.reqif;

import org.eclipse.emf.common.util.EList;
import org.eclipse.rmf.reqif10.AttributeValue;
import org.eclipse.rmf.reqif10.AttributeValueString;
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
}
