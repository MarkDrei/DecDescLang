package net.dreiucker.decdesclanguage.ui.wizards;

import org.eclipse.jface.viewers.ISelection;

/**
 * Special wizard for DDL files with initially empty contents 
 */

public class EmptyFilePage extends DdlWizardPage {

	/**
	 * Constructor for EmptyFilePage.
	 * 
	 */
	public EmptyFilePage(ISelection selection) {
		super(selection, 
				"Create a new Decision Description Language File",
				"This wizard creates a new empty file with *.ddl extension that can be opened by the DDL specific editor.");
	}
}