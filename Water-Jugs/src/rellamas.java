import java.util.*;
public class rellamas {
    static int X_CAPACITY; // Capacity of Jug X
    static int Y_CAPACITY; // Capacity of Jug Y
    static int Z_CAPACITY; // Capacity of Jug Z
    static int TARGET = 2; // Goal: Measure 4 liters

    public static void main(String[] args) {
        X_CAPACITY = Integer.parseInt(args[0]);
        Y_CAPACITY = Integer.parseInt(args[1]);
        Z_CAPACITY = Integer.parseInt(args[2]);
        int jugLevel1 = Integer.parseInt(args[3]);
        int jugLevel2 = Integer.parseInt(args[4]);
        int jugLevel3 = Integer.parseInt(args[5]);
        solve3Jug(jugLevel1, jugLevel2, jugLevel3);
    }

    static void solve3Jug(int jugLevel1, int jugLevel2, int jugLevel3) {
        Queue<State> queue = new LinkedList<>();
        Set<String> frontier = new HashSet<>();

        String intialStateString = "Start: (" + jugLevel1 + ", "
                + jugLevel2 + ", "
                + jugLevel3 + ")";
        State initialState = new State(jugLevel1, jugLevel2 , jugLevel3, List.of(intialStateString));
        queue.add(initialState);
        frontier.add(jugLevel1 + ", " + jugLevel2 + ", " + jugLevel3);

        while (!queue.isEmpty()) {
            State currentState = queue.poll();
            int x = currentState.jugLevel1, y = currentState.jugLevel2, z = currentState.jugLevel3;

            // Check if reached target amount (all jugs)
            if (x == TARGET || y == TARGET || z == TARGET) {
                System.out.println("Solution Found!");
                currentState.seq.forEach(System.out::println);
                return;
            }

            // Get all next states
            List<State> nextStates = generateNextStates(currentState);

            for (State nextState : nextStates) {
                String stateKey = nextState.jugLevel1 + "," + nextState.jugLevel2 + "," + nextState.jugLevel3;
                if (!frontier.contains(stateKey)) {
                    frontier.add(stateKey);
                    queue.add(nextState);
                }
            }
        }

        System.out.println("No solution found.");
    }

    static List<State> generateNextStates(State currentState) {
        int x = currentState.jugLevel1, y = currentState.jugLevel2, z = currentState.jugLevel3;
        List<State> nextStates = new ArrayList<>();

        // All possible pour operations
        addNextState(nextStates, currentState, x - Math.min(x, Y_CAPACITY - y), y + Math.min(x, Y_CAPACITY - y), z, "Pour X → Y");
        addNextState(nextStates, currentState, x - Math.min(x, Z_CAPACITY - z), y, z + Math.min(x, Z_CAPACITY - z), "Pour X → Z");
        addNextState(nextStates, currentState, x + Math.min(y, X_CAPACITY - x), y - Math.min(y, X_CAPACITY - x), z, "Pour Y → X");
        addNextState(nextStates, currentState, x, y - Math.min(y, Z_CAPACITY - z), z + Math.min(y, Z_CAPACITY - z), "Pour Y → Z");
        addNextState(nextStates, currentState, x + Math.min(z, X_CAPACITY - x), y, z - Math.min(z, X_CAPACITY - x), "Pour Z → X");
        addNextState(nextStates, currentState, x, y + Math.min(z, Y_CAPACITY - y), z - Math.min(z, Y_CAPACITY - y), "Pour Z → Y");

        // Emptying jugs
        addNextState(nextStates, currentState, 0, y, z, "Empty X");
        addNextState(nextStates, currentState, x, 0, z, "Empty Y");
        addNextState(nextStates, currentState, x, y, 0, "Empty Z");

        // Filling jugs
        addNextState(nextStates, currentState, X_CAPACITY, y, z, "Fill X");
        addNextState(nextStates, currentState, x, Y_CAPACITY, z, "Fill Y");
        addNextState(nextStates, currentState, x, y, Z_CAPACITY, "Fill Z");

        return nextStates;
    }

    static void addNextState(List<State> nextStates, State currentState, int newX, int newY, int newZ, String action) {
        if (newX != currentState.jugLevel1 || newY != currentState.jugLevel2 || newZ != currentState.jugLevel3) { // Ensure state changes
            List<String> newSeq = new ArrayList<>(currentState.seq);
            newSeq.add(action + ": (" + newX + "," + newY + "," + newZ + ")");
            nextStates.add(new State(newX, newY, newZ, newSeq));
        }
    }
}