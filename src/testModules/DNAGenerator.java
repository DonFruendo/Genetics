package testModules;

import java.util.ArrayList;

public class DNAGenerator {
	
	public EBodyPart[] bodyParts;
	
	public static void main(String[] args) {
		String DNA1, DNA2;
		DNA1 = "ACG GCA TAC G TAA TCG A GCT AAT TAA AAT C CAT G TAC TAA AAT TAA AAT C GG CAT GCT GCC CCG GCC AT CCG TCG";
		//DNA1 = "ACG GCA TAC G GCA CAT"; // simpleres beispiel
		Body main1 = new Body(DNA1);
		//DNA2 = "ACGGCATACGTAAGTGAATTAAAATCCATGTACTAAAATTAAAATCGGCATGCTGCCCCGGCCATCCGTCG";
		DNA2 = DNA1.replaceAll("\\s+", "");
		//DNA2 = "ACGGCATACGGCACAT";
		Body main2 = new Body(DNA2);
		System.out.println(" Should be  : " + "[Body >> [HEAD, ARM[HAND[FINGER], HAND], ARM[HAND, HAND], LEG[FOOT, FOOT]]]");
		System.out.println("Is Actually : " + main1);
		System.out.println("When trimmed: " + main2);
	}
	
	public DNAGenerator() {
	}
	
	public static ArrayList<CBodyPart> findAllBodyPartsIn(String dna) {
		ArrayList<CBodyPart> result = new ArrayList<DNAGenerator.CBodyPart>();
		String dnat = dna;
		CBodyPart tmp = findBodyPartIn(dnat);
		while (tmp != null) {
			result.add(tmp);
			String deleteString = tmp.dna;
			int deleteIndex = dnat.indexOf(deleteString);
			int deleteLength = deleteString.length();
			dnat = dnat.substring(deleteIndex + deleteLength, dnat.length());
			tmp = findBodyPartIn(dnat);
		}
		
		return result;
	}
	
	public static CBodyPart findBodyPartIn(String dna) {
		for (EBodyPart part : EBodyPart.values()) {
			int startIndex = dna.indexOf(part.getStartDNA());
			int endIndex = dna.indexOf(part.getEndDNA());
			if ((startIndex != -1) && (endIndex != -1)) {
				if(startIndex + part.getStartDNA().length() <= endIndex) {
					String dnanew = dna.substring(startIndex, endIndex + part.getEndDNA().length());
					return new CBodyPart(dnanew);
				}
			} else { // mindestens eine Sequenz nicht gefunden
			}
		}
		return null;
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
		private ArrayList<CBodyPart> listOfBodyParts = new ArrayList<DNAGenerator.CBodyPart>();
		
		public CBodyPart(String dna) {
			this.dna = dna;
			String str = dna.substring(0,3);
			this.part = EBodyPart.getBodyPart(str);
			String newDNA = getInnerDNA(dna);
			this.listOfBodyParts = findAllBodyPartsIn(newDNA);
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
