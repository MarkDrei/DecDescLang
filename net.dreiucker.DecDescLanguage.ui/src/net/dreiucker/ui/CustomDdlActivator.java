package net.dreiucker.ui;

import org.eclipse.emf.common.util.URI;
import org.eclipse.xtext.ui.editor.LanguageSpecificURIEditorOpener;

import com.google.inject.Inject;

import net.dreiucker.DecDescLanguage.ui.internal.DecDescLanguageActivator;

/**
 *  
 * @author Mark
 *
 */
public class CustomDdlActivator extends DecDescLanguageActivator {

	private static final boolean DEBUG = false;

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

	public void openDdlEditor(URI uri) {
		if (DEBUG) {
			System.out.println("Opening DDL-Editor for " + uri.toString());
		}
		editorOpener.open(uri, true);
	}
}
