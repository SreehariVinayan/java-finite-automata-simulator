package logic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
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
            if(i == stack.get(stack.size()-1).getIndex() && i == characters.length && 
                states.get(stack.get(stack.size()-1).getStateLabel()).getIsFinalState())
                return true;
        }

        return false;

        
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
    
        System.out.println(nfa.evaluate(args[0]));
    }
}
