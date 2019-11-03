/*******************************************************************************
 * Copyright (C) 2016, Thomas Wolf <thomas.wolf@paranor.ch>
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License 2.0
 * which accompanies this distribution, and is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * SPDX-License-Identifier: EPL-2.0
 *******************************************************************************/
package org.eclipse.egit.ui.internal.commit;

import java.util.Arrays;
import java.util.regex.Pattern;

import org.eclipse.core.runtime.Assert;
import org.eclipse.egit.ui.internal.commit.DiffRegionFormatter.DiffRegion;
import org.eclipse.egit.ui.internal.commit.DiffRegionFormatter.FileDiffRegion;
import org.eclipse.egit.ui.internal.history.FileDiff;
import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.IDocumentPartitioner;
import org.eclipse.jface.text.Region;
import org.eclipse.jface.text.TextUtilities;
import org.eclipse.jface.text.rules.FastPartitioner;
import org.eclipse.jface.text.rules.IPartitionTokenScanner;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;
import org.eclipse.jgit.annotations.NonNull;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;

/**
 * A {@link Document} specialized for displaying unified diffs generated by a
 * {@link DiffRegionFormatter}. Intended usage is to create a DiffDocument, let
 * a DiffRegionFormatter generate into it, and then
 * {@link #connect(DiffRegionFormatter) connect()} the formatter. This will
 * partition the document into regions for file headlines, hunks, and added or
 * removed lines.
 */
public class DiffDocument extends Document {

	static final String HEADLINE_CONTENT_TYPE = "_egit_diff_headline"; //$NON-NLS-1$

	static final String HUNK_CONTENT_TYPE = "_egit_diff_hunk"; //$NON-NLS-1$

	static final String ADDED_CONTENT_TYPE = "_egit_diff_added"; //$NON-NLS-1$

	static final String REMOVED_CONTENT_TYPE = "_egit_diff_removed"; //$NON-NLS-1$

	private DiffRegion[] regions;

	private FileDiffRegion[] fileRegions;

	private Pattern newPathPattern;

	private Pattern oldPathPattern;

	private FileDiff defaultFileDiff;

	private int[] maximumLineNumbers;

	/**
	 * Creates a new {@link DiffDocument}.
	 */
	public DiffDocument() {
		super();
	}

	/**
	 * Creates a new {@link DiffDocument} with initial text.
	 *
	 * @param text
	 *            to set on the document
	 */
	public DiffDocument(String text) {
		super(text);
	}

	/**
	 * Sets up the document to use information from the given
	 * {@link DiffRegionFormatter} for partitioning the document into
	 * partitions for file headlines, hunk headers, and added or removed lines.
	 * It is assumed that the given formatter has been used to generate content
	 * into the document.
	 *
	 * @param formatter
	 *            to obtain information from
	 */
	public void connect(DiffRegionFormatter formatter) {
		regions = formatter.getRegions();
		fileRegions = formatter.getFileRegions();
		if (fileRegions == null || fileRegions.length == 0) {
			FileDiff implicitFileDiff = defaultFileDiff;
			if (implicitFileDiff != null) {
				fileRegions = new FileDiffRegion[] {
						new FileDiffRegion(implicitFileDiff, 0, getLength()) };
			}
		}
		newPathPattern = Pattern.compile(
				Pattern.quote(formatter.getNewPrefix()) + "\\S+"); //$NON-NLS-1$
		oldPathPattern = Pattern.compile(
				Pattern.quote(formatter.getOldPrefix()) + "\\S+"); //$NON-NLS-1$
		maximumLineNumbers = formatter.getMaximumLineNumbers();
		// Connect a new partitioner.
		IDocumentPartitioner partitioner = new FastPartitioner(
				new DiffPartitionTokenScanner(),
				new String[] { IDocument.DEFAULT_CONTENT_TYPE,
						HEADLINE_CONTENT_TYPE, HUNK_CONTENT_TYPE,
						ADDED_CONTENT_TYPE, REMOVED_CONTENT_TYPE });
		IDocumentPartitioner oldPartitioner = getDocumentPartitioner();
		if (oldPartitioner != null) {
			oldPartitioner.disconnect();
		}
		partitioner.connect(this);
		setDocumentPartitioner(partitioner);
	}

	/**
	 * Provide default settings about the {@link Repository} and
	 * {@link FileDiff}, to be used in the absence of explicit information from
	 * a connected {@link DiffRegionFormatter}. Useful if the document is
	 * used for only individual edits from a file.
	 *
	 * @param fileDiff
	 *            to use if none set explicitly
	 */
	public void setDefault(@NonNull FileDiff fileDiff) {
		defaultFileDiff = fileDiff;
	}

