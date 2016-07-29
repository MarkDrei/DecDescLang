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
import net.dreiucker.decDescLanguage.Decision

@RunWith(XtextRunner)
@InjectWith(DecDescLanguageInjectorProvider)
class DecisionTest {
		
	@Inject extension ParseHelper<Model>;
	@Inject extension ValidationTestHelper 
	
	@Test
	def void testMinimalDecision() {
		val result = parse('''
			Decision dec01 {
				Issue: "issue"
				Status: decided
				Solution: "sol"
			}''');
			
		assertNotNull(result);
		result.assertNoErrors
		assertEquals(1, result.definitions.size);
		val dec = result.definitions.filter(Decision).get(0)
		assertEquals("\"issue\"", dec.issue)
		assertNotNull(dec.status.DECIDED)
		assertNull(dec.status.APPROVED)
		assertNull(dec.status.DECLINED)
		assertNull(dec.status.PENDING)
		assertEquals("\"sol\"", dec.solution.solution.solution.solutionText)
	}
	
	@Test
	def void testMissingIssue() {
		val result = parse('''
			Decision dec01 {
				Status: decided
				Solution: "sol"
			}''');
			
		assertNotNull(result);
		result.assertError(DecDescLanguagePackage::eINSTANCE.decision, Diagnostic::SYNTAX_DIAGNOSTIC, "expecting 'Issue:'")
		assertEquals(1, result.definitions.size);
	}
	
	@Test
	def void testMissingStatus() {
		val result = parse('''
			Decision dec01 {
				Issue: "issue"
				Solution: "sol"
			}''');
			
		assertNotNull(result);
		result.assertError(DecDescLanguagePackage::eINSTANCE.decision, Diagnostic::SYNTAX_DIAGNOSTIC, "expecting 'Status:'")
		assertEquals(1, result.definitions.size);
	}
	
	@Test
	def void testMissingSolution() {
		val result = parse('''
			Decision dec01 {
				Issue: "issue"
				Status: decided
			}''');
			
		assertNotNull(result);
		result.assertError(DecDescLanguagePackage::eINSTANCE.decision, Diagnostic::SYNTAX_DIAGNOSTIC, "expecting 'Solution:'")
		assertEquals(1, result.definitions.size);
	}
	
		
	@Test
	def void testSolutionRef() {
		val result = parse('''
			Solution sol: "solText"
			Decision dec01 {
				Issue: "issue"
				Status: decided
				Solution: sol
			}''');
			
		assertNotNull(result);
		result.assertNoErrors
		assertEquals(2, result.definitions.size);
		val dec = result.definitions.filter(Decision).get(0)
		assertEquals("\"issue\"", dec.issue)
		assertNotNull(dec.status.DECIDED)
		assertNull(dec.status.APPROVED)
		assertNull(dec.status.DECLINED)
		assertNull(dec.status.PENDING)
		assertEquals("\"solText\"", dec.solution.solution.solutionRef.solutionText)
		assertEquals("sol", dec.solution.solution.solutionRef.name)
	}
	
		
	@Test
	def void testAllRef() {
		val result = parse('''
			Solution sol: "solText"
			Solution sol2: "solText"
			Assumption ass: "u out of me"
			Constraint con: "this"
			Person owner {
				Name: "Owner name"
			}
			
			
			Decision dec01 {
				Issue: "issue"
				Status: decided
				Solution: sol reasoning "good"
				
				Alternatives: sol2 reasoning "bad"
				Assumptions: ass
				Constraints: con
				Owner: owner
			}''');
			
		assertNotNull(result);
		result.assertNoErrors
		assertEquals(6, result.definitions.size);
		val dec = result.definitions.filter(Decision).get(0)
		assertEquals("\"issue\"", dec.issue)
		assertNotNull(dec.status.DECIDED)
		assertNull(dec.status.APPROVED)
		assertNull(dec.status.DECLINED)
		assertNull(dec.status.PENDING)
		assertEquals("\"solText\"", dec.solution.solution.solutionRef.solutionText)
		assertEquals("sol", dec.solution.solution.solutionRef.name)
	}
	
		
	@Test
	def void testAllInline() {
		val result = parse('''
			Decision dec01 {
				Issue: "issue"
				Status: decided
				Solution: "solText" reasoning "good"
				
				Alternatives: "sol 2 Text" reasoning "bad"
				Assumptions: "assumption"
				Constraints: "foobar"
			}''');
			
		assertNotNull(result);
		result.assertNoErrors
		assertEquals(1, result.definitions.size);
		val dec = result.definitions.filter(Decision).get(0)
		assertEquals("\"issue\"", dec.issue)
		assertNotNull(dec.status.DECIDED)
		assertNull(dec.status.APPROVED)
		assertNull(dec.status.DECLINED)
		assertNull(dec.status.PENDING)
		assertEquals("\"solText\"", dec.solution.solution.solution.solutionText)
		assertNull(dec.solution.solution.solution.name)
	}
	
}