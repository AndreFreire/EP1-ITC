
public class Main {

	public static void main(String[] args) {
		Automaton automaton = Reader.leitura();
		automaton.minimizer();
		System.out.println(automaton.getStatesMinimized().size() + " " + automaton.getNumberOfSimbols() + " " + automaton.getFirstState().getId());
		for(int i :automaton.statesMinimized.keySet()){
			if(!automaton.statesMinimized.get(i).equals(null) && automaton.statesMinimized.get(i).isAcept())
				System.out.print("1 ");
			else
				System.out.print("0 ");
		}System.out.println();
		
	}
}
