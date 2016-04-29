/**
 */
package toreference.tests;

import junit.framework.TestCase;

import junit.textui.TestRunner;

import toreference.SomeClass;
import toreference.ToreferenceFactory;

/**
 * <!-- begin-user-doc -->
 * A test case for the model object '<em><b>Some Class</b></em>'.
 * <!-- end-user-doc -->
 * @generated
 */
public class SomeClassTest extends TestCase {

	/**
	 * The fixture for this Some Class test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SomeClass fixture = null;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public static void main(String[] args) {
		TestRunner.run(SomeClassTest.class);
	}

	/**
	 * Constructs a new Some Class test case with the given name.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public SomeClassTest(String name) {
		super(name);
	}

	/**
	 * Sets the fixture for this Some Class test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected void setFixture(SomeClass fixture) {
		this.fixture = fixture;
	}

	/**
	 * Returns the fixture for this Some Class test case.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SomeClass getFixture() {
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
		setFixture(ToreferenceFactory.eINSTANCE.createSomeClass());
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

} //SomeClassTest
