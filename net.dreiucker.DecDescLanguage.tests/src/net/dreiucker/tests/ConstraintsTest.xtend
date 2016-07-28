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
import net.dreiucker.decDescLanguage.AbstractConstraints

@RunWith(XtextRunner)
@InjectWith(DecDescLanguageInjectorProvider)
class ConstraintsTest {
		
	@Inject extension ParseHelper<Model>;
	@Inject extension ValidationTestHelper 
	
		
	@Test
	def void testInvalidConstraint() {
		val result = parse('''Constraint  ''');
			
		assertNotNull(result);
		result.assertError(DecDescLanguagePackage::eINSTANCE.model, Diagnostic::SYNTAX_DIAGNOSTIC, "no viable alternative")
		assertEquals(0, result.definitions.size);
	}
	
	
	@Test
	def void testConstraint() {
		val result = parse('''
			Constraint name: "text"
		''')
		assertNotNull(result);
		result.assertNoErrors
		val aContraints = result.definitions.filter(AbstractConstraints)
		assertEquals(1, aContraints.size)
		assertEquals(1, aContraints.get(0).constraints.size)
		val c = aContraints.get(0).constraints.get(0).constraint
		assertNotNull(c)
		assertEquals("\"text\"", c.constraintText)
		
	}
	
	@Test
	def void testConstraintBrackets() {
		val result = parse('''
		Constraint { 
			"text"
		}
		''')
		assertNotNull(result);
				result.assertNoErrors
		val aContraints = result.definitions.filter(AbstractConstraints)
		assertEquals(1, aContraints.size)
		assertEquals(1, aContraints.get(0).constraints.size)
	}
	
	@Test
	def void testMulsitpleConstraintBrackets() {
		val result = parse('''
		Constraint { 
			"text"
			name: "text2"
		}
		''')
		assertNotNull(result);
				result.assertNoErrors
		val aContraints = result.definitions.filter(AbstractConstraints)
		assertEquals(1, aContraints.size)
		assertEquals(2, aContraints.get(0).constraints.size)
	}
	
	@Test
	def void testMulsitpleMultipleConstraint() {
		val result = parse('''
		Constraint { 
			"text"
			name: "text2"
		}
		Constraint "foo"
		''')
		assertNotNull(result);
				result.assertNoErrors
		val aContraints = result.definitions.filter(AbstractConstraints)
		assertEquals(2, aContraints.size)
		assertEquals(2, aContraints.get(0).constraints.size)
		assertEquals(1, aContraints.get(1).constraints.size)
	}
	
	@Test
	def void testConstraintReference() {
		val result = parse('''
		Constraint { 
			name: "text2"
		}
		Constraint { name }
		''')
		assertNotNull(result);
				result.assertNoErrors
		val aContraints = result.definitions.filter(AbstractConstraints)
		assertEquals(2, aContraints.size)
		assertEquals(1, aContraints.get(0).constraints.size)
		assertEquals(1, aContraints.get(1).constraints.size)
	}
}