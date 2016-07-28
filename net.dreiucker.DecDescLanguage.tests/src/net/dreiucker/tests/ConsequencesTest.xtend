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
import net.dreiucker.decDescLanguage.AbstractConsequences

@RunWith(XtextRunner)
@InjectWith(DecDescLanguageInjectorProvider)
class ConsequencesTest {
	
		
	@Inject extension ParseHelper<Model>;
	@Inject extension ValidationTestHelper 
	
	@Test
	def void testInvalidConsequence() {
		val result = parse('''
			Consequence''');
			
		assertNotNull(result);
		result.assertError(DecDescLanguagePackage::eINSTANCE.model, Diagnostic::SYNTAX_DIAGNOSTIC, "no viable alternative")
		assertEquals(0, result.definitions.size);
	}
	
	@Test
	def void testConsequence() {
		val result = parse('''
			Consequence "text"
		''');
		
		assertNotNull(result);
		val aCons = result.definitions.filter(AbstractConsequences)
		assertEquals(1, aCons.size)
		assertEquals(1, aCons.get(0).consequences.size)
		val cons = aCons.get(0).consequences.get(0).consequence
		assertNull(cons.name)
	}
	
	@Test
	def void testNamedConsequence() {
		val result = parse('''
			Consequence name: "text"
		''');
		
		assertNotNull(result);
		val aCons = result.definitions.filter(AbstractConsequences)
		assertEquals(1, aCons.size)
		assertEquals(1, aCons.get(0).consequences.size)
		val cons = aCons.get(0).consequences.get(0).consequence
		assertEquals("name", cons.name)
		assertEquals("\"text\"", cons.consequenceText)
	}
	
	@Test
	def void testConsequenceBracket() {
		val result = parse('''
			Consequence { name: "text" }
		''');
		
		assertNotNull(result);
		val aCons = result.definitions.filter(AbstractConsequences)
		assertEquals(1, aCons.size)
		assertEquals(1, aCons.get(0).consequences.size)
		val cons = aCons.get(0).consequences.get(0).consequence
		assertEquals("name", cons.name)
		assertEquals("\"text\"", cons.consequenceText)
	}
	
	@Test
	def void testMultipleConsequenceBracket() {
		val result = parse('''
			Consequence { 
				name: "text"
				name2: "text2"
			 }
		''');
		
		assertNotNull(result);
		val aCons = result.definitions.filter(AbstractConsequences)
		assertEquals(1, aCons.size)
		assertEquals(2, aCons.get(0).consequences.size)
		val cons = aCons.get(0).consequences.get(0).consequence
		assertEquals("name", cons.name)
		assertEquals("\"text\"", cons.consequenceText)
		val cons2 = aCons.get(0).consequences.get(1).consequence
		assertEquals("name2", cons2.name)
		assertEquals("\"text2\"", cons2.consequenceText)
	}
	
	@Test
	def void testMultipleMultipleConsequenceBracket() {
		val result = parse('''
			Consequence { 
				name: "text"
				name2: "text2"
			 }
			Consequence { 
				name3: "text3"
				name4: "text4"
			 }
		''');
		
		assertNotNull(result);
		val aCons = result.definitions.filter(AbstractConsequences)
		assertEquals(2, aCons.size)
		assertEquals(2, aCons.get(0).consequences.size)
		val cons = aCons.get(0).consequences.get(0).consequence
		assertEquals("name", cons.name)
		assertEquals("\"text\"", cons.consequenceText)
		val cons2 = aCons.get(0).consequences.get(1).consequence
		assertEquals("name2", cons2.name)
		assertEquals("\"text2\"", cons2.consequenceText)
		
		assertEquals(2, aCons.get(1).consequences.size)
		val cons3 = aCons.get(1).consequences.get(0).consequence
		assertEquals("name3", cons3.name)
		assertEquals("\"text3\"", cons3.consequenceText)
		val cons4 = aCons.get(1).consequences.get(1).consequence
		assertEquals("name4", cons4.name)
		assertEquals("\"text4\"", cons4.consequenceText)
	}
	
	@Test
	def void testConsequenceReference() {
				val result = parse('''
			Consequence { 
				name: "text"
			 }
			Consequence { 
				name
			 }
		''');
		assertNotNull(result);
		val aCons = result.definitions.filter(AbstractConsequences)
		assertEquals(2, aCons.size)
		assertEquals(1, aCons.get(0).consequences.size)
		assertEquals(1, aCons.get(1).consequences.size)
	}
	
	
}