package testModules;

import java.util.ArrayList;

public class DNAGenerator2 {
	private static String startSequenz = "GACT";
	private static String endSequenz = "TCAG";
	
	public EBodyPart[] bodyParts;
	
	public static void main(String[] args) {
		String DNA1, DNA2;
		//DNA1 = "ACG GCA TAC G TAA TCG A GCT AAT TAA AAT C CAT G TAC TAA AAT TAA AAT C GG CAT GCT GCC CCG GCC AT CCG TCG";
		//DNA1 = "GACTACG TCAG";
		DNA1 = 	  "GACTACG GCATCAG "	// HEAD-
				
				+ "GACTTAC G "			// ARM 1
					
					+ "GACTTAA "		// HAND
						+ "GACTTCG A GCTTCAG " // FINGER-
					+ "AATTCAG "		// HAND END
					
					+ "GACTTAA AATTCAG "// HAND-
				+ "C CATTCAG "			// ARM 1 END
				+ "G "					
				+ "GACTTAC "			// ARM 2
					+ "GACTTAA AATTCAG "// HAND-
					+ "GACTTAA AATTCAG "// HAND-
				+ "C GG CATTCAG "		// ARM 2 END
				
				+ "GACTGCT "			// LEG
					+ "GACTGCC CCGTCAG "// FOOT-
					+ "GACTGCC AT CCGTCAG "// FOOT-
				+ "TCGTCAG ";			// LEG END
		
		DNA1 = "GGCGGACTACGATTACCCAACCCCTTCGCCCCCCACCATCAGTTGGTTGACTTACCTTAAACCGGAAATTAAAAAAAGAAAAAGGTT"
				+ "CAGGGACTTAAGGAAATAATAAAAAGCGGGGGGTGGGGGGGGGGGGCCCATTTTTTTCTTCAGACGATTTAAAAGACTAAAAAT"
				+ "AATTGTCAGAACGGATTTTTTCACCCCCAAACACCAAGAAAAAAAAAAAAAAAAAAAAAA";
		//DNA1 = "ACG GCA TAC G GCA CAT"; // simpleres beispiel
		Body main1 = new Body(DNA1);
		//DNA2 = "ACGGCATACGTAAGTGAATTAAAATCCATGTACTAAAATTAAAATCGGCATGCTGCCCCGGCCATCCGTCG";
		DNA2 = DNA1.replaceAll("\\s+", "");
		//DNA2 = "ACGGCATACGGCACAT";
		
		Body main2 = null;
		int n = 10000;
		System.out.println("Starting tests ...");
		long sec = System.currentTimeMillis();
		for(int i = 0; i < n; i++) {
			main2 = new Body(DNA2);
		}
		sec = Math.round((System.currentTimeMillis() - sec) / 1000.);
		System.out.println(n + " tests done in " + sec + " seconds!");
		
		System.out.println(" Should be  : " + "[Body >> [HEAD, ARM[HAND[FINGER], HAND], ARM[HAND, HAND], LEG[FOOT, FOOT]]]");
		System.out.println("Is Actually : " + main1);
		System.out.println("When trimmed: " + main2);
	}
	
	public DNAGenerator2() {
	}
	
	public static ArrayList<CBodyPart> findAllBodyPartsIn(String dna) {
		ArrayList<CBodyPart> result = new ArrayList<DNAGenerator2.CBodyPart>();
		String dnat = dna;
		CBodyPart tmp = findBodyPartIn(dnat);
		while (tmp != null) {
			result.add(tmp);
			String deleteString = tmp.dna;
			int deleteIndex = dnat.indexOf(deleteString);
			int deleteLength = deleteString.length();
			dnat = dnat.substring(deleteIndex + deleteLength + 4, dnat.length());
			tmp = findBodyPartIn(dnat);
		}
		
		return result;
	}
	
	public static CBodyPart findBodyPartIn(String dna) {
		// find first bodypart
		CBodyPart firstFound = null;
		int startingIndex = dna.indexOf(startSequenz);
		int endingIndex = -1;
		int brackets = 0;
		if(startingIndex != -1) {
			for (int i = startingIndex; i <= dna.length() - startSequenz.length(); i++) {
				//System.out.println(i);
				String nextFour = dna.substring(i, i + startSequenz.length());
				if (nextFour.equals(startSequenz)) {
					brackets++;
					i += startSequenz.length() - 1;
				} else if (nextFour.equals(endSequenz)) {
					brackets--;
					endingIndex = i;
					i += endSequenz.length() - 1;
				}
				if((endingIndex != -1) && (brackets == 0) && (endingIndex - startingIndex >= startSequenz.length() + endSequenz.length() - 1)) {
					// TODO to be completed
					String partDNA = dna.substring(startingIndex + startSequenz.length(), endingIndex);
					//String idStr = partDNA.substring(4);
					EBodyPart partID = getIdentification(partDNA);
					if(partID == EBodyPart.ARM) {
						// ???
						System.out.print("");
					}
					firstFound = new CBodyPart(partDNA);
					return firstFound;
				}
			}
		}
		// if nothing is found
		return null;
	}
	
	public static EBodyPart getIdentification(String dna) {
		String id = dna.substring(0, 3);
		EBodyPart bp = EBodyPart.getBodyPart(id);
		return bp;
	}
	
	public static String getInnerDNA(String dna) {
		if(dna.length() >= 6) {
			return dna.substring(3, dna.length() - 3);
		}
		return dna;
	}
	
	
	private static class Body {
		String dna;
		ArrayList<CBodyPart> listOfBodyParts;
		
		public Body(String dna) {
			this.dna = dna;
			this.listOfBodyParts = findAllBodyPartsIn(dna);
		}
		
		public String toString() {
			return "[Body >> " + listOfBodyParts + "] - " + dna;
		}
	}
	
	private static class CBodyPart {
		private EBodyPart part;
		private String dna;
		private ArrayList<CBodyPart> listOfBodyParts = new ArrayList<DNAGenerator2.CBodyPart>();
		
		public CBodyPart(String dna) {
			this.dna = dna;
			this.part = getIdentification(dna);
			this.listOfBodyParts = findAllBodyPartsIn(dna);
		}
		
		public String toString() {
			return part + ((listOfBodyParts.size() == 0) ? "" : "" + listOfBodyParts + "");
		}
	}
	
	private static enum EBodyPart {
		HEAD("ACG"),
		ARM("TAC"),
		LEG("GCT"),
		
		MOUTH("GGC"),
		HAND("TAA"),
		FOOT("GCC"),
		
		FINGER("TCG");
		
		private String startDNA, endDNA;
		
		EBodyPart(String dna) {
			startDNA = dna;
			endDNA = new StringBuilder(dna).reverse().toString();
		}

		public String getStartDNA() {
			return startDNA;
		}

		public String getEndDNA() {
			return endDNA;
		}
		
		public static EBodyPart getBodyPart(String startDNA) {
			for(EBodyPart part : values()) {
				if(part.startDNA.equals(startDNA)) {
					return part;
				}
			}
			return null;
		}
	}
}
