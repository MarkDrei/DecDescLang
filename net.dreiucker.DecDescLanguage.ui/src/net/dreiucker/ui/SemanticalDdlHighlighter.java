package net.dreiucker.ui;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.AbstractRule;
import org.eclipse.xtext.ide.editor.syntaxcoloring.HighlightingStyles;
import org.eclipse.xtext.ide.editor.syntaxcoloring.IHighlightedPositionAcceptor;
import org.eclipse.xtext.ide.editor.syntaxcoloring.ISemanticHighlightingCalculator;
import org.eclipse.xtext.impl.RuleCallImpl;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.resource.XtextResource;
import org.eclipse.xtext.util.CancelIndicator;

public class SemanticalDdlHighlighter implements ISemanticHighlightingCalculator {

	private static final String MULTI_STRING = "MULTI_STRING";

	@Override
	public void provideHighlightingFor(XtextResource resource, IHighlightedPositionAcceptor acceptor,
			CancelIndicator cancelIndicator) {
		
		if (resource == null || resource.getParseResult() == null)
			return;

		INode root = resource.getParseResult().getRootNode();
		for (INode node : root.getAsTreeIterable()) {
			if (cancelIndicator.isCanceled()) {
				break;
			}
			
			// search for the Multi String Terminal elements and give them
			//  the look of a normal string
			EObject grammarElement = node.getGrammarElement();
			if (grammarElement instanceof RuleCallImpl) {
				AbstractRule rule = ((RuleCallImpl) grammarElement).getRule();
				if (MULTI_STRING.equals(rule.getName())) {
					
					// TODO remove
//					System.out.println(" MDD Found MultiString: " + node.getText());
					
					acceptor.addPosition(node.getOffset(), node.getLength(), 
							HighlightingStyles.STRING_ID);
				}
			}
		}
	}

}
