package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class Main {

    static String INIT_STATE_LABEL = "q0";
    static final String DELIMITER = "[,]";
    static final String EMPTY_STRING = "";
    static final String TRUE_STRING = "TRUE";
    static final int DEFAULT_MAX_COMBINATIONS = 4;

    public static void main(String[] args){

        String fileName = null;
        Integer maxCominations = null;

        if (args.length > 0) {
            fileName = args[0];
            System.out.println("File path set to " + fileName);

            if (args.length == 2){
                maxCominations = Integer.parseInt(args[1]);  
                System.out.println("Maximum Combination length set to " + maxCominations);
            }

        } else {
            System.out.println("No command line argument provided. Defaulting File Path to source.csv");
            fileName = "source.csv";
        }

        HashMap<String,State> states = new HashMap<>();
        
        List<String> rows = new ArrayList<>();

        FileHandler handler = new FileHandler();
        
        try{
            rows = handler.readFile(fileName,false);
        }
        catch(Exception e){
            System.out.println("Error\t: " + e.getMessage());
            System.out.println("Usage\t: java -jar DFA.jar <csv_file_name>");
            System.out.println("Example\t: java -jar DFA.jar files/dfa_alternateAB.csv");
            System.out.println("Note\t: Place all CSV files inside a folder named 'files' in the same directory as DFA.jar.");
            System.out.println("Tip\t: Ensure the file name is spelled correctly and has .csv extension.");
            return;
        }

        int maxLengthOfCombinations;
        List<Character> alphabets = new ArrayList<>(); 


        // Initializing with alphabets and Max Combination Length
        String[] header;

        header = rows.get(0).split(DELIMITER);

        for (String value :  Arrays.copyOfRange(header, 1, header.length-1)){
            alphabets.add(value.toCharArray()[0]);
        } 

        maxLengthOfCombinations = maxCominations == null
            ? header[0] == EMPTY_STRING ? DEFAULT_MAX_COMBINATIONS : Integer.parseInt(header[0])
            : maxCominations;
        
        for(String row : rows.subList(1,rows.size())){
            
            String[] values = row.split(DELIMITER);
            String[] transitions = Arrays.copyOfRange(values, 1, values.length-1);

            HashMap<Character,String> transition = new HashMap<>();
            
            for (int i = 0; i<transitions.length;i++){
                transition.put(alphabets.get(i), transitions[i]);
            }

            State state = new State(values[0], values[values.length-1].equals(TRUE_STRING), transition);
            
            states.put(state.getLabel(), state);
        }

        INIT_STATE_LABEL = rows.get(1).split(DELIMITER)[0];

        DFA dfa = new DFA("DFA", states, alphabets, INIT_STATE_LABEL);

        LinkedHashMap<String,Boolean> result = dfa.bulkEvaluate(maxLengthOfCombinations);        

        List<String> acceptedStrings = new ArrayList<>();
        List<String> rejectedStrings = new ArrayList<>();

        for(String key : result.keySet()){
            if(result.get(key))
                    acceptedStrings.add(key == EMPTY_STRING ? "<Empty String>" : key);
            else{
                rejectedStrings.add(key == EMPTY_STRING ? "<Empty String>" : key);
            }
        }

        System.out.println("\nAccepted Strings");
        for(String key : acceptedStrings)
            System.out.println(key);

        System.out.println("\nRejected Strings");
        for(String key : rejectedStrings)
            System.out.println(key);

        //new CustomFrame(acceptedStrings);
    }
}

// For compiling : javac -sourcepath src -d out src/logic/*.java src/gui/*.java