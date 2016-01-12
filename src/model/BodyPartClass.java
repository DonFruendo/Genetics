package model;

public enum BodyPartClass {
	BODY(0),
	BONE(1),
	MEDI(2),
	MINI(3);
	
	private int level;
	BodyPartClass(int level) {
		this.level = level;
	}
	
	public int getLevel() {
		return level;
	}
	
	public boolean canAttachTo(BodyPartClass parent) {
		return (parent.getLevel() < level);
	}
}
