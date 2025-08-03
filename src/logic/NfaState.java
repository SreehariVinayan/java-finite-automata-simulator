package logic;

import java.util.HashMap;
import java.util.List;

public class NfaState {

    private String label;
    private boolean isFinalState;
    private HashMap<Character,List<String>> transitions;

    public NfaState(String label, boolean isFinalState, HashMap<Character,List<String>> transitions){
        this.label = label;
        this.transitions = transitions;
        this.isFinalState = isFinalState;
    }

    public String getLabel(){
        return label;
    }

    public HashMap<Character,List<String>> getTransitions(){
        HashMap<Character,List<String>> clone = new HashMap<>();
        for(Character key : transitions.keySet()){
            clone.put(key, transitions.get(key));
        }
        return clone;
    }

    public boolean getIsFinalState(){
        return isFinalState;
    }

    public List<String> evaluate(char ch){
        return transitions.get(ch);
    }

}
