package fa.dfa;

import java.util.HashMap;
import java.util.Map;

import fa.State;

/*
 *  The logic for a state within a DFA for this project.
 *  This class focuses on managing state transitions -- this is done via a HashMap, using characters as the method to move to another DFAState.
 *  The class also extends the fa.State class, which is used to manage the name of the state, and the toString.
 *  @Author Sabastian Leeper, Parker Smith
 */

public class DFAState extends State {
    private Map<Character, DFAState> transitions;

    public DFAState(String name) {
        super(name);
        this.transitions = new HashMap<>();
    }

    public void addTransition(char symbol, DFAState toState) {
        transitions.put(symbol, toState);
    }

    public Map<Character, DFAState> getTransitions() {
        return transitions;
    }
}
