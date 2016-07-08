package net.dreiucker.ui;

import org.eclipse.xtext.ui.editor.syntaxcoloring.DefaultHighlightingConfiguration;
import org.eclipse.xtext.ui.editor.syntaxcoloring.IHighlightingConfigurationAcceptor;

public class LexicalDdlHighlighter extends DefaultHighlightingConfiguration {

	// currently no custom lexical highlighting, this is just left as a reference
	//  so that the instantiation code can be found once this is needed  
	
	@Override
	public void configure(IHighlightingConfigurationAcceptor acceptor) {
		super.configure(acceptor);
	}

}
