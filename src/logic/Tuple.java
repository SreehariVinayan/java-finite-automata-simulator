package logic;

public class Tuple {
    String stateLabel;
    int index;
    boolean epsilonClosureTaken;

    public Tuple(String stateLabel, int index, boolean epsilonClosureTaken){
        this.stateLabel = stateLabel;
        this.index = index;
        this.epsilonClosureTaken = epsilonClosureTaken;
    }

    public String getStateLabel() {
        return stateLabel;
    }

    public int getIndex() {
        return index;
    }

    public boolean isEpsilonClosureTaken(){
        return epsilonClosureTaken;
    }
}
