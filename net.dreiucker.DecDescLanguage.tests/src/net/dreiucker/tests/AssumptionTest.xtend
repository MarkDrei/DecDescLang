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
		val abstractAssumptions = result.definitions.filter(AbstractAssumptions)
		assertEquals(1, abstractAssumptions.size)
		assertEquals(1, abstractAssumptions.get(0).assumptions.size)
	}
	
	@Test
	def void testNamedStandaloneAssumption() {
		val result = parse('''
			Assumption name : "This is some text"''');
			
		assertNotNull(result)
		val abstractAssumptions = result.definitions.filter(AbstractAssumptions)
		assertEquals(1, abstractAssumptions.size)
		assertEquals(1, abstractAssumptions.get(0).assumptions.size)
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
		val abstractAssumptions = result.definitions.filter(AbstractAssumptions)
		assertEquals(1, abstractAssumptions.size)
		assertEquals(2, abstractAssumptions.get(0).assumptions.size)
	}
	
	@Test
	def void testMultipleMultipleAssumption() {
		val result = parse('''
			Assumption {
					"first assumption"
					someName : "second"
				}
			Assumption {
				"just another one"
			}
		''');
			
		assertNotNull(result)
		result.assertNoErrors
		val abstractAssumptions = result.definitions.filter(AbstractAssumptions)
		assertEquals(2, abstractAssumptions.size)
		assertEquals(2, abstractAssumptions.get(0).assumptions.size)
		assertEquals(1, abstractAssumptions.get(1).assumptions.size)
	}
	
	@Test
	def void testReuse() {
		val result = parse('''
			Assumption  someName : "foo"
			Assumption {
				someName
			}
		''');
			
		assertNotNull(result)
		result.assertNoErrors
		val abstractAssumptions = result.definitions.filter(AbstractAssumptions)
		assertEquals(2, abstractAssumptions.size)
		assertEquals(1, abstractAssumptions.get(0).assumptions.size)
		assertEquals(1, abstractAssumptions.get(1).assumptions.size)
	}
	
}