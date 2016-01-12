package controller;

import java.util.ArrayList;

import launcher.GlobalValues;
import model.DNA;
import model.DNABase;

public class DNAController {
	
	public static DNA mixDNA(DNA dna1, DNA dna2) {
		if(dna1.getLength() > dna2.getLength()) {
			DNA help = dna1;
			dna1 = dna2;
			dna2 = help;
		}
		
		//int length = (Math.random() > 0.5)? dna1.getLength(): dna2.getLength();
		int length = dna1.getLength();
		//DNABase[] newSequenz = new DNABase[length];
		StringBuilder newSequenz = new StringBuilder();
		
		for(int i = 0; i < length; i ++) {
			newSequenz.append( (Math.random() > 0.5) ? dna1.getSequenz().charAt(i) : dna2.getSequenz().charAt(i) );
		}
		int l2 = length;
		for(int i = 0; i < dna2.getLength() - length; i++) {
			if(Math.random() > 0.3) {
				newSequenz.append(dna2.getSequenz().charAt(i + length));
				l2++;
			}
			else
			{
				break;
			}
		}
		
		DNA result = new DNA(newSequenz.toString());
		return result;
	}
	
	public static DNA applyMutation(DNA dna) {
		// TODO baaaad
		StringBuilder newDNA = new StringBuilder(dna.getSequenz());
		int random = (int) Math.round(Math.random() * 100);
		if(random < GlobalValues.mutationDNAAppend) {
			int insertionPoint = (int)(Math.random() * dna.getLength());
			String newBase = DNABase.values()[(int) (Math.random() * DNABase.values().length)].toString();
			newDNA.insert(insertionPoint, newBase);
			
			/*
			String tmpDNA = dna.getSequenz();
			newDNA = new DNABase[tmpDNA.length+1];
			int insertionPoint = (int)(Math.random() * newDNA.length);
			DNABase newBase = DNABase.values()[(int) (Math.random() * DNABase.values().length)];
			System.arraycopy(tmpDNA, 0, newDNA, 0, tmpDNA.length);
			for(int i = newDNA.length - 1; i >= 0; i--) {
				if(i > insertionPoint) {
					newDNA[i] = newDNA[i-1];
				} else if(i == insertionPoint) {
					newDNA[i] = newBase;
				}
			}//*/
		} else {
			int mutationPoint = (int) Math.round(Math.random() * dna.getLength());
			String newBase = DNABase.values()[(int) (Math.random() * DNABase.values().length)].toString();
			newDNA.replace(mutationPoint, mutationPoint, newBase);
		}
		return new DNA(newDNA.toString());
	}
}
