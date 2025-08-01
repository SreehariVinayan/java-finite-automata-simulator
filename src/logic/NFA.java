package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class NFA {
    private String label;
    private String initialState;
    private List<Character> alphabets;
    private HashMap<String,NfaState> states;

    public NFA(String label, HashMap<String,NfaState> states, List<Character> alphabets, String initialState){
        this.label = label;
        this.states = states;
        this.alphabets = alphabets;
        this.initialState = initialState;
    }

    public boolean evaluate(String string){
        char[] characters = string.toCharArray();

        List<Tuple> stack = new ArrayList<>();
        
        stack.add(new Tuple(initialState, 0)); 

        Tuple currentStateTuple;
        int i = -1;
        char EPSILON_CHARACTER = '\0';
        List<String> transitions = new ArrayList<>();
        List<String> epsilonTransition = new ArrayList<>();
        HashMap<Character,List<String>> transitionsOfCurrentState = new HashMap<>();
        
        while(!stack.isEmpty()){
            currentStateTuple = stack.remove(stack.size()-1);
            i = currentStateTuple.getIndex();

            transitionsOfCurrentState = new HashMap<>();
            transitions = new ArrayList<>();
            epsilonTransition = new ArrayList<>();
            
            transitionsOfCurrentState = states.get(currentStateTuple.getStateLabel()).getTransitions();           
            
            if(characters.length == 0){
                characters = new char[]{'\0'};
            }

            if (transitionsOfCurrentState.containsKey(characters[i])){
                for(String state : transitionsOfCurrentState.get(characters[i])){
                    transitions.add(state);
                }
            }

            if (transitionsOfCurrentState.containsKey(EPSILON_CHARACTER)){
                epsilonTransition = transitionsOfCurrentState.get(EPSILON_CHARACTER);
                for(String state : epsilonTransition){
                    transitions.add(state);
                }
            }

            if(!transitions.isEmpty()){
                i++;
                for(String stateLabel : transitions){
                    Tuple temp = new Tuple(stateLabel, i);
                    stack.add(temp);
                }                
            }

            try{
                if (stack.size() == 0) continue; 
                
                Tuple lastElementInStack = stack.get(stack.size()-1);
                
                if(i == lastElementInStack.getIndex() && i == characters.length && 
                    states.get(lastElementInStack.getStateLabel()).getIsFinalState())
                        return true;
            }
            catch(Exception e){
                System.out.println(e.getMessage());
            }
        }

        return false;
    }

    List<String> generateStrings(int maxLength){

        List<String> strings = new ArrayList<>();
        strings.add("");

        List<String> previousStrings= new ArrayList<>();
        previousStrings.add("");

        List<Character> alphabetsWithoutEpsilon = new ArrayList<>();
        for(Character ch : alphabets)
            if(ch != '\0')
                alphabetsWithoutEpsilon.add(ch);

        for(int i = 0; i < maxLength; i++){    
            List<String> temp = new ArrayList<>(); 
            for(String string : previousStrings){
                for(Character ch : alphabetsWithoutEpsilon ){
                    strings.add(string+Character.toString(ch));
                    temp.add(string+Character.toString(ch));
                }
            }        
            previousStrings = temp;
        }
        return strings;
    }   

    public LinkedHashMap<String,Boolean> bulkEvaluate(int maxLength){
        
        LinkedHashMap<String,Boolean> output = new LinkedHashMap<>();
        List<String> strings = generateStrings(maxLength);
        
        for(String string : strings){
            output.put(string, evaluate(string));
        }

        return output;
    }

    public static void main(String[] args){

        HashMap<Character,List<String>> transition0 = new HashMap<>();
        transition0.put('a', new ArrayList<>(Arrays.asList("q0")));
        transition0.put('\0', new ArrayList<>(Arrays.asList("q1")));
        NfaState q0 = new NfaState("q0", true, transition0);
        
        HashMap<Character,List<String>> transition1 = new HashMap<>();
        transition1.put('b', new ArrayList<>(Arrays.asList("q1")));
        NfaState q1 = new NfaState("q1", true, transition1);
        
        HashMap<String,NfaState> states = new HashMap<>();
        states.put("q0", q0);
        states.put("q1", q1);

        List<Character> alphabets = new ArrayList<>();
        alphabets.add('a');
        alphabets.add('b');
        alphabets.add('\0');

        NFA nfa = new NFA("NFA_1", states, alphabets, "q0");

        System.out.println(nfa.generateStrings(3));
    
        LinkedHashMap<String,Boolean> result = nfa.bulkEvaluate(Integer.parseInt(args[0]));        

        List<String> acceptedStrings = new ArrayList<>();
        List<String> rejectedStrings = new ArrayList<>();

        for(String key : result.keySet()){
            if(result.get(key))
                    acceptedStrings.add(key == "" ? "<Empty String>" : key);
            else{
                rejectedStrings.add(key == "" ? "<Empty String>" : key);
            }
        }

        System.out.println("\nAccepted Strings");
        for(String key : acceptedStrings)
            System.out.println(key);

        System.out.println("\nRejected Strings");
        for(String key : rejectedStrings)
            System.out.println(key);
    }
}
