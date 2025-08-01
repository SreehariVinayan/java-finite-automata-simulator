package logic;

import java.util.HashMap;

public class State{
    private String label;
    private boolean isFinalState;
    private HashMap<Character,String> transitions;

    public State(String label, boolean isFinalState, HashMap<Character,String> transitions){
        this.label = label;
        this.transitions = transitions;
        this.isFinalState = isFinalState;
    }

    public String getLabel(){
        return label;
    }

    public HashMap<Character,String> getTransitions(){
        return transitions;
    }

    public boolean getIsFinalState(){
        return isFinalState;
    }

    public String evaluate(char ch){
        return transitions.get(ch);
    }
}