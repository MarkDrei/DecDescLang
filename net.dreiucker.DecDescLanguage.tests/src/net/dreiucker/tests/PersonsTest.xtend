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
import net.dreiucker.decDescLanguage.Person

@RunWith(XtextRunner)
@InjectWith(DecDescLanguageInjectorProvider)
class PersonsTest {
	
		
	@Inject extension ParseHelper<Model>;
	@Inject extension ValidationTestHelper 
	
	@Test
	def void testInvalidAssumption() {
		val result = parse('''
			Person ''');
			
		assertNotNull(result);
		result.assertError(DecDescLanguagePackage::eINSTANCE.model, Diagnostic::SYNTAX_DIAGNOSTIC, "mismatched input '<EOF")
		assertEquals(0, result.definitions.size);
	}
	
	@Test
	def void testPerson() {
		val result = parse('''
			Person carl {
				
			}''');
			
		assertNotNull(result);
		result.assertError(DecDescLanguagePackage::eINSTANCE.person, Diagnostic::SYNTAX_DIAGNOSTIC, "mismatched input ")
		assertEquals(1, result.definitions.size);
	}
	
	@Test
	def void testMinimalPerson() {
		val result = parse('''
			Person alice {
				Name: "Alice"
			}''');
			
		assertNotNull(result);
		result.assertNoErrors
		assertEquals(1, result.definitions.size);
		val person = result.definitions.filter(Person).get(0)
		assertEquals("alice", person.name)
		assertEquals("\"Alice\"", person.fullname)
		assertNull(person.email)
		assertNull(person.role)
	}
	
	@Test
	def void testPersonRole() {
		val result = parse('''
			Person alice {
				Name: "Alice"
				Role: "foo"
			}''');
			
		assertNotNull(result);
		result.assertNoErrors
		assertEquals(1, result.definitions.size);
		val person = result.definitions.filter(Person).get(0)
		assertEquals("alice", person.name)
		assertEquals("\"Alice\"", person.fullname)
		assertNull(person.email)
		assertEquals("\"foo\"", person.role)
	}
	
	@Test
	def void testPersonEmail() {
		val result = parse('''
			Person alice {
				Name: "Alice"
				Email: "mail"
			}''');
			
		assertNotNull(result);
		result.assertNoErrors
		assertEquals(1, result.definitions.size);
		val person = result.definitions.filter(Person).get(0)
		assertEquals("alice", person.name)
		assertEquals("\"Alice\"", person.fullname)
		assertEquals("\"mail\"", person.email)
		assertNull(person.role)
	}
	
	
	@Test
	def void testPersonFull() {
		val result = parse('''
			Person alice {
				Name: "Alice"
				Email: "mail"
				Role: "foo"
			}''');
			
		assertNotNull(result);
		result.assertNoErrors
		assertEquals(1, result.definitions.size);
		val person = result.definitions.filter(Person).get(0)
		assertEquals("alice", person.name)
		assertEquals("\"Alice\"", person.fullname)
		assertEquals("\"mail\"", person.email)
		assertEquals("\"foo\"", person.role)
	}
	
	
	/**
	 * Order of role/email does not matter
	 */
	@Test
	def void testPersonFullSwapped() {
		val result = parse('''
			Person alice {
				Name: "Alice"
				Role: "foo"
				Email: "mail"
			}''');
			
		assertNotNull(result);
		result.assertNoErrors
		assertEquals(1, result.definitions.size);
		val person = result.definitions.filter(Person).get(0)
		assertEquals("alice", person.name)
		assertEquals("\"Alice\"", person.fullname)
		assertEquals("\"mail\"", person.email)
		assertEquals("\"foo\"", person.role)
	}
	
	
}