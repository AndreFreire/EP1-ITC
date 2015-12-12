import java.util.ArrayList;

public class Automaton {
	ArrayList<Linker> linkers = new ArrayList<Linker>();
	ArrayList<State> states = new ArrayList<State>();
	
	public void addLinker(Linker linker){
		linkers.add(linkers.size(), linker);
	}
	public void addState(State state){
		states.add(states.size(), state);
	}
	
	public void updateLinker(Linker linker, int index){
		linkers.set(index, linker);
	}
	public void updateState(State state, int index){
		states.set(index, state);
	}	
	public State getState(int index){
		return states.get(index);
	}
	public Linker getLinker(int index){
		return linkers.get(index);
	}
	private State getFirstState(){
		for(int i=0; i<this.states.size(); i++){
			if(this.states.get(i).isFirst()){
				return this.states.get(i);
			}
		}
		return null;
	}
	private ArrayList<State> getPreviousStates(State state){
		ArrayList<State> states = new ArrayList<State>(); 
		for(Linker linker : this.linkers){
			if(linker.getFinish().equals(state)){
				states.add(linker.getStart());
			}
		}
		return states;
	}	
	private ArrayList<State> getNextStates(State state){
		ArrayList<State> states = new ArrayList<State>(); 
		for(Linker linker : this.linkers){
			if(linker.getStart().equals(state)){
				states.add(linker.getFinish());
			}
		}
		return states;
	}
	private void removeUselessStates(){
		for(State auxState : this.states){
			if(auxState.isAcept() || auxState.isReject()){
				verifyUseble(auxState);
			}
		}			
	}	
	public void verifyUseble(State state){
		state.setUtil(true);
		ArrayList<State> previousStates = getPreviousStates(state);
		for(State auxState : previousStates){
			if(!state.isUtil())
				verifyUseble(auxState);
		}
		state.setUtilFinished(true);
	}	
	private void removeInaccessibleStates(){
		verifyAccessible(getFirstState());
		for(int i=0;i<this.states.size();i++){
			if(!this.states.get(i).isAccessible())
				this.states.remove(i);
		}
	}	
	public void verifyAccessible(State state){
		state.setAccessible(true);
		ArrayList<State> nextsStates = getNextStates(state);
		for(State auxState : nextsStates){
			if(!state.isAccessible())
				verifyAccessible(auxState);
		}
		state.setAccessibleFinished(true);
	}	
	public void minimizer(){
		removeInaccessibleStates();
		removeUselessStates();
	}
}