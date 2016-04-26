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
 *   <li>{@link toreference.SomeClass#getId <em>Id</em>}</li>
 *   <li>{@link toreference.SomeClass#getWhatever2 <em>Whatever2</em>}</li>
 * </ul>
 *
 * @see toreference.ToreferencePackage#getSomeClass()
 * @model
 * @generated
 */
public interface SomeClass extends EObject {
	/**
	 * Returns the value of the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <p>
	 * If the meaning of the '<em>Id</em>' attribute isn't clear,
	 * there really should be more of a description here...
	 * </p>
	 * <!-- end-user-doc -->
	 * @return the value of the '<em>Id</em>' attribute.
	 * @see #setId(String)
	 * @see toreference.ToreferencePackage#getSomeClass_Id()
	 * @model id="true" required="true"
	 * @generated
	 */
	String getId();

	/**
	 * Sets the value of the '{@link toreference.SomeClass#getId <em>Id</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @param value the new value of the '<em>Id</em>' attribute.
	 * @see #getId()
	 * @generated
	 */
	void setId(String value);

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
