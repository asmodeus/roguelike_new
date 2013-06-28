package system;

public enum AtkType {
	MELEE(1), GUNS(2), RANGED(3), UNARMED(4);

	private int value;

	private AtkType(int value) {
		this.value = value;
	}

}
