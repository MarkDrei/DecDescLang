package net.dreiucker.decdesclanguage.ui.wizards;

import org.eclipse.jface.viewers.ISelection;

public class ComplexExampleWizard extends DdlWizard {

	public ComplexExampleWizard() {
		super("// Comments are possible with the java / C++ syntax\n"
			+"/* This is another valid comment */\n"
			+"\n"
			+"// Solutions can be described as stand-alone elements\n"
			+"Solution solutionName: \"Describe the nature of the solution here.\"\n"
			+"	causes \"A solution usually has consequences, they can be described here\"\n"
			+"	\n"
			+"Solution extendedSolution: \"You can refine a solution by extending another one\"\n"
			+"	extends solutionName\n"
			+"\n"
			+"// Persons\n"
			+"Person name {\n"
			+"	// mandatory:\n"
			+"	Name: \"Homer Simpson\"\n"
			+"	// optional:\n"
			+"	Email: \"any@email.address\"\n"
			+"	Role: \"Job description\"\n"
			+"}\n"
			+"\n"
			+"\n"
			+"Decision decisionName {\n"
			+"	// The next three elements are mandatory\n"
			+"	Issue: \"Describe the issue that needs a decision to be made\"\n"
			+"	// Any decision must have a status. It's one out of decision, pending, approved, declined\n"
			+"	Status: decided\n"
			+"	// The decision is to implement a solution. Describe the solution here.\n"
			+"	// Here a reference to a predefined solution is used\n"
			+"	Solution: extendedSolution\n"
			+"		// you can explain why this solution was picked\n"
			+"		reasoning \"The reason why this solution was picked\"\n"
			+"\n"	
			+"	/*\n"
			+"	 * The next elements are optional and can appear in any order.\n"
			+"	 * Just use the auto-complete feature (ctrl+space by default) to see all the\n"
			+"	 * available options.\n"
			+"	 * You can reference previously defined elements\n"
			+"	 */\n"
			+"\n"	 
			+"	 Owner: name\n"
			+"	 // you can list alternative solutions in the Alternatives: section.\n"
			+"	 // If you want to use multiple references, surround them with curly {} brackets\n"
			+"	 Alternatives: {\n"
			+"	 	solutionName\n"
			+"	 	extendedSolution\n"
			+"	 }\n"
			+"	 Assumptions: \"You may want to document your assumptions which led to a decision\"\n"
			+"\n"	 
			+"	 Constraints: \"Or are there constraints that must be fulfilled?\"\n"
			+"\n"	 
			+"	 // You can reference related decisions\n"
			+"	 Related: anotherDecision\n"
			+"\n"	 
			+"	 // You can also reference requirements which are documented with ProR\n"
			+"	 // Requirements: requirement007\n"
			+"}\n"
			+"\n"
			+"Decision anotherDecision {\n"
			+"	Issue:\n" 
			+"		'The issue can be described with a very long text when you use single quotes.\n"
			+"\n"		
			+"		The indentation works differently and helps you to\n"
			+"		better format a long text. Of course spell chekcing works here as well.\n" 
			+"		'\n"
			+"	Status: pending\n"
			+"	Solution: \"Use single quotes or the\n"
			+"indentation is awkward for multi line texts\"\n"
			+"}\n"
			+"\n");
	}
	
	@Override
	protected DdlWizardPage createPage(ISelection selection) {
		return new ComplexExamplePage(selection);
	}

}
