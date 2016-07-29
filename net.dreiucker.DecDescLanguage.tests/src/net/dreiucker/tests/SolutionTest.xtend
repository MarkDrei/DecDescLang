package net.dreiucker.tests

import org.junit.runner.RunWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.InjectWith
import com.google.inject.Inject
import org.eclipse.xtext.junit4.util.ParseHelper
import net.dreiucker.decDescLanguage.Model
import org.junit.Test
import static org.junit.Assert.*
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import net.dreiucker.decDescLanguage.DecDescLanguagePackage
import org.eclipse.xtext.diagnostics.Diagnostic
import net.dreiucker.decDescLanguage.AbstractSolutions

@RunWith(XtextRunner)
@InjectWith(DecDescLanguageInjectorProvider)
class SolutionTest {
		
	@Inject extension ParseHelper<Model>;
	@Inject extension ValidationTestHelper 
	
	@Test
	def void testInvalidSolution() {
		val result = parse('''
			Solution''');
			
		assertNotNull(result);
		result.assertError(DecDescLanguagePackage::eINSTANCE.model, Diagnostic::SYNTAX_DIAGNOSTIC, "no viable alternative")
		assertEquals(0, result.definitions.size);
	}
	
	@Test
	def void testStandaloneSolution() {
		val result = parse('''
			Solution "This is some text"''');
			
		assertNotNull(result)
		result.assertNoErrors
		val abstractSolutions = result.definitions.filter(AbstractSolutions)
		assertEquals(1, abstractSolutions.size)
		assertEquals(1, abstractSolutions.get(0).solutions.size)
	}
	
	@Test
	def void testSolutionBracket() {
		val result = parse('''
			Solution { "This is some text" } ''');
			
		assertNotNull(result)
		result.assertNoErrors
		val abstractSolutions = result.definitions.filter(AbstractSolutions)
		assertEquals(1, abstractSolutions.size)
		assertEquals(1, abstractSolutions.get(0).solutions.size)
	}
	
	@Test
	def void testMultipleSolutionBracket() {
		val result = parse('''
			Solution { 
				"Text"
				sol2: "foo"
			} ''');
			
		assertNotNull(result)
		result.assertNoErrors
		val abstractSolutions = result.definitions.filter(AbstractSolutions)
		assertEquals(1, abstractSolutions.size)
		assertEquals(2, abstractSolutions.get(0).solutions.size)
		val solution = abstractSolutions.get(0).solutions.get(0)
		assertNotNull(solution.solution)
		assertEquals("\"Text\"", solution.solution.solutionText)
		assertEquals("sol2", abstractSolutions.get(0).solutions.get(1).solution.name)
		assertEquals("\"foo\"", abstractSolutions.get(0).solutions.get(1).solution.solutionText)
	}
	
	@Test
	def void testMultipleSolutionDefinitions() {
		val result = parse('''
			Solution { 
				"Text"
				sol2: "foo"
			}
			Solution another: "bar" 
		''');
			
		assertNotNull(result)
		result.assertNoErrors
		val abstractSolutions = result.definitions.filter(AbstractSolutions)
		assertEquals(2, abstractSolutions.size)
		assertEquals(2, abstractSolutions.get(0).solutions.size)
		assertEquals(1, abstractSolutions.get(1).solutions.size)
		val solution = abstractSolutions.get(0).solutions.get(0)
		assertNotNull(solution.solution)
		assertEquals("\"Text\"", solution.solution.solutionText)
		assertEquals("sol2", abstractSolutions.get(0).solutions.get(1).solution.name)
		assertEquals("\"foo\"", abstractSolutions.get(0).solutions.get(1).solution.solutionText)
		val solution2 = abstractSolutions.get(1).solutions.get(0)
		assertEquals("another", solution2.solution.name)
		assertEquals("\"bar\"",solution2.solution.solutionText)
	}
	
	@Test
	def void testReason() {
		// reason not allowed upon definition
		val result = parse('''
			Solution sol: "bar" reasoning "reason" 
		''');
		assertNotNull(result)
		result.assertError(DecDescLanguagePackage::eINSTANCE.model, Diagnostic::SYNTAX_DIAGNOSTIC, "reasoning")
		
	}
}