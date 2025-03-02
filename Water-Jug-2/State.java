import java.util.*;
class MyString {
    public static String center(String text, int length, char paddingChar) {
        if (text == null || length <= text.length()) {
            return text;
        }

        int paddingSize = length - text.length();
        int leftPadding = paddingSize / 2;
        int rightPadding = paddingSize - leftPadding;

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < leftPadding; i++) {
            sb.append(paddingChar);
        }
        sb.append(text);
        for (int i = 0; i < rightPadding; i++) {
            sb.append(paddingChar);
        }
        return sb.toString();
    }
}

class State extends ArrayList<Integer> {
    int m_level ;
    ArrayList<State> m_parents;

    public State (ArrayList<Integer> water, int level) {
        super (water);
        m_level = level;
        m_parents = new ArrayList<> ();
    }
    public int getLevel () { return m_level ; }

    public void addParent ( State par ) {
      m_parents.add (par);
    }
    public ArrayList<State> getParents () {
      return m_parents;
    }

    @Override
    public String toString () {
      String ans = "" + super.toString() ;
      int n = m_parents.size();
      if ( n>1 ) {
        ans += "p" + n;
      }
      return ans; //  + ", L" + m_level;
    }
}
