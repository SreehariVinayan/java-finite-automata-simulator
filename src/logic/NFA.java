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
                characters = new char[]{'#'};
            }

            if (i<characters.length && transitionsOfCurrentState.containsKey(characters[i])){
                for(String state : transitionsOfCurrentState.get(characters[i])){
                    transitions.add(state);
                    transitions.addAll(epsilonClosure(state));
                }
                i++;
            }

            if (transitionsOfCurrentState.containsKey('#')){
                epsilonTransition = epsilonClosure(currentStateTuple.getStateLabel());
                for(String state : epsilonTransition){
                    transitions.add(state);
                }
            }

            if(!transitions.isEmpty()){
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
            if(ch != '#')
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

    public static NFA defaultNfa(){
        
        HashMap<Character,List<String>> transition0 = new HashMap<>();
        transition0.put('#', new ArrayList<>(Arrays.asList("q1","q3")));
        NfaState q0 = new NfaState("q0", false, transition0);
        
        HashMap<Character,List<String>> transition1 = new HashMap<>();
        transition1.put('a', new ArrayList<>(Arrays.asList("q2")));
        NfaState q1 = new NfaState("q1", false, transition1);

        HashMap<Character,List<String>> transition2 = new HashMap<>();
        transition2.put('#', new ArrayList<>(Arrays.asList("q5")));
        NfaState q2 = new NfaState("q2", false, transition2);

        HashMap<Character,List<String>> transition3 = new HashMap<>();
        transition3.put('b', new ArrayList<>(Arrays.asList("q4")));
        NfaState q3 = new NfaState("q3", false, transition3);

        HashMap<Character,List<String>> transition4 = new HashMap<>();
        transition4.put('#', new ArrayList<>(Arrays.asList("q5")));
        NfaState q4 = new NfaState("q4", false, transition4);

        HashMap<Character,List<String>> transition5 = new HashMap<>();
        NfaState q5 = new NfaState("q5", true, transition5);
        
        HashMap<String,NfaState> states = new HashMap<>();
        states.put("q0", q0);
        states.put("q1", q1);
        states.put("q2", q2);
        states.put("q3", q3);
        states.put("q4", q4);
        states.put("q5", q5);

        List<Character> alphabets = new ArrayList<>();
        alphabets.add('a');
        alphabets.add('b');
        alphabets.add('#');

        return new NFA("NFA_1", states, alphabets, "q0");
    }

    public static void main(String[] args){

        NFA nfa = defaultNfa();
    
        LinkedHashMap<String,Boolean> result = nfa.bulkEvaluate(args.length > 0 ? Integer.parseInt(args[0]) : 3);        

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

    public String getLabel(){
        return label;
    }

    List<String> epsilonClosure(String state){
        List<String> epsilonClosure = new ArrayList<>();
        
        List<String> epsilonClosureOfAState =states.get(state).getTransitions().get('#');
        
        if(epsilonClosureOfAState != null)
        for(String stateLabel : epsilonClosureOfAState)
            epsilonClosure.add(stateLabel);

        List<String> temp = new ArrayList<>();

        if(epsilonClosure != null)
        for(String stateLabel : epsilonClosure){
            temp.addAll(epsilonClosure(stateLabel));
        }

        if(temp != null)
        epsilonClosure.addAll(temp);

        return epsilonClosure;
    }
}

