package logic;

public class Tuple {
    private String stateLabel;
    private int index;

    public Tuple(String stateLabel, int index){
        this.stateLabel = stateLabel;
        this.index = index;
    }

    public String getStateLabel() {
        return stateLabel;
    }

    public int getIndex() {
        return index;
    }
}
