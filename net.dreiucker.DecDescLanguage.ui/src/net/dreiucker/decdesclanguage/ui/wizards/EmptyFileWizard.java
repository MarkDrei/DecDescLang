package net.dreiucker.decdesclanguage.ui.wizards;

import org.eclipse.jface.viewers.ISelection;

/**
 * Wizard for creating a new .ddl file with initially empty content.
 */

public class EmptyFileWizard extends DdlWizard {
	
	/**
	 * Constructor for NewDdlFileWizard.
	 */
	public EmptyFileWizard() {
		super("");
	}
	
	@Override
	protected DdlWizardPage createPage(ISelection selection) {
		return new EmptyFilePage(selection);
	}
	
}