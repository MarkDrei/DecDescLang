/**
 */
package toreference;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;

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
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int SOME_CLASS__NAME = 0;

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
	 * The meta object id for the '{@link toreference.impl.CollectionImpl <em>Collection</em>}' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see toreference.impl.CollectionImpl
	 * @see toreference.impl.ToreferencePackageImpl#getCollection()
	 * @generated
	 */
	int COLLECTION = 1;

	/**
	 * The feature id for the '<em><b>Children</b></em>' containment reference list.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLLECTION__CHILDREN = 0;

	/**
	 * The feature id for the '<em><b>Name</b></em>' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLLECTION__NAME = 1;

	/**
	 * The number of structural features of the '<em>Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLLECTION_FEATURE_COUNT = 2;

	/**
	 * The number of operations of the '<em>Collection</em>' class.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 * @ordered
	 */
	int COLLECTION_OPERATION_COUNT = 0;


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
	 * Returns the meta object for the attribute '{@link toreference.SomeClass#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see toreference.SomeClass#getName()
	 * @see #getSomeClass()
	 * @generated
	 */
	EAttribute getSomeClass_Name();

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
	 * Returns the meta object for class '{@link toreference.Collection <em>Collection</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for class '<em>Collection</em>'.
	 * @see toreference.Collection
	 * @generated
	 */
	EClass getCollection();

	/**
	 * Returns the meta object for the containment reference list '{@link toreference.Collection#getChildren <em>Children</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the containment reference list '<em>Children</em>'.
	 * @see toreference.Collection#getChildren()
	 * @see #getCollection()
	 * @generated
	 */
	EReference getCollection_Children();

	/**
	 * Returns the meta object for the attribute '{@link toreference.Collection#getName <em>Name</em>}'.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @return the meta object for the attribute '<em>Name</em>'.
	 * @see toreference.Collection#getName()
	 * @see #getCollection()
	 * @generated
	 */
	EAttribute getCollection_Name();

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
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOME_CLASS__NAME = eINSTANCE.getSomeClass_Name();

		/**
		 * The meta object literal for the '<em><b>Whatever2</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute SOME_CLASS__WHATEVER2 = eINSTANCE.getSomeClass_Whatever2();

		/**
		 * The meta object literal for the '{@link toreference.impl.CollectionImpl <em>Collection</em>}' class.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @see toreference.impl.CollectionImpl
		 * @see toreference.impl.ToreferencePackageImpl#getCollection()
		 * @generated
		 */
		EClass COLLECTION = eINSTANCE.getCollection();

		/**
		 * The meta object literal for the '<em><b>Children</b></em>' containment reference list feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EReference COLLECTION__CHILDREN = eINSTANCE.getCollection_Children();

		/**
		 * The meta object literal for the '<em><b>Name</b></em>' attribute feature.
		 * <!-- begin-user-doc -->
		 * <!-- end-user-doc -->
		 * @generated
		 */
		EAttribute COLLECTION__NAME = eINSTANCE.getCollection_Name();

	}

} //ToreferencePackage
