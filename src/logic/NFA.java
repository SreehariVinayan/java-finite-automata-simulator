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

        List<String> NO_TRANSITION = new ArrayList<>();

        List<Tuple> stack = new ArrayList<>();
        
        stack.add(new Tuple(initialState, 0, false)); 

        boolean epsilonClosureTaken;
        Tuple currentStateTuple;
        int i = 0;
        int j;
        int stringLength = string.length(); 
        Tuple tempTuple;
        
        while (!stack.isEmpty()){
            currentStateTuple = stack.remove(stack.size()-1);
            i = currentStateTuple.getIndex();
            
            List<String> transitionOfCurrentState = new ArrayList<>();

            if (states.get(currentStateTuple.stateLabel).getTransitions().containsKey(characters[i])){
                transitionOfCurrentState = states.get(currentStateTuple.stateLabel).getTransitions().get(characters[i]);           
            }

            if(!transitionOfCurrentState.equals(NO_TRANSITION) || i==characters.length){
            if(!currentStateTuple.epsilonClosureTaken){
                for(String stateLabel : states.get(currentStateTuple.getStateLabel()).epsilonClosure()){
                    epsilonClosureTaken = stateLabel == (currentStateTuple.stateLabel);
                    tempTuple = new Tuple(stateLabel, i, epsilonClosureTaken);
                    stack.add(tempTuple);
                }
            }
            
                i++;
                for(String stateLabel : transitionOfCurrentState){
                    epsilonClosureTaken = stateLabel == (currentStateTuple.stateLabel);
                    Tuple temp = new Tuple(stateLabel, i, epsilonClosureTaken);
                    stack.add(temp);
                }
            }  
            else{                
                for(j = i; stack.get(j).getIndex() == stack.get(j-1).getIndex();j--)
                    stack.remove(j);
                stack.remove(j--);
                i=j;
            }



            System.out.printf("i : %d\nchar : %c\nStack: ",i-1,characters[i-1]);
            for(Tuple item : stack){
                System.out.printf("(%s, %d) , ",item.getStateLabel(),item.getIndex());
            }
            System.out.println();

            if(i == stringLength && i == currentStateTuple.index && 
                states.get(currentStateTuple.stateLabel).getIsFinalState()){
                    return true;
            }
        }

        return false;
    }

    public static void main(String[] args){

        HashMap<Character,List<String>> transition0 = new HashMap<>();
        transition0.put('a', new ArrayList<>(Arrays.asList("q0")));
        transition0.put('\0', new ArrayList<>(Arrays.asList("q1")));
        NfaState q0 = new NfaState("q0", true, transition0);
        
        HashMap<Character,List<String>> transition1 = new HashMap<>();
        transition0.put('b', new ArrayList<>(Arrays.asList("q1")));
        NfaState q1 = new NfaState("q1", true, transition1);
        
        HashMap<String,NfaState> states = new HashMap<>();
        states.put("q0", q0);
        states.put("q1", q1);

        List<Character> alphabets = new ArrayList<>();
        alphabets.add('a');
        alphabets.add('b');
        alphabets.add('\0');

        NFA nfa = new NFA("NFA_1", states, alphabets, "q0");
    
        System.out.println(nfa.evaluate("aabb"));
    }
}
