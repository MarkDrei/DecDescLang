package net.dreiucker.ui;

import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;

import com.google.inject.Inject;

import net.dreiucker.DecDescLanguage.ui.internal.DecDescLanguageActivator;

/**
 * 
 * @FIXME
 * This is a dirty hack in order to get the injected stuff which works in this
 * plug in, but does not in the others without (significant?) effort.
 * Correct would be to inject the stuff with Guice, but at the moment I am not aware
 * how to do that
 *  
 * @author Mark
 *
 */
public class CustomDdlActivator extends DecDescLanguageActivator {

	private static CustomDdlActivator instance;
	
	@Inject
	LanguageSpecificURIEditorOpener editorOpener;
	
	public CustomDdlActivator() {
		super();
		instance = this;
	}
	
	public static CustomDdlActivator getInstance() {
		// initialized upon plugin activation
		return instance;
	}
	
	public LanguageSpecificURIEditorOpener getEditorOpener() {
		return editorOpener;
	}
}
