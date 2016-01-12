package model;

import java.util.ArrayList;

public class CBodyPart {
	private static String startSequenz = "GACT";
	private static String endSequenz = "TCAG";	
	
	
	private CBodyPart parent;
	protected BodyPart part;
	private DNA dna;
	private int layer;
	protected ArrayList<CBodyPart> listOfBodyParts = new ArrayList<CBodyPart>();

	public CBodyPart(String dna, CBodyPart parent) {
		this.dna = new DNA(dna);
		this.parent = parent;
		if(parent == null) {
			this.layer = 0;	
		} else {
			this.layer = parent.getLayer();
		}
		BodyPart id = getIdentification(dna);
		if((parent != null) && (parent.isBodyPartAlreadyInHierarchy(id) == false)) {
			this.part = id;
		} else {
			this.part = id;
		}
		this.listOfBodyParts = findAllBodyPartsIn(this);
	}

	public boolean isBodyPart(BodyPart bodyPart) {
		return part == bodyPart;
	}
	
	public BodyPart getBodyPart() {
		return part;
	}

	public int getLayer() {
		return layer;
	}
	
	public DNA getDNA() {
		return dna;
	}

	public void setLayer(int layer) {
		this.layer = layer;
		for(CBodyPart part : listOfBodyParts) {
			part.setLayer(layer + 1);
		}
	}

	public String toString() {
		return ((part == null) ? "N/A" : part) + "" + layer + ((listOfBodyParts.size() == 0) ? "" : "" + listOfBodyParts + "");
	}

	public ArrayList<CBodyPart> getListOfBodyParts() {
		return listOfBodyParts;
	}
	
	public boolean isBodyPartAlreadyInHierarchy(BodyPart bodyPart) {
		if(this.part == bodyPart) {
			return true;
		} else if(parent == null) {
			return false;
		} else {
			return parent.isBodyPartAlreadyInHierarchy(bodyPart);
		}
	}

	public ArrayList<CBodyPart> getCompleteListOfBodyParts() {
		ArrayList<CBodyPart> partsInParts = new ArrayList<CBodyPart>();
		for (CBodyPart cpart : listOfBodyParts) {
			partsInParts.add(cpart);
			partsInParts.addAll(cpart.getCompleteListOfBodyParts());
		}
		return partsInParts;
	}
	public static ArrayList<CBodyPart> findAllBodyPartsIn(CBodyPart bodyPart) {
		ArrayList<CBodyPart> result = new ArrayList<CBodyPart>();
		if(bodyPart.getBodyPart() != null) {
			DNA dna = bodyPart.getDNA();
			String dnat = dna.getSequenz();
			CBodyPart tmp = findBodyPartIn(bodyPart);
			while (tmp != null) {
				result.add(tmp);
				String deleteString = tmp.dna.getSequenz();
				int deleteIndex = dnat.indexOf(deleteString);
				int deleteLength = deleteString.length();
				dnat = dnat.substring(deleteIndex + deleteLength + 4, dnat.length());
				tmp = findBodyPartIn(bodyPart, dnat);
			}
		}
		return result;
	}
	
	public static CBodyPart findBodyPartIn(CBodyPart bodyPart) {
		return findBodyPartIn(bodyPart, bodyPart.getDNA().getSequenz());
	}
	
	public static CBodyPart findBodyPartIn(CBodyPart bodyPart, String dna) {
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
//					BodyPart partID = getIdentification(partDNA);
//					if(bodyPart.isBodyPartAlreadyInHierarchy(partID)) {
//						partID = null;
//					}
					firstFound = new CBodyPart(partDNA, bodyPart);
					return firstFound;
				}
			}
		}
		// if nothing is found
		return null;
	}
	
	public static BodyPart getIdentification(String dna) {
		String id = dna.substring(0, 3);
		BodyPart bp = BodyPart.getBodyPart(id);
		return bp;
	}

}
