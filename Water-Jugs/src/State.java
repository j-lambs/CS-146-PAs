import java.util.*;
public class State {
    int jugLevel1;
    int jugLevel2;
    int jugLevel3;
    List<String> seq; // Stores the sequence of levels of water jugs

    public State(int jugLevel1, int jugLevel2, int jugLevel3, List<String> seq) {
        this.jugLevel1 = jugLevel1;
        this.jugLevel2 = jugLevel2;
        this.jugLevel3 = jugLevel3;
        this.seq = new ArrayList<>(seq);
    }
}