	DiffRegion[] getRegions() {
		return regions;
	}

	FileDiffRegion[] getFileRegions() {
		return fileRegions;
	}

	int getMaximumLineNumber(@NonNull DiffEntry.Side side) {
		if (maximumLineNumbers == null) {
			return DiffRegion.NO_LINE;
		}
		if (DiffEntry.Side.OLD.equals(side)) {
			return maximumLineNumbers[0];
		}
		return maximumLineNumbers[1];
	}

	private int findRegionIndex(int offset) {
		DiffRegion key = new DiffRegion(offset, 0);
		return Arrays.binarySearch(regions, key, (a, b) -> {
			if (!TextUtilities.overlaps(a, b)) {
				return a.getOffset() - b.getOffset();
			}
			return 0;
		});
	}

	DiffRegion findRegion(int offset) {
		int i = findRegionIndex(offset);
		return i >= 0 ? regions[i] : null;
	}

	FileDiffRegion findFileRegion(int offset) {
		Region key = new Region(offset, 0);
		int i = Arrays.binarySearch(fileRegions, key, (a, b) -> {
			if (!TextUtilities.overlaps(a, b)) {
				return a.getOffset() - b.getOffset();
			}
			return 0;
		});
		return i >= 0 ? fileRegions[i] : null;
	}

	int getLogicalLine(int physicalLine, @NonNull DiffEntry.Side side) {
		int offset;
		try {
			offset = getLineOffset(physicalLine);
			DiffRegion region = findRegion(offset);
			if (region == null) {
				return DiffRegion.NO_LINE;
			}
			int logicalStart = region.getLine(side);
			if (logicalStart == DiffRegion.NO_LINE) {
				return DiffRegion.NO_LINE;
			}
			int physicalStart = getLineOfOffset(region.getOffset());
			return logicalStart + (physicalLine - physicalStart);
		} catch (BadLocationException e) {
			return DiffRegion.NO_LINE;
		}
	}

	Pattern getPathPattern(@NonNull DiffEntry.Side side) {
		switch (side) {
		case OLD:
			return oldPathPattern;
		default:
			return newPathPattern;
		}
	}

	private class DiffPartitionTokenScanner implements IPartitionTokenScanner {

		private final Token HEADLINE_TOKEN = new Token(HEADLINE_CONTENT_TYPE);

		private final Token HUNK_TOKEN = new Token(HUNK_CONTENT_TYPE);

		private final Token ADDED_TOKEN = new Token(ADDED_CONTENT_TYPE);

		private final Token DELETED_TOKEN = new Token(REMOVED_CONTENT_TYPE);

		private final Token OTHER_TOKEN = new Token(
				IDocument.DEFAULT_CONTENT_TYPE);

		private int currentOffset;

		private int end;

		private int tokenStart;

		private int currIdx;

		@Override
		public void setRange(IDocument document, int offset, int length) {
			Assert.isLegal(document == DiffDocument.this);
			currentOffset = offset;
			end = offset + length;
			tokenStart = -1;
		}

		@Override
		public IToken nextToken() {
			if (tokenStart < 0) {
				currIdx = findRegionIndex(currentOffset);
				if (currIdx < 0) {
					currIdx = -(currIdx + 1);
				}
			}
			tokenStart = currentOffset;
			if (currentOffset < end) {
				if (currIdx >= DiffDocument.this.regions.length) {
					currentOffset = end;
					return OTHER_TOKEN;
				}
				if (currentOffset < DiffDocument.this.regions[currIdx]
						.getOffset()) {
					currentOffset = DiffDocument.this.regions[currIdx]
							.getOffset();
					return OTHER_TOKEN;
				}
				// We're in range[currIdx]. Typically at the beginning, but if
				// called via setPartialRange, we may also be somewhere in the
				// middle.
				currentOffset += DiffDocument.this.regions[currIdx].getLength()
						- (currentOffset
								- DiffDocument.this.regions[currIdx]
										.getOffset());
				switch (DiffDocument.this.regions[currIdx++].getType()) {
				case HEADLINE:
					return HEADLINE_TOKEN;
				case HUNK:
					return HUNK_TOKEN;
				case ADD:
					return ADDED_TOKEN;
				case REMOVE:
					return DELETED_TOKEN;
				default:
					return OTHER_TOKEN;
				}
			}
			return Token.EOF;
		}

		@Override
		public int getTokenOffset() {
			return tokenStart;
		}

		@Override
		public int getTokenLength() {
			return currentOffset - tokenStart;
		}

		@Override
		public void setPartialRange(IDocument document, int offset, int length,
				String contentType, int partitionOffset) {
			setRange(document, offset, length);
		}

	}
}
