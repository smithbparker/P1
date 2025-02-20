package fa.dfa;

import fa.State;
import java.util.HashMap;
import java.util.Map;

/**
 * Represents a state in a Deterministic Finite Automaton (DFA).
 * Each state has a set of transitions that map input symbols to other DFA states.
 * This class extends the abstract {@link fa.State} class.
 * 
 * A DFA state maintains a mapping of input characters (from Sigma) 
 * to the next DFA state, ensuring that each input leads to a unique destination state.
 * 
 * @author [Parker and Sebastian]
 */
public class DFAState extends State {
    /** Stores transitions for this state: maps input symbols to destination states */
    private Map<Character, DFAState> transitions;

    /**
     * Constructs a DFA state with a given name.
     * The state starts with an empty set of transitions.
     *
     * @param name The name of the state.
     */
    public DFAState(String name) {
        super(name);
        this.transitions = new HashMap<>();
    }

    /**
     * Adds a transition from this state to another DFA state on a given input symbol.
     * 
     * @param symbol The input character that triggers the transition.
     * @param toState The destination state for this transition.
     */
    public void addTransition(char symbol, DFAState toState) {
        transitions.put(symbol, toState);
    }

    /**
     * Retrieves all transitions from this state.
     * 
     * @return A map of input symbols to their corresponding destination states.
     */
    public Map<Character, DFAState> getTransitions() {
        return transitions;
    }
}
