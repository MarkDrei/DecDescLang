package net.dreiucker.decdesclanguage.reqif

import org.eclipse.rmf.reqif10.ReqIFContent

class ReqifModelHelper {
	
	static val SPEC_OBJECT_NAME_ID = "ID";
	static val SPEC_OBJECT_DESC_ID = "Description";
	static val REQUIREMENT_TYPE_LONG_NAME = "Requirement Type";
	
	/**
	 * Find the definition of the name/id attribute and return its ID.
	 * 
	 * @param it
	 * @return the requested UUID or <code>null</code> in case it could not be found 
	 */
	def static String findIdForNameAttribute(ReqIFContent it) {
		/*
		 * The expected hierarchy is:
		 * 
		 *  ReqIFContent
		 *  \-- SpecType (0...n) (long name = "Requirement Type")
		 *      \-- AttributeDefinition (long name = ID")
		 *          \-- identifier
		 */
		 
		/*
		 * Does the same as this java code
		 * 	private String findIdForNameAttribute(ReqIFContent content) {
		 *
		 *      EList<SpecType> specTypes = content.getSpecTypes();
		 *      for (SpecType type : specTypes) {
		 *      	if (REQUIREMENT_TYPE_LONG_NAME.equals(type.getLongName())) {
		 *      		EList<AttributeDefinition> attributes = type.getSpecAttributes();
		 *      		for (AttributeDefinition attribute : attributes) {
		 *      			if (SPEC_OBJECT_NAME_ID.equals(attribute.getLongName())) {
		 *      				return attribute.getIdentifier();
		 *      			}
		 *      		}
		 *      	}
		 *      }
		 *      // not found...
		 *      return null;
		 *  }
		 */
		
		specTypes.filter[longName == REQUIREMENT_TYPE_LONG_NAME]
			.last?.specAttributes.filter[longName == SPEC_OBJECT_NAME_ID].last?.identifier;
	}
	
	def static String findIdForDescriptionAttribute(ReqIFContent it) {
		specTypes.filter[longName == REQUIREMENT_TYPE_LONG_NAME]
			.last?.specAttributes.filter[longName == SPEC_OBJECT_DESC_ID].last?.identifier;
	}
	
	
	
	
	
}