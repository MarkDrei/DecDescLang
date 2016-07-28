package net.dreiucker.tests;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
	AssumptionTest.class,
	ConsequencesTest.class,
	ConstraintsTest.class,
	SolutionTest.class,
	DecDescLanguageParsingTest.class
})
public class AllTests {

}
