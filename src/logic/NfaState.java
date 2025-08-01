package logic;

import java.util.ArrayList;
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
        return transitions;
    }

    public boolean getIsFinalState(){
        return isFinalState;
    }

    public List<String> evaluate(char ch){
        return transitions.get(ch);
    }

    public List<String> epsilonClosure(){
        List<String> epsilonClosure = new ArrayList<>();
        epsilonClosure.add(label);
        if(transitions.isEmpty()){
            return new ArrayList<>();
        }
        for(String stateLabel : transitions.get('\0'))
            epsilonClosure.add(stateLabel);
        return epsilonClosure;
    }

}
