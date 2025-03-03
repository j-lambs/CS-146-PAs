import java.util.*;
import java.util.stream.Collectors;

public class WaterJug {
    ArrayList<Integer> m_cap;
    ArrayList<Integer> m_water;
    ArrayList<State>   m_queue;

	/**
		Collect states at level (step) i = 1,2,3 ... 
		@return ArrayList <ArrayList<State>> 
    */
    private ArrayList <ArrayList<State>> collectStateAtEachStep () {
      ArrayList <ArrayList<State>> rows = new ArrayList <> ();
      int i = 0;
      ArrayList<State> tmp =  new ArrayList <> (); ;
      for (State s: m_queue) {
        if (s.getLevel()!= i ) {
            rows.add (tmp);
            tmp = new ArrayList <> ();
            i = s.getLevel();
          }
          tmp.add ( s );
        }
        if ( tmp.size() > 0 )
        rows.add(tmp);
        return rows;
    }

    String title() {
        String f = "capacity: {'Jug A': %d, 'Jug B': %d, 'Jug C': %d}.\n";
        String s1 = String.format (f,m_cap.get(0), m_cap.get(1), m_cap.get(2) );
        f = "Initial amount of water: {'Jug A': %d, 'Jug B': %d, 'Jug C': %d}.\n";
        String s2 = String.format (f, m_water.get(0), m_water.get(1), m_water.get(2) );
        String s3 = "Step: New Measurements:  New States\n";
        String s4 = "----:-----------------:----------------------------------------\n";
       return s1+s2+s3+s4;
    }

    @Override
    public String toString() {
       String ans = title();

      TreeSet <Integer> set = new TreeSet<> ();
      int i = 0 ;
       for ( ArrayList<State> r: collectStateAtEachStep() ) {
          TreeSet<Integer>  old = new TreeSet< > (set );
          for (State s: r) {
              set.addAll ( s ) ;
          }
          TreeSet<Integer> newSet = new TreeSet< > (set);
          newSet.removeAll ( old );
          String newMeasurement = newSet.stream().map(String::valueOf).collect(Collectors.joining(", "));
          String rr = r.stream().map(x->x.toString() ).collect(Collectors.joining(", "));
          newMeasurement = MyString.center ( newMeasurement, 16, ' ');
          String f = "%4d: %s: %-20s\n";
          ans += String.format (f, i, newMeasurement, rr );
          ++i;
       }
      return ans;
    }

    public WaterJug(ArrayList<Integer> cap, ArrayList<Integer> water) {
         m_cap = new ArrayList<> ( cap );
         m_water = new ArrayList<> ( water );

         State root = new State ( water,0 ) ;

         m_queue = new ArrayList<> ( );
         m_queue.add(root);

    }

    State search (State chd) {
      for (State s: m_queue) {
        if (s.equals(chd)) return s;
      }
      return null;
    }
	/**
		Pour water from Jug i to Jug j (if possible)
		@param  current
		@param  i
		@param  j
	*/
    void pourWater (State current, int i, int j) {
      ArrayList<Integer> water = current;
      int level = current.getLevel();
      int x = Math.min ( water.get(i), m_cap.get(j)-water.get(j));
      if (x==0) return ;
      ArrayList<Integer> tmp  = new ArrayList<Integer> ( water );
      tmp.set (i, tmp.get(i)-x);
      tmp.set (j, tmp.get(j)+x);

      State chd = new State(tmp, level + 1);
      // System.out.print ("try to add " + chd + ", ");

      if (m_queue.contains(chd)) {
          State state = search(chd);
          if (state.getLevel() == current.getLevel() + 1) {
            state.addParent ( current); // update the list of parents
          }
        } else { // append the unique child state to the queue
          chd.addParent ( current);
          m_queue.add(chd);
      }
    }
    /**
    * Produce all unique child states from a given state 
    *
    * @param current 	The current state
    * @return void
    */
    public void produce (State current) {
        int level = current.getLevel();
        int n = current.size();     // number of water jugs
        for (int i=0; i < n; ++i ) {
          for (int j=0; j < n; ++j ) {
              ArrayList<Integer> water = current;
              int x = Math.min ( water.get(i), m_cap.get(j)-water.get(j));
              if (i!=j && x > 0 ) pourWater (current, i, j);
           }
        }
    }
    /**
      Produce all states reachable from the initial state
    */
    public void produceStates () {
        int i = 0;
        while ( i < m_queue.size() ) {
            State curr = m_queue.get(i++);
            produce(curr);
        }
    }

