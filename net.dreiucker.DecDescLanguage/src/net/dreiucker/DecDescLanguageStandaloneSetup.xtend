/*
 * generated by Xtext 2.9.2
 */
package net.dreiucker


/**
 * Initialization support for running Xtext languages without Equinox extension registry.
 */
class DecDescLanguageStandaloneSetup extends DecDescLanguageStandaloneSetupGenerated {

	def static void doSetup() {
		new DecDescLanguageStandaloneSetup().createInjectorAndDoEMFRegistration()
	}
}
