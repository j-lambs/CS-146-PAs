import java.util.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class WaterJugTest {
    public static void main ( String[] args ) {
        List<String> stringList = Arrays.asList(args);

        int n = stringList.size();
		boolean ok = n==6;
		if ( !ok ) {
			String s = "The program must take 6 command line arguments ";
			s += "where the first 3 specify he capacities of jug A, B, and C";
			s += " and the rest the initial amount of water in these jugs.";
			System.out.println (s);
			System.exit(0);
		}
        List<Integer> input = null;

		try {
        	input = stringList.stream()
        	.map(Integer::parseInt)
        	.collect(Collectors.toList());
		} catch (IllegalArgumentException ex) {
			String s = "The 6 command line arguments must be integers.";
			System.out.println (s);
			System.exit(0);
		}

        List first = input.subList(0, n/2);
        List second = input.subList(n/2,n);

        ArrayList<Integer>  cap = new ArrayList<> (first);
        ArrayList<Integer>  water = new ArrayList<> (second);

        WaterJug puzzle = new WaterJug ( cap, water );
        puzzle.solve();
        System.out.println ( );
        System.out.println ( puzzle );
    }
}