    /**
     * Makes pour string such as 'Pour water from jug A to jug B'
     * @param prev
     * @param next
     * @return
     */
    private String getPourDescription(State prev, State next) {
        for (int i = 0; i < prev.size(); i++) {
            for (int j = 0; j < prev.size(); j++) {
                if (i != j && prev.get(i) > next.get(i) && prev.get(j) < next.get(j)) {
                    String pourDescription = String.format("Pour water from jug %c to jug %c",
                            (char) ('A' + i), (char) ('A' + j));
                    return pourDescription;
                }
            }
        }
        return "null";
    }

    /**
     * Checks if ancestors of parameter, state, contain targetCapacity
     * @param state
     * @param targetCapacity
     * @return
     */
    private boolean ancestorsContainTargetCapacity(State state, int targetCapacity) {
        for (State parent : state.getParents()) {
            if (parent.contains(targetCapacity) || ancestorsContainTargetCapacity(parent, targetCapacity)) {
                return true; }
        }
        return false;
    }

    /**
     * Print Number of solutions to given problem and show the shortest sequence for each solution
     * @param targetCapacity
     */
    void printMeasurementSolution (int targetCapacity) {
        List<List<State>> shortestSolutions = new ArrayList<>();
        int minSeqLen = Integer.MAX_VALUE;

        // get all valid solutions of shortest length
        for (State state : m_queue) {
            if (state.contains(targetCapacity) && !ancestorsContainTargetCapacity(state, targetCapacity)) {
                List<List<State>> allPaths = getAllSolutionSequences(state);
                for (List<State> path : allPaths) {
                    int seqLen = path.size() - 1;
                    if (seqLen < minSeqLen) {
                        minSeqLen = seqLen;
                        shortestSolutions.clear();
                        shortestSolutions.add(path);
                    } else if (seqLen == minSeqLen) {
                        shortestSolutions.add(path);
                    }
                }
            }
        }
        System.out.printf("\nMeasure %d-unit water: %d solution(s)\n", targetCapacity, shortestSolutions.size());

        // print all solutions
        for (int i = 0; i < shortestSolutions.size(); i++) {
            List<State> seq = shortestSolutions.get(i);
            System.out.printf("Solution %d:\n", i + 1);
            for (int j = 0; j < seq.size(); j++) {
                State s = seq.get(j);
                System.out.print(s);
                if (j < seq.size() - 1) {
                    System.out.printf("    ---> %s\n", getPourDescription(seq.get(j), seq.get(j + 1)));
                } else {
                    System.out.println();
                }
            }
        }
	}

    /**
     * Returns sequences of all solutions
     * @param state
     * @return
     */
    private List<List<State>> getAllSolutionSequences(State state) {
        List<List<State>> allSequences = new ArrayList<>();
        LinkedList<State> path = new LinkedList<>();
        findAllPaths(state, path, allSequences);
        return allSequences;
    }

    /**
     * Backtrack through all shortest paths
     * @param current
     * @param path
     * @param allSequences
     */
    private void findAllPaths(State current, LinkedList<State> path, List<List<State>> allSequences) {
        path.addFirst(current);

        if (current.getParents().isEmpty()) {
            // Reached initial state, add full sequence as solution
            allSequences.add(new ArrayList<>(path));
        } else {
            for (State parent : current.getParents()) {
                findAllPaths(parent, path, allSequences);
            }
        }
        path.removeFirst();
    }


    /**

    */
    public void solve(){
        produceStates();

        int total = m_water.stream().mapToInt(Integer::intValue).sum();
        int max = Math.min ( total, Collections.max(m_cap) );

        System.out.print(max);
        for (int i=1; i <= max; ++i ) { // to measure i-unit water
			printMeasurementSolution(i) ;
        }
    }
}
