package model;

public enum BodyPart {
	
	HEAD("ACG", BodyPartClass.MEDI),
	
	ARM("TAC", BodyPartClass.BONE),
	WING("TAG", BodyPartClass.BONE),
	LEG("GCT", BodyPartClass.BONE),
	
	MOUTH("GGC", BodyPartClass.MINI),
	HAND("TAA", BodyPartClass.MEDI),
	FOOT("GCC", BodyPartClass.MEDI),
	
	FINGER("TCG", BodyPartClass.MINI),
	
	
	
	
	BODY("  .  ", BodyPartClass.BODY);
	
	private String startDNA, endDNA;
	private BodyPartClass bodyClass;
	
	BodyPart(String dna, BodyPartClass bClass) {
		startDNA = dna;
		bodyClass = bClass;
		endDNA = new StringBuilder(dna).reverse().toString();
	}

	public String getStartDNA() {
		return startDNA;
	}

	public String getEndDNA() {
		return endDNA;
	}
	
	public BodyPartClass getBodyClass() {
		return bodyClass;
	}
	
	public boolean isAttachableTo(BodyPartClass parent) {
		return bodyClass.canAttachTo(parent);
	}
	
	public static BodyPart getBodyPart(String startDNA) {
		for(BodyPart part : values()) {
			if(part.startDNA.equals(startDNA)) {
				return part;
			}
		}
		return null;
	}
}
