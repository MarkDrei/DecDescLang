package net.dreiucker.decdesclanguage.reqif;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.rmf.reqif10.SpecObject;
import org.eclipse.xtext.naming.DefaultDeclarativeQualifiedNameProvider;
import org.eclipse.xtext.naming.IQualifiedNameConverter;
import org.eclipse.xtext.naming.QualifiedName;

import com.google.inject.Inject;

public class QualifiedNameProvider  extends DefaultDeclarativeQualifiedNameProvider {

	private IQualifiedNameConverter nameConverter;

	@Inject
	public QualifiedNameProvider(IQualifiedNameConverter nameConverter) {
		this.nameConverter = nameConverter;
	}
	
	/**
	 * TODO Probably need to overwrite this to map the names correctly
	 */
	@Override
	public QualifiedName getFullyQualifiedName(EObject obj) {
		QualifiedName result;
		
		if (obj instanceof SpecObject) {
			SpecObject specObject = (SpecObject) obj;
			result = nameConverter.toQualifiedName(specObject.getIdentifier());
			
			//TODO remove
			System.out.println("MDD generated a new qualified name: " + result.toString());
		} else {
			result = super.getFullyQualifiedName(obj); 
		}
		
		return result;
	}
	
}
