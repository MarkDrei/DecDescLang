/*
 * generated by Xtext 2.9.2
 */
package net.dreiucker.tests

import com.google.inject.Inject
import net.dreiucker.decDescLanguage.Model
import org.eclipse.xtext.junit4.InjectWith
import org.eclipse.xtext.junit4.XtextRunner
import org.eclipse.xtext.junit4.util.ParseHelper
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(XtextRunner)
@InjectWith(DecDescLanguageInjectorProvider)
class DecDescLanguageParsingTest{

	@Inject
	ParseHelper<Model> parseHelper;

	@Test 
	def void loadModel() {
		val result = parseHelper.parse('''
			Assumption "asd"
		''')
		Assert.assertNotNull(result)
	}

}
