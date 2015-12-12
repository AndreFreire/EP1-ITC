public class State {
	private int id;
	private boolean isAcept;
	private boolean isReject;
	private boolean accessible;
	private boolean accessibleFinished;
	private boolean util;
	private boolean utilFinished;
	private boolean isFirst;
	
	public State(int id) {
		this.id = id;		
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAcept() {
		return isAcept;
	}

	public void setAcept(boolean isAcept) {
		this.isAcept = isAcept;
	}

	public boolean isReject() {
		return isReject;
	}

	public void setReject(boolean isReject) {
		this.isReject = isReject;
	}

	public boolean isAccessible() {
		return accessible;
	}

	public void setAccessible(boolean accessible) {
		this.accessible = accessible;
	}

	public boolean isAccessibleFinished() {
		return accessibleFinished;
	}

	public void setAccessibleFinished(boolean accessibleFinished) {
		this.accessibleFinished = accessibleFinished;
	}

	public boolean isUtil() {
		return util;
	}

	public void setUtil(boolean util) {
		this.util = util;
	}

	public boolean isFirst() {
		return isFirst;
	}

	public void setFirst(boolean isFirst) {
		this.isFirst = isFirst;
	}

	public boolean isUtilFinished() {
		return utilFinished;
	}

	public void setUtilFinished(boolean utilFinished) {
		this.utilFinished = utilFinished;
	}
	
}
