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
	
	List<String> requirementRowHeaders = new ArrayList<>();
	List<String> decisionColumnHeaders = new ArrayList<>();
	
	/**
	 * (Index of decision / index of Requirement) -> True if there is a usage of that requirement 
	 */
	Map<Pair<Integer, Integer>, URI> requirementReferedToByDec = new HashMap<>();;
	
	// Buffers the relation from "ddl decision" to the file path it is contained in
	Map<String, URI> decisionsToFiles = new HashMap<>();
	
	// Buffers the relation from reqif requirements to the file path it is contained in
	Map<String, java.net.URI> requirementsToFiles = new HashMap<>();
	
	public BodyDataProvider() {
	}
	
	@Override
	public int getColumnCount() {
		return decisionColumnHeaders.size();
	}
	
	@Override
	public int getRowCount() {
		return requirementRowHeaders.size();
	}
	

	@Override
	public Object getDataValue(int columnIndex, int rowIndex) {
		URI hasReference = requirementReferedToByDec.get(Tuples.create(Integer.valueOf(rowIndex), Integer.valueOf(columnIndex)));
		if (hasReference != null) {
			return "referenced";
		}
		return "";
	}
	
	public URI getURI(int columnIndex, int rowIndex) {
		return requirementReferedToByDec.get(Tuples.create(Integer.valueOf(rowIndex), Integer.valueOf(columnIndex)));
	}

	@Override
	public void setDataValue(int columnIndex, int rowIndex, Object newValue) {
		// not supported
	}

	public String[] getColumnHeaders() {
		return decisionColumnHeaders.toArray(new String[decisionColumnHeaders.size()]);
	}
	
	public String[] getRowHeaders() {
		return requirementRowHeaders.toArray(new String[requirementRowHeaders.size()]);
	}

	public URI getDecisionUri(String decision) {
		return decisionsToFiles.get(decision);
	}
	
	public java.net.URI getRequirementsUri(String requirement) {
		return requirementsToFiles.get(requirement);
	}
	
	
	
}
