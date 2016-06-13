package net.dreiucker.decdesclanguage.reqif;

import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule;

public class GuiceModule extends AbstractGenericResourceRuntimeModule   {

	@Override
	protected String getLanguageName() {
		// This is the editor ID of the editor which is to be opened whenever
		//  ctrl-click / F3 happens on the referenced name
		return "org.eclipse.rmf.reqif10.pror.SpecificationEditor";
	}

	@Override
	protected String getFileExtensions() {
		return "reqif";
	}
	
	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return ReqifResourceDescriptionStrategy.class;
	}
	
	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return QualifiedNameProvider.class;
	}

}
