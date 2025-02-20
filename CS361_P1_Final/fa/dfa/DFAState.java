package fa.dfa;

import fa.State;
import java.util.HashMap;
import java.util.Map;

/**
 * A state in a DFA.
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
