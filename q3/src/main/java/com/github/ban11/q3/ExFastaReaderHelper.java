package com.github.ban11.q3;

import java.io.File;
import java.io.InputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.LinkedHashMap;

import org.biojava.nbio.core.sequence.DNASequence;
import org.biojava.nbio.core.sequence.io.FastaReaderHelper;
import org.biojava.nbio.core.sequence.compound.AmbiguityDNACompoundSet;
import org.biojava.nbio.core.sequence.compound.NucleotideCompound;
import org.biojava.nbio.core.sequence.io.DNASequenceCreator;
import org.biojava.nbio.core.sequence.io.FastaReader;
import org.biojava.nbio.core.sequence.io.GenericFastaHeaderParser;

public class ExFastaReaderHelper extends FastaReaderHelper {

	public static LinkedHashMap<String, DNASequence> readFastaAmDNASequence(
			InputStream inStream) throws IOException {
		FastaReader<DNASequence, NucleotideCompound> fastaReader = new FastaReader<DNASequence, NucleotideCompound>(
				inStream,
				new GenericFastaHeaderParser<DNASequence, NucleotideCompound>(),
				new DNASequenceCreator(AmbiguityDNACompoundSet.getDNACompoundSet()));
		return fastaReader.process();
	}
	/**
	 *
	 * @param file
	 * @return
	 * @throws IOException
	 */
	public static LinkedHashMap<String, DNASequence> readFastaAmDNASequence(
			File file) throws IOException {
		FileInputStream inStream = new FileInputStream(file);
		LinkedHashMap<String, DNASequence> dnaSequences = readFastaAmDNASequence(inStream);
		inStream.close();
		return dnaSequences;
	}
}



