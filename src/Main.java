
public class Main {

	public static void main(String[] args) {
		Automaton automaton = Reader.leitura(args[0]);
		automaton.minimizer();
		Writer.WriteAutomaton(automaton, args[1]);		
	}
}