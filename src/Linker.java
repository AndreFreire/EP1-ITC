public class Linker {

	private State start;
	private State finish;
	private String simbol;
	
	public Linker(State start, State finish, String simbol) {
		this.start = start;
		this.finish = finish;
		this.simbol = simbol;
	}

	public State getStart() {
		return start;
	}

	public void setStart(State start) {
		this.start = start;
	}

	public State getFinish() {
		return finish;
	}

	public void setFinish(State finish) {
		this.finish = finish;
	}

	public String getSimbol() {
		return simbol;
	}

	public void setSimbol(String simbol) {
		this.simbol = simbol;
	}
	
	
}
