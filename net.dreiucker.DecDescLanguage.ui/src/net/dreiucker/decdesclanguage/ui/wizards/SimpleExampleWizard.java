package net.dreiucker.decdesclanguage.ui.wizards;

import org.eclipse.jface.viewers.ISelection;

public class SimpleExampleWizard extends DdlWizard {

	public SimpleExampleWizard() {
		super("Decision decisionName {\n"
	+ "\t// The next three elements are mandatory\n"
	+ "\tIssue: \"Describe the issue that needs a decision to be made\"\n"
	+ "\tStatus: decided\n"
	+ "\tSolution: \"Describe the solution that was picked in order to solve the issue\"\n"
	+ "\t\n"
	+ "\t/*\n"
	+ "\t * The next elements are optional and can appear in any order.\n"
	+ "\t * Just use the auto-complete feature (ctrl+space by default) to see all the\n"
	+ "\t * available options\n"
	+ "\t */\n"
+"}\n");
	}
	
	@Override
	protected DdlWizardPage createPage(ISelection selection) {
		return new SimpleExamplePage(selection);
	}

}
