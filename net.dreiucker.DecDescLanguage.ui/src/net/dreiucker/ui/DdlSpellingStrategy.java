package net.dreiucker.ui;

import static org.eclipse.xtext.ui.editor.model.TerminalsTokenTypeToPartitionMapper.STRING_LITERAL_PARTITION;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.ITypedRegion;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.TypedRegion;
import org.eclipse.jface.text.source.ISourceViewer;
import org.eclipse.xtext.ui.editor.model.ITokenTypeToPartitionTypeMapperExtension;
import org.eclipse.xtext.ui.editor.reconciler.XtextSpellingReconcileStrategy;

import com.google.inject.Inject;

public class DdlSpellingStrategy extends XtextSpellingReconcileStrategy {
	
	public static class Factory extends XtextSpellingReconcileStrategy.Factory {
		
		@Inject
		private ITokenTypeToPartitionTypeMapperExtension partitionMapperExtension;
		
		@Override
		public XtextSpellingReconcileStrategy create(ISourceViewer sourceViewer) {
			DdlSpellingStrategy result = new DdlSpellingStrategy(sourceViewer);
			result.setPartitionMapperExtension(partitionMapperExtension);
			return result;
		}
	}

	protected DdlSpellingStrategy(ISourceViewer viewer) {
		super(viewer);
	}
	
	@Override
	protected ITypedRegion[] computePartitioning(int offset, int length, String partitionType) {
		List<ITypedRegion> result = new ArrayList<>();
		ITypedRegion[] allRegions = new ITypedRegion[0];
		try {
			// if "getDocument()" gives an error in eclipse, then the access rules must be adapted or
			//  the error has to be changed into a warning
			allRegions = TextUtilities.computePartitioning(getDocument(), partitionType, offset, length, false);
		} catch (BadLocationException x) {
		}
		for (int i = 0; i < allRegions.length; i++) {
			if (shouldProcess(allRegions[i])) {
				result.add(allRegions[i]);
			} else {
				shouldProcess(result, allRegions[i]);
			}
		}
		return result.toArray(new ITypedRegion[result.size()]);
	}

	private void shouldProcess(List<ITypedRegion> result, ITypedRegion region) {
		// This region might contain several interesting sub-regions, 
		//  thus might need to add multiple regions 
		String string;
		try {
			string = getDocument().get(region.getOffset(), region.getLength());
			int indexOfQuote = string.indexOf("'");
			if (indexOfQuote >= 0) {
				int currentOffset = region.getOffset() + indexOfQuote + 1; // +1 because we skip the quote ( ' )
				string = string.substring(indexOfQuote + 1);
				
				String[] strings = string.split("'");
				for(int indexIntoString = 0; indexIntoString < strings.length; indexIntoString++)
				{
					int stringLength = strings[indexIntoString].length();
					// step width of two: we always have "string" -> "none string" -> "string" ...
					if (indexIntoString % 2 == 0 && stringLength > 0) {
						result.add(new TypedRegion(currentOffset, stringLength, STRING_LITERAL_PARTITION));
						// TODO remove
//						System.out.println(" MDD Adding region: " + strings[indexIntoString]);
					}
					currentOffset += stringLength + 1; // +1 because we skip the quote ( ' )
				}
			}
		} catch (BadLocationException e) {
		}
	}
	

}
