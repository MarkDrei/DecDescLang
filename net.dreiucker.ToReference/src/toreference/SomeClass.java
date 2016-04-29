/**
 */
package toreference;

import org.eclipse.emf.ecore.EObject;

/**
 * <!-- begin-user-doc -->
 * A representation of the model object '<em><b>Some Class</b></em>'.
 * <!-- end-user-doc -->
 *
 * <p>
 * The following features are supported:
 * </p>
 * <ul>
 *   <li>{@link toreference.SomeClass#getName <em>Name</em>}</li>
 *   <li>{@link toreference.SomeClass#getWhatever2 <em>Whatever2</em>}</li>
 * </ul>
 *
 * @see toreference.ToreferencePackage#getSomeClass()
 * @model
 * @generated
 */
public interface SomeClass extends EObject {
	/**
	 * Returns the value of the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Name</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Name</em>' attribute.
	 * @see #setName(String)
	 * @see toreference.ToreferencePackage#getSomeClass_Name()
	 * @model required="true"
	 * @generated
	 */
	String getName();

	/**
	 * Sets the value of the '{@link toreference.SomeClass#getName <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Name</em>' attribute.
	 * @see #getName()
	 * @generated
	 */
	void setName(String value);

	/**
	 * Returns the value of the '<em><b>Whatever2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Whatever2</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Whatever2</em>' attribute.
	 * @see #setWhatever2(String)
	 * @see toreference.ToreferencePackage#getSomeClass_Whatever2()
	 * @model
	 * @generated
	 */
	String getWhatever2();

	/**
	 * Sets the value of the '{@link toreference.SomeClass#getWhatever2 <em>Whatever2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Whatever2</em>' attribute.
	 * @see #getWhatever2()
	 * @generated
	 */
	void setWhatever2(String value);

} // SomeClass
