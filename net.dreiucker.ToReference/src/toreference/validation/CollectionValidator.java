/**
 *
 * $Id$
 */
package toreference.validation;

import org.eclipse.emf.common.util.EList;
import toreference.SomeClass;

/**
 * A sample validator interface for {@link toreference.Collection}.
 * This doesn't really do anything, and it's not a real EMF artifact.
 * It was generated by the org.eclipse.emf.examples.generator.validator plug-in to illustrate how EMF's code generator can be extended.
 * This can be disabled with -vmargs -Dorg.eclipse.emf.examples.generator.validator=false.
 */
public interface CollectionValidator {
	boolean validate();

	boolean validateChildren(EList<SomeClass> value);

	boolean validateName(String value);

	boolean validateChildren(SomeClass value);
}