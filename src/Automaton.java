import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Automaton {
	ArrayList<Linker> linkers = new ArrayList<Linker>();
	ArrayList<State> states = new ArrayList<State>();
	ArrayList<Linker> linkersMinimized = new ArrayList<Linker>();
	Map<Integer,State> statesMinimized = new HashMap<Integer,State>();
	int numberOfSimbols;
	
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
		
	public ArrayList<Linker> getLinkersMinimized() {
		return linkersMinimized;
	}

	public void setLinkersMinimized(ArrayList<Linker> linkersMinimized) {
		this.linkersMinimized = linkersMinimized;
	}

	public Map<Integer, State> getStatesMinimized() {
		return statesMinimized;
	}

	public void setStatesMinimized(Map<Integer, State> statesMinimized) {
		this.statesMinimized = statesMinimized;
	}	

	public int getNumberOfSimbols() {
		return numberOfSimbols;
	}

	public void setNumberOfSimbols(int numberOfSimbols) {
		this.numberOfSimbols = numberOfSimbols;
	}
	
	public int getIndexFromIdState(int id){
		for(int i=0; i<this.states.size(); i++){
			if (this.states.get(i).getId()==id){
				return i;
			}
		}
		return -1;
	}
	
	public String getFinishFromStateAndSymbol(State state, String symbol){
		for(int i=0; i<this.linkersMinimized.size();i++){
			if(this.linkersMinimized.get(i).getStart().equals(state) && this.linkersMinimized.get(i).getSimbol().equals(symbol)){
				return String.valueOf(this.linkersMinimized.get(i).getFinish().getId());
			}
		}
		return "-1";
	}
	
	public State getStateFromId(int id){
		for(int i=0; i<this.states.size(); i++){
			if (this.states.get(i).getId()==id){
				return this.states.get(i);
			}
		}
		return null;
	}
	
	public void updateStates(){
		for(int i=0; i<this.states.size(); i++){
			this.states.get(i).setAccessible(false);
			this.states.get(i).setAccessibleFinished(false);
			this.states.get(i).setUtil(false);
			this.states.get(i).setUtilFinished(false);			
		}
	}

	public State getFirstState(){
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
		for(int i=0;i<this.states.size();i++){
			if(!this.states.get(i).isUtil()){
				this.states.remove(i);   
			}
		}  
	}

	public void verifyUseble(State state){
		state.setUtil(true);
		ArrayList<State> previousStates = getPreviousStates(state);
		for(State auxState : previousStates){
			if(!auxState.isUtil())
				verifyUseble(auxState);
		}
		state.setUtilFinished(true);
	}

	private void removeInaccessibleStates(){
		verifyAccessible(getFirstState());
		for(int i = 0; i <this.states.size(); i++){
			if(!this.states.get(i).isAccessible())
				this.states.remove(i);
		}
	}	

	public void printArray(int [][] matriz){
		for(int i = 0; i < matriz.length; i++){
			for(int j = 0; j < matriz[i].length; j++){
				System.out.print(matriz[i][j]);
				System.out.print(" ");
			}  
			System.out.println("");
		}
	}

	public void verifyAccessible(State state){
		state.setAccessible(true);
		ArrayList<State> nextsStates = getNextStates(state);
		for(State auxState : nextsStates){
			if(!auxState.isAccessible())
				verifyAccessible(auxState);
		}
		state.setAccessibleFinished(true);
	}

	public void equivalentStates(){

		//Primeira parte
		int [][] matriz = new int[this.states.size()][this.states.size()];
		for(int i = 0; i < this.states.size(); i++){
			for(int j = 0; j < this.states.size(); j++){
				if((this.states.get(i).isAcept() && this.states.get(j).isAcept() ||
						this.states.get(i).isReject() && this.states.get(j).isReject() || 
						!this.states.get(i).isAcept() && !this.states.get(j).isAcept() &&
						!this.states.get(i).isReject() && !this.states.get(j).isReject()) &&
						i != j){
					matriz[i][j] = 1;
				} else {
					matriz[i][j] = 0;
				}
			}
		}

		//System.out.println("Parte 1 - Separacao dos estados finais e n�o-finais");
		//printArray(matriz);

		//Segunda parte
		for(int i = 0; i < this.states.size(); i++){
			for(int j = 0; j < this.states.size(); j++){
				if(matriz[i][j]	 == 1){
					if(verifyTransions(this.states.get(i),this.states.get(j))){
						matriz[i][j] = 0;
					}
				}
			}
		}

		//System.out.println("Parte 2 - Marca��o dos estados que n�o tenham transi��es sobre os mesmos s�mbolos ");
		//printArray(matriz);


		//Terceira parte
		for(int i = 0; i < this.states.size(); i++){
			for(int j = 0; j < this.states.size(); j++){
				if(matriz[i][j]	 == 1){
					if(noEquivalentTransition(this.states.get(i),this.states.get(j))){
						matriz[i][j] = 0;
					}
				}
			}
		}

		//System.out.println("Parte 3 - Marca��o dos estados que possuam transi��es n�o equivalentes");
		//printArray(matriz);
		
		//Quarta parte 
		int [] rep = new int[this.states.size()];
		int count = 0;
		for(int i=0; i< this.states.size(); i++){
			rep[i]= -1;
		}
		for(int i=0; i<rep.length;i++){
			if(rep[i] == -1){
				count = count + 1;
				rep[i] = count-1;
				for(int j=0;j<matriz.length;j++){
					if(matriz[i][j]==1){
						rep[j] = rep[i];
					}					
				}
			}
		}
		
		for(int i=0;i<this.states.size();i++){
			for(int j=0; j<i; j++){
				if(matriz[i][j]==1){
					for(int k=0; k< this.linkers.size(); k++){
						if(this.linkers.get(k).getFinish() == this.states.get(i)){
							this.linkers.get(k).setFinish(this.states.get(j));
							if(this.states.get(i).isFirst()){
								this.states.get(j).setFirst(true);
								this.states.get(i).setFirst(false);
							}
							if(this.states.get(i).isAcept()){
								this.states.get(j).setAcept(true);
								this.states.get(i).setAcept(false);
							}
							
						}
						if(this.linkers.get(k).getStart() == this.states.get(i)){
							this.linkers.get(k).setStart(this.states.get(j));
							if(this.states.get(i).isFirst()){
								this.states.get(j).setFirst(true);
								this.states.get(i).setFirst(false);
							}
							if(this.states.get(i).isAcept()){
								this.states.get(j).setAcept(true);
								this.states.get(i).setAcept(false);
							}
						}
						linkersMinimized.add(linkers.get(k));						
					}					
				}
			}
		}
		updateStates();
		removeInaccessibleStates();
		removeUselessStates();

		for(int i =0; i< this.linkers.size();i++){
			if(getStateFromId(this.linkers.get(i).getStart().getId()) != null && getStateFromId(this.linkers.get(i).getFinish().getId())!= null){
				this.linkersMinimized.add(this.linkers.get(i));
			}
		}
		for(int i=0; i<this.linkersMinimized.size();i++){
			this.statesMinimized.put(this.linkersMinimized.get(i).getStart().getId(), this.linkersMinimized.get(i).getStart());
			this.statesMinimized.put(this.linkersMinimized.get(i).getFinish().getId(), this.linkersMinimized.get(i).getFinish());			
		}
		
	}
	//Segunda parte
	private boolean verifyTransions(State state, State state2){
		for(int i = 0; i < this.linkers.size(); i++){
			if(this.linkers.get(i).getStart().equals(state)){
				for(int j = 0; j < this.linkers.size(); j++){
					if(this.linkers.get(j).getStart().equals(state2)){ 
						if(this.linkers.get(i).getSimbol().equals(this.linkers.get(j).getSimbol())){
							return false;
						}
					}
				}
			}
			if(this.linkers.get(i).getFinish().equals(state)){
				for(int j = 0; j < this.linkers.size(); j++){
					if(this.linkers.get(j).getFinish().equals(state2)){ 
						if(this.linkers.get(i).getSimbol().equals(this.linkers.get(j).getSimbol())){
							return false;
						}
					}
				}
			}
		}
		return true;		
	} 

	private boolean noEquivalentTransition(State state, State state2){
		for(int i = 0; i < this.linkers.size(); i++){
			if(this.linkers.get(i).getStart().equals(state)){
				for(int j = 0; j < this.linkers.size(); j++){
					if(this.linkers.get(j).getStart().equals(state2)){ 
						if(this.linkers.get(i).getSimbol().equals(this.linkers.get(j).getSimbol())){
							if(this.linkers.get(i).getFinish().isAcept() && this.linkers.get(j).getFinish().isAcept() ||
									this.linkers.get(i).getFinish().isReject() && this.linkers.get(j).getFinish().isReject() ||
									!this.linkers.get(i).getFinish().isAcept() && !this.linkers.get(j).getFinish().isAcept() ||
									!this.linkers.get(i).getFinish().isReject() && !this.linkers.get(j).getFinish().isReject())
								return false;
						}
					}
				}				
			}
			if(this.linkers.get(i).getFinish().equals(state)){
				for(int j = 0; j < this.linkers.size(); j++){
					if(this.linkers.get(j).getFinish().equals(state2)){ 
						if(this.linkers.get(i).getSimbol().equals(this.linkers.get(j).getSimbol())){
							if(this.linkers.get(i).getFinish().isAcept() && this.linkers.get(j).getFinish().isAcept() ||
									this.linkers.get(i).getFinish().isReject() && this.linkers.get(j).getFinish().isReject() ||
									!this.linkers.get(i).getFinish().isAcept() && !this.linkers.get(j).getFinish().isAcept() ||
									!this.linkers.get(i).getFinish().isReject() && !this.linkers.get(j).getFinish().isReject())
								return false;
						}
					}
				}				
			}
		}
		return true;		
	}
	public void minimizer(){
		removeInaccessibleStates();
		removeUselessStates();
		equivalentStates();
	}
}