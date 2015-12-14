import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class Reader {
	public static Automaton leitura(){

		Automaton automaton = new Automaton();
		
		try {
			FileReader file = new FileReader("input.txt"); 
			BufferedReader fileReader = new BufferedReader(file); 
			String header = fileReader.readLine(); // le a primeira linha
			String [] fieldHeader = header.split(" ");
			
			if (fieldHeader.length != 3){
				System.out.println("Erro na leitura do cabecalho");
				//return;
			}
			
			for(int i = 0; i < Integer.parseInt(fieldHeader[0]); i++){
				automaton.addState(new State(i));
			}
			
			automaton.setNumberOfSimbols(Integer.parseInt(fieldHeader[1]));
			State updatedState = automaton.getState(Integer.parseInt(fieldHeader[2]));
			updatedState.setFirst(true);
			automaton.updateState(updatedState, Integer.parseInt(fieldHeader[2]));
			
			String [] isAcept = fileReader.readLine().split(" ");
			if(isAcept.length != Integer.parseInt(fieldHeader[0])){
				System.out.println("Primeira e segunda linha incompativeis.");
				//return
			}
			for(int i=0; i<isAcept.length; i++){
				State auxState = automaton.getState(i);
				if(isAcept[i].equals("0")){
					auxState.setAcept(false);
					automaton.updateState(auxState, i);
				}else{
					auxState.setAcept(true);
					automaton.updateState(auxState, i);
				}
			}

			for(int i=0; i<Integer.parseInt(fieldHeader[0]);i++){
				String line = fileReader.readLine();
				String [] transions = line.split(" "); 
				for(int j=0; j<Integer.parseInt(fieldHeader[1]);j++){
					if (!transions[j].equals("-1"))
						automaton.addLinker(new Linker(automaton.getState(i), automaton.getState(Integer.parseInt(transions[j])), String.valueOf(j)));
				}
			}
			
			file.close(); 
		} catch (IOException e) {
			System.err.printf("Erro na abertura do arquivo: %s.\n", e.getMessage()); 
		} 
		return automaton;
	}
}