/**
 */
package toreference.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import toreference.Collection;
import toreference.ToreferenceFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Collection</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class CollectionTest extends TestCase {

	/**
	 * The fixture for this Collection test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(CollectionTest.class);
	}

	/**
	 * Constructs a new Collection test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public CollectionTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Collection test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(Collection fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Collection test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected Collection getFixture() {
		return fixture;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#setUp()
	 * @generated
	 */
	@Override
	protected void setUp() throws Exception {
		setFixture(ToreferenceFactory.eINSTANCE.createCollection());
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see junit.framework.TestCase#tearDown()
	 * @generated
	 */
	@Override
	protected void tearDown() throws Exception {
		setFixture(null);
	}

} //CollectionTest
