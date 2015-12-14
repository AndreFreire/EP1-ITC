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
		
		System.out.println("Parte 1 - Separacao dos estados finais e n�o-finais");
		printArray(matriz);
		
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
		
		System.out.println("Parte 2 - Marca��o dos estados que n�o tenham transi��es sobre os mesmos s�mbolos ");
		printArray(matriz);
		
		/*
		//Terceira parte
		for(int i = 0; i < this.states.size(); i++){
			for(int j = 0; j < this.states.size(); j++){
				if(matriz[i][j]	 == 1){
					if(verifyTransions(this.states.get(i),this.states.get(j))){
						matriz[i][j] = 0;
						//matriz[j][i] = 0;
					}
				}
			}
		}
		
		System.out.println("Parte 3 - Marca��o dos estados que possuam transi��es n�o equivalentes");
		printArray(matriz);

	*/
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
	
	/*private boolean verifyTransions2(State state, State state2){
		boolean aux = false;
		for(int i = 0; i < this.linkers.size(); i++){
			aux = false;
			for(int j = 0; j < this.linkers.size(); j++){
				if(((this.linkers.get(i).getStart().equals(this.linkers.get(j).getStart()) &&
						this.linkers.get(i).getFinish().equals(this.linkers.get(j).getFinish())) &&
						this.linkers.get(i).getSimbol().equals(this.linkers.get(j).getSimbol())) && i != j) {
					aux = true;
				}
				if(aux == true){
					return aux;
				}
			}
		}
		return aux;
	}
	*/
	public void minimizer(){
		removeInaccessibleStates();
		removeUselessStates();
		equivalentStates();
	}
}