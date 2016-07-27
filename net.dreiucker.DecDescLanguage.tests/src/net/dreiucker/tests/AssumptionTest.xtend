package net.dreiucker.tests

import org.junit.runner.RunWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.InjectWith
import com.google.inject.Inject
import org.eclipse.xtext.junit4.util.ParseHelper
import net.dreiucker.decDescLanguage.Model
import org.junit.Test
import static org.junit.Assert.*
import net.dreiucker.decDescLanguage.Assumption
import org.eclipse.xtext.junit4.validation.ValidationTestHelper
import net.dreiucker.decDescLanguage.DecDescLanguagePackage
import org.eclipse.xtext.diagnostics.Diagnostic
import net.dreiucker.decDescLanguage.AbstractAssumptions

@RunWith(XtextRunner)
@InjectWith(DecDescLanguageInjectorProvider)

class AssumptionTest {
	
	@Inject extension ParseHelper<Model>;
	@Inject extension ValidationTestHelper 
	
	@Test
	def void testInvalidAssumption() {
		val result = parse('''
			Assumption''');
			
		assertNotNull(result);
		result.assertError(DecDescLanguagePackage::eINSTANCE.model, Diagnostic::SYNTAX_DIAGNOSTIC, "no viable alternative")
		assertEquals(0, result.definitions.size);
	}
	
	@Test
	def void testStandaloneAssumption() {
		val result = parse('''
			Assumption "This is some text"''');
			
		assertNotNull(result)
		result.assertNoErrors
		assertEquals(1, result.definitions.filter(AbstractAssumptions).size)
	}
	
	@Test
	def void testNamedStandaloneAssumption() {
		val result = parse('''
			Assumption name : "This is some text"''');
			
		assertNotNull(result)
		result.assertNoErrors
		assertEquals(1, result.definitions.filter(AbstractAssumptions).size)
	}
	
	@Test
	def void testMultipleAssumption() {
		val result = parse('''
			Assumption {
					"first assumption"
					someName : "second"
				}
		''');
			
		assertNotNull(result)
		result.assertNoErrors
		assertEquals(1, result.definitions.filter(AbstractAssumptions).size)
		
	}
	
}