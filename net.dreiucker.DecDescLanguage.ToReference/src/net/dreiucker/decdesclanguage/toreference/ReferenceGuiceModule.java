package net.dreiucker.decdesclanguage.toreference;

import org.eclipse.xtext.naming.IQualifiedNameProvider;
import org.eclipse.xtext.resource.IDefaultResourceDescriptionStrategy;
import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule;

/**
 * This is the Guice Module, it will handle the creation of objects
 * that will be created by Guice in order to inject them as dependencies.
 * 
 * @author Mark
 *
 */
public class ReferenceGuiceModule extends AbstractGenericResourceRuntimeModule  {

	@Override
	protected String getLanguageName() {
		// This is the editor ID of the editor which is to be opened whenever
		//  ctrl-click / F3 happens on the referenced name
		return "toreference.presentation.ToreferenceEditorID";
	}

	@Override
	protected String getFileExtensions() {
		// TODO document
		return "toreference";
	}
	
	public Class<? extends IDefaultResourceDescriptionStrategy> bindIDefaultResourceDescriptionStrategy() {
		return ToreferenceResourceDescriptionStrategy.class;
	}

	@Override
	public Class<? extends IQualifiedNameProvider> bindIQualifiedNameProvider() {
		return QualifiedNameProvider.class;
	}

}
