package net.dreiucker.decdesclanguage.javadoc;

import org.eclipse.xtext.resource.generic.AbstractGenericResourceRuntimeModule;

public class JDocGuiceModule extends AbstractGenericResourceRuntimeModule {

	@Override
	protected String getLanguageName() {
		return "http://www.dreiucker.net/DecDescLanguage";
	}

	@Override
	protected String getFileExtensions() {
		return "ddl";
	}

}
