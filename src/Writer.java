import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Writer {

	public static void WriteAutomaton(Automaton automaton, String fileName){
		try {
			File arquivo = new File(fileName);
			try(PrintWriter pw = new PrintWriter(arquivo) ){
				pw.println(automaton.getStatesMinimized().size() + " " + automaton.getNumberOfSimbols() + " " + automaton.getFirstState().getId());
				System.out.println(automaton.getStatesMinimized().size() + " " + automaton.getNumberOfSimbols() + " " + automaton.getFirstState().getId());
				for(int i :automaton.statesMinimized.keySet()){
					if(!automaton.statesMinimized.get(i).equals(null) && automaton.statesMinimized.get(i).isAcept()){
						pw.print("1 ");
						System.out.print("1 ");
					}else{
						pw.print("0 ");
						System.out.print("0 ");
					}
				}
				pw.println();
				System.out.println();
				for(int i :automaton.statesMinimized.keySet()){
					for(int j=0; j< automaton.getNumberOfSimbols();j++){
						pw.print(automaton.getFinishFromStateAndSymbol(automaton.statesMinimized.get(i), String.valueOf(j))+ " ");
						System.out.print(automaton.getFinishFromStateAndSymbol(automaton.statesMinimized.get(i), String.valueOf(j))+ " ");
					}	
					pw.println();
					System.out.println();
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}