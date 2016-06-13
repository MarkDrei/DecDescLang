package net.dreiucker.decdesclanguage.reqif;

import org.eclipse.xtext.resource.generic.AbstractGenericResourceSupport;

import com.google.inject.Module;

/**
 * We need this to glue the other stuff to the MWE 2 workflow 
 * 
 * @author Mark
 *
 */
public class ReqifMweSupport extends AbstractGenericResourceSupport  {

	@Override
	protected Module createGuiceModule() {
		return new GuiceModule();
	}

}
