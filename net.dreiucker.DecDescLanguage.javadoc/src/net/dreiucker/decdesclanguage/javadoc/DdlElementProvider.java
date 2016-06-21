package net.dreiucker.decdesclanguage.javadoc;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.xtext.resource.impl.ResourceDescriptionsProvider;

import com.google.inject.Inject;

import net.dreiucker.javadocextender.extensionpoint.IElementProvider;

public class DdlElementProvider implements IElementProvider {

	private final static Set<String> VALID_TAGS;
	
	static {
		VALID_TAGS = new HashSet<>();
		VALID_TAGS.add("requirement");
	}
	
	@Inject
	private ResourceDescriptionsProvider resourceDescriptionsProvider;

	@Override
	public Set<String> getValidTags() {
		return VALID_TAGS;
	}

	@Override
	public Set<String> getKnownElements(String tag) {
		if (resourceDescriptionsProvider == null) {
			System.out.println("MDD: resource provider is null");
		} else {
			System.out.println("MDD: resource provider is NOT null, yeah!");
		}
		return new HashSet<String>();
	}

	@Override
	public boolean unknownElementsAllowed(String tag) {
		return false;
	}

}
