package net.dreiucker.bughunt;

import net.dreiucker.decdesclanguage.toreference.ReferenceGuiceModule;
import com.google.inject.Module;

public class BugMain {

	public static void main(String[] args) {
		ReferenceGuiceModule referenceGuiceModule = new ReferenceGuiceModule();
		
		if (referenceGuiceModule instanceof Module) {
			System.out.println("Is instance of");
		} else {
			System.out.println("Isses nich");
		}

	}

}
