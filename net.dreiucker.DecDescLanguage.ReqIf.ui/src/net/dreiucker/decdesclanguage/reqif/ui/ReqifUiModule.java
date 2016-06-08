package net.dreiucker.decdesclanguage.reqif.ui;

import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.LanguageSpecific;
import org.eclipse.xtext.ui.editor.IURIEditorOpener;
import org.eclipse.xtext.ui.resource.generic.EmfUiModule;

import com.google.inject.Binder;

public class ReqifUiModule extends EmfUiModule {

	public ReqifUiModule(AbstractUIPlugin plugin) {
		super(plugin);
	}

	@Override
	public void configureLanguageSpecificURIEditorOpener(Binder binder) {
		binder.bind(IURIEditorOpener.class).annotatedWith(LanguageSpecific.class).to(ReqifEditorOpener.class);
	}

}
