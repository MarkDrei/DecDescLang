package net.dreiucker.decdesclanguage.toreference;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.QualifiedName;

public class QualifiedNameProvider extends DefaultDeclarativeQualifiedNameProvider {

	/**
	 * Default behavior should suffice.
	 * Just overwritten in order to debug this
	 */
	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		QualifiedName result = super.getFullyQualifiedName(obj); 
		return result;
	}
	
}
