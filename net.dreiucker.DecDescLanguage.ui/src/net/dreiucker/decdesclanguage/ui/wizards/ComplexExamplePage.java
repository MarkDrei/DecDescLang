package net.dreiucker.decdesclanguage.ui.wizards;

import org.eclipse.jface.viewers.ISelection;

public class ComplexExamplePage extends DdlWizardPage {

	public ComplexExamplePage(ISelection selection) {
		super(selection, 
				"Create a new Decision Description Language File",
				"This wizard creates a new file with *.ddl extension that can be opened by the DDL specific editor. "
				+ "Initially the file will contain a full example of DDL with all available elements");
	}
}
