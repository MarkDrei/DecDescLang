package net.dreiucker.decdesclanguage.toreference.ui;

import org.apache.log4j.Logger;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.shared.SharedStateModule;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import com.google.inject.util.Modules.OverriddenModuleBuilder;

import net.dreiucker.decdesclanguage.toreference.ReferenceGuiceModule;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	private static final Logger logger = Logger.getLogger(Activator.class);
	
	// The plug-in ID
	public static final String PLUGIN_ID = "net.dreiucker.DecDescLanguage.ToReference.ui"; //$NON-NLS-1$

	// The shared instance
	private static Activator plugin;
	
	private Injector injector;
	
	/**
	 * The constructor
	 */
	public Activator() {
	}
	
	public Injector getInjector() {
		return injector;
	}
	
	private void tutNix(Module... modules) {
		System.out.println("Keine exception??");
		// no op
	}
	
	private void crash(Module module)
	{
		if (module instanceof Module) {
			System.out.println("Du kommst hier net rein!");
		}
		tutNix(module);
	}

	private void initializeEcoreInjector() {
		ReferenceGuiceModule referenceGuiceModule = new ReferenceGuiceModule();
		ToreferenceUiModule toreferenceUiModule = new ToreferenceUiModule(plugin);

		Module tmp = referenceGuiceModule;
		try {
			crash(tmp);
		} catch (ArrayStoreException e) {
			System.err.println(e.toString());
			e.printStackTrace();
		}
		OverriddenModuleBuilder override = Modules.override(referenceGuiceModule);
		Module module = override.with(toreferenceUiModule);
		module = Modules.override(referenceGuiceModule).with(toreferenceUiModule);
		
		
		SharedStateModule sharedStateModule = new SharedStateModule();
		Module finalModule = Modules.override(module).with(sharedStateModule);
	
		
		injector = Guice.createInjector(finalModule);
		
//		injector = Guice.createInjector(
//				Modules.override(
//						Modules.override(new ReferenceGuiceModule()).with(new ToreferenceUiModule(plugin))
//						).with(new SharedStateModule()));
	}
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		try {
			initializeEcoreInjector();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#stop(org.osgi.framework.BundleContext)
	 */
	public void stop(BundleContext context) throws Exception {
		plugin = null;
		injector = null;
		super.stop(context);
	}

	/**
	 * Returns the shared instance
	 *
	 * @return the shared instance
	 */
	public static Activator getDefault() {
		return plugin;
	}

}
