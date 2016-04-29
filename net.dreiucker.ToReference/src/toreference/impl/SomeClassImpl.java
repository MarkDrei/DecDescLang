/**
 */
package toreference.impl;

import org.eclipse.emf.common.notify.Notification;

import org.eclipse.emf.ecore.EClass;

import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.MinimalEObjectImpl;

import toreference.SomeClass;
import toreference.ToreferencePackage;

/**
 * <!-- begin-user-doc -->
 * An implementation of the model object '<em><b>Some Class</b></em>'.
 * <!-- end-user-doc -->
 * <p>
 * The following features are implemented:
 * </p>
 * <ul>
 *   <li>{@link toreference.impl.SomeClassImpl#getName <em>Name</em>}</li>
 *   <li>{@link toreference.impl.SomeClassImpl#getWhatever2 <em>Whatever2</em>}</li>
 * </ul>
 *
 * @generated
 */
public class SomeClassImpl extends MinimalEObjectImpl.Container implements SomeClass {
	/**
	 * The default value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected static final String NAME_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getName() <em>Name</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getName()
	 * @generated
	 * @ordered
	 */
	protected String name = NAME_EDEFAULT;

	/**
	 * The default value of the '{@link #getWhatever2() <em>Whatever2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWhatever2()
	 * @generated
	 * @ordered
	 */
	protected static final String WHATEVER2_EDEFAULT = null;

	/**
	 * The cached value of the '{@link #getWhatever2() <em>Whatever2</em>}' attribute.
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @see #getWhatever2()
	 * @generated
	 * @ordered
	 */
	protected String whatever2 = WHATEVER2_EDEFAULT;

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	protected SomeClassImpl() {
		super();
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	protected EClass eStaticClass() {
		return ToreferencePackage.Literals.SOME_CLASS;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getName() {
		return name;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setName(String newName) {
		String oldName = name;
		name = newName;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ToreferencePackage.SOME_CLASS__NAME, oldName, name));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public String getWhatever2() {
		return whatever2;
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	public void setWhatever2(String newWhatever2) {
		String oldWhatever2 = whatever2;
		whatever2 = newWhatever2;
		if (eNotificationRequired())
			eNotify(new ENotificationImpl(this, Notification.SET, ToreferencePackage.SOME_CLASS__WHATEVER2, oldWhatever2, whatever2));
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public Object eGet(int featureID, boolean resolve, boolean coreType) {
		switch (featureID) {
			case ToreferencePackage.SOME_CLASS__NAME:
				return getName();
			case ToreferencePackage.SOME_CLASS__WHATEVER2:
				return getWhatever2();
		}
		return super.eGet(featureID, resolve, coreType);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eSet(int featureID, Object newValue) {
		switch (featureID) {
			case ToreferencePackage.SOME_CLASS__NAME:
				setName((String)newValue);
				return;
			case ToreferencePackage.SOME_CLASS__WHATEVER2:
				setWhatever2((String)newValue);
				return;
		}
		super.eSet(featureID, newValue);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public void eUnset(int featureID) {
		switch (featureID) {
			case ToreferencePackage.SOME_CLASS__NAME:
				setName(NAME_EDEFAULT);
				return;
			case ToreferencePackage.SOME_CLASS__WHATEVER2:
				setWhatever2(WHATEVER2_EDEFAULT);
				return;
		}
		super.eUnset(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public boolean eIsSet(int featureID) {
		switch (featureID) {
			case ToreferencePackage.SOME_CLASS__NAME:
				return NAME_EDEFAULT == null ? name != null : !NAME_EDEFAULT.equals(name);
			case ToreferencePackage.SOME_CLASS__WHATEVER2:
				return WHATEVER2_EDEFAULT == null ? whatever2 != null : !WHATEVER2_EDEFAULT.equals(whatever2);
		}
		return super.eIsSet(featureID);
	}

	/**
	 * <!-- begin-user-doc -->
	 * <!-- end-user-doc -->
	 * @generated
	 */
	@Override
	public String toString() {
		if (eIsProxy()) return super.toString();

		StringBuffer result = new StringBuffer(super.toString());
		result.append(" (name: ");
		result.append(name);
		result.append(", Whatever2: ");
		result.append(whatever2);
		result.append(')');
		return result.toString();
	}

} //SomeClassImpl
