package net.dreiucker.decdesclanguage.reqif.ui;

import org.apache.log4j.Logger;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.eclipse.xtext.ui.shared.SharedStateModule;
import org.osgi.framework.BundleContext;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.util.Modules;
import com.google.inject.util.Modules.OverriddenModuleBuilder;

import net.dreiucker.decdesclanguage.reqif.GuiceModule;

/**
 * The activator class controls the plug-in life cycle
 */
public class Activator extends AbstractUIPlugin {

	private static final Logger logger = Logger.getLogger(Activator.class);
	
	// The plug-in ID
	public static final String PLUGIN_ID = "net.dreiucker.DecDescLanguage.ReqIf.ui"; //$NON-NLS-1$

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
	
	/*
	 * (non-Javadoc)
	 * @see org.eclipse.ui.plugin.AbstractUIPlugin#start(org.osgi.framework.BundleContext)
	 */
	public void start(BundleContext context) throws Exception {
		super.start(context);
		plugin = this;
		try {
			initializeReqifInjector();
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw e;
		}
	}
	
	private void initializeReqifInjector() {
		GuiceModule guiceModule = new GuiceModule();
		ReqifUiModule reqifUiModule = new ReqifUiModule(plugin);
		
		Module module = Modules.override(guiceModule).with(reqifUiModule);
		
		SharedStateModule sharedStateModule = new SharedStateModule();
		Module finalModule = Modules.override(module).with(sharedStateModule);
		injector = Guice.createInjector(finalModule);
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
