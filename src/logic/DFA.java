package logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public class DFA {
    private String label;
    private String initialState;
    private List<Character> alphabets;
    private HashMap<String,State> states;

    public DFA(String label, HashMap<String,State> states, List<Character> alphabets, String initialState){
        this.label = label;
        this.states = states;
        this.alphabets = alphabets;
        this.initialState = initialState;
    }

    public String getLabel(){
        return label;
    }

    public void listTransitionFunction(){
        System.out.println("   a  b");
        for(String stateLabel : states.keySet()){
            HashMap<Character,String> transitions = states.get(stateLabel).getTransitions();
            System.out.print(stateLabel + "  ");
            for(Character key : transitions.keySet()){
                System.out.print(transitions.get(key) + "  ");
            }
            System.out.println();
        }
    }

    public List<String> generateStrings(int maxLength){

        List<String> strings = new ArrayList<>();
        strings.add("");

        List<String> previousStrings= new ArrayList<>();
        previousStrings.add("");

        for(int i = 0; i < maxLength; i++){    
            List<String> temp = new ArrayList<>(); 
            for(String string : previousStrings){
                for(Character ch : alphabets ){
                    strings.add(string+Character.toString(ch));
                    temp.add(string+Character.toString(ch));
                }
            }        
            previousStrings = temp;
        }
        return strings;
    }   

    public boolean evaluate(String string){
        char[] characters = string.toCharArray();

        State currentState = states.get(initialState);
        String newStateLabel = "init";
        for(char ch : characters){
            newStateLabel = currentState.evaluate(ch);
            currentState = states.containsKey(newStateLabel) ? states.get(newStateLabel) : null;
        }

        return currentState.getIsFinalState();
    }

    public LinkedHashMap<String,Boolean> bulkEvaluate(int maxLength){
        
        LinkedHashMap<String,Boolean> output = new LinkedHashMap<>();
        List<String> strings = generateStrings(maxLength);
        
        for(String string : strings){
            output.put(string, evaluate(string));
        }

        return output;
    }

    public void addState(State state){
        states.put(state.getLabel(),state);
    }

    public void removeState(String stateLabel){
        if (states.containsKey(stateLabel)) states.remove(stateLabel);
    }
}
