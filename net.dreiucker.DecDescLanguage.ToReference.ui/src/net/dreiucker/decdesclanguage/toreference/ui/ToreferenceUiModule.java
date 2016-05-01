package net.dreiucker.decdesclanguage.toreference.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.LanguageSpecific;
import org.eclipse.xtext.ui.resource.generic.EmfUiModule;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;

import com.google.inject.Binder;

public class ToreferenceUiModule extends EmfUiModule {
	
	public ToreferenceUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}
	
	@Override
	public void configureLanguageSpecificURIEditorOpener(Binder binder) {
		binder.bind(IURIEditorOpener.class).annotatedWith(LanguageSpecific.class).to(EmfEditorOpener.class);
	}
}
