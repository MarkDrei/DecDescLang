/**
 */
package toreference;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;

/**
 * <!-- begin-user-doc -->
 * The <b>Package</b> for the model.
 * It contains accessors for the meta objects to represent
 * <ul>
 *   <li>each class,</li>
 *   <li>each feature of each class,</li>
 *   <li>each operation of each class,</li>
 *   <li>each enum,</li>
 *   <li>and each data type</li>
 * </ul>
 * <!-- end-user-doc -->
 * @see toreference.ToreferenceFactory
 * @model kind="package"
 * @generated
 */
public interface ToreferencePackage extends EPackage {
	/**
	 * The package name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNAME = "toreference";

	/**
	 * The package namespace URI.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_URI = "http://dreiucker.net/toreference";

	/**
	 * The package namespace name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	String eNS_PREFIX = "toreference";

	/**
	 * The singleton instance of the package.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	ToreferencePackage eINSTANCE = toreference.impl.ToreferencePackageImpl.init();

	/**
	 * The meta object id for the '{@link toreference.impl.SomeClassImpl <em>Some Class</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see toreference.impl.SomeClassImpl
	 * @see toreference.impl.ToreferencePackageImpl#getSomeClass()
	 * @generated
	 */
	int SOME_CLASS = 0;

	/**
	 * The feature id for the '<em><b>Id</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOME_CLASS__ID = 0;

	/**
	 * The feature id for the '<em><b>Whatever2</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOME_CLASS__WHATEVER2 = 1;

	/**
	 * The number of structural features of the '<em>Some Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOME_CLASS_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Some Class</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOME_CLASS_OPERATION_COUNT = 0;


	/**
	 * Returns the meta object for class '{@link toreference.SomeClass <em>Some Class</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Some Class</em>'.
	 * @see toreference.SomeClass
	 * @generated
	 */
	EClass getSomeClass();

	/**
	 * Returns the meta object for the attribute '{@link toreference.SomeClass#getId <em>Id</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Id</em>'.
	 * @see toreference.SomeClass#getId()
	 * @see #getSomeClass()
	 * @generated
	 */
	EAttribute getSomeClass_Id();

	/**
	 * Returns the meta object for the attribute '{@link toreference.SomeClass#getWhatever2 <em>Whatever2</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Whatever2</em>'.
	 * @see toreference.SomeClass#getWhatever2()
	 * @see #getSomeClass()
	 * @generated
	 */
	EAttribute getSomeClass_Whatever2();

	/**
	 * Returns the factory that creates the instances of the model.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the factory that creates the instances of the model.
	 * @generated
	 */
	ToreferenceFactory getToreferenceFactory();

	/**
	 * <!-- begin-user-doc -->
	 * Defines literals for the meta objects that represent
	 * <ul>
	 *   <li>each class,</li>
	 *   <li>each feature of each class,</li>
	 *   <li>each operation of each class,</li>
	 *   <li>each enum,</li>
	 *   <li>and each data type</li>
	 * </ul>
	 * <!-- end-user-doc -->
	 * @generated
	 */
	interface Literals {
		/**
		 * The meta object literal for the '{@link toreference.impl.SomeClassImpl <em>Some Class</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see toreference.impl.SomeClassImpl
		 * @see toreference.impl.ToreferencePackageImpl#getSomeClass()
		 * @generated
		 */
		EClass SOME_CLASS = eINSTANCE.getSomeClass();

		/**
		 * The meta object literal for the '<em><b>Id</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOME_CLASS__ID = eINSTANCE.getSomeClass_Id();

		/**
		 * The meta object literal for the '<em><b>Whatever2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOME_CLASS__WHATEVER2 = eINSTANCE.getSomeClass_Whatever2();

	}

} //ToreferencePackage
