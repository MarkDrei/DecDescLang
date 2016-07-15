package net.dreiucker.decdesclanguage.tracebility.data;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.common.util.URI;
import org.eclipse.nebula.widgets.nattable.data.IDataProvider;
import org.eclipse.xtext.util.Pair;
import org.eclipse.xtext.util.Tuples;

public class BodyDataProvider implements IDataProvider {
	
	List<String> requirementColumnHeaders = new ArrayList<>();
	List<String> decisionRowHeaders = new ArrayList<>();
	
	/**
	 * (Index of decision / index of Requirement) -> True if there is a usage of that requirement 
	 */
	Map<Pair<Integer, Integer>, Boolean> decisionRefersToReq = new HashMap<>();;
	
	// Buffers the relation from "ddl decision" to the file path it is contained in
	Map<String, URI> decisionsToFiles = new HashMap<>();
	
	// Buffers the relation from reqif requirements to the file path it is contained in
	Map<String, java.net.URI> requirementsToFiles = new HashMap<>();
	
	public BodyDataProvider() {
	}
	
	@Override
	public int getColumnCount() {
		return requirementColumnHeaders.size();
	}
	
	@Override
	public int getRowCount() {
		return decisionRowHeaders.size();
	}
	

	@Override
	public Object getDataValue(int columnIndex, int rowIndex) {
		Boolean hasReference = decisionRefersToReq.get(Tuples.create(Integer.valueOf(rowIndex), Integer.valueOf(columnIndex)));
		if (hasReference != null && hasReference.equals(Boolean.TRUE)) {
			return "hasReference";
		}
		return "";
	}

	@Override
	public void setDataValue(int columnIndex, int rowIndex, Object newValue) {
		// not supported
	}

	public String[] getColumnHeaders() {
		return requirementColumnHeaders.toArray(new String[requirementColumnHeaders.size()]);
	}
	
	public String[] getRowHeaders() {
		return decisionRowHeaders.toArray(new String[decisionRowHeaders.size()]);
	}

	public URI getDecisionUri(String decision) {
		return decisionsToFiles.get(decision);
	}
	
	public java.net.URI getRequirementsUri(String requirement) {
		return requirementsToFiles.get(requirement);
	}
	
	
	
}
